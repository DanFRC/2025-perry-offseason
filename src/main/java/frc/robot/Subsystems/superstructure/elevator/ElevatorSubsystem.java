//

package frc.robot.Subsystems.superstructure.elevator;

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
  TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(24500, 34500));
  TrapezoidProfile.State _setpoint = new TrapezoidProfile.State();
  TrapezoidProfile.State _goal = new TrapezoidProfile.State();

  private ElevatorFeedforward feedforward = new ElevatorFeedforward(
    ks,
    kg,
    kv,
    ka
  );

  // Funcs
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
    double feedforwardOutput = feedforward.calculate(_setpoint.velocity);
    _setpoint = profile.calculate(
      kDt, 
      _setpoint, 
      _goal
    );
    
    _motor1.set(ControlMode.PercentOutput, feedforwardOutput);

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
    SmartDashboard.putNumber("FeedForward Output", feedforwardOutput);
    SmartDashboard.putString("ElevatorState", state);
  }

  @Override
  public void simulationPeriodic() {
  }
}
