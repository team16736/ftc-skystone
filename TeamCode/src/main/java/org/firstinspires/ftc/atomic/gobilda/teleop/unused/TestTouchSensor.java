package org.firstinspires.ftc.atomic.gobilda.teleop.unused;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "Test Touch Sensor", group = "Sensor")
@Disabled
public class TestTouchSensor extends LinearOpMode {
    /**
     * It is HIGH if the button is unpressed.
     * It pulls LOW if the button is pressed.
     */

    DigitalChannel digitalTouch;  // Hardware Device Object

    @Override
    public void runOpMode() {

        digitalTouch = hardwareMap.get(DigitalChannel.class, "touch_sensor");

        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        while (opModeIsActive()) {

            if (digitalTouch.getState() == true) {

                telemetry.addData("Touch Sensor ", " NOT Pressed " + System.currentTimeMillis());
                telemetry.update();

            } else {

                telemetry.addData("Touch Sensor ", " Pressed " + System.currentTimeMillis());
                telemetry.update();
            }

            telemetry.update();
        }
    }
}
