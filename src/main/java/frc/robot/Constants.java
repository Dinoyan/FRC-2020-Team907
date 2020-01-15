/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {

    // ****************** DRIVETRAIN ***********************
    public final static int[] DRIVE_RIGHT = {0, 1};
    public final static int[] DRIVE_LEFT = {2, 3};
    

    // ********************* HOOK **************************
    public final static int HOOK_CAN = 4;


    // ******************** SHOOTER ************************
    public final static int[] SHOOTER_CAN = {5, 6};

    public final static int[] HOOD_PISTON = {6, 7};

    // ******************* CW PANEL ************************
    public final static int CW_CAN = 7;


    // ******************** INTAKE *************************
    public final static int[] INTAKE_MOTORS = {8, 9};
    public final static int[] FRONT_PISTONS = {0, 1};
    public final static int[] BACK_PISTONS = {2, 3};

    public final static double INTAKE_ROLLER_SPEED = 1.0;

    // ****************** JOYSTICKS ************************
    public final static int DRIVE_STICK = 0;
    public final static int SHOOT_STICK = 1;

    public final static int DRIVE_LEFT_AXIS = 1;
    public final static int DRIVE_RIGHT_AXIS = 5;

    public final static int CW_BUTTON = 3;

    public final static int FRONT_INTAKE_PISTON_BTN = 6;
    public final static int BACK_INTAKE_PISTON_BTN = 5;

    public final static int INTAKE_ROLLERS_BTN = 7;

    // ********************* PID ***************************
    public final static double[] DRIVE_PID  = {0.1, 0.1, 0.1};
    public final static double[] TURN_PID = {0.1, 0.1, 0.1};
    public final static double[] CW_PID = {0.1, 0.1, 0.1};
    public final static double[] SHOOTER_PID = {0.1, 0.1, 0.1};


    // ********************* MISC ***************************
    public final static int AUTO_TIME = 15;


}
