package org.firstinspires.ftc.atomic.gobilda.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

/**
 * Purpose:
 * 1. Identify Skystone/s on RED Quarry
 * 2. Deliver to the other side of the RED bridge
 * 3. Park by Wall
 *
 * Sensors must be attached to one of the I2C ports #######
 */
@Autonomous(name = "Skystones RED Wall", group = "Narwhals")
public class SkystoneRedWallWithArm extends HelperAction {

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

        // Step 1: Lift arm, open elbow and open grabber
        armUpAndStop(armActions, 0.6, 0.7);
        sleep(250);
        elbowCompletelyOpen(armActions);
        sleep(250);
        grabberCompletelyOpen(armActions);

        // Step 1.5: Drive FORWARD
        driveActions.applySensorSpeed = true;// we have altered the speed for the forwards movement
        drive_ForwardAndStop(driveActions, 1.0 , .31);
        sleep(3000);

        // Step 1.6: identify color of all stones in RED quarry
        StoneColors stoneColors = identify_Red_Quarry_Colors(left_sensor, right_sensor, hsvValues);
        sleep(500); //Long wait to see the telemetry on phone -- REMOVE this before competition

        //Step 0: drive BACKWARDS to be by 2nd Skystone
        drive_ReverseAndStop(driveActions, SPEED-0.2 , 0.1);
        sleep(100);

        double travel_forward_value;
        double travel_backward_value;

        if (stoneColors.isStone_1()) {

            //Collect and Deliver stone - 1, 4 *************

            //Step:0
            strafe_RightAndStop(driveActions,SPEED,0.12);

            travel_forward_value = 0.625;
            travel_backward_value = 1.4;
            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            myFirstStoneWasDelivered = true;

            //Step 9: turn Left towards 2nd Skystone
            spin_LeftAndStop(driveActions, SPEED - 0.1, 1.15);
            sleep(100);

            //Step 10: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED, 0.3);

            //Step 11: Repeat collect and deliver again
            travel_forward_value = 1.35;
            travel_backward_value = 0.3;
            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

        }
        else if (stoneColors.isStone_2()) {

            //Collect and Deliver stone - 2, 5 *************

            //Step 1: Strafe Left//
            strafe_LeftAndStop(driveActions, SPEED , 0.25);

            travel_forward_value = 1.0;
            travel_backward_value = 1.7;
            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            myFirstStoneWasDelivered = true;

            //Step 8: turn LEFT towards 2nd Skystone
            spin_LeftAndStop(driveActions, SPEED - 0.1, 1.15);
            sleep(100);

            //Step 9: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED, 0.2);
            sleep(100);

            //Step 10: Repeat collect and deliver again
            travel_forward_value = 1.35;
            travel_backward_value = 0.25;
            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

        }
        else {

            //Collect and Deliver stone - 3, 6 *************

            travel_forward_value = 1.15;
            travel_backward_value = 1.6;

            //Step 0.5: Strafe Left
            strafe_LeftAndStop(driveActions, SPEED + .25, .425);

            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

            myFirstStoneWasDelivered = true;

            //Step 1: turn Left towards 2nd Skystone
            spin_LeftAndStop(driveActions, SPEED - 0.1, 1.15);
            sleep(100);

            //Step 2: Strafe left
            strafe_LeftAndStop(driveActions,SPEED, 0.35);
            sleep(100);

            //Step 3: Move Forwards To second Skystone
            drive_ForwardAndStop(driveActions, SPEED-0.2, 0.5);

            sleep(100);

            //Step 10: Repeat collect and deliver again
            travel_forward_value = 1.5;
            travel_backward_value = 0.3;
            collect_Skystone_Deliver_And_Return(driveActions, stoneColors, travel_forward_value, travel_backward_value);

        }

        //Turn OFF the sensor LED
        right_sensor.enableLed(false);
        left_sensor.enableLed(false);
        driveActions.applySensorSpeed = false;// we have altered the speed for the forwards movement

        telemetry.addData("Mission complete!! ", " Go Narwhals ");
        telemetry.update();
        sleep(300);
    }

    private void collect_Skystone_Deliver_And_Return(DriveWheelActions driveActions,
                                                     StoneColors stoneColors,
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
        sleep(1000);

        //Step 5: turn Right towards the bridge
        if (stoneColors.isStone_6()){
            spin_RightAndStop(driveActions, SPEED, 1.05);
            sleep(100);

        }else{
            spin_RightAndStop(driveActions, SPEED, 1.0);
            sleep(100);
        }

        if(myFirstStoneWasDelivered){

            if(stoneColors.isStone_4()|| stoneColors.isStone_5() || stoneColors.isStone_6()){

                strafe_RightAndStop(driveActions, SPEED, 1.0);
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