package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

/**
 * Purpose:
 * 1. Identify Skystone on Blue Quarry
 * 2. Deliver to the other side of the Blue bridge
 * 3. Park by the BridgeBlue
 *
 * Sensors must be attached to one of the I2C ports
 */
//START AT FIRST HOLE FROM THE LEFT OF THE FRAME
@Autonomous(name = "Skystone RED Bridge", group = "GoBilda")
@Disabled

public class SkystoneRedBridge extends HelperAction {

    @Override
    public void runOpMode() {

        right_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.RIGHT_COLOR); //NOT USED
        right_sensor.enableLed(false);

        left_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.LEFT_COLOR);
        left_sensor.enableLed(true);

        DriveWheelActions wheelActions = new DriveWheelActions(telemetry, hardwareMap);
        waitForStart();

        // Step 1: Move FORWARD
        wheelActions.applySensorSpeed = true;// w e have altered the speed for the forwards movement
        drive_ForwardAndStop(wheelActions, SPEED, 1.2);
        sleep(3000);

        // Step --> detect skystone using sensor
        foundStone = isThisSkystone(left_sensor, hsvValues);
        telemetry.update();

        // If stone is found, the collect it and deliver it
        if (foundStone) {

            telemetry.addData("Found black block: ", "1");
            telemetry.update();

            sleep(1000);
            collectStoneAndDeliverRedSide(wheelActions, 2.0);

        } else {

            strafe_LeftAndStop(wheelActions, SPEED, 0.4);
            sleep(1000);
            foundStone = isThisSkystone(left_sensor, hsvValues);
            telemetry.update();

            if (foundStone) {

                telemetry.addData("Found black block: ", "2");
                telemetry.update();

                sleep(1000);
                collectStoneAndDeliverRedSide(wheelActions, 2.7);

            } else {

                telemetry.addData("Found black block: ", "3");
                telemetry.update();

                strafe_LeftAndStop(wheelActions, SPEED, 0.4);
                sleep(1000);
                collectStoneAndDeliverRedSide(wheelActions, 3.0);
            }

        }

        //Step8: Move backwards to park under bridge
        drive_ReverseAndStop(wheelActions, SPEED, 0.7);
        foundStone = false;
        sleep(1000);

        //Turn OFF the sensor LED
        left_sensor.enableLed(false);
        right_sensor.enableLed(false);
        wheelActions.applySensorSpeed = false;// we have altered the speed for the forwards movement
        telemetry.update();
    }


    /**
     * This method will collect stone and deliver.
     * This method can be used again for 2nd stone.. collect and deliver
     */
    private void collectStoneAndDeliverRedSide(DriveWheelActions wheelActions, double distance) {

        //Step 2: if detect black block; Strafe RIGHT
        strafe_LeftAndStop(wheelActions,SPEED,0.3);//changed
        sleep(1000);

        //Step 3: Move FORWARD
        drive_ForwardAndStop(wheelActions, SPEED, 0.6);
        sleep(2000);

        //Step 4: Spin RIGHT
        wheelActions.setMotorDirection_SpinRight();
        wheelActions.driveByTime(this, 0.3, 2.2);
        sleep(2000);

        //Step 5: Move FORWARD
        drive_ForwardAndStop(wheelActions, SPEED, 0.8);
        sleep(1000);

        //Step6: Spin LEFT to face the BLUE bridge
        wheelActions.setMotorDirection_SpinLeft();
        wheelActions.driveByTime(this, 0.3, 0.1);
        sleep(2000);

        // Step 7: Move FORWARD and deliver to the other side of the bridge
        drive_ForwardAndStop(wheelActions, SPEED, distance);
        sleep(1000);
    }

}