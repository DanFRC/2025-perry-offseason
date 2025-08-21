package frc.robot.Subsystems.drivebase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivebase extends SubsystemBase {

    private Rotation2d rotation = new Rotation2d();
    private String side = "left";

  // Drivebase
    private final MecanumDrive _robotDrive;

  // Hardware
    VictorSPX frontLeft = new VictorSPX(DrivebaseConstants.kLeftMotorPort1);
    VictorSPX rearLeft = new VictorSPX(DrivebaseConstants.kLeftMotorPort2);
    VictorSPX frontRight = new VictorSPX(DrivebaseConstants.kRightMotorPort1);
    VictorSPX rearRight = new VictorSPX(DrivebaseConstants.kRightMotorPort2);
    final AHRS gyro = new AHRS(NavXComType.kUSB1);

  public double getGyroYaw() {
    return -gyro.getYaw();
    }

  public String getReefSide() {
    return side;
  }

  public void setReefSide(String givenSide) {
    if (givenSide == "left" || givenSide == "right" || givenSide == "Left" || givenSide == "Right")
    this.side = givenSide.toLowerCase();
  }

  public Drivebase() {
    gyro.reset();
    // Invert the right side motors.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    _robotDrive = new MecanumDrive((double speedFL) -> frontLeft.set(ControlMode.PercentOutput, speedFL),
    (double speedRL) -> rearLeft.set(ControlMode.PercentOutput, speedRL),
    (double speedFR) -> frontRight.set(ControlMode.PercentOutput, speedFR),
    (double speedRR) -> rearRight.set(ControlMode.PercentOutput, speedRR));
  }

    @SuppressWarnings("static-access")
    public void drive(double speedX, double speedY, double PIDoutput, boolean AUTOTurning, boolean fieldOriented) { 

      if (AUTOTurning == false) {
        if (PIDoutput > 0) {
          PIDoutput *= PIDoutput;
        } else if (PIDoutput < 0) {
          PIDoutput *= -1 * PIDoutput;
        }
        
        if (fieldOriented == true) {
          _robotDrive.driveCartesian(speedX, speedY, PIDoutput, rotation.fromDegrees(-gyro.getYaw()));
        } else if (fieldOriented == false) {
          _robotDrive.driveCartesian(speedX, speedY, PIDoutput);
        }
      } else if (AUTOTurning == true) {
        _robotDrive.driveCartesian(speedX, speedY, PIDoutput);
      }

    }

    public void zeroGyro() {
        gyro.reset();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Yaw", gyro.getYaw());
        SmartDashboard.putString("Reef Side", side);
    }

    @Override
    public void simulationPeriodic() {
    }
}
