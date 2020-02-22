/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class CrashTracker {
    private static PowerDistributionPanel pdp = new PowerDistributionPanel();

    public static void logRobotConstruction() {
        logNow("robot startup");
    }

    public static void logRobotInit() {
        logNow("robot init");

    }

    public static void logTeleopInit() {
        logNow("teleop init");

    }

    public static void robotStatus() {
        double voltage = pdp.getVoltage();
        SmartDashboard.putString("Battery Charge", Math.round(((voltage - 10.5) / 2.8) * 100.0) + " %");
    }

    public static void logAutoInit() {
        logNow("auto init");

    }

    public static void logDisabledInit() {
        logNow("disable init");
    }

    public static void logTestInit() {
        logNow("test init");

    }

    public static void SubsystemsDisgonitics() {
        boolean mPass = true;

        SmartDashboard.putBoolean("Subsystems", mPass);
    }

    private static void logNow(String mode) {
        // Logger tracker = Logger.getLogger("Crash Tracker");
        // FileHandler logFile;

        // try {
        //     logFile = new FileHandler("C:/");
        //     tracker.addHandler(logFile);

        //     tracker.info(mode);

        // } catch (SecurityException e) {
        //     e.printStackTrace();

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
