package frc.robot.Commands.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Elevator.ElevatorSubsystem;

public class a_setElevatorPos extends Command {

  private final ElevatorSubsystem _elevator;
  private double point;

  private boolean finished = false;

  public a_setElevatorPos(ElevatorSubsystem elevatorObject, double Givenpoint) {
    _elevator = elevatorObject;
    point = Givenpoint;
  }

  @Override
  public void initialize() {
    setElevatorPoint(point);
    finished = false;
  }

  public void setElevatorPoint(double gimmePoint) {
    double setpoint = 0;
    SmartDashboard.putNumber("Commanded Point", point);
    if (point == 0) { // Neutral
      setpoint = ElevatorConstants.kElevatorNeutral;
    } else if (point == 4) { // L4
      setpoint = ElevatorConstants.kElevatorDeadZoneMax;
    } else if (point == 3) { // L3
      setpoint = ElevatorConstants.kElevatorMid;
    } else if (point == 2) { // L2
      setpoint = ElevatorConstants.kElevatorLow;
    } else if (point == 5) { // Algae
      setpoint = ElevatorConstants.kElevatorMid;
    } else {
      setpoint = ElevatorConstants.kElevatorNeutral;
    }
    SmartDashboard.putNumber("Commanded Setpoint", setpoint);
    _elevator.setSetpoint(setpoint);
    
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
