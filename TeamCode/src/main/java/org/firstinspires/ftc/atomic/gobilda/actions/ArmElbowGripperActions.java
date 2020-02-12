package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.atomic.gobilda.utilities.MotorConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Purpose:
 * 1. Arm move up and down
 * 2. Elbow open and close
 * 3. Grabber open and close
 */
public class ArmElbowGripperActions {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    private DcMotor armMotor;
    private Servo elbowServo;
    private Servo grabberServo;

    private int arm_current_position = 50;

    private double grabber_position = 0.0;
    private double elbow_position = 0.0;

    private DigitalChannel limit_switch;
    DigitalChannel digitalTouch;


    public ArmElbowGripperActions(Telemetry opModeTelemetry, HardwareMap opModeHardware) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        armMotor = hardwareMap.get(DcMotor.class, ConfigConstants.ARM);
        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        grabberServo = hardwareMap.get(Servo.class, ConfigConstants.GRABBER_SERVO);
        limit_switch = hardwareMap.get(DigitalChannel.class, "limit_switch");


        // 2. Set direction
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        elbowServo.setDirection(Servo.Direction.REVERSE);
        grabberServo.setDirection(Servo.Direction.FORWARD);
    }
    public boolean isLimitSwitchPressed(){

        boolean switchPressed = limit_switch.getState();

        if (switchPressed) {

            telemetry.addData("Digital Touch: ", " NOT Pressed different");

        } else {

            telemetry.addData("Digital Touch: ", " Pressed different");
            arm_current_position = arm_current_position - 10;

            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setTargetPosition(arm_current_position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);

            telemetry.addData("Arm: ", "UP");

        }
        telemetry.update();

        return switchPressed;
    }
    public void armUpDown(boolean armUp, boolean armDown) {

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_current_position = armMotor.getCurrentPosition();
        telemetry.addData("Arm CURRENT: ", armMotor.getCurrentPosition() + " Timestamp: " + System.currentTimeMillis());

        if(armUp) {

            arm_current_position = arm_current_position + 10;

            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setTargetPosition(arm_current_position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);

            telemetry.addData("Arm: ", "UP");

        } else if(armDown) {

            arm_current_position = arm_current_position - 10;

            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setTargetPosition(arm_current_position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.5);
            telemetry.addData("Arm: ", "DOWN");


        }

        telemetry.addData("Arm TARGET: ", arm_current_position + " Timestamp: " + System.currentTimeMillis());
    }
    public void driveByTime(LinearOpMode opMode, double speed, double drivingTime) {

        armMotor.setPower(speed);
        opMode.sleep((long)(1000 * drivingTime)); //Make the opMode wait - while it is driving
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
            grabberServo.setPosition(grabber_position);

        } else if (grabberOpen) {

            grabber_position = 0;
            grabberServo.setPosition(grabber_position);
        }

    }


    public void elbowOpenClose(boolean elbowOpen, boolean elbowClose) {

        if (elbowClose) {

            elbow_position = elbow_position + 0.2;
            elbowServo.setPosition(Range.clip(elbow_position, 0, 1.0));

        } else if (elbowOpen) {

            elbow_position = elbow_position - 0.2;
            elbowServo.setPosition(Range.clip(elbow_position, 0, 1.0));
        }

        telemetry.addData("Method - ", "elbowOpenClose");
        telemetry.update();
    }


    public void elbow_FullOpen_FullClose(boolean elbowOpen, boolean elbowClose) {

        if (elbowClose) {

            elbow_position = 1.0;
            elbowServo.setPosition(elbow_position);

        } else if (elbowOpen) {

            elbow_position = 0.0;
            elbowServo.setPosition(elbow_position);
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

            armMotor.setPower(slide_power * 0.1);  //Going down use 60% of power

        } else {

            armMotor.setPower(slide_power); //Going up use 100% of power
        }

        old_joystick_value = new_joystick_value;

        telemetry.addData("SLIDE POWER: ", slide_power);
        telemetry.update();

    }
    public void setMotorDirection_ArmUp() {
        armMotor.setDirection(MotorConstants.FORWARD);
    }
    public void setMotorDirection_ArmDown() {
        armMotor.setDirection(MotorConstants.REVERSE);
    }
    public void brake() {
        armMotor.setPower(0.0);
    }
    public void setElbowPosition_open() {
        elbowServo.setPosition(0.0);
    }
    public void setGrabberPosition_open() {
        grabberServo.setPosition(0.0);
    }
    public void setGrabberPosition_closed() {
        grabberServo.setPosition(1.0);
    }

}
