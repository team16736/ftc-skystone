package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;

/**
 * Purpose: Pull RED foundation to the building site
 */
@Autonomous(name = "Spin Left - Tuesday", group = "GoBilda")
@Disabled
public class SpinLeftOrRight extends HelperAction {

    @Override
    public void runOpMode() {

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);

        driveActions.setMotorDirection_SpinLeft();
        drive_ForwardAndStop(driveActions, SPEED , 1.0); //SPEED-0.5, added 2.5 driving time
    }


}

