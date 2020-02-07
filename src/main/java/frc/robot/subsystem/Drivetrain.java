/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

    private static Drivetrain mInstance = null;

    private double mRightDistance = 0.0;
    private double mLeftDistance = 0.0;
    private double mAvgDistance = 0.0;
    private double mAngle = 0.0;

    private WPI_TalonFX mLMaster;
    private WPI_TalonFX mLSlave;
    private WPI_TalonFX mRMaster;
    private WPI_TalonFX mRSlave;

    private DifferentialDrive mDrive;

    private AHRS navx;

    public static Drivetrain getInstance() {
        if (mInstance == null) {
            mInstance = new Drivetrain();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mLMaster = new WPI_TalonFX(Constants.DRIVE_LEFT[0]);
        mLSlave = new WPI_TalonFX(Constants.DRIVE_LEFT[1]);
        mRMaster = new WPI_TalonFX(Constants.DRIVE_RIGHT[0]);
        mRSlave = new WPI_TalonFX(Constants.DRIVE_RIGHT[1]);

        mDrive = new DifferentialDrive(mLMaster, mRMaster);


        mLMaster.configFactoryDefault();
        mLSlave.configFactoryDefault();
        mRMaster.configFactoryDefault();
        mRSlave.configFactoryDefault();

        mLMaster.follow(mLSlave);
        mRMaster.follow(mRSlave);

        mDrive.setRightSideInverted(false);

        // TO-DO
        // config encoders
        mRMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        navx = new AHRS(SPI.Port.kMXP);

    }

    @Override
    public void zeroSensors() {
        mRMaster.setSelectedSensorPosition(0);
        mLMaster.setSelectedSensorPosition(0);
    }

    @Override
    public void stop() {
        drive(0, 0);
    }

    @Override
    public Boolean checkSystem() {
        return null;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Right Pos", getRightDistance());
        SmartDashboard.putNumber("Left Pos", getLeftDistance());
        SmartDashboard.putNumber("Angle", getAngle());
    }

    public void drive(double left, double right) {
        mDrive.tankDrive(left, right);
    }

    public double getRightDistance() {
        mRightDistance = mRMaster.getSelectedSensorPosition();
        return mRightDistance;
    }

    public double getLeftDistance() {
        mLeftDistance = mLMaster.getSelectedSensorPosition();
        return mLeftDistance;
    }

    public double getAvgDistance() {
        mAvgDistance = (mLeftDistance + mRightDistance) / 2;
        return mAvgDistance;
    }

    public double getAngle() {
        mAngle = navx.getAngle();
        return mAngle;
    }

    public void switchToBrake() {
        mRMaster.setNeutralMode(NeutralMode.Brake);
        mLMaster.setNeutralMode(NeutralMode.Brake);
    }

    public void switchToCoast() {
        mRMaster.setNeutralMode(NeutralMode.Coast);
        mLMaster.setNeutralMode(NeutralMode.Coast);
    }
}
