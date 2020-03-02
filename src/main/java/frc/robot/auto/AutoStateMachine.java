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
    private byte DEFAULT        = 0;
    private byte MIDDLE_SHOOT   = 1;
    private byte RIGHT_SHOOT    = 2;
    private byte LEFT_SHOOT     = 3;
    private byte RIGHT_TRENCH_PICKUP = 4;
   
    // ************* STATES ******************
    private byte DRIVE  = 0;
    private byte TURN   = 1;
    private byte SHOOT  = 2;
    private byte INTAKE = 3;
    private byte WAIT   = 4;
    private byte DRIVE_AND_INTAKE = 5;

    private byte currentState;
    private byte currentStateIndex;
    private byte[] nextStateArray = new byte[255];

    private Timer mTimer;

    private boolean mLastShootState = false;
    private int count = 0;

    boolean doneShooting = false;

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

        mDrivePID = new CyberPID(Constants.DRIVE_PID[0], Constants.DRIVE_PID[1], Constants.DRIVE_PID[2]);
        mTurnPID = new CyberPID(Constants.TURN_PID[0], Constants.TURN_PID[1], Constants.TURN_PID[2]);

        mDrivePID.setTolerance(1);
        mTurnPID.setTolerance(1);

        mTimer = new Timer();

        currentStateIndex = 0;
        setCurrentState(WAIT);
        buildAuto(selection);
    }

    public void buildAuto(byte mode) {
        if (DEFAULT == mode) {
            byte[] statesOrder = {DRIVE, (byte) 30, SHOOT, (byte) 3, WAIT, (byte) 0};
            fillStateArray(statesOrder);

        } else if (MIDDLE_SHOOT == mode) {
            byte[] statesOrder = {
                                    SHOOT, (byte) 3, 
                                    DRIVE, (byte)  12
                                };
            fillStateArray(statesOrder);

        } else if (RIGHT_SHOOT == mode) {
            byte[] statesOrder = {
                                    TURN, (byte) -30, 
                                    SHOOT, (byte) 3, 
                                    DRIVE, (byte) 20
                                };
            fillStateArray(statesOrder);

        } else if (LEFT_SHOOT == mode) {
            byte[] statesOrder = {
                                    TURN, (byte) 30, 
                                    SHOOT, (byte) 3, 
                                    DRIVE, (byte) 20
                                };
            fillStateArray(statesOrder);

        } else if (RIGHT_TRENCH_PICKUP == mode) {
            byte[] statesOrder = { 
                                    SHOOT, (byte) 3, 
                                    TURN, (byte) -34, 
                                    DRIVE_AND_INTAKE, (byte) -80, 
                                    WAIT, (byte) 0 };
                                    // DRIVE, (byte) 90,
                                //     TURN, (byte) 30,
                                //     SHOOT, (byte) 3,
                                // };
            fillStateArray(statesOrder);
        }
        
        setCurrentState(nextStateArray[currentStateIndex]);
    }

    public void fillStateArray(byte[] states) {
        nextStateArray = states;
    }

    public void setCurrentState(byte state) {
        currentState = state;
    }

    public void autonomousEnabledLoop() {

        if (currentState == DRIVE) {
            drive((double) nextStateArray[currentStateIndex + 1]);
        } else if (currentState == TURN) {
            turn((double) nextStateArray[currentStateIndex + 1]);
        } else if (currentState == SHOOT) {
            mTimer.start();
            shoot((int) nextStateArray[currentState + 1]);
        } else if (currentState == INTAKE) {
            intake((int) nextStateArray[currentState + 1]);
        } else if (currentState == DRIVE_AND_INTAKE) {
            intake((int) nextStateArray[currentState + 1]);
            drive((double) nextStateArray[currentStateIndex + 1]);
        } else if(currentState ==  WAIT){
            mIntake.intakeRawSpeed(0, 0);
            mIntake.conveyorControl(0);
            mIntake.backIntake(true);
        }
    }

    // **************************************
    // *********** AUTO ACTIONS *************
    // **************************************

    private void drive(double distance) {
        mDrivePID.setSetpoint(distance);
        boolean onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
        System.out.print("HEREEEE");
        if (!onTarget) {
            onTarget = mDrivePID.onTarget(mDrive.getRightDistance());
            double value = mDrivePID.getOutput(mDrive.getRightDistance());
            mDrive.drive(value * 0.4, value * 0.4);

        } else if (onTarget){
            mDrive.drive(0, 0);
            currentStateIndex += 2;
            setCurrentState(nextStateArray[currentStateIndex]);
            mDrivePID.reset();
            mDrive.zeroSensors();
        }
    }

    private void turn(double angle) {
        mTurnPID.setSetpoint(angle);
        boolean onTarget = mTurnPID.onTarget(mDrive.getAngle());
        
        if (!onTarget) {
            onTarget = mTurnPID.onTarget(mDrive.getAngle());
            double value = mTurnPID.getOutput(mDrive.getAngle());
            mDrive.drive(-value * .6, value * .6);

        } else if (onTarget) {
            mDrive.drive(0, 0);
            currentStateIndex += 2;
            setCurrentState(nextStateArray[currentStateIndex]);
            mTurnPID.reset();
            mDrive.zeroSensors();
        }
    }

    // private void shoot(int numCells) {
        
    //     boolean current = mIntake.getAccPhoto();

    //     if (!doneShooting) {
    //         if (mLastShootState != current) {
    //             System.out.print(count);
    //             count++;
    //             mLastShootState = current;
    //             doneShooting = count > numCells;
    //         }
    //         mShooter.BangBangControl(1000);

    //         if (Math.abs(mShooter.getShooterSpeed() - 1000) < 100) {
    //             mShooter.controlAcc(1.0);
    //             mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
    //             mIntake.intakeRawSpeed(Constants.INTAKE_ROLLER_SPEED, Constants.INTAKE_ROLLER_SPEED);
    //         }
            
    //     } else if (doneShooting) {
    //         System.out.print("LALALALA");
    //         mIntake.intakeRawSpeed(0, 0);
    //         doneShooting = false;
    //         count = 0;
    //         mLastShootState = true;
    //         mShooter.stop();
    //         mShooter.zeroSensors();
    //         currentStateIndex += 2;
    //         setCurrentState(nextStateArray[currentStateIndex]);
    //     }
    // }

    private void shoot(int numCells) {
        
        if (this.count < 200) {            
            
            mShooter.BangBangControl(5000);
            mShooter.controlHood(true);
            if (Math.abs(mShooter.getShooterSpeed() - 5000) < 100) {
                count++;
                mShooter.controlAcc(1.0);
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
                mIntake.intakeRawSpeed(Constants.INTAKE_ROLLER_SPEED, Constants.INTAKE_ROLLER_SPEED);
            }

        } else {
            mIntake.intakeRawSpeed(0, 0);
            mShooter.controlAcc(0);
            mShooter.stop();
            count = 0;
            mShooter.zeroSensors();
            currentStateIndex += 2;
            setCurrentState(nextStateArray[currentStateIndex]);
            mTimer.stop();
        }
    }

    private void intake(int inWay) {
        mTimer.start();
        if (mTimer.get() < 5) {
            if (inWay > 0) {
                mIntake.frontIntake(false);
                mIntake.intakeRawSpeed(0.5, 0.5);
            } else {
                mIntake.backIntake(false);
                mIntake.intakeRawSpeed(0.5, 0.5);
            }

            if (!mIntake.getAccPhoto()) {
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
            } else {
                mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
            }
        } 
    }
}
