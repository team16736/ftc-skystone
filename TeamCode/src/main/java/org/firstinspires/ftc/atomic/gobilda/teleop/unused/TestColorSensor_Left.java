package org.firstinspires.ftc.atomic.gobilda.teleop.unused;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.atomic.gobilda.utilities.ConfigConstants;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "Narwhals - Left Color", group = "Sensor")
public class TestColorSensor_Left extends LinearOpMode {

  ColorSensor colorSensor;
  DistanceSensor distanceSensor;

  @Override
  public void runOpMode() {

    float hsvValues[] = {0F,0F,0F};
    final float values[] = hsvValues;
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    colorSensor = hardwareMap.get(ColorSensor.class, ConfigConstants.LEFT_COLOR);
    colorSensor.enableLed(true);

    distanceSensor = hardwareMap.get(DistanceSensor.class, ConfigConstants.LEFT_COLOR);

    waitForStart();

    while (opModeIsActive()) {

      // convert the RGB values to HSV values.
      Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

      float hueValue = hsvValues[0];
      if (hueValue < 50){
        telemetry.addData("YELLOW: Hue=", hueValue);
      } else {
        telemetry.addData("BLACK: Hue=", hueValue);
      }

      //DISTANCE SENSOR
      telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", distanceSensor.getDistance(DistanceUnit.CM)));
      telemetry.addData("Distance (in)", String.format(Locale.US, "%.01f", distanceSensor.getDistance(DistanceUnit.INCH)));

      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        }
      });

      telemetry.update();
    }

    relativeLayout.post(new Runnable() {
      public void run() {
        relativeLayout.setBackgroundColor(Color.WHITE);
      }
    });
  }
}
