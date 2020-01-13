package org.firstinspires.ftc.atomic.gobilda.actions.unused;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This program uses CSR Servo - Continuous Servo Rotation
 */
public class MecanumCSRServoActions {

    private double trapServoPower = 0.1;
    private double hookServoPower = 0.1;

    private CRServo servo_trap = null;
    private CRServo servo_left = null;
    private CRServo servo_right = null;

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    public MecanumCSRServoActions(Telemetry telemetry, HardwareMap hardwareMap) {

        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        setupServoMotors();
    }

    public void setupServoMotors() {
        servo_trap = hardwareMap.get(CRServo.class, "servo_trap");
        servo_left = hardwareMap.get(CRServo.class, ConfigConstants.SERVO_LEFT);
        servo_right = hardwareMap.get(CRServo.class, ConfigConstants.SERVO_RIGHT);

        servo_trap.setDirection(CRServo.Direction.FORWARD);

        servo_left.setDirection(CRServo.Direction.REVERSE); //Motor attachment is flipped
        servo_right.setDirection(CRServo.Direction.FORWARD);
    }

    public void trapperMoveUpDown(boolean dpadLeftPressed, boolean dpadRightPressed) {

        if (dpadLeftPressed) {
            trapServoPower = trapServoPower + 0.20;

        } else if (dpadRightPressed) {
            trapServoPower = trapServoPower - 0.20;

        } else {
            //When none of the dpad is pressed
            trapServoPower = 0.0;
        }

        servo_trap.setPower(trapServoPower);
        telemetry.addData("trap-Power: ", trapServoPower);
        telemetry.update();
    }

    public void hookMoveUpDown(boolean dpadUpPressed, boolean dpadDownPressed) {

        if (dpadUpPressed) {
            servo_right.setPower(hookServoPower - 0.20);
            servo_left.setPower(hookServoPower + 0.20);

        } else if (dpadDownPressed) {
            servo_left.setPower(hookServoPower - 0.20);
            servo_right.setPower(hookServoPower + 0.20);

        } else {
            //When none of the dpad is pressed
            servo_left.setPower(0.0);
            servo_right.setPower(0.0);
        }
        //telemetry.addData("hookUpDown-Power: ", hookServoPower);
        telemetry.update();
    }

    public void trapBrick() {
    }

    public void untrapBrick() {
    }

}