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
public abstract class Subsystem {

    public abstract void init();
    public abstract void zeroSensors();
    public abstract void stop();
    public abstract Boolean checkSystem();
    public abstract void updateDashboard();
}
