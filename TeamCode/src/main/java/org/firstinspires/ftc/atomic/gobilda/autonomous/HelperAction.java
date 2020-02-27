package org.firstinspires.ftc.atomic.gobilda.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;

public abstract class HelperAction extends LinearOpMode {

    protected ColorSensor right_sensor;
    protected ColorSensor left_sensor;
    protected boolean foundStone = false;
    protected float hsvValues[] = {0F,0F,0F};

    public final double SPEED = 0.5;

    public void drive_ReverseAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        
        driveWheelActions.setMotorDirection_Reverse();
        driveWheelActions.driveByTime(this, speed, drivingTime);
        driveWheelActions.stop();
    }

    public void drive_ForwardAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        
        driveWheelActions.setMotorDirection_Forward();
        driveWheelActions.driveByTime(this, speed, drivingTime);
        driveWheelActions.stop();
    }

    public void strafe_RightAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        
        driveWheelActions.setMotorDirection_StrafeRight();
        driveWheelActions.driveByTime(this, speed, drivingTime);
        driveWheelActions.stop();
    }

    public void strafe_LeftAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        
        driveWheelActions.setMotorDirection_StrafeLeft();
        driveWheelActions.driveByTime(this, speed, drivingTime);
        driveWheelActions.stop();

        telemetry.addData("strafe_LeftAndStop: ", "-->strafe_LeftAndStop");
        telemetry.update();

    }
    public void spin_LeftAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        driveWheelActions.setMotorDirection_SpinLeft();
        driveWheelActions.driveByTime(this, speed, drivingTime);

    }

    public void spin_RightAndStop(DriveWheelActions driveWheelActions, double speed, double drivingTime) {
        driveWheelActions.setMotorDirection_SpinRight();
        driveWheelActions.driveByTime(this, speed, drivingTime);
    }

    public void armUpAndStop(ArmElbowGripperActions armElbowGripperActions, double speed, double drivingTime) {
        
        armElbowGripperActions.setMotorDirection_ArmUp();
        armElbowGripperActions.driveByTime(this, speed, drivingTime);
        sleep(1000);
        armElbowGripperActions.brake();
    }
    
    public void armDownAndStop(ArmElbowGripperActions armElbowGripperActions, double speed, double drivingTime) {
        
        armElbowGripperActions.setMotorDirection_ArmDown();
        armElbowGripperActions.driveByTime(this, speed, drivingTime);
        sleep(1000);
        armElbowGripperActions.brake();
    }
    
    public void elbowCompletelyOpen(ArmElbowGripperActions armElbowGripperActions) {
        armElbowGripperActions.setElbowPosition_open();
        sleep(500);
    }
    
    public void grabberCompletelyOpen(ArmElbowGripperActions armElbowGripperActions) {
        armElbowGripperActions.setGrabberPosition_open();
        sleep(500);
    }
    
    public void grabberCompletelyClosed(ArmElbowGripperActions armElbowGripperActions) {
        armElbowGripperActions.setGrabberPosition_closed();
        sleep(500);
    }
    
    public boolean isThisSkystone(ColorSensor colorSensor, float hsvValues[]){

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        telemetry.addData("Hue", hsvValues[0]);

        if(hsvValues[0] < 50){

            telemetry.addData("YELLOW block : ", System.currentTimeMillis());
            telemetry.update();
            return false;

        } else {

            telemetry.addData("BLACK block : ", System.currentTimeMillis());
            telemetry.update();
            return true;
        }
    }


    // Step --> detect Skystone using "left_sensor".
    // If a skystone is detected, then stone 1,4 are the skystones
    // If a skystone is not detected, then use right sensor. If yes, then stone 2, 5 are the skystones
    // If a skystone is still not detected, assume stone 3, 6 are the skystones

    public StoneColors identify_Blue_Quarry_Colors(ColorSensor left_color_sensor,
                                                   ColorSensor right_color_sensor,
                                                   float hsvValues[])
    {
        boolean left_sensor_result = isThisSkystone(left_color_sensor, hsvValues);
        boolean right_sensor_result = isThisSkystone(right_color_sensor, hsvValues);

        StoneColors stoneColors = new StoneColors();

        if(left_sensor_result){

            stoneColors.setStone_1(true);
            stoneColors.setStone_4(true);
        }
        else if (right_sensor_result){

            stoneColors.setStone_2(true);
            stoneColors.setStone_5(true);

        } else{

            stoneColors.setStone_3(true);
            stoneColors.setStone_6(true);
        }

        telemetry.addData("sky-stone 1 is Black: ", "" + stoneColors.isStone_1());
        telemetry.addData("sky-stone 2 is Black: ", "" + stoneColors.isStone_2());
        telemetry.addData("sky-stone 3 is Black: ", "" + stoneColors.isStone_3());
        telemetry.addData("sky-stone 4 is Black: ", "" + stoneColors.isStone_4());
        telemetry.addData("sky-stone 5 is Black: ", "" + stoneColors.isStone_5());
        telemetry.addData("sky-stone 6 is Black: ", "" + stoneColors.isStone_6());
        telemetry.update();

        return stoneColors;
    }

    // Step --> detect Skystone using "right_sensor".
    // If a skystone is detected, then stone 1,4 are the skystones
    // If a skystone is not detected, then use right sensor. If yes, then stone 2, 5 are the skystones
    // If a skystone is still not detected, assume stone 3, 6 are the skystones

    public StoneColors identify_Red_Quarry_Colors(ColorSensor left_color_sensor,
                                                  ColorSensor right_color_sensor,
                                                  float hsvValues[])
    {
        boolean left_sensor_result = isThisSkystone(left_color_sensor, hsvValues);
        boolean right_sensor_result = isThisSkystone(right_color_sensor, hsvValues);

        StoneColors stoneColors = new StoneColors();

        if(right_sensor_result){

            stoneColors.setStone_1(true);
            stoneColors.setStone_4(true);
        }
        else if (left_sensor_result){

            stoneColors.setStone_2(true);
            stoneColors.setStone_5(true);
        } else{

            stoneColors.setStone_3(true);
            stoneColors.setStone_6(true);
        }

        telemetry.addData("sky-stone 1 is Black: ", "" + stoneColors.isStone_1());
        telemetry.addData("sky-stone 2 is Black: ", "" + stoneColors.isStone_2());
        telemetry.addData("sky-stone 3 is Black: ", "" + stoneColors.isStone_3());
        telemetry.addData("sky-stone 4 is Black: ", "" + stoneColors.isStone_4());
        telemetry.addData("sky-stone 5 is Black: ", "" + stoneColors.isStone_5());
        telemetry.addData("sky-stone 6 is Black: ", "" + stoneColors.isStone_6());
        telemetry.update();

        return stoneColors;
    }

}