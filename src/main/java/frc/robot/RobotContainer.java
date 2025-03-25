// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.Elevator.SetElevator;
import frc.robot.Subsystems.superstructure.elevator.ElevatorSubsystem;

public class RobotContainer {
  public RobotContainer() {
    configureBindings();
  }
  
  // Systems
  //private final DrivebaseSubsystem drivebaseSubsystem = new DrivebaseSubsystem();
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();

  private final CommandXboxController _operator = new CommandXboxController(0);

  private void configureBindings() {

    //Subsystem Inits

    // non FMS Init
    Trigger noFMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isTeleop() && !DriverStation.isFMSAttached());
    noFMSTrigger.onTrue(new SequentialCommandGroup(
      Commands.runOnce(
        () -> {
          elevatorSubsystem.init();
        }
      )
    ));

    // FMS Init
    Trigger FMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isAutonomous() && DriverStation.isFMSAttached());
    FMSTrigger.onTrue(new SequentialCommandGroup(
      Commands.runOnce(
        () -> {
          elevatorSubsystem.init();
        }
      )
    ));

    // L4
    _operator.y().whileTrue(new SequentialCommandGroup(
      new SetElevator(elevatorSubsystem, 4)
    ));
    // L3
    _operator.x().whileTrue(new SequentialCommandGroup(
      new SetElevator(elevatorSubsystem, 3)
    ));
    // L2
    _operator.a().whileTrue(new SequentialCommandGroup(
    new SetElevator(elevatorSubsystem, 2)
    ));
    // Neutral
    _operator.b().whileTrue(new SequentialCommandGroup(
      new SetElevator(elevatorSubsystem, 0)
    ));
    // // Barge
    // _operator.povUp().onTrue(new SequentialCommandGroup(
    //   new a_setElevatorPos(elevatorSubsystem, 5)
    // ));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
