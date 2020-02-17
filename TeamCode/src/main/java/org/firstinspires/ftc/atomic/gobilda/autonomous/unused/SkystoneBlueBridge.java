package org.firstinspires.ftc.atomic.gobilda.autonomous.unused;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.autonomous.HelperAction;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

/**
 * Purpose:
 * 1. Identify Skystone on Blue Quarry
 * 2. Deliver to the other side of the Blue bridge
 * 3. Park by the BridgeBlue
 *
 * Sensors must be attached to one of the I2C ports
 */
//START AT FIRST HOLE FROM THE RIGHT OF THE FRAME
@Autonomous(name = "Skystone BLUE Bridge", group = "GoBilda")
@Disabled

public class SkystoneBlueBridge extends HelperAction {

    @Override
    public void runOpMode() {

        right_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.RIGHT_COLOR);
        right_sensor.enableLed(true);

        left_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.LEFT_COLOR); // NOT USED
        left_sensor.enableLed(false);

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        waitForStart();

        // Step 1: Move FORWARD
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(driveActions, SPEED, 1.2);
        sleep(3000);

        // Step --> detect skystone using sensor
        foundStone = isThisSkystone(right_sensor, hsvValues);
        telemetry.update();

        // If stone is found, the collect it and deliver it
        if (foundStone) {

            telemetry.addData("Found black block: ", "1");
            telemetry.update();

            sleep(3000);
            collectStoneAndDeliverBlueSide(driveActions, 2.0);

        } else {

            strafe_RightAndStop(driveActions, SPEED, 0.4);
            sleep(3000);
            foundStone = isThisSkystone(right_sensor, hsvValues);

            if (foundStone) {

                telemetry.addData("Found black block: ", "2");
                telemetry.update();

                sleep(3000);
                collectStoneAndDeliverBlueSide(driveActions, 2.7);

            } else {

                telemetry.addData("Found black block: ", "3");
                telemetry.update();

                strafe_RightAndStop(driveActions, SPEED, 0.4);
                sleep(3000);
                collectStoneAndDeliverBlueSide(driveActions, 3.0);
            }
        }

        //Step8: Move backwards to park under bridge
        drive_ReverseAndStop(driveActions, SPEED, 0.7);
        foundStone = false;
        sleep(1000);

        //Turn OFF the sensor LED
        right_sensor.enableLed(false);
        left_sensor.enableLed(false);
        driveActions.applySensorSpeed = false;// we have altered the speed for the forwards movement

        telemetry.addData("Mission complete!! ", " Woot ");
        telemetry.update();
    }

    /**
     * This method will collect stone and deliver.
     * This method can be used again for 2nd stone.. collect and deliver
     */
    private void collectStoneAndDeliverBlueSide(DriveWheelActions wheelActions, double distance) {

        //Step 2: if detect black block; Strafe RIGHT
        strafe_RightAndStop(wheelActions,SPEED,0.3);//changed
        sleep(1000);

        //Step 3: Move FORWARD
        drive_ForwardAndStop(wheelActions, SPEED, 0.6);
        sleep(2000);

        //Step 4: Spin LEFT
        wheelActions.setMotorDirection_SpinLeft();
        wheelActions.driveByTime(this, 0.3, 2.2);
        sleep(2000);

        //Step 5: Move FORWARD
        drive_ForwardAndStop(wheelActions, SPEED, 0.8);
        sleep(1000);

        //Step6: Spin RIGHT to face the BLUE bridge
        wheelActions.setMotorDirection_SpinRight();
        wheelActions.driveByTime(this, 0.3, 0.1);
        sleep(2000);

        // Step 7: Move FORWARD and deliver to the other side of the bridge
        drive_ForwardAndStop(wheelActions, SPEED, distance);
        sleep(1000);
    }

}