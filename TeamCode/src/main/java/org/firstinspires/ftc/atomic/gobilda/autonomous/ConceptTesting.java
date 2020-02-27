package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
/**
 * Purpose: Pull RED foundation to the building site
 */
@Autonomous(name = "Concept Testing James", group = "GoBilda")
@Disabled
public class ConceptTesting extends HelperAction {
    private ArmElbowGripperActions armActions = null;

    @Override
    public void runOpMode() {


        right_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.RIGHT_COLOR);
        right_sensor.enableLed(true);

        left_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.LEFT_COLOR); // NOT USED
        left_sensor.enableLed(false);

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new ArmElbowGripperActions(telemetry, hardwareMap);
        waitForStart();
        // Step 1: Move FORWARD
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        spin_LeftAndStop(driveActions, SPEED, 0.5);
        sleep(400);
    }
}