package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Purpose:
 * 1. Arm move up and down
 * 2. Elbow open and close
 * 3. Grabber open and close
 */
public class ArmElbowGripperActions {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    private DcMotor armMotor;
    private Servo elbowServo;
    private Servo grabberServo;

    private int arm_current_position = 50;

    private double grabber_position = 0.0;
    private double elbow_position = 0.0;

    public ArmElbowGripperActions(Telemetry opModeTelemetry, HardwareMap opModeHardware) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        armMotor = hardwareMap.get(DcMotor.class, ConfigConstants.ARM);
        elbowServo = hardwareMap.get(Servo.class, ConfigConstants.ELBOW_SERVO);
        grabberServo = hardwareMap.get(Servo.class, ConfigConstants.GRABBER_SERVO);

        // 2. Set direction
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        elbowServo.setDirection(Servo.Direction.REVERSE);
        grabberServo.setDirection(Servo.Direction.FORWARD);
    }

    public void armUpDown(boolean armUp, boolean armDown) {

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_current_position = armMotor.getCurrentPosition();
        telemetry.addData("Arm CURRENT: ", armMotor.getCurrentPosition() + " Timestamp: " + System.currentTimeMillis());

        if(armUp) {

            arm_current_position = arm_current_position + 10;

            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setTargetPosition(arm_current_position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(1.0);

            telemetry.addData("Arm: ", "UP");

        } else if(armDown) {

            arm_current_position = arm_current_position - 10;

            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setTargetPosition(arm_current_position);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.5);
            telemetry.addData("Arm: ", "DOWN");
        }

        telemetry.addData("Arm TARGET: ", arm_current_position + " Timestamp: " + System.currentTimeMillis());
    }

    /**
     * Open to MAX position
     * Close to MIN position
     *
     * @param grabberClose
     * @param grabberOpen
     */
    public void grabberOpenClose(boolean grabberOpen, boolean grabberClose) {

        if (grabberClose) {

            grabber_position = 1.0;
            grabberServo.setPosition(grabber_position);

        } else if (grabberOpen) {

            grabber_position = 0;
            grabberServo.setPosition(grabber_position);
        }
//
//        telemetry.addData("Grabber position: ", grabber_position);
//        telemetry.update();
    }


    public void elbowOpenClose(boolean elbowOpen, boolean elbowClose) {

        if (elbowClose) {

            elbow_position = elbow_position + 0.2;
            elbowServo.setPosition(Range.clip(elbow_position, 0, 1.0));

        } else if (elbowOpen) {

            elbow_position = elbow_position - 0.2;
            elbowServo.setPosition(Range.clip(elbow_position, 0, 1.0));
        }
//
//        telemetry.addData("Elbow position: ", elbow_position);
//        telemetry.update();
    }

    public void armUpDown_LinearSlide(double armVal) {

        telemetry.addData("Inside: armUpDown_Linear(): ", "" + System.currentTimeMillis());
        telemetry.addData("joystick value: ", armVal);

        double armPower = Range.clip(armVal, -1, 1);
        armMotor.setPower(armPower);

        telemetry.addData("Arm power: ", armPower);
        telemetry.update();
    }

}
