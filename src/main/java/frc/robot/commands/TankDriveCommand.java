package frc.robot.commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TankDriveCommand extends CommandBase {
    DrivetrainSubsystem driveTrain;
    DoubleSupplier rightStick;
    DoubleSupplier leftStick;

    public TankDriveCommand(DrivetrainSubsystem driveTrain, DoubleSupplier leftStick, DoubleSupplier rightStick) {
        this.driveTrain = driveTrain;
        this.rightStick = rightStick;
        this.leftStick = leftStick;
        
        addRequirements(driveTrain);
      }
      double cubePower(double input) {
        return Math.pow(input , 3);
      }

      double deadZone(double input) {
        if(Math.abs(input) <= Constants.DEADZONE_AMOUNT) return 0;
        return input;
      }

      public void execute (){
        driveTrain.setMotorPowers(cubePower(deadZone(leftStick.getAsDouble())) , cubePower(deadZone(rightStick.getAsDouble())), "Joysticks said so");
      }
}
