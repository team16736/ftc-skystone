package org.firstinspires.ftc.atomic.gobilda.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;
import org.firstinspires.ftc.atomic.gobilda.actions.LinearSlideActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

@TeleOp(name="TeleOp-Sliding-Arm", group="Linear Opmode")
public class TeleOpSlidingArm extends LinearOpMode {

    private HookActions hookActions = null;
    private DriveWheelActions driveActions = null;
    private ArmElbowGripperActions armActions = null;
    private LinearSlideActions slideActions = null;


    @Override
    public void runOpMode() {

        hookActions = new HookActions(telemetry, hardwareMap);
        driveActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new ArmElbowGripperActions(telemetry, hardwareMap);
        slideActions = new LinearSlideActions(telemetry, hardwareMap);

        //Set Speed for teleOp. Mecannum wheel speed.
        driveActions.setSpeed(1.0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /** Gamepad 1 **/
            driveActions.drive(gamepad1.left_stick_x,      //joystick controlling strafe
                        -gamepad1.left_stick_y,     //joystick controlling forward/backward
                        gamepad1.right_stick_x);    //joystick controlling rotation


            /** Gamepad 2 **/
            hookActions.hookUpDown(gamepad2.dpad_left,         //key to move up hookUpDown
                                    gamepad2.dpad_right);       //key to move down hookUpDown


            armActions.elbowOpenClose(gamepad2.dpad_up,    //elbow up
                    gamepad2.dpad_down);    //elbow down


            armActions.grabberOpenClose(gamepad2.left_bumper,  //open grabber
                    gamepad2.right_bumper); //close grabber


            slideActions.drive(gamepad2.left_stick_x,      //joystick controlling strafe
                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                    gamepad1.right_stick_x);    //joystick controlling rotation


            telemetry.update();
        }

        telemetry.addData("AtomicBot", "Stopping");
        telemetry.update();

        idle();
    }
}
