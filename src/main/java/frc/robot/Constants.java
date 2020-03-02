/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {

     // ****************** DRIVETRAIN ***********************
     public final static int[] DRIVE_RIGHT = {0, 2};
     public final static int[] DRIVE_LEFT = {2, 3};
     
     // ********************* HOOK **************************
     public final static int HOOK_CAN = 8;
 
     // ******************** SHOOTER ************************
     public final static int[] SHOOTER_CAN = {4, 5};
 
     public final static int[] HOOD_PISTON = {5, 4};
 
     public final static double DEFAULT_CONVEYOR_SPEED = 0.0;
     public final static double DEFAULT_SHOOTER_SPEED = 0.0;
 
     public final static double CONTROL_CONVEYOR_SPEED = 1.0;
 
     public final static int ACC_CAN = 7;

     public final static int BOTTOM_SHOOTER_PRESET_VELOCITY = 4800;
     public final static int TOP_SHOOTER_PRESET_VELOCITY = 5000;
 
     // ******************* CW PANEL ************************
     public final static int CW_CAN = 9;
 
     // ******************** INTAKE *************************
     public final static int[] INTAKE_MOTORS = {0, 1};
     public final static int[] FRONT_PISTONS = {3, 2};
     public final static int[] BACK_PISTONS = {0, 1};
 
     public final static int CONVEYOR_MOTOR = 6;
 
     public final static double INTAKE_ROLLER_SPEED = 0.85;
     public final static double INTAKE_IDLE_SPEED = 0.75;
     public final static double VOMIT_SPEED = -0.5;

     public final static int[] FRONT_PHOTO = {0, 1};
     public final static int[] CONV_PHOTO = {5,3};
     public final static int[] ACC_PHOTO = {2,5};
 
     // ****************** JOYSTICKS ************************
     public final static int DRIVE_STICK = 0;
     public final static int SHOOT_STICK = 1;
 
     public final static int DRIVE_LEFT_AXIS = 1;
     public final static int DRIVE_RIGHT_AXIS = 5;
 
     public final static int CW_BUTTON = 2;
 
     public final static int FRONT_INTAKE = 5;
     public final static int BACK_INTAKE = 6;
     public final static int EJECT = 10;
 
     public final static int SHOOT_AUTO = 4;
     public final static int SHOOT_MANUALLY_AXIS = 3;
     public final static int AUTO_ALIGN = 6;
 
     public final static int CLIMB_AXIS = 1;

     public final static int DEV_INIT = 6;
 
     // ********************* PID ***************************
     public final static double[] DRIVE_PID  = {0.12, 0.01, 0.012};
     public final static double[] TURN_PID = {0.1, 0.0, 0.0};
     public final static double[] CW_PID = {0.1, 0.1, 0.1};
     public final static double[] SHOOTER_PID = {0.1, 0.1, 0.1};
 
     // ********************* MISC ***************************
     public final static int AUTO_TIME = 15;
     public final static int DEV_SHOOT_EDIT_VALUE = 100;

}
