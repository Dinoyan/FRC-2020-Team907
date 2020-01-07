/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Hook extends Subsystem {

    private static Hook mInstance = null;

    WPI_TalonSRX mHookMotor = new WPI_TalonSRX(Constants.HOOK_CAN);

    public static Hook getInstance() {
        if (mInstance == null) {
            mInstance = new Hook();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mHookMotor.configFactoryDefault();
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        mHookMotor.stopMotor();
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

    public void pullUp(double speed) {
        mHookMotor.set(speed);
    }

    public void releaseDown(double speed) {
        mHookMotor.set(-speed);
    }

    public void switchModeBrake() {

    }

    public void switchModeCoast() {
        
    }
}
