package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;

/**
 * Purpose: Pull RED foundation to the building site
 */
@Autonomous(name = "Red Foundation Wall Pull", group = "GoBilda")
public class PullRedFoundationWall extends HelperAction {

    @Override
    public void runOpMode() {

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        HookActions hookActions = new HookActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Strafe LEFT
        strafe_LeftAndStop(driveActions, SPEED, 0.4);
       sleep(1000); //wait for 2 seconds

        // Step 2: Drive REVERSE towards the building zone
        drive_ReverseAndStop(driveActions, SPEED, 1.6);
        sleep(1000);

        // Step 3: Move rear Hooks DOWN to grab the foundation
        hookActions.moveHooksDown();
        sleep(2000);

        // Step 4: Drive FORWARD towards building site
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(driveActions, SPEED-0.2 , 1.0); //SPEED-0.5, added 2.5 driving time
        sleep(250);

        //Step 4.1-4.5
        drive_ForwardAndStop(driveActions, SPEED-0.2 , 5); //SPEED-0.5, added 2.5 driving time
        sleep(250);

        // Step5: Hook move UP to release the foundation
        hookActions.moveHooksUp();
        sleep(1000);

        driveActions.applySensorSpeed = false;// we have altered the speed for the forwards movement

        // Step 6: Strafe RIGHT and park under bridge
        strafe_RightAndStop(driveActions, SPEED, 2.5);
    }


}

