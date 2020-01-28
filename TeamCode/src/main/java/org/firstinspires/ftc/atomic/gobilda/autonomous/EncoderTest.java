package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.MotorConstants;

@Autonomous(name = "Encoder Test", group = "GoBilda")
@Disabled
public class EncoderTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        DriveWheelActions driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);

        waitForStart();

        //FORWARD
        driveWheelActions.driveByInches(this,2, MotorConstants.DIRECTION_FORWARD, 0.5);
        driveWheelActions.stop();

    }
}

