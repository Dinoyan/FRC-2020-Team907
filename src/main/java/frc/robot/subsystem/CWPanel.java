/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.util.CyberPID;

/**
 * Add your docs here.
 */
public class CWPanel extends Subsystem {

    private static CWPanel mInstance = null;

    private char mDColour = '?';

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 mColourSensor = new ColorSensorV3(i2cPort);
    
    private final ColorMatch mcolourMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    WPI_TalonSRX mCWMotor = new WPI_TalonSRX(Constants.CW_CAN);

    private CyberPID mCW_PID;

    boolean mStagedFinished = false;

    public static CWPanel getInstance() {
        if (mInstance == null) {
            mInstance = new CWPanel();
        }
        return mInstance;
    }

    @Override
    public void init() {
        mcolourMatcher.addColorMatch(kBlueTarget);
        mcolourMatcher.addColorMatch(kGreenTarget);
        mcolourMatcher.addColorMatch(kRedTarget);
        mcolourMatcher.addColorMatch(kYellowTarget); 

        mCWMotor.configFactoryDefault();

        mCW_PID = new CyberPID();
        mCW_PID.reset();
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {
        mCWMotor.stopMotor();
    }

    @Override
    public Boolean checkSystem() {
        return null;
    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("CW Finished", mStagedFinished);
    }

    // Read enc -> PID -> output
    public void rotate() {
        mCW_PID.setSetpoint(4);
        boolean onTarget = mCW_PID.onTarget(getWheelEnc());

        if (!onTarget) {
            mStagedFinished = false;
            onTarget = mCW_PID.onTarget(getWheelEnc());
            double value = mCW_PID.getOutput(getWheelEnc());
            manualControl(value);
        } else {
            manualControl(0);
            mStagedFinished = true;
        }
    }

    public boolean stagedFinished() {
        return mStagedFinished;
    }

    // Get the colour -> rotate to colour
    public void posToColour(char colour) {

    }

    public double getWheelEnc() {
        return 0.0;
    }

    public void manualControl(double speed) {  
        mCWMotor.set(speed);
    }

    public char getDetectedColour() {
        Color detectedColor = mColourSensor.getColor();
        ColorMatchResult match = mcolourMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            mDColour = 'B';
        }
        return mDColour;
    }
}
