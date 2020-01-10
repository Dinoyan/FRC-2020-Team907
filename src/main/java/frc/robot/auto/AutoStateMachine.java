/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

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

    private byte currentState;
    private byte currentStateIndex = 0;
    private byte[] nextStateArray = new byte[255];

    private boolean mStop = false;

    public static AutoStateMachine getInstance() {
        if (mInstance == null) {
            mInstance = new AutoStateMachine();
        }
        return mInstance;
    }

    public void init(byte selection) {

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
        
        return mStop;
    }

    // **************************************
    // *********** AUTO ACTIONS *************
    // **************************************

    private void drive(double distance) {

    }

    private void turn(double angle) {

    }

    private void intake() {

    }

    private void shoot() {
        
    }
    
}
