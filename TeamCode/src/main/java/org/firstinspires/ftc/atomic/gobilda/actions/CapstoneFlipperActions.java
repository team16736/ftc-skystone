package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CapstoneFlipperActions {

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    public Servo flipper_hand;

    private double flipper_hand_position = 0.5;

    // Constructor
    public CapstoneFlipperActions(Telemetry opModeTelemetry, HardwareMap opModeHardware) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        flipper_hand = hardwareMap.get(Servo.class, ConfigConstants.FLIPPER_SERVO);

        // 2. Set direction
        flipper_hand.setDirection(Servo.Direction.REVERSE);

        // 3. Set beginning position
        flipper_hand.setPosition(flipper_hand_position);
    }


    public void flipper_Forward_Backward(boolean forward, boolean backward) {

        if (forward) {  //forward move arm inwards (towards the linear slide)

            flipper_hand_position = 0.5;
            telemetry.addData("Left Hook - DOWN Position x: ", flipper_hand_position);

        } else if (backward) {  //backward moves arm outwards (taps the skystone)

            flipper_hand_position = 0;
            telemetry.addData("Left Hook - UP Position y: ", flipper_hand_position);
        }

        flipper_hand.setPosition(flipper_hand_position);
        telemetry.update();
    }


}