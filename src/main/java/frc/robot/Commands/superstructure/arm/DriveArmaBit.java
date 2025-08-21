package frc.robot.Commands.superstructure.arm;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.superstructure.arm.Arm;
import frc.robot.Subsystems.superstructure.arm.ArmConstants;

public class DriveArmaBit extends Command {

  private final Arm _arm;
  private double point;

  private boolean finished = false;

  public DriveArmaBit(Arm armObject) {
    _arm = armObject;
    

    addRequirements(armObject);
  }

  @Override
  public void initialize() {
    _arm.runabit(true);
    finished = false;
  }

  public void setElevatorPoint(double gimmePoint) {

  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    _arm.runabit(false);
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
