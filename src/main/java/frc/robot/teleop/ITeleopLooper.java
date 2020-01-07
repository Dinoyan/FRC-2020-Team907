/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.teleop;

/**
 * Add your docs here.
 */
public class ITeleopLooper implements ITeleop {
    private static ITeleopLooper mInstance = null;

    char mColour = '?';

    public static ITeleopLooper getInstance() {
        if (mInstance == null){
            mInstance = new ITeleopLooper();
        }
        return mInstance;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void driveEnabledLoop() {
        // TODO Auto-generated method stub

    }

    @Override
    public void superstructureEnabledLoop() {
        // TODO Auto-generated method stub

    }

    public char getFMSColour() {


        return mColour;
    }
}
