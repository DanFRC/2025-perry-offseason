//

package frc.robot.Subsystems.superstructure.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  // Hardware
  private VictorSPX _motor1 = new VictorSPX(ElevatorConstants.kElevatorMotorPort);
  private Encoder encoder = new Encoder(ElevatorConstants.kENC_A, ElevatorConstants.kENC_B);

  // Vars
  private double kDt = ElevatorConstants.kDt, ks = ElevatorConstants.ks, kg = ElevatorConstants.kg, kv = ElevatorConstants.kv, ka = ElevatorConstants.ka;
  private String state = "null";

  // Vars: Threasholds
  private double kL4Threash = ElevatorConstants.kL4 - 2000;
  private double kL3Threash = ElevatorConstants.kL3 - 2000;
  private double kL2Threash = ElevatorConstants.kL2 - 2000;

  // Control Systems
  TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(16000, 41000));
  TrapezoidProfile.State _setpoint = new TrapezoidProfile.State();
  TrapezoidProfile.State _goal = new TrapezoidProfile.State();

  PIDController pid = new PIDController(
    ElevatorConstants.kP,
    ElevatorConstants.kI,
    ElevatorConstants.kD
  );

  double point = 0;

  private ElevatorFeedforward feedforward = new ElevatorFeedforward(
    ks,
    kg,
    kv,
    ka
  );

  // Funcs
  public void setSetpoint(double returnedPoint) {
    point = returnedPoint;
  }

  public void init() {
    encoder.reset();
    point = encoder.get();
    pid.reset();
  }

  public Elevator() {
    _motor1.enableVoltageCompensation(true);
    DataLogManager.start();
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

    if (runner == 0) {
      _motor1.set(ControlMode.PercentOutput, -pid.calculate(encoder.get(), point));
    } else if (runner == 1) {
      _motor1.set(ControlMode.PercentOutput, 0.65);
    } else {
      _motor1.set(ControlMode.PercentOutput, 0);
    }
    

    if (encoder.get() >= kL4Threash) {
      state = "l4";
    } else if (encoder.get() < kL4Threash && encoder.get() >= kL3Threash) {
      state = "l3";
    } else if (encoder.get() < kL3Threash && encoder.get() >= kL2Threash) {
      state = "l2";
    } else if (encoder.get() < kL2Threash) {
      state = "whysolow";
    }

    // Comparable in AdvantageScope
    SmartDashboard.putNumber("Profile Position", _setpoint.position);
    SmartDashboard.putNumber("Elevator Position", encoder.get());
    SmartDashboard.putNumber("FeedForward Output", 0);
    SmartDashboard.putString("ElevatorState", state);
    SmartDashboard.putNumber("encoder rah", encoder.get());
    SmartDashboard.putNumber("PID PID GOG", pid.calculate(point));
  }

  @Override
  public void simulationPeriodic() {
  }
}
