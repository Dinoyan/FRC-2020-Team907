/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

/**
 * Add your docs here.
 */
public class Hook extends Subsystem {

    private static Hook mInstance = null;

    public static Hook getInstance() {
        if (mInstance == null) {
            mInstance = new Hook();
        }
        return mInstance;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean checkSystem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub

    }
}
