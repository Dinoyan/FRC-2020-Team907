/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.teleop;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.JoystickHandler;
import frc.robot.subsystem.CWPanel;
import frc.robot.subsystem.Drivetrain;
import frc.robot.subsystem.Hook;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Shooter;
import frc.robot.util.VisionTracking;

/**
 * Contains all the teleop code
 */
public class ITeleopLooper implements ITeleop {
    private static ITeleopLooper mInstance = null;

    private Drivetrain mDrive;
    private Shooter mShooter;
    private Intake mIntake;
    private Hook mHook;
    private VisionTracking mLimelight;
    private CWPanel mCWPanel;

    private JoystickHandler mJoystick;

    String mGameData = "??";

    boolean mCWButtonPressed = false;

    boolean mReadyToShoot = false;

    Compressor mCompressor = new Compressor();

    double bottomButtonShootValue;
    double topButtonShootValue;


    // aiming constants
    float kP = -0.2f;
    float min_command = 0.09f;

    // shooter states
    private enum mShooterState {
        SHOOT,
        ALIGN,
        WAIT_FOR_VEL,
        MOVE_CONVEYOR,
        MOVE_INTAKE,
        LIFT_HOOD,
        HOOD_DOWN,
        STOP
    };

    private Timer teleopTime = new Timer();

