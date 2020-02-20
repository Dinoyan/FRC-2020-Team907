/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem{

    private static Intake mInstance = null;

    private WPI_TalonSRX mConveyor;

    private WPI_VictorSPX mFrontMotor;
    private WPI_VictorSPX mBackMotor;

    private DoubleSolenoid mFrontPistons;
    private DoubleSolenoid mBackPistons;

    private DigitalInput mFrontPhotoOne;
    private DigitalInput mAccPhoto;
    private DigitalInput mConveyorPhoto;

    public static Intake getInstance() {
        if (mInstance == null) {
            mInstance = new Intake();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mFrontMotor = new WPI_VictorSPX(Constants.INTAKE_MOTORS[0]);
        mBackMotor = new WPI_VictorSPX(Constants.INTAKE_MOTORS[1]);

        mConveyor = new WPI_TalonSRX(Constants.CONVEYOR_MOTOR);

        mFrontPistons = new DoubleSolenoid(Constants.FRONT_PISTONS[0], Constants.FRONT_PISTONS[1]);
        mBackPistons = new DoubleSolenoid(Constants.BACK_PISTONS[0], Constants.BACK_PISTONS[1]);

        mFrontPhotoOne = new DigitalInput(Constants.FRONT_PHOTO[0]);
        mAccPhoto = new DigitalInput(Constants.ACC_PHOTO[0]);
        mConveyorPhoto = new DigitalInput(Constants.CONV_PHOTO[0]);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
        mFrontMotor.stopMotor();
        mBackMotor.stopMotor();
        mConveyor.stopMotor();
    }

    @Override
    public Boolean checkSystem() {
        return true;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Front Intake", getFrontPhoto());
        SmartDashboard.putBoolean("Acc", getConvPhoto());
        SmartDashboard.putBoolean("Conv", getAccPhoto());
    
    }

    public void conveyorControl(double speed) {
        mConveyor.set(speed);
    }

    public void intakeRawSpeed(double frontSpeed, double backSpeed) {
        if (frontSpeed > 0) {
            mFrontMotor.set(frontSpeed);
        }
        
        if (backSpeed > 0) {
            mBackMotor.set(backSpeed);
        }
    }

    public void intakeCell(double speed) {
        if (mFrontPistons.get() == Value.kReverse) {
            mFrontMotor.set(speed);
        }

        if (mBackPistons.get() == Value.kReverse) {
            mBackMotor.set(speed);
        }

    }

    public void ejectCell(double speed) {
        if (mFrontPistons.get() == Value.kForward) {
            mFrontMotor.set(-speed);
        }

        if (mBackPistons.get() == Value.kForward) {
            mBackMotor.set(-speed);
        }
    }

    public void frontIntake(boolean state) {
        if (state) {
            mFrontPistons.set(Value.kForward);
        } else {
            mFrontPistons.set(Value.kReverse);
        }
    }

    public void backIntake(boolean state) {
        if (state) {
            mBackPistons.set(Value.kForward);
        } else {
            mBackPistons.set(Value.kReverse);
        }
    }

    public boolean getFrontPhoto() {
        return mFrontPhotoOne.get();
    }

    public boolean getConvPhoto() {
        return mConveyorPhoto.get();
    }

    public boolean getAccPhoto() {
        return mAccPhoto.get();
    }

}
