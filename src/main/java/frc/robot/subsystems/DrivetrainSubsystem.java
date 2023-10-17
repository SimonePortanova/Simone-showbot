package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ITeamTalon;
import frc.robot.TeamTalonFX;
import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class DrivetrainSubsystem extends SubsystemBase {

    ITeamTalon rightDriveFalconMain;
    ITeamTalon leftDriveFalconMain;
    ITeamTalon rightDriveFalconSub;
    ITeamTalon leftDriveFalconSub;

    public double speedMod;

    public DrivetrainSubsystem () {

        rightDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.RightMain", Ports.RightDriveFalconMainCAN);
        leftDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.LeftMain", Ports.LeftDriveFalconMainCAN);
        rightDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.RightSub", Ports.RightDriveFalconSubCAN);
        leftDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.LeftSub", Ports.LeftDriveFalconSubCAN);
         
        TalonFXConfiguration configs = new TalonFXConfiguration();

        rightDriveFalconMain.configBaseAllSettings(configs);
        leftDriveFalconMain.configBaseAllSettings(configs);

        rightDriveFalconSub.follow(rightDriveFalconMain);
        leftDriveFalconSub.follow(leftDriveFalconMain);

        rightDriveFalconMain.setInverted(false);
        rightDriveFalconSub.setInverted(false);

        leftDriveFalconMain.setInverted(true);
        leftDriveFalconSub.setInverted(true);

        rightDriveFalconMain.setNeutralMode(NeutralMode.Brake);
        leftDriveFalconMain.setNeutralMode(NeutralMode.Brake);
    }

    private double getCappedPower (double powerDesired){
        return Math.max(Math.min(1 , powerDesired), -1);
    }

    private double getRampedPower(double powerDesired , double powerCurrent) {

        if(powerDesired < powerCurrent){
            return Math.max(powerDesired , powerCurrent - Constants.DRIVETRAIN_MAX_POWER_CHANGE);
        }else if (powerDesired < powerCurrent) {
            return Math.min(powerDesired , powerCurrent + Constants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        return powerDesired;
    }

    public void setMotorPowers(double leftPowerDesired , double rightPowerDesired , String reason){
        speedMod = RobotContainer.driveSpeedEntry.getDouble(RobotContainer.driveSpeed);

        if(Robot.isTestMode) {
            double rightPowerCurrent = rightDriveFalconMain.get();
            double leftPowerCurrent = leftDriveFalconMain.get();

            leftPowerDesired *= speedMod;
            rightPowerDesired *= speedMod;

            leftPowerDesired = getRampedPower(leftPowerDesired, leftPowerCurrent);
            rightPowerDesired = getRampedPower(rightPowerDesired, rightPowerCurrent);

            leftPowerDesired = getCappedPower(leftPowerDesired);
            rightPowerDesired = getCappedPower(rightPowerDesired);

            rightDriveFalconMain.set(rightPowerDesired , reason);
            leftDriveFalconMain.set(leftPowerDesired , reason);

        } else if(!Robot.isTestMode){
            rightDriveFalconMain.set(0 , "Not in test mode");
            leftDriveFalconMain.set(0 , "Not in test mode");
        }
    }
}
