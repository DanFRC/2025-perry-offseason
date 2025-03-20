package frc.robot.Subsystems.Elevator;

import frc.robot.Constants;

import com.ctre.phoenix.Logger;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {

  // Hardware
  private VictorSPX Motor1 = new VictorSPX(Constants.ElevatorConstants.kElevatorMotorPort);
  private Encoder Encoder = new Encoder(Constants.ElevatorConstants.kENC_A, Constants.ElevatorConstants.kENC_B);

  private double kDt = 0.02;
  private double ks = 0.00001;
  private double kg = 0.0001; // Constant Output For Gravity Mitigation
  private double kv = 0.0001; // Constant Output For Motor Speed (For moving to setpoints)
  private double ka = 0.0000001;
  private double kp = 0.0;
  private double ki = 0.0;
  private double kd = 0.0;

  TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(8000, 6500));
  TrapezoidProfile.State _setpoint = new TrapezoidProfile.State();
  TrapezoidProfile.State _goal = new TrapezoidProfile.State();

  private ElevatorFeedforward feedforward = new ElevatorFeedforward(
    ks,
    kg,
    kv,
    ka
  );

  private int Position = 0;
  public static double setpoint = 0;

  private final double ticks_in_degrees = 8192 / 360.0;

  public void setSetpoint(double returnedPoint) {
    _goal = new TrapezoidProfile.State(returnedPoint, 0);
  }

  public void init() {
    Encoder.reset();
    Position = Encoder.get();
  }

  public ElevatorSubsystem() {
    Position = Encoder.get();
    DataLogManager.start();
  }

  @Override
  public void periodic() {
    _setpoint = profile.calculate(kDt, _setpoint, _goal);
    setpoint = _setpoint.position;

    // Comparable in AdvantageScore
    double feedforwardOutput = feedforward.calculate(_setpoint.velocity);
    SmartDashboard.putNumber("Profile Position", _setpoint.position);
    SmartDashboard.putNumber("Elevator Position", Encoder.get());
    SmartDashboard.putNumber("FeedForward Output", feedforwardOutput);
  }

  @Override
  public void simulationPeriodic() {
        // Periodic during Simulation
  }
}
