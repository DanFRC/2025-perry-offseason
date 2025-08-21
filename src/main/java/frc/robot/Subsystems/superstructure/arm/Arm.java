package frc.robot.Subsystems.superstructure.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    // Hardware
    private final VictorSPX _motor1 = new VictorSPX(ArmConstants.kArmPivotMotorPort);
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(ArmConstants.kEncoderPort);
    private final SlewRateLimiter limiter = new SlewRateLimiter(.5);

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
        pid.setIZone(.4);
    }
    double pointi = 0;
    // Funcs
    public void setSetpoint(double returnedPoint) {
        pointi = returnedPoint;
    }

    public static double convertToRadians(double input) {
        // Linear mapping: When input = 1/6, degrees = 90; when input = 2/3, degrees = -90.
        double degrees = -360 * input + 150;
        return Math.toRadians(degrees);
    }    

    public void init() {
        pid.reset();
    }

    double runner = 0;

    public void runabit(boolean timmy) {
      if (timmy == true) {
        runner = 1;
      } else {
        runner = 0;
      }
    }

    @Override
    public void periodic() {

        if (runner == 1) {
            _motor1.set(ControlMode.PercentOutput, 0.5);
        } else if (runner == 0) {
            _motor1.set(ControlMode.PercentOutput, pid.calculate(encoder.get(), pointi));
        } else {
            _motor1.set(ControlMode.PercentOutput, 0);
        }

        
        // Compare in Advantage Scope
        SmartDashboard.putNumber("encoder Arm tist tist tist", encoder.get());
        SmartDashboard.putNumber("Arm Feed Forward OUtput", 0);
        SmartDashboard.putNumber("Arm Profile", _setpoint.position);
        SmartDashboard.putNumber("pos", pid.calculate(pointi));
    }

    @Override
    public void simulationPeriodic() {
    }
}