package frc.robot.Commands.superstructure.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.superstructure.elevator.Elevator;
import frc.robot.Subsystems.superstructure.elevator.ElevatorConstants;

public class DriveaTad extends Command {

  private final Elevator _elevator;
  private double point;

  private boolean finished = false;

  public DriveaTad(Elevator elevatorObject) {
    _elevator = elevatorObject;

    addRequirements(_elevator);
  }

  @Override
  public void initialize() {
    _elevator.runabit(true);
    finished = false;
  }

  @Override
  public void execute() {
    
  }

  @Override
  public void end(boolean interrupted) {
    _elevator.runabit(false);
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
