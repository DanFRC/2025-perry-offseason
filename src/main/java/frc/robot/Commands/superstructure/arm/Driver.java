package frc.robot.Commands.superstructure.arm;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.drivebase.Drivebase;

public class Driver extends Command {

  private final Drivebase _arm;
  private double point;

  CommandXboxController utterorphan;

  private boolean finished = false;

  public Driver(Drivebase armObject, CommandXboxController orphanator) {
    _arm = armObject;
    utterorphan = orphanator;

    addRequirements(armObject);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    _arm.drive(utterorphan.getLeftX(), -utterorphan.getLeftY(), utterorphan.getRightX(), false, true);
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
