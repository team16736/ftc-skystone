package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;

/**
 * Purpose:
 * Go forward and park under bridge
 */
//SET ROBOT FORWARD FACING BRIDGE - ONE BRICK AWAY FROM RED OR BLUE BRIDGE
@Autonomous(name = "Forward", group = "GoBilda")
public class GoForward extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveWheelActions.applySensorSpeed = true;
        // Step 1:  Drive Forward for 1 Second
        driveWheelActions.setMotorDirection_Forward();
        driveWheelActions.driveByTime(this, 0.35, 0.25);//changed

        sleep(2000); //wait for 2 seconds
    }
}

