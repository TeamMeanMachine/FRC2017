package org.team2471.frc.steamworks;

import com.team254.frc2016.vision.TargetInfo;
import com.team254.frc2016.vision.VisionServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;
import java.util.Optional;

public class ChesDroid {
  private final VisionServer server = VisionServer.getInstance();

  private boolean receivedPacket = false;
  private Optional<Double> error = Optional.empty();

  public ChesDroid() {
    server.addVisionUpdateReceiver(update -> {
      receivedPacket = true;
      List<TargetInfo> targets = update.getTargets();
      if(targets.size() == 0) {
        error = Optional.empty();
      } else {
        TargetInfo target = targets.get(0);
        SmartDashboard.putNumber("Target X", target.getX());
        SmartDashboard.putNumber("Target Y", target.getY());
        SmartDashboard.putNumber("Target Z", target.getZ());
        error = Optional.of(target.getZ());
      }
    });
  }

  public boolean hasReceivedPacket() {
    return receivedPacket;
  }

  public Optional<Double> getError() {
    return error;
  }
}
