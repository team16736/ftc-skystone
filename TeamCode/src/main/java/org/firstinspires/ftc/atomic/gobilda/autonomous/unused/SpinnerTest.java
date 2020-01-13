package org.firstinspires.ftc.atomic.gobilda.autonomous.unused;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;

/**
 * Purpose: Go to the LEFT side and park under bridge
 *
 * 1. Goes forward
 * 2. Strafes to the RIGHT
 */
@Autonomous(name = "Spinner Left", group = "GoBilda")
@Disabled
public class SpinnerTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // ################################ Spin left #########################
        driveWheelActions.setMotorDirection_SpinLeft();
        driveWheelActions.driveByTime(this, 0.5, 3.0);
        sleep(1000); //wait for 1 seconds


        // ################################ Spin Right #########################
        driveWheelActions.setMotorDirection_SpinRight();
        driveWheelActions.driveByTime(this, 0.5, 3.0);
        sleep(1000); //wait for 1 seconds

        driveWheelActions.stop();
    }
}

