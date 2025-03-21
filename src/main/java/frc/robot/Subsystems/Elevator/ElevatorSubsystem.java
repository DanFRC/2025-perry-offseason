package frc.robot.Subsystems.Elevator;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
  // Hardware
  private VictorSPX _motor1 = new VictorSPX(Constants.ElevatorConstants.kElevatorMotorPort);
  private Encoder encoder = new Encoder(Constants.ElevatorConstants.kENC_A, Constants.ElevatorConstants.kENC_B);

  private double kDt = 0.02, ks = 0.00001, kg = 0.09, kv = 0.000045, ka = 0.0000001;
  private String state = "null";

  TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(16000, 19000));
  TrapezoidProfile.State _setpoint = new TrapezoidProfile.State();
  TrapezoidProfile.State _goal = new TrapezoidProfile.State();

  private ElevatorFeedforward feedforward = new ElevatorFeedforward(
    ks,
    kg,
    kv,
    ka
  );

  public void setSetpoint(double returnedPoint) {
    _goal = new TrapezoidProfile.State(returnedPoint, 0);
  }

  public void init() {
    encoder.reset();
  }

  public ElevatorSubsystem() {
    _motor1.enableVoltageCompensation(true);
    DataLogManager.start();
  }

  @Override
  public void periodic() {
    _setpoint = profile.calculate(
      kDt, 
      _setpoint, 
      _goal
    );
    double feedforwardOutput = feedforward.calculate(_setpoint.velocity);

    _motor1.set(ControlMode.PercentOutput, feedforwardOutput);

    if (encoder.get() >= 13500) {
      state = "l4";
    } else if (encoder.get() < 13500 && encoder.get() >= 7000) {
      state = "l3";
    } else if (encoder.get() < 7000 && encoder.get() >= 1000) {
      state = "l2";
    } else if (encoder.get() < 1000) {
      state = "whysolow";
    }

    // Comparable in AdvantageScope
    SmartDashboard.putNumber("Profile Position", _setpoint.position);
    SmartDashboard.putNumber("Elevator Position", encoder.get());
    SmartDashboard.putNumber("FeedForward Output", feedforwardOutput);
    SmartDashboard.putString("ElevatorState", state);
  }

  @Override
  public void simulationPeriodic() {
  }
}
