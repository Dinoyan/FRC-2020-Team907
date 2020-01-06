/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystem.CWPanel;
import frc.robot.subsystem.Drivetrain;
import frc.robot.subsystem.Hook;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Shooter;
import frc.robot.util.CrashTracker;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

   private SubsystemManager mSubsystemManager;
   private Drivetrain mDrive;
   private Shooter mShooter;
   private Intake mIntake;
   private Hook mHook;
   private CWPanel mCWPanel;

  @Override
  public void robotInit() {
    mSubsystemManager = SubsystemManager.getInstance();

    mDrive = Drivetrain.getInstance();
    mShooter = Shooter.getInstance();
    mIntake = Intake.getInstance();
    mHook = Hook.getInstance();
    mCWPanel = CWPanel.getInstance();

    mSubsystemManager.setSystem (
      mDrive, 
      mShooter, 
      mIntake, 
      mHook,
      mCWPanel
    );

    CrashTracker.logRobotInit();
  }

  @Override
  public void autonomousInit() {
    mSubsystemManager.zeroAll();
    mSubsystemManager.outPutDashboard();

    CrashTracker.logAutoInit();
  }

  @Override
  public void autonomousPeriodic() {
    mSubsystemManager.outPutDashboard();

  }

  @Override
  public void teleopInit() {
    mSubsystemManager.zeroAll();
    mSubsystemManager.outPutDashboard();

    CrashTracker.logTeleopInit();
  }

  @Override
  public void teleopPeriodic() {
    mSubsystemManager.outPutDashboard();
  }

  @Override
  public void testInit() {
    mSubsystemManager.zeroAll();
  }

  @Override
  public void testPeriodic() {
  }

}
