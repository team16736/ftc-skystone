package org.firstinspires.ftc.atomic.gobilda.autonomous.unused;

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
@Autonomous(name = "Blue-Quarry To Bridge", group = "GoBilda")
@Disabled

//PLACE ROBOT FORWARD FACING BLUE QUARRY. ALIGHT WITH 2nd TILE ON BRIDGE SIDE.
public class QuarryToBlueBridge extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        driveActions.applySensorSpeed = true;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Drive Forward
        driveActions.setMotorDirection_Forward();
        driveActions.driveByTime(this, 0.5, 1.2);
        driveActions.stop();

        // Step 2:  Strafe left
        driveActions.setMotorDirection_StrafeLeft();
        driveActions.driveByTime(this, 0.5, 2.0);
        driveActions.stop();

    }
}

