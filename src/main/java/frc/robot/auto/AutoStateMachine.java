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
    private byte MIDDLE_SHOOT = 1;
    private byte RIGHT_SHOOT = 2;
    private byte LEFT_SHOOT = 3;
    // private byte RIGHT_TRENCH_PICKUP = 4;
    // private byte LEFT_TRENCH_PICKUP = 5;
   
    // ************* STATES ******************
    private byte DRIVE = 0;
    private byte TURN = 1;
    private byte SHOOT = 2;
    private byte INTAKE = 3;
    private byte WAIT = 4;
    private byte DRIVE_AND_INTAKE = 5;

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

         mDrivePID = new CyberPID();
         mTurnPID = new CyberPID();
 
        currentStateIndex = 0;
        setCurrentState(WAIT);
        buildAuto(selection);
        
         mTimer.start();
    }

    public void buildAuto(byte mode) {
        byte stateCounter = 0;
        if (DEFAULT == mode) {
            nextStateArray[stateCounter] = TURN;
            stateCounter++;
            nextStateArray[stateCounter] = DRIVE;
            stateCounter++;
            nextStateArray[stateCounter] = WAIT;
            stateCounter++;
        } else if (MIDDLE_SHOOT == mode) {

        } else if (RIGHT_SHOOT == mode) {

        } else if (LEFT_SHOOT == mode) {

        } 

        setCurrentState(nextStateArray[currentStateIndex]);
    }

    public void setCurrentState(byte state) {
        currentState = state;
    }

    public void autonomousEnabledLoop() {
        boolean cond = infLoopChecker();
    
        if (currentState == DRIVE) {
            drive(179);
        } else if (currentState == TURN) {
            turn(90);
        } else if (currentState == SHOOT) {
            // shoot();
        } else if (currentState == INTAKE) {
            // intake();
        } else if (currentState == DRIVE_AND_INTAKE) {
            // drive(4);
            // intake();
        } else if(currentState ==  WAIT){
            
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
        boolean onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
       
        if (!onTarget) {
            onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
            double value = mDrivePID.getOutput(mDrive.getRightDistance());
        
            mDrive.drive(value * .5, value * .5);
        } else {
            mDrive.drive(0, 0);
            currentStateIndex++;
            setCurrentState(nextStateArray[currentStateIndex]);
            System.out.println(currentStateIndex);
        }

        mDrivePID.reset();
    }

    private void turn(double angle) {
        mTurnPID.setSetpoint(angle);

        boolean onTarget = mTurnPID.onTarget(mDrive.getAngle());

        if (!onTarget) {
            onTarget = mTurnPID.onTarget(mDrive.getAngle());
            double value = mTurnPID.getOutput(mDrive.getAngle());
            mDrive.drive(-value * .5, value * .5);
        } else {
            mDrive.drive(0, 0);
            currentStateIndex++;
            setCurrentState(nextStateArray[currentStateIndex]);
        }
    
        mTurnPID.reset();
    }

    private void intake() {
       
    }

    private void shoot() {
        boolean doneShooting = false;

        if (!doneShooting) {

        } else {
            currentStateIndex++;
            setCurrentState(nextStateArray[currentStateIndex]);
        }
    }
    
}
