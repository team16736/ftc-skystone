package org.firstinspires.ftc.atomic.gobilda.actions.unused;

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
public class MecanumDriveWheelEncoderActions {

    public DcMotor leftFrontMotor;
    public DcMotor rightFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightBackMotor;

    //the amount to throttle the power of the motors
    private static final double THROTTLE = 0.8;

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    /**
     * Creates a mecanum motor using the 4 individual motors passed in as the arguments
     * @param tele : Telemetry to send messages to the Driver Control
     * @param hardware : Hardware Mappings
     */
    // Constructor
    public MecanumDriveWheelEncoderActions(Telemetry tele, HardwareMap hardware ) {

        this.telemetry = tele;
        this.hardwareMap = hardware;

        // 1. Hardware config
        leftFrontMotor = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_LEFT);
        rightFrontMotor = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_RIGHT);
        rightBackMotor = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_RIGHT);
        leftBackMotor = hardwareMap.get(DcMotor.class, ConfigConstants.BACK_LEFT);

        // 2. Set direction
        setMotorDirection_Forward();

        // 3. Set Brake
        applyBrake();
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

        telemetry.addData("Strafe Speed X: = ", speedX);
        telemetry.addData("Motion Speed Y: = ", speedY);
        telemetry.addData("Rotations: = ", rotation);
        telemetry.update();

        double backLeftValue = speedX + speedY + rotation;
        double frontLeftValue = -speedX + speedY + rotation;
        double backRightValue = speedX + speedY - rotation;
        double frontRightValue = -speedX + speedY - rotation;

        double max = getMaxPower(frontLeftValue, frontRightValue, backLeftValue, backRightValue);
        if (max > 1) {
            frontLeftValue = frontLeftValue / max;
            frontRightValue = frontRightValue / max;
            backLeftValue = backLeftValue / max;
            backRightValue = backRightValue / max;
        }

        rightFrontMotor.setPower(frontRightValue);
        leftFrontMotor.setPower(frontLeftValue);
        rightBackMotor.setPower(backRightValue);
        leftBackMotor.setPower(backLeftValue);

        telemetry.addData("frontRightValue: = ", frontRightValue);
        telemetry.addData("frontLeftValue: = ", frontLeftValue);
        telemetry.addData("backRightValue: = ", backRightValue);
        telemetry.addData("backLeftValue: = ", backLeftValue);
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
    public void driveByDistance(int inches, int direction, double speed){

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

//        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        left_back.setTargetPosition(revolutions);
//        left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        int currentPosition = left_back.getCurrentPosition();
//        printLogOnDriverPhone("Wheel current position: " + currentPosition);
//
//        int targetPosition = left_back.getTargetPosition();
//        printLogOnDriverPhone("Wheel target Position: " + targetPosition);
//
//        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        right_back.setTargetPosition(revolutions);
//        right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slide.setTargetPosition(revolutions);
//        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        right_front.setTargetPosition(revolutions);
//        right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        slide.setPower(power);
//        right_back.setPower(power);
//        left_back.setPower(power);
//        right_front.setPower(power);
    }

    //Added 12/14 - not tested
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
     * Returns if atleast one of the wheels is moving
     * @return true if the robot is moving
     */
    public boolean isMoving() {
        return leftFrontMotor.isBusy()
                || rightFrontMotor.isBusy()
                || rightBackMotor.isBusy()
                || leftBackMotor.isBusy();
    }

    private double getMaxPower(double frontLeftValue, double frontRightValue, double backLeftValue, double backRightValue) {
        List<Double> valueList = new LinkedList<>();
        valueList.add(frontLeftValue);
        valueList.add(frontRightValue);
        valueList.add(backLeftValue);
        valueList.add(backRightValue);

        return Collections.max(valueList);
    }

    //Added 12/14 - not tested
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

    public void setMotorDirection_Forward() {
        leftBackMotor.setDirection(MotorConstants.REVERSE);
        rightBackMotor.setDirection(MotorConstants.REVERSE);
        leftFrontMotor.setDirection(MotorConstants.REVERSE);
        rightFrontMotor.setDirection(MotorConstants.FORWARD);
    }

    public void setMotorDirection_Reverse() {
        leftBackMotor.setDirection(MotorConstants.FORWARD);
        rightBackMotor.setDirection(MotorConstants.FORWARD);
        leftFrontMotor.setDirection(MotorConstants.FORWARD);
        rightFrontMotor.setDirection(MotorConstants.REVERSE);
    }

    public void setMotorDirection_StrafeLeft() {
        leftBackMotor.setDirection(MotorConstants.FORWARD);
        rightBackMotor.setDirection(MotorConstants.FORWARD);
        leftFrontMotor.setDirection(MotorConstants.REVERSE);
        rightFrontMotor.setDirection(MotorConstants.FORWARD);
    }

    public void setMotorDirection_StrafeRight() {
        leftBackMotor.setDirection(MotorConstants.REVERSE);
        rightBackMotor.setDirection(MotorConstants.REVERSE);
        leftFrontMotor.setDirection(MotorConstants.FORWARD);
        rightFrontMotor.setDirection(MotorConstants.REVERSE);
    }

    public void stop() {
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
    }

    public void applyBrake() {
        leftBackMotor.setZeroPowerBehavior(MotorConstants.BRAKE);
        rightBackMotor.setZeroPowerBehavior(MotorConstants.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(MotorConstants.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(MotorConstants.BRAKE);
    }

    public void forwardByTime(LinearOpMode opMode, double speed, double drivingTime) {
        leftBackMotor.setPower(speed);
        rightBackMotor.setPower(speed);
        rightFrontMotor.setPower(speed);
        leftFrontMotor.setPower(speed);
        opMode.sleep((long)(1000 * drivingTime));
    }

    public void reverseByTime(LinearOpMode opMode, double speed, double drivingTime) {
        leftBackMotor.setPower(-speed);
        rightBackMotor.setPower(-speed);
        leftFrontMotor.setPower(-speed);
        rightFrontMotor.setPower(-speed);
        opMode.sleep((long)(1000 * drivingTime));
    }

    public void strafeRightByTime(LinearOpMode opMode, double speed, double drivingTime) {
        leftBackMotor.setPower(-speed);
        rightBackMotor.setPower(speed);
        leftFrontMotor.setPower(speed);
        rightFrontMotor.setPower(-speed);
        opMode.sleep((long)(1000 * drivingTime));
    }

    public void strafeLeftByTime(LinearOpMode opMode, double speed, double drivingTime) {
        leftFrontMotor.setPower(-speed);
        rightFrontMotor.setPower(speed);
        leftBackMotor.setPower(speed);
        rightBackMotor.setPower(-speed);
        opMode.sleep((long)(1000*drivingTime));
    }

    public void forwardByDistance(LinearOpMode opMode, double speed, double drivingTime) {
        leftBackMotor.setPower(speed);
        rightBackMotor.setPower(speed);
        rightFrontMotor.setPower(speed);
        leftFrontMotor.setPower(speed);
        opMode.sleep((long)(1000 * drivingTime));
    }

}