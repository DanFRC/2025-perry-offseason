// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class FieldConstants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ArmPivotConstants {
    public static final int kArmPivotMotorPort = 3;
    public static final int kEncoderPort = 1;

    public static final double kArmPivotDeadZoneMax = 0.166;
    public static final double kArmPivotDeadZoneMin = 0.666;

    public static final double kArmPivotMid = 0.24;
    public static final double kArmPivotLow = 0.24;
    public static final double kArmPivotDropper = 0.3;
    public static final double kArmPivotHigh = 0.225;
    public static final double kArmPivotNeutral = 0.64;
    public static final double kArmPivotHighScore = 0.31;
    public static final double kArmPivotAlgae = 0.4;

    public static double kP = 3.25;
    public static double kI = 2.25;
    public static double kD = 0.89;
    // public static double kP = 2.25;
    // public static double kI = 3;
    // public static double kD = 0.6;
  }

  public static class ButtonBoxConstants {
    public static final int kL4_L_Button = 8;
    public static final int kL4_R_Button = 7;

    public static final int kL3_L_Button = 10;
    public static final int kL3_R_Button = 11;

    public static final int kL2_L_Button = 5;
    public static final int kL2_R_Button = 6;

    public static final int kALG_34 = 12;
    public static final int kALG_23 = 9;
  }

  public static class AprilTagConstants {
    public static final double kCORAL_STATION_X = 1.5;
    public static final double kCORAL_STATION_Y = 0;
  }
}
