package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;

/**
 * Purpose:
 * Start from Blue Quarry side - place on the 2nd tile
 * Go FORWARD toward Quarry
 * Strafe LEFT under bridge
 */
@Autonomous(name = "Bridge Park Right-to-Left", group = "GoBilda")
@Disabled

//PLACE ROBOT FORWARD FACING BLUE QUARRY. ALIGHT WITH 2nd TILE ON BRIDGE SIDE.
public class QuarryToBlueBridge extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);
        driveWheelActions.applySensorSpeed = true;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Drive Forward
        driveWheelActions.setMotorDirection_Forward();
        driveWheelActions.driveByTime(this, 0.3, 2.0);
        driveWheelActions.stop();

        // Step 2:  Strafe left
        driveWheelActions.setMotorDirection_StrafeLeft();
        driveWheelActions.driveByTime(this, 0.35, 2.75);
        driveWheelActions.stop();

    }
}

