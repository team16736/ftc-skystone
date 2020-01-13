package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.atomic.gobilda.utilities.MotorConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Make sure to have the following:
 *
 * 1. Hardware config
 * 2. Setup direction of motors
 * 3. Action method to do something (hookUpDown, drive, etc.,)
 * 4. Helper methods (stop, brake, leftTurn, rightTurn, etc.,)
 *
 * Purpose: Drive the 4 wheels
 */
public class DriveWheelActions {

    public DcMotor left_front;
    public DcMotor right_front;
    public DcMotor left_back;
    public DcMotor right_back;

    //the amount to throttle the power of the motors
    public double THROTTLE = 0.5;

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    public boolean applySensorSpeed = false;

    /**
     * Creates a mecanum motor using the 4 individual motors passed in as the arguments
     * @param opModeTelemetry : Telemetry to send messages to the Driver Control
     * @param opModeHardware : Hardware Mappings
     */
    // Constructor
    public DriveWheelActions(Telemetry opModeTelemetry, HardwareMap opModeHardware ) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        left_front = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_LEFT);
        right_front = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_RIGHT);

        right_back = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_RIGHT);
        left_back = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_LEFT);

        // 2. Set direction
        setMotorDirection_Forward();
    }

    public void setSpeed(double mySpeed){
        THROTTLE = mySpeed;
    }


    /**
     * Drive method to throttle the power
     * @param speedX - the x value of the joystick controlling strafe
     * @param speedY - the y value of the joystick controlling the forward/backward motion
     * @param rotation - the x value of the joystick controlling the rotation
     */
    public void drive(double speedX, double speedY, double rotation){

        double throttledX = speedX * THROTTLE;
        double throttledY = speedY * THROTTLE;
        double throttledRotation = rotation * THROTTLE;

        driveUsingJoyStick(throttledX, throttledY, throttledRotation);
    }

    /**
     * This function makes the mecanum motor drive using the joystick
     * @param speedX - the x value of the joystick controlling strafe
     * @param speedY - the y value of the joystick controlling the forward/backwards motion
     * @param rotation - the x value of the joystick controlling the rotation
     */
    public void driveUsingJoyStick(double speedX, double speedY, double rotation) {

        double backLeftValue = -speedX + speedY + rotation;
        double frontLeftValue = speedX + speedY + rotation;

        double backRightValue = speedX + speedY - rotation;
        double frontRightValue = -speedX + speedY - rotation;

        double max = getMaxPower(frontLeftValue, frontRightValue, backLeftValue, backRightValue);
        if (max > 1) {
            frontLeftValue = frontLeftValue / max;
            frontRightValue = frontRightValue / max;
            backLeftValue = backLeftValue / max;
            backRightValue = backRightValue / max;
        }

        right_front.setPower(frontRightValue);
        left_front.setPower(frontLeftValue);
        right_back.setPower(backRightValue);
        left_back.setPower(backLeftValue);
    }

    private double getMaxPower(double frontLeftValue, double frontRightValue, double backLeftValue, double backRightValue) {
        List<Double> valueList = new LinkedList<>();
        valueList.add(frontLeftValue);
        valueList.add(frontRightValue);
        valueList.add(backLeftValue);
        valueList.add(backRightValue);

        return Collections.max(valueList);
    }

    public void setMotorDirection_Forward() {
        left_back.setDirection(MotorConstants.REVERSE);
        left_front.setDirection(MotorConstants.REVERSE);

        right_back.setDirection(MotorConstants.REVERSE);
        right_front.setDirection(MotorConstants.FORWARD);
    }

    public void setMotorDirection_Reverse() {
        left_back.setDirection(MotorConstants.FORWARD);
        left_front.setDirection(MotorConstants.FORWARD);

        right_back.setDirection(MotorConstants.FORWARD);
        right_front.setDirection(MotorConstants.REVERSE);
    }

    public void setMotorDirection_StrafeLeft() {
        left_back.setDirection(MotorConstants.REVERSE);
        left_front.setDirection(MotorConstants.FORWARD);

        right_back.setDirection(MotorConstants.FORWARD);
        right_front.setDirection(MotorConstants.FORWARD);
    }

    public void setMotorDirection_StrafeRight() {
        left_back.setDirection(MotorConstants.FORWARD);
        left_front.setDirection(MotorConstants.REVERSE);

        right_back.setDirection(MotorConstants.REVERSE);
        right_front.setDirection(MotorConstants.REVERSE);
    }

    public void setMotorDirection_SpinLeft() {
        left_back.setDirection(MotorConstants.FORWARD);
        left_front.setDirection(MotorConstants.FORWARD);

        right_back.setDirection(MotorConstants.REVERSE);
        right_front.setDirection(MotorConstants.FORWARD);
    }

    public void setMotorDirection_SpinRight() {
        left_back.setDirection(MotorConstants.REVERSE);
        left_front.setDirection(MotorConstants.REVERSE);

        right_back.setDirection(MotorConstants.FORWARD);
        right_front.setDirection(MotorConstants.REVERSE);
    }

    public void stop() {
        left_front.setPower(0);
        right_front.setPower(0);
        left_back.setPower(0);
        right_back.setPower(0);
    }

    public void applyBrake() {
        left_back.setZeroPowerBehavior(MotorConstants.BRAKE);
        right_back.setZeroPowerBehavior(MotorConstants.BRAKE);
        left_front.setZeroPowerBehavior(MotorConstants.BRAKE);
        right_front.setZeroPowerBehavior(MotorConstants.BRAKE);
    }

    public void driveByTime(LinearOpMode opMode, double speed, double drivingTime) {

        left_back.setPower(speed);
        right_back.setPower(speed);
        right_front.setPower(speed);

        if(applySensorSpeed){

            left_front.setPower(speed * 1.1); //Speed needed for sensor

        } else {

            left_front.setPower(speed);  //Speed needed for hooks (this is our normal speed)

        }

        opMode.sleep((long)(1000 * drivingTime));
    }


    /**
     * Method to drive a specified distance using motor encoder functionality
     *
     * @param inches - The Number Of Inches to Move
     * @param direction - The Direction to Move
     *                  - Valid Directions:
     *                  - MecanumDrivetrain.DIRECTION_FORWARD
     *                  - MecanumDrivetrain.DIRECTION_REVERSE
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_LEFT
     *                  - MecanumDrivetrain.DIRECTION_STRAFE_RIGHT
     * @param speed - The desired motor power (most accurate at low powers < 0.25)
     */
    public void driveByInches(int inches, int direction, double speed){

        setMotorDirection(direction);

        driveByRevolution(convertDistanceToTarget(inches, direction) * 3, speed);
    }

    /**
     * Method will motors a specified number of revolutions at the desired power
     * agnostic of direction.
     *
     * @param revolutions - the number of motor encoder ticks to move
     * @param power - the speed at which to move
     */
    private void driveByRevolution(int revolutions, double power){

        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_front.setTargetPosition(revolutions);
        left_back.setTargetPosition(revolutions);
        right_front.setTargetPosition(revolutions);
        right_back.setTargetPosition(revolutions);

        left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left_front.setPower(power);
        left_back.setPower(power);
        right_front.setPower(power);
        right_back.setPower(power);

        telemetry.addData("Current Position: ",
                "left_front: ", left_front.getCurrentPosition(),
                        "left_back: ", left_back.getCurrentPosition(),
                        "right_front: ", right_front.getCurrentPosition(),
                        "right_back: ", right_back.getCurrentPosition());
        telemetry.update();
    }

    //NOT TESTED
    private int convertDistanceToTarget(int inches, int direction){

        float target;

        if (direction == MotorConstants.DIRECTION_FORWARD
                || direction == MotorConstants.DIRECTION_REVERSE){

            target = inches * MotorConstants.ENCODER_CLICKS_FORWARD_1_INCH;

        } else{
            target = inches * MotorConstants.ENCODER_CLICKS_STRAFE_1_INCH;
        }

        return Math.round(target);
    }

    /**
     * Returns true if the robot is moving
     */
    public boolean isMoving() {

        return left_front.isBusy() || left_back.isBusy() || right_front.isBusy() || right_back.isBusy();
    }

    //NOT TESTED
    private void setMotorDirection(int direction){

        if (direction == MotorConstants.DIRECTION_REVERSE){

            setMotorDirection_Reverse();

        } else if (direction == MotorConstants.DIRECTION_STRAFE_LEFT){

            setMotorDirection_StrafeLeft();

        } else if (direction == MotorConstants.DIRECTION_STRAFE_RIGHT){

            setMotorDirection_StrafeRight();

        } else {

            setMotorDirection_Forward();
        }
    }



}