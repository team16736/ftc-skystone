package org.firstinspires.ftc.atomic.gobilda.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.CapstoneFlipperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;
import org.firstinspires.ftc.atomic.gobilda.actions.LinearSlideActions;
import org.firstinspires.ftc.atomic.gobilda.actions.SensorControlActions;

@TeleOp(name = "TeleOp-SLIDING-ARM", group = "Linear Opmode")
public class TeleOpSlidingArm extends LinearOpMode {

    private HookActions hookActions = null;
    private DriveWheelActions driveActions = null;
    private ArmElbowGripperActions armActions = null;
    private CapstoneFlipperActions flipperActions = null;
    // private SensorControlActions sensorControlActions = null;
    //private LinearSlideActions slideActions = null;

    @Override
    public void runOpMode() {

        hookActions = new HookActions(telemetry, hardwareMap);
        driveActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new ArmElbowGripperActions(telemetry, hardwareMap);
        flipperActions = new CapstoneFlipperActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        driveActions.setSpeed(1.0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /** Gamepad 1 **/
            driveActions.drive(
                    gamepad1.left_stick_x,      //joystick controlling strafe
                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                    gamepad1.right_stick_x);    //joystick controlling rotation

            flipperActions.flipper_Forward_Backward(
                    gamepad1.left_bumper,       //open grabber
                    gamepad1.right_bumper);     //close grabber

            /** Gamepad 2 **/
            hookActions.hookUpDown(
                    gamepad2.dpad_left,         //key to move up hookUpDown
                    gamepad2.dpad_right);       //key to move down hookUpDown

//            armActions.elbowOpenClose(
//                  gamepad2.dpad_up,           //elbow open
//                  gamepad2.dpad_down);        //elbow close


            armActions.elbow_FullOpen_FullClose(
                    gamepad2.dpad_up,           //elbow full open
                    gamepad2.dpad_down);        //elbow full close

            armActions.grabberOpenClose(
                    gamepad2.left_bumper,       //open grabber
                    gamepad2.right_bumper);     //close grabber

            armActions.armUpDown_LinearSlide(gamepad2.left_stick_y);     //arm up/down uses Linear Slide

            //todo - This method simple prints true/false.
            //sensorControlActions.isLimitSwitchPressed();

            //todo - This method moves the linear slide up a little bit
            //armActions.isLimitSwitchPressed();

            telemetry.update();
        }

        telemetry.addData("STEPHON ", "Stopping");
        telemetry.update();

        idle();
    }
}
