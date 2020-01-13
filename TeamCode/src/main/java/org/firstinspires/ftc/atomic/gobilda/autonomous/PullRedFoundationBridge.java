package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;

/**
 * Purpose: Pull RED foundation to the building site
 */
@Autonomous(name = "Red Foundation Bridge Pull", group = "GoBilda")
@Disabled
public class PullRedFoundationBridge extends HelperAction {


    @Override
    public void runOpMode() {

        DriveWheelActions wheelActions = new DriveWheelActions(telemetry, hardwareMap);
        HookActions hookActions = new HookActions(telemetry, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step 1:  Strafe LEFT
        strafe_LeftAndStop(wheelActions, SPEED, 0.9);
        sleep(2000); //wait for 2 seconds

        // Step 2: Drive REVERSE towards the building zone
        drive_ReverseAndStop(wheelActions, SPEED, 1.3);
        sleep(2000);

        // Step 3: Move rear Hooks DOWN to grab the foundation
        hookActions.moveHooksDown();
        sleep(2000);

        // Step4: Drive FORWARD towards building site
        drive_ForwardAndStop(wheelActions, SPEED , 4.0); //SPEED-0.5, added 2.5 driving time
        sleep(2000);

        // Step5: Hook move UP to release the foundation
        hookActions.moveHooksUp();
        sleep(2000);

        // Step 6: Strafe RIGHT
        strafe_RightAndStop(wheelActions, SPEED, 1.6);
        sleep(2000);

        // Step 7: Move Backwards
        drive_ReverseAndStop(wheelActions,SPEED,1.0);
        sleep(2000);

        //Step 8: Strafe RIGHT and park under bridge
        strafe_RightAndStop(wheelActions, SPEED, 1.0);
        sleep(2000);
    }


}

