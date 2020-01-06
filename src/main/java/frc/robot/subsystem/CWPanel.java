/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

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

    public static CWPanel getInstance() {
        if (mInstance == null) {
            mInstance = new CWPanel();
        }
        return mInstance;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        mcolourMatcher.addColorMatch(kBlueTarget);
        mcolourMatcher.addColorMatch(kGreenTarget);
        mcolourMatcher.addColorMatch(kRedTarget);
        mcolourMatcher.addColorMatch(kYellowTarget); 
        
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean checkSystem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub

    }

    public void rotate() {

    }

    public void posToColour(char colour) {

    }

    public void getWheelEnc() {

    }

    public void manualControl(double speed) {

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
