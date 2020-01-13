package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;

/**
 * Purpose: Pull BLUE foundation to the building site
 */
//Right wheels to the left of
@Autonomous(name = "Blue Foundation Wall Pull", group = "GoBilda")
@Disabled
public class PullBlueFoundationWall extends HelperAction {


    @Override
    public void runOpMode() {

        DriveWheelActions wheelActions = new DriveWheelActions(telemetry, hardwareMap);
        HookActions hookActions = new HookActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Strafe RIGHT
        strafe_RightAndStop(wheelActions, SPEED, 0.9);
        sleep(2000); //wait for 2 seconds

        // Step 2: Drive REVERSE towards the building zone
        drive_ReverseAndStop(wheelActions, SPEED, 1.5);
        sleep(4000);

        // Step 3: Move rear Hooks DOWN to grab the foundation
        hookActions.moveHooksDown();
        sleep(2000);

        // Step 4: Drive FORWARD towards building site
        wheelActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(wheelActions, SPEED , 4.0); //SPEED-0.5, added 2.5 driving time
        sleep(2000);
        wheelActions.applySensorSpeed = false;// we have altered the speed for the forwards movement

        // Step 5: Hook move UP to release the foundation
        hookActions.moveHooksUp();
        sleep(2000);

        // Step 6: Strafe LEFT
        strafe_LeftAndStop(wheelActions, SPEED, 3.3);
        sleep(2000);
    }


}