    public static ITeleopLooper getInstance() {
        if (mInstance == null){
            mInstance = new ITeleopLooper();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mDrive = Drivetrain.getInstance();
        mShooter = Shooter.getInstance();
        mIntake = Intake.getInstance();
        mHook = Hook.getInstance();
        mCWPanel = CWPanel.getInstance();

        mJoystick = JoystickHandler.getInstance();

        mLimelight = VisionTracking.getInstance();
        
        mCompressor.start();

        teleopTime.start();

        mLimelight.setLEDMode(3);

        this.initValues();
    }

    private void initValues(){
        bottomButtonShootValue = Constants.BOTTOM_SHOOTER_PRESET_VELOCITY;
        topButtonShootValue = Constants.TOP_SHOOTER_PRESET_VELOCITY;
    }

    @Override
    public void driveEnabledLoop() {
        //this.moveBackward();
        //this.moveForward();

        double left = mJoystick.getDriveLeft();
        double right = mJoystick.getDriveRight();

        mDrive.drive(left, right);

        if (mJoystick.getAutoAlign()) {
            shooterStateController(mShooterState.ALIGN);
            // mLimelight.setLEDMode(3);
        }

        // mLimelight.setLEDMode(1);

    }

    @Override
    public void superstructureEnabledLoop() {
        this.intakeEnabledLoop();
        this.shooterEnabledLoop();
        this.hookEnabledLoop();
        // CWEnabledLoop();
        // this.onThFlyFixesLoop();
    }

    private void intakeEnabledLoop() {
        // intaking cell
        if (mJoystick.getFrontIntake()) {
            mIntake.frontIntake(false);
            mIntake.intakeCell(Constants.INTAKE_ROLLER_SPEED);
            // mIntake.intakeRawSpeed(0.0, Constants.INTAKE_IDLE_SPEED);
            if (!mIntake.getAccPhoto()) {
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
            } else {
                mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
            }
            
        } else {
            mIntake.frontIntake(true);
            // mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
        }

        if (mJoystick.getBackIntake()) {
            mIntake.backIntake(false);
            mIntake.intakeCell(Constants.INTAKE_ROLLER_SPEED);
            if (!mIntake.getAccPhoto()) {
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
            } else {
                mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
            }
        } else {
            mIntake.backIntake(true);
            // mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
        }

        if (!mJoystick.getBackIntake() && !mJoystick.getFrontIntake()) {
            mIntake.intakeCell(0);
            mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
        }

        // eject cell
        if (mJoystick.getEject()) {
            mIntake.ejectCell(-Constants.VOMIT_SPEED);
        } else {
            mIntake.ejectCell(0);
        }
    }

    private void shooterEnabledLoop() {
        // manual shooter
        double shootValue = mJoystick.getManuallyShoot();
    
        if (shootValue > 0.1) {
            mShooter.controlHood(true);

            // mShooter.BangBangControl((6.38 * Math.pow(this.mLimelight.vGetDistance(), 2))
                  //   + (148 * this.mLimelight.vGetDistance()) + 2000);
            // mShooter.shootCellOpen(shootValue);
            mShooter.BangBangControl(7500 * shootValue);

            mCompressor.stop();

            if (mJoystick.getShootNow()) {
                mShooter.controlAcc(1.0);
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
                mIntake.intakeRawSpeed(Constants.INTAKE_ROLLER_SPEED, Constants.INTAKE_ROLLER_SPEED);
            }
        }

        if (mJoystick.getEmergencyShoot() && (mJoystick.getManuallyShoot() < 0.1)) {
            shooterStateController(mShooterState.LIFT_HOOD);
            mShooter.BangBangControl(bottomButtonShootValue);
            mCompressor.stop();

            if (Math.abs(mShooter.getShooterSpeed() - bottomButtonShootValue) < 100) {
                mShooter.controlAcc(1.0);
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
                mIntake.intakeRawSpeed(Constants.INTAKE_ROLLER_SPEED, Constants.INTAKE_ROLLER_SPEED);
            }
        }

        // auto shooter
        if (mJoystick.getShootNow() && (mJoystick.getManuallyShoot() < 0.1)) {
            mCompressor.stop();
            shooterStateController(mShooterState.LIFT_HOOD);
            shooterStateController(mShooterState.WAIT_FOR_VEL);
           
            if (mReadyToShoot) {
                shooterStateController(mShooterState.SHOOT);
            }
        }

        if (!mJoystick.getShootBtn() && (mJoystick.getManuallyShoot() < 0.1) && !mJoystick.getEmergencyShoot()) {
            mShooter.shootCellOpen(Constants.DEFAULT_SHOOTER_SPEED);
            mShooter.controlHood(false);
            mCompressor.start();
            mShooter.controlAcc(0.0);
        }

        // mLimelight.setLEDMode(1);
        // boolean keepSpinning = shootValue > 0.1 || (mJoystick.getEmergencyShoot() || mJoystick.getShootNow();
        // this.keepItSpinning(keepSpinning);
    }
    
    private void shooterStateController(mShooterState state) {
        switch(state) {
            case SHOOT:
                shooterStateController(mShooterState.MOVE_CONVEYOR);
                shooterStateController(mShooterState.MOVE_INTAKE);
                mShooter.controlAcc(1);
                break;
            case ALIGN:
                double mCorrection = mLimelight.getX();
                double adjust = 0.0;

                if (mCorrection > 1.0) {
                    adjust = kP * mCorrection - min_command;
                } else if (mCorrection < 1.0) {
                    adjust = kP * mCorrection + min_command;
                }

                mDrive.drive(adjust * 0.2, adjust * -0.2);
                break;
            case WAIT_FOR_VEL:
                // mLimelight.setLEDMode(3);
                // double desiredVel = (6.38 * Math.pow(this.mLimelight.vGetDistance(), 2))
                // + (148 * this.mLimelight.vGetDistance()) + 2000;
                
                mShooter.BangBangControl(topButtonShootValue);
                
                if (Math.abs(mShooter.getShooterSpeed() - topButtonShootValue) < 100) {
                    mReadyToShoot = true;
                } else {
                    mReadyToShoot = false;
                }
                break;
            case MOVE_CONVEYOR:
                mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
                break;
            case MOVE_INTAKE:
                mIntake.intakeRawSpeed(Constants.INTAKE_ROLLER_SPEED, Constants.INTAKE_ROLLER_SPEED);
                break;
            case LIFT_HOOD:
                mShooter.controlHood(true);
                break;
            case HOOD_DOWN:
                mShooter.controlHood(false);
                break;
            case STOP:
                mShooter.stop();
                mIntake.stop();
                break;
        }
    }

    private void hookEnabledLoop() {
       // mCompressor.stop();
        // mHook.adust(mJoystick.getHookAxis());
        // mHook.pullUp(mJoystick.gw);
        
    }

    private void CWEnabledLoop() {
        char FMScolour = getFMSColour();

        if (mJoystick.getCWButton()) {
            mCWButtonPressed = true;
        }

        // check which stage by checking fms data for colour
        // if colour is null then, in stage 1 or 2
        if (mCWButtonPressed) {
            if (FMScolour == '?') {
                mCWPanel.rotate();
                if (mCWPanel.stagedFinished()) {
                    mCWButtonPressed = false;
                }
            } else {
                mCWPanel.posToColour(getFMSColour());
                if (mCWPanel.stagedFinished()) {
                    mCWButtonPressed = false;
                }
            }
        }
    }

    private void onThFlyFixesLoop(){
        // initalize the rquired buttons
        boolean initButton = mJoystick.getDriveStick().getRawButton(Constants.DEV_INIT);

        double increaseAxis = mJoystick.getShootStick().getRawAxis(3);
        double decreaseAxis = mJoystick.getShootStick().getRawAxis(2);

        boolean increaseCond = (increaseAxis > 0.1) && (decreaseAxis < 0.1);
        boolean decreaseCond = (decreaseAxis > 0.1) && (increaseAxis < 0.1);

        boolean bottomButton = mJoystick.getEmergencyShoot();
        boolean topButton = mJoystick.getShootNow();

        boolean bottomPressed = false;
        boolean topPressed = false;

        if(increaseAxis < 0.1 && decreaseAxis < 0.1){
            bottomPressed = false;
            topPressed = false;
        }

        // Logic to use the dev mode
        if(initButton && bottomButton && !bottomPressed){
            if(increaseCond){
                bottomPressed = true;
                bottomButtonShootValue += Constants.DEV_SHOOT_EDIT_VALUE;
            } 

            if(decreaseCond){
                bottomPressed = true;
                bottomButtonShootValue -= Constants.DEV_SHOOT_EDIT_VALUE;
            }
        }

        if (initButton && topButton && !topPressed){
            if(increaseCond){
                topPressed = true;
                topButtonShootValue += Constants.DEV_SHOOT_EDIT_VALUE;
            }
            
            if(decreaseCond){
                topPressed = true;
                topButtonShootValue -= Constants.DEV_SHOOT_EDIT_VALUE;
            }
        }
        
    }

    private void keepItSpinning(Boolean input){
        double count = 0;

        if(input){
            count=0;
        } 

        if(count > 125 && count < 150){
            shooterStateController(mShooterState.WAIT_FOR_VEL);
        }
        
        count += 0.5;
    }

    public void moveForward(){
        if (mJoystick.getDrvieForward()) {
            mDrive.drive(0.16, 0.16);
        } else {
            mDrive.drive(0, 0);
        }
    }
    public void moveBackward(){
        if (mJoystick.getDrvieForward()) {
            mDrive.drive(-0.16, -0.16);
        } else {
            mDrive.drive(0, 0);
        }
    }

    // get data from fms
    private char getFMSColour() {
        char _colour = '?';
        mGameData = DriverStation.getInstance().getGameSpecificMessage();
        if (mGameData.length() > 1) {
            _colour = mGameData.charAt(0);
        }
        return _colour;
    }
}
