package org.firstinspires.ftc.atomic.gobilda.actions;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SensorControlActions {

    private DigitalChannel limit_switch;

    private Telemetry telemetry;
    private HardwareMap hardwareMap;


    // Constructor
    public SensorControlActions(Telemetry opModeTelemetry, HardwareMap opModeHardware ) {

        this.telemetry = opModeTelemetry;
        this.hardwareMap = opModeHardware;

        // 1. Hardware config
        limit_switch = hardwareMap.get(DigitalChannel.class, "limit_switch");


        // set the digital channel to input.
        limit_switch.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean isLimitSwitchPressed(){

        boolean switchPressed;

        if (limit_switch.getState()) {

            switchPressed = false;
            telemetry.addData("Digital Touch: ", " NOT Pressed ");

        } else {

            switchPressed = true;
            telemetry.addData("Digital Touch: ", " Pressed");
        }
        telemetry.update();

        return switchPressed;
    }

}