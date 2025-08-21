package frc.robot.Commands.superstructure.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.superstructure.elevator.Elevator;
import frc.robot.Subsystems.superstructure.elevator.ElevatorConstants;

public class SetElevator extends Command {

  private final Elevator _elevator;
  private double point;

  private boolean finished = false;

  public SetElevator(Elevator elevatorObject, double Givenpoint) {
    _elevator = elevatorObject;
    point = Givenpoint;

    addRequirements(_elevator);
  }

  @Override
  public void initialize() {
    _elevator.runabit(false);
    setElevatorPoint(point);
    finished = false;
  }

  public void setElevatorPoint(double gimmePoint) {
    double setpoint = 0;
    SmartDashboard.putNumber("Commanded Point", point);
    if (point == 0) { // Neutral
      setpoint = ElevatorConstants.kNeutral;
    } else if (point == 4) { // L4
      setpoint = ElevatorConstants.kL4;
    } else if (point == 3) { // L3
      setpoint = ElevatorConstants.kL3;
    } else if (point == 2) { // L2
      setpoint = ElevatorConstants.kL2;
    } else if (point == 5) { // Algae
      setpoint = ElevatorConstants.kL3;
    }
    else if (point == 6) { // Intak2
      setpoint = 7350;
    } else {
      setpoint = ElevatorConstants.kNeutral;
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
