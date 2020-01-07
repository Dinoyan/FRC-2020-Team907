/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.teleop;

import frc.robot.subsystem.CWPanel;
import frc.robot.subsystem.Drivetrain;
import frc.robot.subsystem.Hook;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Shooter;

/**
 * Contains all the teleop code
 */
public class ITeleopLooper implements ITeleop {
    private static ITeleopLooper mInstance = null;

    private Drivetrain mDrive;
    private Shooter mShooter;
    private Intake mIntake;
    private Hook mHook;
    private CWPanel mCWPanel;

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
        mDrive = Drivetrain.getInstance();
        mShooter = Shooter.getInstance();
        mIntake = Intake.getInstance();
        mHook = Hook.getInstance();
        mCWPanel = CWPanel.getInstance();

        mDrive.init();
        mShooter.init();
        mIntake.init();
        mHook.init();
        mCWPanel.init();
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
