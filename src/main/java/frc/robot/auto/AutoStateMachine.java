/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.subsystem.Drivetrain;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Shooter;
import frc.robot.util.CyberPID;

/**
 * Add your docs here.
 */
public class AutoStateMachine {
    private static AutoStateMachine mInstance = null;

    // ************ AUTO MODES ***************
    private byte DEFAULT = 0;
   
    // ************* STATES ******************
    private byte DRIVE = 0;
    private byte TURN = 1;
    private byte SHOOT = 2;
    private byte INTAKE = 3;
    private byte WAIT = 4;

    private byte currentState;
    private byte currentStateIndex = 0;
    private byte[] nextStateArray = new byte[255];

    private boolean mStop = false;
    private Timer mTimer = new Timer();

    // PIDs
    private CyberPID mDrivePID;
    private CyberPID mTurnPID;

    // Subsystems
    private Drivetrain mDrive;
    private Shooter mShooter;
    private Intake mIntake;

    public static AutoStateMachine getInstance() {
        if (mInstance == null) {
            mInstance = new AutoStateMachine();
        }
        return mInstance;
    }

    public void init(byte selection) {
        // Subsystems
         mDrive = Drivetrain.getInstance();
         mShooter = Shooter.getInstance();
         mIntake = Intake.getInstance();

         mDrive.init();
         mShooter.init();
         mIntake.init();

         currentStateIndex = 0;
         setCurrentState(WAIT);
         buildAuto(selection);
        
         mTimer.start();
    }

    public void buildAuto(byte mode) {
        byte stateCounter = 0;

        if (DEFAULT == mode) {

        }

        setCurrentState(nextStateArray[currentStateIndex]);
    }

    public void setCurrentState(byte state) {
        currentState = state;
    }

    public void autonomousEnabledLoop() {
        if (currentState == DRIVE) {

        } else if (currentState == TURN) {

        } else if (currentState == SHOOT) {

        } else if (currentState == INTAKE) {

        }
    }

    private boolean infLoopChecker() {
        if (mTimer.get() > Constants.AUTO_TIME) {
            mStop = true;
        }
        return mStop;
    }

    // **************************************
    // *********** AUTO ACTIONS *************
    // **************************************

    private void drive(double distance) {
        mDrivePID.setSetpoint(distance);
        Boolean cond = infLoopChecker();
        boolean onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
        
        if (!cond) {
            if (!onTarget) {
            onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
            cond = infLoopChecker();
            double value = mDrivePID.getOutput(mDrive.getRightDistance());
            mDrive.drive(value * .5, value * .5);
            } else {
                mDrive.drive(0, 0);
                currentStateIndex++;
                setCurrentState(nextStateArray[currentStateIndex]);
            }
        }
        mDrivePID.reset();
    }

    private void turn(double angle) {
        mTurnPID.setSetpoint(angle);
        boolean cond = infLoopChecker();
        boolean onTarget = mTurnPID.onTarget(mDrive.getAngle());

        if (!cond) {
            if (!onTarget) {
            onTarget = mTurnPID.onTarget(mDrive.getAngle());
            cond = infLoopChecker();
            double value = mTurnPID.getOutput(mDrive.getAngle());
            mDrive.drive(-value * .5, value * .5);
            } else {
                mDrive.drive(0, 0);
                currentStateIndex++;
                setCurrentState(nextStateArray[currentStateIndex]);
            }
        }
        mTurnPID.reset();
    }

    private void intake() {

    }

    private void shoot() {
        
    }
    
}
