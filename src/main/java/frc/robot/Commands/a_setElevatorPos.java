package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Elevator.ElevatorSubsystem;

public class a_setElevatorPos extends Command {

  private final ElevatorSubsystem _elevator;
  private double point;

  public a_setElevatorPos(ElevatorSubsystem elevatorObject, double Givenpoint) {
    _elevator = elevatorObject;
    point = Givenpoint;
  }

  @Override
  public void initialize() {
    SmartDashboard.putNumber("The Shig", 1);
    _elevator.setSetpoint(point);
    
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
