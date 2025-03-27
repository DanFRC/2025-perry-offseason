// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.superstructure.arm.SetArm;
import frc.robot.Commands.superstructure.elevator.SetElevator;
import frc.robot.Subsystems.drivebase.Drivebase;
import frc.robot.Subsystems.superstructure.arm.Arm;
import frc.robot.Subsystems.superstructure.elevator.Elevator;

public class RobotContainer {
  public RobotContainer() {
    configureBindings();
  }
  
  // Systems
  private final Drivebase drivebaseSubsystem = new Drivebase();
  private final Elevator elevatorSubsystem = new Elevator();
  private final Arm armSubsystem = new Arm();

  private final CommandXboxController _operator = new CommandXboxController(0);

  private void configureBindings() {

    //Subsystem Inits

    // non FMS Init
    Trigger noFMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isTeleop() && !DriverStation.isFMSAttached());
    noFMSTrigger.onTrue(new SequentialCommandGroup(
      Commands.runOnce(
        () -> {
          elevatorSubsystem.init();
          armSubsystem.init();
        }
      )
    ));

    // FMS Init
    Trigger FMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isAutonomous() && DriverStation.isFMSAttached());
    FMSTrigger.onTrue(new SequentialCommandGroup(
      Commands.runOnce(
        () -> {
          elevatorSubsystem.init();
          armSubsystem.init();
        }
      )
    ));

    // L4
    _operator.y().whileTrue(new ParallelCommandGroup(
      new SetElevator(elevatorSubsystem, 4),
      new SetArm(armSubsystem, 4)
    ));
    // L3
    _operator.x().whileTrue(new ParallelCommandGroup(
      new SetElevator(elevatorSubsystem, 3),
      new SetArm(armSubsystem, 3)
    ));
    // L2 - L1
    _operator.a().whileTrue(new ParallelCommandGroup(
    new SetElevator(elevatorSubsystem, 2),
    new SetArm(armSubsystem, 2)
    ));
    // Neutral
    _operator.b().whileTrue(new ParallelCommandGroup(
      new SetElevator(elevatorSubsystem, 0),
      new SetArm(armSubsystem, 0)
    ));
    // Barge
    _operator.povDown().onTrue(new ParallelCommandGroup(
      new SetElevator(elevatorSubsystem, 5),
      new SetArm(armSubsystem, 5)
    ));

    // Set Side
    _operator.rightBumper().whileTrue(new ParallelCommandGroup(
      Commands.run(() -> {
        if (_operator.getLeftX() > 0.5) {
          drivebaseSubsystem.setReefSide("right");
        } else if (_operator.getLeftX() < -0.5) {
          drivebaseSubsystem.setReefSide("left");
        }
      }
    )));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}