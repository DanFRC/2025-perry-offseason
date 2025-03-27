package frc.robot.Subsystems.superstructure.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    // Hardware
    private final VictorSPX _motor1 = new VictorSPX(ArmConstants.kArmPivotMotorPort);
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(ArmConstants.kEncoderPort);

    // Control Systems
    TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 2));
    TrapezoidProfile.State _setpoint = new TrapezoidProfile.State();
    TrapezoidProfile.State _goal = new TrapezoidProfile.State();

    ArmFeedforward feedforward = new ArmFeedforward(
        ArmConstants.ks,
        ArmConstants.kg,
        ArmConstants.kv,
        ArmConstants.ka
    );

    PIDController pid = new PIDController(
        ArmConstants.kP,
        ArmConstants.kI,
        ArmConstants.kD
    );


    public Arm() {
        _motor1.enableVoltageCompensation(true);
    }

    // Funcs
    public void setSetpoint(double returnedPoint) {
        _goal = new TrapezoidProfile.State(returnedPoint, 0);
    }

    public static double convertToRadians(double input) {
        // Linear mapping: When input = 1/6, degrees = 90; when input = 2/3, degrees = -90.
        double degrees = -360 * input + 150;
        return Math.toRadians(degrees);
    }    

    public void init() {
        pid.reset();
    }

    @Override
    public void periodic() {
        double feedforwardOutput = feedforward.calculate(convertToRadians(encoder.get()),_setpoint.velocity);
        _setpoint = profile.calculate(
          ArmConstants.kDt, 
          _setpoint, 
          _goal
        );

        _motor1.set(ControlMode.PercentOutput, feedforwardOutput);

        // Compare in Advantage Scope
        SmartDashboard.putNumber("encoder Arm", encoder.get());
        SmartDashboard.putNumber("Arm Feed Forward OUtput", feedforwardOutput);
        SmartDashboard.putNumber("Arm Profile", _setpoint.position);
    }

    @Override
    public void simulationPeriodic() {
    }
}