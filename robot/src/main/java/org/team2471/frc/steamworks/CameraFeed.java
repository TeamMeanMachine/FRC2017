package org.team2471.frc.steamworks;

import edu.wpi.cscore.*;
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
    final int fps = 15;

    final UsbCamera camera = new UsbCamera("Camera", 0);
    final MjpegServer server = new MjpegServer("Backup Feed", 5805);

    camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, width, height, fps);

    final CvSink cvSink = new CvSink("cvsink");

    cvSink.setSource(camera);

    final CvSource processedSource = new CvSource("processedSource", VideoMode.PixelFormat.kMJPEG, width, height, fps);
    server.setSource(processedSource);

    DashboardUtils.putPersistentNumber("Camera Exposure", 20);

    Mat image = new Mat();
    new Thread(() -> {
      int i = 0;

      while(true) {
        long time = cvSink.grabFrame(image);

        if(time == 0) {
          continue;
        }

        Imgproc.line(image, new Point(width / 2, 0), new Point(width / 2, height), new Scalar(0, 255, 255), 2);
        processedSource.putFrame(image);

        if(i % 3 == 0) {
          camera.setExposureManual((int) SmartDashboard.getNumber("Camera Exposure", 20));
        }
        i++;
      }
    }).start();
  }


}
