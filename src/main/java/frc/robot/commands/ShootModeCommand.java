package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootModeCommand extends CommandBase {

    ShooterSubsystem shooter;
    public DoubleSupplier speedMod;

    public ShootModeCommand (DoubleSupplier newSpeedMod , ShooterSubsystem shooter){
        this.shooter = shooter;
        speedMod = newSpeedMod;

        addRequirements(shooter);
    }
   
    public void initialize () {
        shooter.speedMod = speedMod;
    }

}
