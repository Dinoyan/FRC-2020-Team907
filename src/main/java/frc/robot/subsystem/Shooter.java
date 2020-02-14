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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {

    private static Shooter mInstance = null;

    private WPI_TalonSRX mShooterMaster;
    private WPI_TalonSRX mShooterSlave;

    private DoubleSolenoid mHood;

    private WPI_TalonSRX mAcc;

    private double mVelocity;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mShooterMaster = new WPI_TalonSRX(Constants.SHOOTER_CAN[0]);
        mShooterSlave = new WPI_TalonSRX(Constants.SHOOTER_CAN[1]);

        mAcc = new WPI_TalonSRX(Constants.ACC_CAN);

        mHood = new DoubleSolenoid(Constants.HOOD_PISTON[0], Constants.HOOD_PISTON[1]);

        mShooterMaster.configFactoryDefault();
        mShooterSlave.configFactoryDefault();

        mShooterMaster.follow(mShooterSlave);
        
        mShooterMaster.setNeutralMode(NeutralMode.Coast);
        mShooterSlave.setNeutralMode(NeutralMode.Coast);

        // TO-DO:
        // configure sensor
        mShooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        mShooterMaster.setSensorPhase(true);

        mShooterMaster.configNominalOutputForward(0);
        mShooterMaster.configNominalOutputReverse(0);
        mShooterMaster.configPeakOutputForward(1);
        mShooterMaster.configPeakOutputReverse(-1);

        // set kF to 0 and tune from there
        mShooterMaster.config_kP(0, 0.1);
        mShooterMaster.config_kI(0, 0);
        mShooterMaster.config_kD(0, 0.1);
        mShooterMaster.config_kF(0, 0);
    }

    @Override
    public void zeroSensors() {
       mShooterMaster.setSelectedSensorPosition(0);
    }

    @Override
    public void stop() {
        mShooterMaster.set(ControlMode.PercentOutput, 0);
        mShooterSlave.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public Boolean checkSystem() {
        return true;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Shooter Speed", getShooterSpeed());
    }

    public void shootCellOpen(double speed) {
        mShooterMaster.set(-speed);
        mShooterSlave.set(-speed);
    }

    // closed loop control
    public void shootCellClosed(double _velocity) {
        mShooterMaster.set(ControlMode.Velocity, _velocity);
    }

    public void vomitCell(double speed) {
        mShooterMaster.set(ControlMode.PercentOutput, -speed);
        mShooterSlave.set(ControlMode.PercentOutput, -speed);
    }

    // shooter hood
    public void controlHood(boolean state) {
        if (state) {
            mHood.set(Value.kForward);
        } else {
            mHood.set(Value.kReverse);
        }
    }

    public void controlAcc(double speed) {
        mAcc.set(speed);
    }

    // return shooter velocity
    public double getShooterSpeed() {
        mVelocity = mShooterMaster.getSelectedSensorVelocity();
        return mVelocity;
    }

    // bang-bang control
    public void BangBangControl(double value) {
        if (getShooterSpeed() < value) {
            shootCellOpen(1);
        } else {
            shootCellOpen(0);
        }
    }
}
