package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.atomic.gobilda.utilities.MotorConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Purpose:
 * 1. Arm move up and down
 * 2. Elbow open and close
 * 3. Grabber open and close
 */
public class SlideElbowGripperActions {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    private DcMotor slide_motor;
    private Servo elbow_servo;
    private Servo grabber_servo;

    private int arm_current_position = 50;

    private double grabber_position = 0.0;
    private double elbow_position = 0.0;

    public SlideElbowGripperActions(Telemetry opModeTelemetry, HardwareMap opModeHardware) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        slide_motor = hardwareMap.get(DcMotor.class, ConfigConstants.ARM);
        elbow_servo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        grabber_servo = hardwareMap.get(Servo.class, ConfigConstants.GRABBER_SERVO);

        // 2. Set direction
        slide_motor.setDirection(DcMotor.Direction.REVERSE);
        elbow_servo.setDirection(Servo.Direction.REVERSE);
        grabber_servo.setDirection(Servo.Direction.FORWARD);
    }

    public void armUpDown(boolean armUp, boolean armDown) {

        slide_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_current_position = slide_motor.getCurrentPosition();
        telemetry.addData("Arm CURRENT: ", slide_motor.getCurrentPosition() + " Timestamp: " + System.currentTimeMillis());

        if(armUp) {

            arm_current_position = arm_current_position + 10;

            slide_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide_motor.setTargetPosition(arm_current_position);
            slide_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide_motor.setPower(1.0);

            telemetry.addData("Arm: ", "UP");

        } else if(armDown) {

            arm_current_position = arm_current_position - 10;

            slide_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide_motor.setTargetPosition(arm_current_position);
            slide_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide_motor.setPower(0.5);
            telemetry.addData("Arm: ", "DOWN");
        }

        telemetry.addData("Arm TARGET: ", arm_current_position + " Timestamp: " + System.currentTimeMillis());
    }

    /**
     * Open to MAX position
     * Close to MIN position
     *
     * @param grabberClose
     * @param grabberOpen
     */
    public void grabberOpenClose(boolean grabberOpen, boolean grabberClose) {

        if (grabberClose) {

            grabber_position = 1.0;
            grabber_servo.setPosition(grabber_position);

        } else if (grabberOpen) {

            grabber_position = 0;
            grabber_servo.setPosition(grabber_position);
        }

    }


    public void elbowOpenClose(boolean elbowOpen, boolean elbowClose) {

        if (elbowClose) {

            elbow_position = elbow_position + 0.2;
            elbow_servo.setPosition(Range.clip(elbow_position, 0, 1.0));

        } else if (elbowOpen) {

            elbow_position = elbow_position - 0.2;
            elbow_servo.setPosition(Range.clip(elbow_position, 0, 1.0));
        }

        telemetry.addData("Method - ", "elbowOpenClose");
        telemetry.update();
    }


    public void elbow_FullOpen_FullClose(boolean elbowOpen, boolean elbowClose) {

        if (elbowClose) {

            elbow_position = 1.0;
            elbow_servo.setPosition(elbow_position);

        } else if (elbowOpen) {

            elbow_position = 0.0;
            elbow_servo.setPosition(elbow_position);
        }
        telemetry.addData("Method - ", "elbow_FullOpen_FullClose");
        telemetry.update();
    }


    private double old_joystick_value = 0;

    // Code modified - 1/22
    public void armUpDown_LinearSlide(double new_joystick_value) {

        telemetry.addData("Method: armUpDown_Linear(): ", "" + System.currentTimeMillis());
        telemetry.addData("new_joystick_value: ", new_joystick_value);
        telemetry.addData("old_joystick_value: ", old_joystick_value);

        double slide_power = Range.clip(new_joystick_value, -0.5, 0.5);


        if(new_joystick_value < old_joystick_value){

            slide_motor.setPower(slide_power * 0.1);  //Going down use 60% of power

        } else {

            slide_motor.setPower(slide_power); //Going up use 100% of power
        }

        old_joystick_value = new_joystick_value;

        telemetry.addData("SLIDE POWER: ", slide_power);
        telemetry.update();
    }


    public void stop_slide() {

        slide_motor.setPower(0);
    }

    public void brake_slide() {

        slide_motor.setZeroPowerBehavior(MotorConstants.BRAKE);
    }


}
