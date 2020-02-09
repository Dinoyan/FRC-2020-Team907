/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class AutoSelector {

    private static final String kJustDrive = "Default";
    private static final String kMiddleShoot = "Middle Shoot";

    private final SendableChooser<String> mChooser = new SendableChooser<>();

    private String mSelected;
    private byte mMode;

    private static AutoSelector mInstance = null;

    public static AutoSelector getInstance() {
        if (mInstance == null) {
            mInstance = new AutoSelector();
        }

        return mInstance;
    }

    public AutoSelector() {
        mChooser.setDefaultOption("Just Drive", kJustDrive);
        mChooser.addOption("Center Shoot", kMiddleShoot);

        SmartDashboard.putData(mChooser);
    }

    public byte getAutoMode() {
        mSelected = mChooser.getSelected();

        switch(mSelected) {
            case kJustDrive:
                mMode = 0;
                break;
            
            case kMiddleShoot:
                mMode = 1;
                break;
        }
        return mMode;
    }
}
