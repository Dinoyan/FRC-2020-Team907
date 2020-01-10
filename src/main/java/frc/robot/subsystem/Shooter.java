/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {

    private static Shooter mInstance = null;

    private WPI_TalonSRX mShooterMaster;
    private WPI_TalonSRX mShooterSlave;


    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        mShooterMaster = new WPI_TalonSRX(Constants.SHOOTER_CAN[0]);
        mShooterSlave = new WPI_TalonSRX(Constants.SHOOTER_CAN[1]);

        mShooterMaster.configFactoryDefault();
        mShooterSlave.configFactoryDefault();

        mShooterMaster.follow(mShooterSlave);
        
        mShooterMaster.setNeutralMode(NeutralMode.Coast);
        mShooterSlave.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void zeroSensors() {
        mShooterMaster.set(ControlMode.PercentOutput, 0);
        mShooterSlave.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean checkSystem() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub

    }

    public void shootCell(double speed) {
        mShooterMaster.set(ControlMode.PercentOutput, speed);
        mShooterSlave.set(ControlMode.PercentOutput, speed);
    }

    public void vomitCell(double speed) {
        mShooterMaster.set(ControlMode.PercentOutput, -speed);
        mShooterSlave.set(ControlMode.PercentOutput, -speed);
    }

    public void getShooterSpeed() {

    }

}
