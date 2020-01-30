package org.firstinspires.ftc.atomic.gobilda.teleop.unused;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;
import org.firstinspires.ftc.atomic.gobilda.actions.SlideElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

@TeleOp(name="TeleOp-Original-Arm", group="Linear Opmode")
@Disabled
public class TeleOpOriginalArm extends LinearOpMode {

    private HookActions hookActions = null;
    private DriveWheelActions driveActions = null;
    private SlideElbowGripperActions armActions = null;

    private DcMotor armDrive = null;
    private Servo elbowServo = null;
    private Servo grabberServo = null;


    @Override
    public void runOpMode() {

        hookActions = new HookActions(telemetry, hardwareMap);
        driveActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new SlideElbowGripperActions(telemetry, hardwareMap);

        armDrive  = hardwareMap.get(DcMotor.class, ConfigConstants.ARM);
        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        grabberServo = hardwareMap.get(Servo.class, ConfigConstants.GRABBER_SERVO);

        armDrive.setDirection(DcMotor.Direction.REVERSE);
        elbowServo.setDirection(Servo.Direction.REVERSE);
        grabberServo.setDirection(Servo.Direction.FORWARD);

        //Set Speed for teleOp
        driveActions.setSpeed(1.0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /** Gamepad 1 **/
            driveActions.drive(gamepad1.left_stick_x,           //joystick controlling strafe
                                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                                    gamepad1.right_stick_x);    //joystick controlling rotation

            /** Gamepad 2 **/
            hookActions.hookUpDown(gamepad2.dpad_left,         //key to move up hookUpDown
                                    gamepad2.dpad_right);       //key to move down hookUpDown


            armActions.grabberOpenClose(gamepad2.left_bumper,   //open grabber
                                        gamepad2.right_bumper); //close grabber

            armActions.elbowOpenClose(gamepad2.dpad_up,         //elbow up
                                        gamepad2.dpad_down);    //elbow down

            //REV - Core Hex Motor -- Old Arm
            armActions.armUpDown(gamepad2.y,                    //arm up - ORANGE button
                                gamepad2.x);                    //arm down - GREEN button

            telemetry.update();
        }

        telemetry.addData("AtomicBot", "Stopping");
        telemetry.update();

        idle();
    }
}
