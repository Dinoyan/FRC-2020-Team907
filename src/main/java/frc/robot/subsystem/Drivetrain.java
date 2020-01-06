/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

    private static Drivetrain mInstance = null;

    private double mRightDistance = 0.0;
    private double mLeftDistance = 0.0;
    private double mAngle = 0.0;

    public static Drivetrain getInstance() {
        if (mInstance == null) {
            mInstance = new Drivetrain();
        }
        return mInstance;
    }


    @Override
    public void init() {
        // TODO Auto-generated method stub

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

    public void drive(double left, double right) {

    }

    public double getRightDistance() {
        return mRightDistance;
    }

    public double getLeftDistance() {
        return mLeftDistance;
    }

    public double getAngle() {
        return mAngle;
    }

    public void switchToBrake() {

    }

    public void switchToCoast() {
        
    }
}
