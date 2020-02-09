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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    Compressor mCompressor = new Compressor();

    // shooter states
    private enum mShooterState {
        SHOOT,
        ALIGN,
        WAIT_FOR_VEL,
        MOVE_CONVEYOR,
        MOVE_INTAKE,
        LIFT_HOOD,
        STOP
    };

    // boolean mFrontIntakeState = true;
    // boolean mBackIntakeState = true;

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
        // mHook = Hook.getInstance();
        mCWPanel = CWPanel.getInstance();

        mJoystick = JoystickHandler.getInstance();

        mLimelight = VisionTracking.getInstance();
        
        mCompressor.start();

        teleopTime.start();
    }

    @Override
    public void driveEnabledLoop() {
        double left = mJoystick.getDriveLeft();
        double right = mJoystick.getDriveRight();

        mDrive.drive(left, right);
    }

    @Override
    public void superstructureEnabledLoop() {
        intakeEnabledLoop();
        shooterEnabledLoop();
        hookEnabledLoop();
        CWEnabledLoop();
    }

    private void intakeEnabledLoop() {
        // intaking cell
        if (mJoystick.getFrontIntake()) {
            mIntake.frontIntake(false);
            mIntake.intakeCell(Constants.INTAKE_ROLLER_SPEED);
        } else {
            mIntake.frontIntake(true);
            mIntake.intakeCell(0);
        }

        if (mJoystick.getBackIntake()) {
            mIntake.backIntake(false);
            mIntake.intakeCell(Constants.INTAKE_ROLLER_SPEED);
        } else {
            mIntake.backIntake(true);
            mIntake.intakeCell(0);
        }

        // eject cell
        if (mJoystick.getEject()) {
            mIntake.intakeCell(Constants.VOMIT_SPEED);
        }
    }

    private void shooterEnabledLoop() {
        // manual shooter
        double shootValue = mJoystick.getManuallyShoot();

        if (shootValue > 0.1) {
            mShooter.controlHood(true);
            mShooter.shootCellOpen(shootValue);
            mIntake.conveyorControl(Constants.CONTROL_CONVEYOR_SPEED);
            mIntake.intakeCell(0.2);
            mCompressor.stop();
        } else {
            mShooter.controlHood(false);
            mIntake.conveyorControl(Constants.DEFAULT_CONVEYOR_SPEED);
            mShooter.shootCellOpen(Constants.DEFAULT_SHOOTER_SPEED);
            mCompressor.start();
        }

        shooterStateController(mShooterState.SHOOT);

    }

    private void shooterStateController(mShooterState state) {
        switch(state) {
            case SHOOT:
                break;
            case ALIGN:
                break;
            case WAIT_FOR_VEL:
                break;
            case MOVE_CONVEYOR:
                break;
            case MOVE_INTAKE:
                break;
            case LIFT_HOOD:
                break;
            case STOP:
             break;
        }
    }

    private void hookEnabledLoop() {
        // if (teleopTime.get() > 120) {
        //     mHook.pullUp(mJoystick.getHookAxis());
        // }
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

    // get data from fms
    private char getFMSColour() {
        char _colour = '?';
        mGameData = DriverStation.getInstance().getGameSpecificMessage();
        _colour = mGameData.charAt(0);

        SmartDashboard.putString("FMS Data", "test");
        return _colour;
    }
}
