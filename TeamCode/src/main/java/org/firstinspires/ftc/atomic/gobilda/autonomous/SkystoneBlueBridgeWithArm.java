package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
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
//START AT FIRST HOLE FROM THE RIGHT OF THE FRAME
@Autonomous(name = "Skystone BLUE Bridge With Arm", group = "GoBilda")


public class SkystoneBlueBridgeWithArm extends HelperAction {
    private ArmElbowGripperActions armActions = null;
    @Override
    public void runOpMode() {

        right_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.RIGHT_COLOR);
        right_sensor.enableLed(true);

        left_sensor = hardwareMap.get(ColorSensor.class, ConfigConstants.LEFT_COLOR);
        left_sensor.enableLed(true);

        DriveWheelActions driveActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new ArmElbowGripperActions(telemetry, hardwareMap);
        waitForStart();


        // Step 1: Lift arm, Open elbow and grabber
        armUpAndStop(armActions, 0.6, 0.3);
        sleep(250);///
        elbowCompletelyOpen(armActions);////
        grabberCompletelyOpen(armActions);////


        // Step 1.5: Move FORWARD
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(driveActions, SPEED*2, 0.31 );
        sleep(250);


        // Step --> detect skystone using sensor
       foundStone = isThisSkystone(right_sensor, hsvValues);

        StoneColor quarryDetail = identifyQuarryColors(left_sensor, right_sensor, hsvValues);

        telemetry.addData("Stone 1 is black: ", "" + quarryDetail.isStone_1());
        telemetry.addData("Stone 2 is black: ", "" + quarryDetail.isStone_2());
        telemetry.addData("Stone 3 is black: ", "" + quarryDetail.isStone_3());
        telemetry.addData("Stone 4 is black: ", "" + quarryDetail.isStone_4());
        telemetry.addData("Stone 5 is black: ", "" + quarryDetail.isStone_5());
        telemetry.addData("Stone 6 is black: ", "" + quarryDetail.isStone_6());
        telemetry.update();


        //If Block 1 is black
        //Strafe left

        double travel_forward_value;
        double travel_backward_value;

        if ( quarryDetail.isStone_1()){


            //Step 1: Strafe Left
            strafe_LeftAndStop(driveActions, SPEED+.25, 0.23);

            travel_forward_value = 0.6;
            travel_backward_value = 1.3;
            collectSkystoneDeliverAndComeBack(driveActions, travel_forward_value, travel_backward_value);

            //Step 9: turn RIGHT towards 2nd Skystone
            spin_RightAndStop(driveActions,SPEED-0.1,1.15);
            sleep(100);

            //Step 10: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED, 0.2);

            travel_forward_value = 1.2;
            travel_backward_value = 0.35;
            collectSkystoneDeliverAndComeBack(driveActions, travel_forward_value, travel_backward_value);
        }

        //Turn OFF the sensor LED
        right_sensor.enableLed(false);
        left_sensor.enableLed(false);
        driveActions.applySensorSpeed = false;// we have altered the speed for the forwards movement

        telemetry.addData("Mission complete!! ", " yeet ");
        telemetry.update();
        sleep(300);
    }

    private void collectSkystoneDeliverAndComeBack(DriveWheelActions driveActions,
                                                   double travel_forward_value,
                                                   double travel_backward_value) {

        //Step 2 : lower arm down to block
        armDownAndStop(armActions, 0.2, 0.3);
        sleep(100);

        //Step 3: grab block
        grabberCompletelyClosed(armActions);
        sleep(100);

        //Step 4: drive BACKWARDS to align with the bridge
        drive_ReverseAndStop(driveActions, SPEED, 0.2);
        sleep(100);

        //Step 5: turn LEFT towards the bridge
        spin_LeftAndStop(driveActions, SPEED, 0.9);
        sleep(100);

        //Step 6: drive FORWARD towards the bridge
        drive_ForwardAndStop(driveActions, SPEED+0.5, travel_forward_value);

        //Step 7: let go of block
        grabberCompletelyOpen(armActions);

        //Step 8: drive BACKWARDS to be by 2nd Skystone
        drive_ReverseAndStop(driveActions, SPEED+0.5, travel_backward_value);
        sleep(100);
    }

    /**
     * This method will collect stone and deliver.
     * This method can be used again for 2nd stone.. collect and deliver
     */
    private void collectStoneAndDeliverBlueSidexxxx(DriveWheelActions wheelActions, double distance) {

/*
        //Step 2: if detect black block; Strafe RIGHT
        strafe_RightAndStop(wheelActions,SPEED,0.25);//changed
        sleep(250);

        //Step 3 : lower arm down to block
        armDownAndStop(armActions, 0.2, 0.75);
        sleep(250);

        //Step 4: grab block
        grabberCompletelyClosed(armActions);
        sleep(250);

        //Step 5: drive BACKWARDS to align with the bridge
        drive_ReverseAndStop(wheelActions, SPEED, 0.2);
        sleep(250);

        //Step 6: turn LEFT towards the bridge
        spin_LeftAndStop(wheelActions, SPEED, 0.8);
        sleep(250);

        //Step 7: drive FORWARD towards the bridge
        drive_ForwardAndStop(wheelActions, SPEED+0.25, distance -.75);

        //Step 8: let go of block
        grabberCompletelyOpen(armActions);

        */
    }

}