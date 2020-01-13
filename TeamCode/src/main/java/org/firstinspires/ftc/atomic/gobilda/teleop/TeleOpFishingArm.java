package org.firstinspires.ftc.atomic.gobilda.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.atomic.gobilda.actions.ArmElbowGripperActions;
import org.firstinspires.ftc.atomic.gobilda.actions.DriveWheelActions;
import org.firstinspires.ftc.atomic.gobilda.actions.HookActions;
import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;

@TeleOp(name="Mecannum-Christmas", group="Linear Opmode")
public class TeleOpFishingArm extends LinearOpMode {

    private HookActions hookActions = null;
    private DriveWheelActions driveWheelActions = null;
    private ArmElbowGripperActions armActions = null;

    private DcMotor armDrive = null;
    private Servo elbowServo = null;
    private Servo grabberServo = null;


    @Override
    public void runOpMode() {

        hookActions = new HookActions(telemetry, hardwareMap);
        driveWheelActions = new DriveWheelActions(telemetry, hardwareMap);
        armActions = new ArmElbowGripperActions(telemetry, hardwareMap);

        armDrive  = hardwareMap.get(DcMotor.class, ConfigConstants.ARM);
        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        grabberServo = hardwareMap.get(Servo.class, ConfigConstants.GRABBER_SERVO);

        armDrive.setDirection(DcMotor.Direction.REVERSE);
        elbowServo.setDirection(Servo.Direction.REVERSE);
        grabberServo.setDirection(Servo.Direction.FORWARD);

        //Set Speed for teleOp
        driveWheelActions.setSpeed(1.0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            /** Gamepad 1 **/
            driveWheelActions.drive(gamepad1.left_stick_x,      //joystick controlling strafe
                                    -gamepad1.left_stick_y,     //joystick controlling forward/backward
                                    gamepad1.right_stick_x);    //joystick controlling rotation


            //NOT TESTED
            armActions.magicButton(gamepad1.guide);     //Grab the block and raise the arm up - driver 1


            /** Gamepad 2 **/
            hookActions.hookUpDown(gamepad2.dpad_left,         //key to move up hookUpDown
                                    gamepad2.dpad_right);       //key to move down hookUpDown


            armActions.armUpDown(gamepad2.left_stick_y);     //arm up/down


            armActions.armUpDown(gamepad2.y,   //arm up - ORANGE button
                                gamepad2.x);    //arm down - GREEN button


            armActions.grabberOpenClose(gamepad2.left_bumper,  //open grabber
                                        gamepad2.right_bumper); //close grabber


            armActions.elbowOpenClose(gamepad2.dpad_up,    //elbow up
                                    gamepad2.dpad_down);    //elbow down

            telemetry.update();
        }

        telemetry.addData("AtomicBot", "Stopping");
        telemetry.update();

        idle();
    }
}
