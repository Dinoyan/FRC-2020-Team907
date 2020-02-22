/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
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
    private VictorSPX mLSlave;
    private WPI_TalonFX mRMaster;
    private VictorSPX mRSlave;

    private SupplyCurrentLimitConfiguration currentLimit = new SupplyCurrentLimitConfiguration();

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
        mLSlave = new VictorSPX(Constants.DRIVE_LEFT[1]);
        mRMaster = new WPI_TalonFX(Constants.DRIVE_RIGHT[0]);
        mRSlave = new VictorSPX(Constants.DRIVE_RIGHT[1]);

        mLMaster.configFactoryDefault();
        // mLSlave.configFactoryDefault();
        mRMaster.configFactoryDefault();
        // mRSlave.configFactoryDefault();

        mLMaster.follow(mLSlave);
        mRMaster.follow(mRSlave);

        mRMaster.setNeutralMode(NeutralMode.Coast);
        mLMaster.setNeutralMode(NeutralMode.Coast);
        mRSlave.setNeutralMode(NeutralMode.Coast);
        mLSlave.setNeutralMode(NeutralMode.Coast);

        mRMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        mLMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);

        // currentLimit.currentLimit = 40;
        // currentLimit.triggerThresholdCurrent = 35;
        // currentLimit.enable = true;

        // mRMaster.configSupplyCurrentLimit(currentLimit, 0);
        // mLMaster.configSupplyCurrentLimit(currentLimit, 0);

        // mRMaster.configOpenloopRamp(4);
        // mLMaster.configOpenloopRamp(4);

        navx = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void zeroSensors() {
        mRMaster.setSelectedSensorPosition(0);
        mLMaster.setSelectedSensorPosition(0);
        navx.reset();
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
       if (Math.abs(left) > 0.08 || Math.abs(right) > 0.08) {
        this.mRMaster.set(-right);
        this.mRSlave.set(ControlMode.PercentOutput, -right);
        this.mLMaster.set(left);
        this.mLSlave.set(ControlMode.PercentOutput, left);
       } else {
        this.mRMaster.set(0);
        this.mRSlave.set(ControlMode.PercentOutput ,0);
        this.mLMaster.set(0);
        this.mLSlave.set(ControlMode.PercentOutput, 0);
       }
    }

    public double getRightDistance() {
        // mRightDistance = mRMaster.getSelectedSensorPosition() * ((1.0/2048) * (10.0/64) * 4 * Math.PI);
        mRightDistance = mRMaster.getSensorCollection().getIntegratedSensorPosition() * ((1.0/2048) * (10.0/64) * 4 * Math.PI);
        return mRightDistance;
    }

    public double getLeftDistance() {
        mLeftDistance = mLMaster.getSelectedSensorPosition() * ((1.0/2048) * (10.0/64) * 4 * Math.PI);
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
        mRMaster.setNeutralMode(NeutralMode.Brake);
        mLMaster.setNeutralMode(NeutralMode.Brake);
    }

    public void switchToCoast() {
        mRMaster.setNeutralMode(NeutralMode.Coast);
        mLMaster.setNeutralMode(NeutralMode.Coast);
        mRMaster.setNeutralMode(NeutralMode.Coast);
        mLMaster.setNeutralMode(NeutralMode.Coast);
    }
}
