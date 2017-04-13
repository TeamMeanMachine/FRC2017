package org.team2471.frc.steamworks;

import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.team2471.frc.lib.io.dashboard.DashboardUtils;

@SuppressWarnings("InfiniteLoopStatement")
public class CameraFeed {
  public CameraFeed() {
    final int width = 640;
    final int height = 480;
    final int fps = 30;

    final UsbCamera camera = new UsbCamera("Camera", 0);
    final MjpegServer server = new MjpegServer("Camera Feed", 1081);

    camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, width, height, fps);

    final CvSink cvSink = new CvSink("cvsink");

    cvSink.setSource(camera);

    final CvSource processedSource = new CvSource("processedSource", VideoMode.PixelFormat.kUnknown, width, height, fps);
    server.setSource(processedSource);

    DashboardUtils.putPersistentNumber("Camera Exposure", 20);
    DashboardUtils.putPersistentNumber("Camera Brightness", 50);

    Mat image = new Mat();
    new Thread(() -> {
      int i = 0;
      while (true) {
        long time = cvSink.grabFrame(image);

        if (time == 0) {
          continue;
        }

        Imgproc.line(image, new Point(width / 2, 0), new Point(width / 2, height), new Scalar(0, 255, 255), 2);
        processedSource.putFrame(image);

        if(i % 30 == 0) {
          camera.setExposureManual((int) SmartDashboard.getNumber("Camera Exposure", 20));
          camera.setBrightness((int) SmartDashboard.getNumber("Camera Brightness", 50));
        }
      }
    }).start();
  }


}
