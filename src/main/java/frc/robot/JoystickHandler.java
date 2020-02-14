/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickHandler {

    private static JoystickHandler mInstance = null;

    private Joystick mDriveStick;
    private Joystick mShootStick;

    public static JoystickHandler getInstance() {
        if (mInstance == null) {
            mInstance = new JoystickHandler();
        }
        return mInstance;
    }

    public JoystickHandler() {
        mDriveStick = new Joystick(Constants.DRIVE_STICK);
        mShootStick = new Joystick(Constants.SHOOT_STICK);
    }

    public Joystick getDriveStick() {
        return mDriveStick;
    }

    public Joystick getShootStick() {
        return mShootStick;
    }

    public double getDriveLeft() {
        return mDriveStick.getRawAxis(Constants.DRIVE_LEFT_AXIS);
    }

    public double getDriveRight() {
        return mDriveStick.getRawAxis(Constants.DRIVE_RIGHT_AXIS);
    }

    public boolean getCWButton() {
        return mShootStick.getRawButton(Constants.CW_BUTTON);
    }

    public boolean getFrontIntake() {
        return mShootStick.getRawButton(Constants.FRONT_INTAKE);
    }

    public boolean getBackIntake() {
        return mShootStick.getRawButton(Constants.BACK_INTAKE);
    }

    public double getHookAxis() {
        return mShootStick.getRawAxis(Constants.CLIMB_AXIS);
    }

    public boolean getShootBtn() {
        return mShootStick.getRawButton(Constants.SHOOT_AUTO);
    }

    public boolean getAutoAlign() {
        return mShootStick.getRawButton(Constants.AUTO_ALIGN);
    }

    public double getManuallyShoot() {
        return mShootStick.getRawAxis(Constants.SHOOT_MANUALLY_AXIS);
    }

    public boolean getEject() {
        return mShootStick.getRawButton(Constants.EJECT);
    }

    public boolean getShootNow() {
        return mShootStick.getRawButton(4);
    }
}
