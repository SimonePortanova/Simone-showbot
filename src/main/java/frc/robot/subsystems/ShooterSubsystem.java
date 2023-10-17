package frc.robot.subsystems;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;                                                                                                                                                                                                                                                                                                      
import frc.robot.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase {

    public TeamSparkMAX leftMotor, rightMotor;

    private static double voltage = 12;

    public DoubleSupplier speedMod = () -> 0.5;

    public ShooterSubsystem() {
        leftMotor = new TeamSparkMAX("Left Shooter Motor", Ports.SHOOTER_MOTOR_LEFT);
        rightMotor = new TeamSparkMAX("Right Shooter Motor", Ports.SHOOTER_MOTOR_RIGHT);

        rightMotor.follow(leftMotor, true);
        leftMotor.setInverted(false); 

        leftMotor.enableVoltageCompensation(voltage);
        rightMotor.enableVoltageCompensation(voltage);
    }

    public void setMotorPower(float power, String reason) {
        leftMotor.set(power * speedMod.getAsDouble(), reason);
    }
    
}