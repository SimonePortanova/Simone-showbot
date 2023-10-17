package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
    
    private ShooterSubsystem shooter;
    private JoystickButton safetyButton;
    
    public ShootCommand(JoystickButton safetyButton, ShooterSubsystem shooter) {
        this.safetyButton = safetyButton;
        this.shooter = shooter;

        addRequirements(shooter);
    }

    public void execute() {
        if (Robot.isTestMode || safetyButton.getAsBoolean()) {
            shooter.setMotorPower(1, "Right trigger on secondary joystick said so");
        }
        else {
            shooter.setMotorPower(0, "Not in test mode or secondary button not pressed");
        }
    }

    public void end(boolean interrupted) {
        shooter.setMotorPower(0, "Stopped shooting");
    }
}