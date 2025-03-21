package frc.robot.Commands.Elevator;

public class ElevatorConstants {
        public static final int kElevatorMotorPort = 4;
        public static final int kLimitSwitchPort = 0;
        public static final int kENC_A = 8;
        public static final int kENC_B = 9;
    
        public static double kP = 0.0005;
        public static double kI = 0.0005;
        public static double kD = 0.0002;
    
        public static final double kElevatorDeadZoneMax = 16050;
        public static final double kElevatorDeadZoneMin = 10;
    
        public static final double kElevatorMid = 8350;
        public static final double kElevatorLow = 2000;
        public static final double kElevatorDropper = 4000;
    
        public static final double kElevatorNeutral = 7800;
    
        public static final double kElevatorNoteUp = 7461;
        public static final double kElevatorNoteDown = 5800;
}
