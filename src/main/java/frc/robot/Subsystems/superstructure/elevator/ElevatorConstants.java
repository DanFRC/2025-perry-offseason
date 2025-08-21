package frc.robot.Subsystems.superstructure.elevator;

public class ElevatorConstants {
    public static final int kElevatorMotorPort = 2;
    public static final int kLimitSwitchPort = 0;
    public static final int kENC_A = 8;
    public static final int kENC_B = 9;

    public static double kP = 0.00018;
    public static double kI = 0.00004;
    public static double kD = 0.0;

    public static double kDt = 0.02;
    public static double ks = 0.00001;
    public static double kg = 0.175;
    public static double kv = 0.000075;
    public static double ka = 0.0000001;

    public static final double kElevatorDeadZoneMax = 16050;
    public static final double kElevatorDeadZoneMin = 10;

    public static final double kL4 = kElevatorDeadZoneMax;
    public static final double kL3 = 7850;
    public static final double kL2 = 2000;
    public static final double kL1 = 4000;

    public static final double kNeutral = 7850;

    public static final double kCoralUp = 7461;
    public static final double kCoralDown = 5800;
}
