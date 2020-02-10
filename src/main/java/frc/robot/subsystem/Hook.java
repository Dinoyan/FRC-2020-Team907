/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Hook extends Subsystem {

    private static Hook mInstance = null;

    WPI_VictorSPX mHookMotor;

    public static Hook getInstance() {
        if (mInstance == null) {
            mInstance = new Hook();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mHookMotor = new WPI_VictorSPX(Constants.HOOK_CAN);
        mHookMotor.configFactoryDefault();
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
        mHookMotor.stopMotor();
    }

    @Override
    public Boolean checkSystem() {
        return true;
    }

    @Override
    public void updateDashboard() {

    }

    public void pullUp(double speed) {
        mHookMotor.set(speed);
    }

    public void releaseDown(double speed) {
        mHookMotor.set(-speed);
    }

    public void lock() {

    }

    public void unlock() {
        
    }
}
