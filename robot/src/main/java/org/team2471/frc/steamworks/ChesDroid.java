package org.team2471.frc.steamworks;

import com.team254.frc2016.vision.TargetInfo;
import com.team254.frc2016.vision.VisionServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;
import java.util.Optional;

public class ChesDroid {
  private final VisionServer server = VisionServer.getInstance();

  private boolean receivedPacket = false;
  private Optional<VisionData> data = Optional.empty();

  public ChesDroid() {
    server.addVisionUpdateReceiver(update -> {
      receivedPacket = true;
      List<TargetInfo> targets = update.getTargets();
      if(targets.size() == 0) {
        data = Optional.empty();
      } else {
        TargetInfo target = targets.get(0);
        data = Optional.of(new VisionData(target.getY(), target.getZ()));
        data.ifPresent(visionData -> System.out.println(visionData.error));
      }
    });
  }

  public boolean hasReceivedPacket() {
    return receivedPacket;
  }

  public boolean isConnected() {
    return server.isConnected();
  }

  public Optional<VisionData> getData() {
    return data;
  }

  public class VisionData {
    public final double error;
    public final double distance;

    private VisionData(double error, double distance) {
      this.error = error * 59;
      this.distance = distance;
      SmartDashboard.putNumber("Boiler Error", this.error);
      SmartDashboard.putNumber("Boiler Distance", this.distance);
    }
  }
}
