/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class VisionTracking {
    private double mAngle;
    private double mDistance;
    private double mX;
    private double mY;
    private double mArea;

    private double kLimelightHeight;
    private double kLimelightAngle;
    private double kGoalHeigh;
   
    NetworkTable mTable;

    private static VisionTracking mInstance = null;

    public static VisionTracking getInstance() {
        if (mInstance == null) {
            mInstance = new VisionTracking();
        }
        return mInstance;
    }

    public VisionTracking() {
        mAngle = 0.0;
        mDistance = 0.0;
        mX = 0.0;
        mY = 0.0;

        mTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public double vGetAngle() {
        return mAngle;
    }

    public double vGetDistance() {
        mDistance = (kGoalHeigh - kLimelightHeight) / Math.tan(kLimelightAngle + this.getY());
        return mDistance;
    }

    public double getX() {
        NetworkTableEntry tx = mTable.getEntry("tx");
        mX = tx.getDouble(0.0);
        return mX;
    }

    public double getY() {
        NetworkTableEntry ty = mTable.getEntry("ty");
        mY = ty.getDouble(0.0);
        return mY;
    }

    public double getArea() {
        NetworkTableEntry ta = mTable.getEntry("ta");
        mArea = ta.getDouble(0.0);
        return mArea;
    }

    public void updateDashboard() {
        
    }
}
