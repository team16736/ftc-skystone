package org.firstinspires.ftc.atomic.gobilda.autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;

/**
 * Purpose: Methods for pulling foundation
 */
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
}