package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

/**
 * Purpose:
 * 1. Identify Skystones on Blue Quarry
 * 2. Deliver to the other side of the Blue bridge
 * 3. Park by the BridgeBlue
 * <p>
 * Sensors must be attached to one of the I2C ports
 */
//STARTING POINT: Team number display (white pvc) on the left side
// must be aligned between and 1st the 2nd tile joint
@Autonomous(name = "Skystone BLUE Wall", group = "GoBilda")
public class SkystoneBlueWallWithArm extends HelperAction {

    private ArmElbowGripperActions armActions = null;

    private boolean myFirstStoneWasDelivered = false;

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
        armUpAndStop(armActions, 0.6, 0.4);
        sleep(250);///
        elbowCompletelyOpen(armActions);////
        sleep(250);
        grabberCompletelyOpen(armActions);////

        // Step 1.5: Move FORWARD
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(driveActions, SPEED *2 , 0.335);
        sleep(250);

        // Step --> detect Skystone using left sensor. If yes, then stone 1,4 are the skystones
        // If a skystone is not detected, then use right sensor. If yes, then stone 2, 5 are the skystones
        // If a skystone is still not detected, assume stone 3, 6 are the skystones
        StoneColors stoneColors = identifyQuarryColors(left_sensor, right_sensor, hsvValues);

        telemetry.addData("Stone 1 IS BLACK: ", "" + stoneColors.isStone_1());
        telemetry.addData("Stone 2 IS BLACK: ", "" + stoneColors.isStone_2());
        telemetry.addData("Stone 3 IS BLACK: ", "" + stoneColors.isStone_3());
        telemetry.addData("Stone 4 IS BLACK: ", "" + stoneColors.isStone_4());
        telemetry.addData("Stone 5 IS BLACK: ", "" + stoneColors.isStone_5());
        telemetry.addData("Stone 6 IS BLACK: ", "" + stoneColors.isStone_6());
        telemetry.update();
        sleep(3000);

        double travel_forward_value;
        double travel_backward_value;

        if (stoneColors.isStone_1()) {

            //Collect and Deliver stone - 1, 4 *************

            //Step 0: drive BACKWARDS to be by 2nd Skystone
            drive_ReverseAndStop(driveActions, SPEED , 0.03);
            sleep(100);

            //Step 1: Strafe Left
            strafe_LeftAndStop(driveActions, SPEED + .25, 0.25);

            travel_forward_value = 0.6;
            travel_backward_value = 1.3;
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            //Indicate if my 1st stone was delivered
            myFirstStoneWasDelivered = true;

            //Step 9: turn RIGHT towards 2nd Skystone
            spin_RightAndStop(driveActions, SPEED - 0.1, 1.11);
            sleep(100);

            //Step 10: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED, 0.23);

            //Step 11: Repeat collect and deliver again
            travel_forward_value = 1.2;
            travel_backward_value = 0.35;
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

        }
        else if (stoneColors.isStone_2()) {

            //Collect and Deliver stone - 2, 5 *************

            //Step 0: drive BACKWARDS to be by 2nd Skystone
            drive_ReverseAndStop(driveActions, SPEED , 0.03);
            sleep(100);

            travel_forward_value = 0.7;
            travel_backward_value = 1.4;
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            //Indicate if my 1st stone was delivered
            myFirstStoneWasDelivered = true;

            //Step 8: turn RIGHT towards 2nd Skystone
            spin_RightAndStop(driveActions, SPEED - 0.1, 1.15);
            sleep(100);

            //Step 9: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED, 0.2);
            sleep(100);

            //Step 10: Repeat collect and deliver again
            travel_forward_value = 1.2;
            travel_backward_value = 0.2;
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

        }
        else {

            //Collect and Deliver stone - 3, 6 *************

            travel_forward_value = 0.95;
            travel_backward_value = 1.3;

            //Step 0: drive BACKWARDS to be by 2nd Skystone
            drive_ReverseAndStop(driveActions, SPEED , 0.03);
            sleep(100);

            //Step 0.5: Strafe Right
            strafe_RightAndStop(driveActions, SPEED + .25, 0.25);
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            //Indicate if my 1st stone was delivered
            myFirstStoneWasDelivered = true;

            //Step 1: turn RIGHT towards 2nd Skystone
            spin_RightAndStop(driveActions, SPEED - 0.1, 1.15);
            sleep(100);

            //Step 2: Strafe right
            strafe_RightAndStop(driveActions,SPEED, 0.6);
            sleep(100);

            //Step 2.5: turn RIGHT towards 2nd Skystone
            spin_RightAndStop(driveActions, SPEED /2, 0.3);
            sleep(100);

            //Step 3: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED-0.2, 0.5);

            sleep(100);

            //Step 10: Repeat collect and deliver again
            travel_forward_value = 1.5;
            travel_backward_value = 0.45;
            collectSkystoneDeliverAndComeBack(driveActions, stoneColors, travel_forward_value, travel_backward_value);

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
                                                   StoneColors quarryDetail,
                                                   double travel_forward_value,
                                                   double travel_backward_value) {

        //Step 2 : lower arm down to block
        armDownAndStop(armActions, 0.25, 0.3);
        sleep(100);

        //Step 3: grab block
        grabberCompletelyClosed(armActions);
        sleep(100);

        //Step 4: drive BACKWARDS to align with the bridge
        drive_ReverseAndStop(driveActions, SPEED, 0.2);
        sleep(100);

        //Step 5: turn LEFT towards the bridge
        if (quarryDetail.isStone_6()){
            spin_LeftAndStop(driveActions, SPEED, 1.05);
            sleep(100);

        }else{
            spin_LeftAndStop(driveActions, SPEED, 0.94);
            sleep(100);
        }

        if(myFirstStoneWasDelivered){

            if(quarryDetail.isStone_4()|| quarryDetail.isStone_5() || quarryDetail.isStone_6()){

                strafe_LeftAndStop(driveActions, SPEED, 0.75);
            }
        }

        //Step 6: drive FORWARD towards the bridge
        drive_ForwardAndStop(driveActions, SPEED + 0.5, travel_forward_value);

        //Step 7: let go of block
        grabberCompletelyOpen(armActions);

        //Step 8: drive BACKWARDS to be by 2nd Skystone
        drive_ReverseAndStop(driveActions, SPEED + 0.5, travel_backward_value);
        sleep(100);
    }

}