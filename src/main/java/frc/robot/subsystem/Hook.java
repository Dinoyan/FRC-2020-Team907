/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Hook extends Subsystem {

    private static Hook mInstance = null;

    VictorSP mHookMotor;
    //VictorSPX mWnchMotor;


    public static Hook getInstance() {
        if (mInstance == null) {
            mInstance = new Hook();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mHookMotor = new VictorSP(8);
        //mWnchMotor = new VictorSPX(9);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
    }

    @Override
    public Boolean checkSystem() {
        return true;
    }

    @Override
    public void updateDashboard() {

    }

    public void adust(double speed) {
        mHookMotor.set(speed);
        
    }

    public void pullUp(double speed){
       // mWnchMotor.set(ControlMode.PercentOutput, speed);
    }

    public void releaseDown(double speed) {
        mHookMotor.set(speed);
    }

    public void lock() {

    }

    public void unlock() {
        
    }
}
