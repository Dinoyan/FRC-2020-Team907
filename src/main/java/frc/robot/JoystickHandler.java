/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
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


}
