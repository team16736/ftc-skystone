package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;

/**
 * Purpose:
 * Start from Red Quarry side - place on the 2nd tile
 * Go FORWARD toward Quarry
 * Strafe RIGHT under bridge
 */
@Autonomous(name = "Red-Quarry To Bridge", group = "GoBilda")


//PLACE ROBOT FORWARD FACING RED QUARRY. ALIGHT WITH 2nd TILE ON BRIDGE SIDE.
public class QuarryToRedBridge extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        driveActions.applySensorSpeed = true;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Drive Forward
        driveActions.setMotorDirection_Forward();
        driveActions.driveByTime(this, 0.5, 1.0);
        driveActions.stop();

        // Step 2:  Strafe right
        driveActions.setMotorDirection_StrafeRight();
        driveActions.driveByTime(this, 0.5, 1.75);
        driveActions.stop();

    }
}

