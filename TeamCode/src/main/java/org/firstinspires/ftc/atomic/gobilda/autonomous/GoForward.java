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

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveActions.applySensorSpeed = true;
        // Step 1:  Drive Forward for 1 Second
        driveActions.setMotorDirection_Forward();
        driveActions.driveByTime(this, 0.35, 0.25);//changed

        // Step 1:  Drive Forward for 1 Second
        driveActions.setMotorDirection_Reverse();
        driveActions.driveByTime(this, 0.35, 0.25);//changed



        sleep(2000); //wait for 2 seconds
    }
}

