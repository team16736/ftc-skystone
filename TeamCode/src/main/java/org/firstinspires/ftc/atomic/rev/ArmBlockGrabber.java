package org.firstinspires.ftc.atomic.rev;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This program runs a basic Tank Drive Teleop for a two wheeled robot
 * Download this to 16736-C-RC (Robot Controller phone)
 */

@TeleOp(name = "Basic: Bjorn", group = "Iterative Opmode")
public class ArmBlockGrabber extends OpMode {

    //The string values provide here should match the hardware variables names used on the RC phone (16736-C-RC - Robot Controller app)
    public static final String LEFT_DRIVE = "left_drive";
    public static final String RIGHT_DRIVE = "right_drive";
    public static final String ARM_DRIVE = "arm_drive";
    public static final String PICKUP_DRIVE = "pickup_drive";

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armDrive = null;
    private DcMotor pickupDrive = null;

    private ElapsedTime runtime = new ElapsedTime();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "AtomicNarwhal Robot is Initialized");

        leftDrive = hardwareMap.get(DcMotor.class, LEFT_DRIVE);
        rightDrive = hardwareMap.get(DcMotor.class, RIGHT_DRIVE);
        armDrive = hardwareMap.get(DcMotor.class, ARM_DRIVE);
        pickupDrive = hardwareMap.get(DcMotor.class, PICKUP_DRIVE);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        armDrive.setDirection(DcMotor.Direction.FORWARD);
        pickupDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "initialization is complete");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     *
     * There are 2 modes:
     * Tank Mode
     * i) Tank Mode uses one stick to control each wheel.
     * ii) Requires no math
     * iii) It is hard to drive forward slowly
     * iv) It is hard to keep straight
     *
     * POV Mode
     * i) POV Mode uses left stick to go forward
     * ii) POV Mode uses right stick to turn
     * iii) Easy to control
     * iv) Requires basic math to combine motions and is easier to drive straight
     */
    @Override
    public void loop() {

        // Setup power level for each drive wheel
        double leftPower;
        double rightPower;
        double armPower;
        double pickupPower;

        // POV Mode
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        double arm_up = gamepad2.right_stick_y;
        double arm_down = gamepad2.right_stick_x;

        boolean pickup_up = gamepad2.a;
        boolean pickup_down = gamepad2.b;

        leftPower = Range.clip(drive + turn, -10.0, 10.0); // TODO: Rahul - if 10.0 is high, the use 1.0
        rightPower = Range.clip(drive - turn, -10.0, 10.0); // TODO: Rahul - if -10.0 is high, the use -1.0

        armPower = Range.clip(arm_up - arm_down, -5.0, 5.0); // TODO: Rahul - if -10.0 is high, the use -1.0
       if (pickup_up){

            pickupPower = -10.0;

        }
       else if(pickup_down){

           pickupPower = 10.0;
        }

       else{
        pickupPower = 0.0;
    }

        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        armDrive.setPower(armPower);
        pickupDrive.setPower(pickupPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
