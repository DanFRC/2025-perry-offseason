package frc.robot.Commands.superstructure.arm;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.superstructure.arm.Arm;
import frc.robot.Subsystems.superstructure.arm.ArmConstants;

public class SetArm extends Command {

  private final Arm _arm;
  private double point;

  private boolean finished = false;

  public SetArm(Arm armObject, double Givenpoint) {
    _arm = armObject;
    point = Givenpoint;
  }

  @Override
  public void initialize() {
    setElevatorPoint(point);
    finished = false;
  }

  public void setElevatorPoint(double gimmePoint) {
    double setpoint = 0;
    SmartDashboard.putNumber("Arm Commanded Point", point);
    if (point == 0) { // Neutral
      setpoint = ArmConstants.kNeutral;
    } else if (point == 4) { // L4
      setpoint = ArmConstants.kL4;
    } else if (point == 3) { // L3
      setpoint = ArmConstants.kL3;
    } else if (point == 2) { // L2
      setpoint = ArmConstants.kL2;
    } else if (point == 5) { // Algae
      setpoint = ArmConstants.kAlg;
    } else {
      setpoint = ArmConstants.kNeutral;
    }
    SmartDashboard.putNumber("Arm Commanded Setpoint", setpoint);
    _arm.setSetpoint(setpoint);
    
    finished = true;
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    finished = false;
  }

  @Override
  public boolean isFinished() {
    if (finished == true) {
      return true;
    } else {
      return false;
    }
  }
}
