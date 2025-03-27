package frc.robot.Subsystems.superstructure.arm;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    private final DutyCycleEncoder encoder = new DutyCycleEncoder(1);

    public Arm() {}

    @Override
    public void periodic() {
        SmartDashboard.putNumber("encoder Arm", encoder.get());
    }

    @Override
    public void simulationPeriodic() {
    }
}