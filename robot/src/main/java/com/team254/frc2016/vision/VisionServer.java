package com.team254.frc2016.vision;

import com.team254.frc2016.Constants;
import com.team254.frc2016.vision.messages.HeartbeatMessage;
import com.team254.frc2016.vision.messages.OffWireMessage;
import com.team254.frc2016.vision.messages.VisionMessage;
import com.team254.lib.util.CrashTrackingRunnable;
import edu.wpi.first.wpilibj.Timer;
import org.team2471.frc.lib.io.log.LogLevel;
import org.team2471.frc.lib.io.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This controls all vision actions, including vision updates, capture, and
 * interfacing with the Android phone with Android Debug Bridge. It also stores
 * all VisionUpdates (from the Android phone) and contains methods to add
 * to/prune the VisionUpdate list. Much like the subsystems, outside methods get
 * the VisionServer instance (there is only one VisionServer) instead of
 * creating new VisionServer instances.
 *
 * @see VisionUpdate
 */

public class VisionServer extends CrashTrackingRunnable {

  private final Logger logger = new Logger("VisionServer");

  private static VisionServer s_instance = null;
  AdbBridge adb = new AdbBridge();
  double lastMessageReceivedTime = 0;
  private ServerSocket m_server_socket;
  private boolean m_running = true;
  private int m_port;
  private ArrayList<VisionUpdateReceiver> receivers = new ArrayList<>();
  private boolean m_use_java_time = false;

  private ArrayList<ServerThread> serverThreads = new ArrayList<>();
  private volatile boolean mWantsAppRestart = false;
  private boolean mIsConnect = false;

  private VisionServer(int port) {
//    logger.setLocalLogLevel(LogLevel.DEBUG);
    try {
      adb = new AdbBridge();
      m_port = port;
      m_server_socket = new ServerSocket(port);
      adb.start();
      adb.reversePortForward(port, port);
      try {
        String useJavaTime = System.getenv("USE_JAVA_TIME");
        m_use_java_time = "true".equals(useJavaTime);
      } catch (NullPointerException e) {
        m_use_java_time = false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Starting server thread");
    new Thread(this).start();
    new Thread(new AppMaintainanceThread()).start();
  }

  public static VisionServer getInstance() {
    if (s_instance == null) {
      s_instance = new VisionServer(Constants.kAndroidAppTcpPort);
    }
    return s_instance;
  }

  public boolean isConnected() {
    return mIsConnect;
  }

  public void requestAppRestart() {
    mWantsAppRestart = true;
  }

  public void restartAdb() {
    adb.restartAdb();
    adb.reversePortForward(m_port, m_port);
  }

  /**
   * If a VisionUpdate object (i.e. a target) is not in the list, add it.
   *
   * @see VisionUpdate
   */
  public void addVisionUpdateReceiver(VisionUpdateReceiver receiver) {
    if (!receivers.contains(receiver)) {
      receivers.add(receiver);
    }
  }

  public void removeVisionUpdateReceiver(VisionUpdateReceiver receiver) {
    if (receivers.contains(receiver)) {
      receivers.remove(receiver);
    }
  }

  @Override
  public void runCrashTracked() {
    logger.debug("Start server thread");
    while (m_running) {
      logger.debug("Running loop iteration");
      try {
        logger.debug("Wait for client");
        Socket p = m_server_socket.accept();
        logger.info("Client " + p.getLocalAddress().toString() + " connected.");
        ServerThread s = new ServerThread(p);
        new Thread(s).start();
        serverThreads.add(s);
      } catch (IOException e) {
        System.err.println("Issue accepting socket connection!");
      } finally {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private double getTimestamp() {
    if (m_use_java_time) {
      return System.currentTimeMillis();
    } else {
      return Timer.getFPGATimestamp();
    }
  }

  protected class ServerThread extends CrashTrackingRunnable {
    private Socket m_socket;

    public ServerThread(Socket socket) {
      m_socket = socket;
    }

    public void send(VisionMessage message) {
      String toSend = message.toJson() + "\n";
      if (m_socket != null && m_socket.isConnected()) {
        try {
          OutputStream os = m_socket.getOutputStream();
          os.write(toSend.getBytes());
        } catch (IOException e) {
          System.err.println("VisionServer: Could not send data to socket");
        }
      }
    }

    public void handleMessage(VisionMessage message, double timestamp) {
      if ("targets".equals(message.getType())) {
        VisionUpdate update = VisionUpdate.generateFromJsonString(timestamp, message.getMessage());
        receivers.removeAll(Collections.singleton(null));
        if (update.isValid()) {
          for (VisionUpdateReceiver receiver : receivers) {
            receiver.gotUpdate(update);
          }
        }
      }
      if ("heartbeat".equals(message.getType())) {
        send(HeartbeatMessage.getInstance());
      }
    }

    public boolean isAlive() {
      return m_socket != null && m_socket.isConnected() && !m_socket.isClosed();
    }

    @Override
    public void runCrashTracked() {
      if (m_socket == null) {
        return;
      }
      try {
        InputStream is = m_socket.getInputStream();
        byte[] buffer = new byte[2048];
        int read;
        while (m_socket.isConnected() && (read = is.read(buffer)) != -1) {
          double timestamp = getTimestamp();
          lastMessageReceivedTime = timestamp;
          String messageRaw = new String(buffer, 0, read);
          String[] messages = messageRaw.split("\n");
          for (String message : messages) {
            OffWireMessage parsedMessage = new OffWireMessage(message);
            if (parsedMessage.isValid()) {
              handleMessage(parsedMessage, timestamp);
            }
          }
        }
        System.out.println("Socket disconnected");
      } catch (IOException e) {
        System.err.println("Could not talk to socket");
      }
      if (m_socket != null) {
        try {
          m_socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private class AppMaintainanceThread extends CrashTrackingRunnable {
    @Override
    public void runCrashTracked() {
      while (true) {
        if (getTimestamp() - lastMessageReceivedTime > .1) {
          // camera disconnected
          adb.reversePortForward(m_port, m_port);
          mIsConnect = false;
        } else {
          mIsConnect = true;
        }
        if (mWantsAppRestart) {
          adb.restartApp();
          mWantsAppRestart = false;
        }
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
