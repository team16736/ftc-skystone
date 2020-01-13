package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.MotorConstants;

/**
 * Purpose:
 * Go forward and park under bridge
 */
@Autonomous(name = "Encoder Test", group = "GoBilda")
public class EncoderTest extends LinearOpMode {

    public double SLOW_SPEED = 0.25;

    @Override
    public void runOpMode() {

        DriveWheelActions driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);

        waitForStart();

        //FORWARD
        driveWheelActions.driveByInches(6, MotorConstants.DIRECTION_FORWARD, SLOW_SPEED);
        while (opModeIsActive() && driveWheelActions.isMoving()) {
            telemetry.addData("Encoder: ", "Driving Forward \"");
            telemetry.update();
        }
        driveWheelActions.stop();


        //REVERSE
        driveWheelActions.driveByInches(6, MotorConstants.DIRECTION_REVERSE, SLOW_SPEED);
        while (opModeIsActive() && driveWheelActions.isMoving()) {
            telemetry.addData("Encoder: ", "Driving Reverse \"");
            telemetry.update();
        }
        driveWheelActions.stop();


        //STRAFE LEFT
        driveWheelActions.driveByInches(6, MotorConstants.DIRECTION_STRAFE_LEFT, SLOW_SPEED);
        while (opModeIsActive() && driveWheelActions.isMoving()) {
            telemetry.addData("Encoder: ", "Driving Reverse \"");
            telemetry.update();
        }
        driveWheelActions.stop();


        //STRAFE RIGHT
        driveWheelActions.driveByInches(6, MotorConstants.DIRECTION_STRAFE_RIGHT, SLOW_SPEED);
        while (opModeIsActive() && driveWheelActions.isMoving()) {
            telemetry.addData("Encoder: ", "Driving Reverse \"");
            telemetry.update();
        }
        driveWheelActions.stop();


        sleep(2000); //wait for 2 seconds
    }
}

