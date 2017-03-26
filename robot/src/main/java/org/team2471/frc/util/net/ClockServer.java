package org.team2471.frc.util.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class ClockServer {
  public static void spawnClockServer(int port) {
    new Thread(() -> {
      try {
        DatagramSocket socket = new DatagramSocket(port);
        //noinspection InfiniteLoopStatement
        while(true) {
          DatagramPacket packet = new DatagramPacket(new byte[4], 4);
          try {
            socket.receive(packet);

            long time = System.currentTimeMillis();
            ByteBuffer timeBuffer = ByteBuffer.allocate(Long.BYTES);
            timeBuffer.putLong(time);
            packet.setData(timeBuffer.array());
            socket.send(packet);
          } catch (IOException e) {
            System.out.println(e);
          }
        }
      } catch (SocketException e) {
        System.out.println(e);
      }
    }).start();
  }
}
