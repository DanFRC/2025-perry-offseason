// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.a_setElevatorPos;
import frc.robot.Subsystems.Drivebase.DrivebaseSubsystem;
import frc.robot.Subsystems.Elevator.ElevatorSubsystem;

public class RobotContainer {
  public RobotContainer() {
    configureBindings();
  }
  
  // Systems
  private final DrivebaseSubsystem drivebaseSubsystem = new DrivebaseSubsystem();
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();

  private final CommandXboxController _operator = new CommandXboxController(0);

  private void configureBindings() {

    // Subsystem Inits

    // // non FMS Init
    // Trigger noFMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isTeleop() && !DriverStation.isFMSAttached());
    // noFMSTrigger.onTrue(new SequentialCommandGroup(
    //   Commands.runOnce(
    //     () -> {
    //       elevatorSubsystem.init();
    //     }
    //   )
    // ));

    // // FMS Init
    // Trigger FMSTrigger = new Trigger(() -> RobotState.isEnabled() && RobotState.isAutonomous() && DriverStation.isFMSAttached());
    // FMSTrigger.onTrue(new SequentialCommandGroup(
    //   Commands.runOnce(
    //     () -> {
    //       elevatorSubsystem.init();
    //     }
    //   )
    // ));

    // Bindings
    _operator.a().whileTrue(new SequentialCommandGroup(
      new a_setElevatorPos(elevatorSubsystem, 16000)
    ));
    _operator.b().whileTrue(new SequentialCommandGroup(
      new a_setElevatorPos(elevatorSubsystem, 10000)
    ));
    _operator.x().whileTrue(new SequentialCommandGroup(
      new a_setElevatorPos(elevatorSubsystem, 0)
    ));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
