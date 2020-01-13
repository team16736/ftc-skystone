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
public class LinearSlideActions {

    public DcMotor left_front;

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
    public LinearSlideActions(Telemetry opModeTelemetry, HardwareMap opModeHardware ) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        left_front = hardwareMap.get(DcMotor.class, ConfigConstants.FRONT_LEFT);

        // 2. Set direction
        setMotorDirection_Down();
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

        left_front.setPower(frontLeftValue);
    }

    private double getMaxPower(double frontLeftValue, double frontRightValue, double backLeftValue, double backRightValue) {
        List<Double> valueList = new LinkedList<>();
        valueList.add(frontLeftValue);
        valueList.add(frontRightValue);
        valueList.add(backLeftValue);
        valueList.add(backRightValue);

        return Collections.max(valueList);
    }

    public void setMotorDirection_Down() {

        left_front.setDirection(MotorConstants.REVERSE);
    }

    public void setMotorDirection_Up() {

        left_front.setDirection(MotorConstants.FORWARD);
    }

    public void stop() {

        left_front.setPower(0);
    }

    public void applyBrake() {

        left_front.setZeroPowerBehavior(MotorConstants.BRAKE);
    }

    public void driveByTime(LinearOpMode opMode, double speed, double drivingTime) {

        left_front.setPower(speed);  //Speed needed for hooks (this is our normal speed)

        opMode.sleep((long) (1000 * drivingTime));
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
        left_front.setTargetPosition(revolutions);
        left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_front.setPower(power);

        telemetry.addData("Current Position: ",
                "left_front: ", left_front.getCurrentPosition());
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

        return left_front.isBusy();
    }

    //NOT TESTED
    private void setMotorDirection(int direction){

        if (direction == MotorConstants.DIRECTION_REVERSE){

            setMotorDirection_Up();

        } else {

            setMotorDirection_Down();
        }
    }



}