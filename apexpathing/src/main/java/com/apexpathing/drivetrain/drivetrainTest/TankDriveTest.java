package com.apexpathing.drivetrain.drivetrainTest;

import com.apexpathing.drivetrain.TankDrive;
import com.apexpathing.localization.PinpointLocalizer;
import com.apexpathing.util.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/**
 * 4WD tank drive tank drive beta test :)
 * @author Sohum Arora - 22985 Paraducks
 */
public class TankDriveTest extends LinearOpMode {
    private static final String lF  = "leftFront";
    private static final String rF = "rightFront";
    private static final String lR= "leftRear";
    private static final String rR = "rightRear";
    TankDrive drive;
    private boolean brakeMode = false;
    PinpointLocalizer localizer;
    String localizerName = "localizer"; //todo change as required
    double localizerXOffset = 0.0;
    double localizerYOffset = 0.0;
    //todo change offsets as required


    @Override
    public void runOpMode() throws InterruptedException {
        drive = new TankDrive(hardwareMap,telemetry,brakeMode, lF, rF, lR, rR);
        localizer = new PinpointLocalizer(hardwareMap, localizerName, localizerXOffset, localizerYOffset);
        localizer.init();

        telemetry.addLine("Tank Drivetrain Movement Test");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            localizer.update();
            Pose currentPose = localizer.getPose();

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            drive.drive(x,y,turn);

            telemetry.addData("Bot pose ", currentPose);
            telemetry.addData("x ", currentPose.x());
            telemetry.addData("y ", currentPose.y());
            telemetry.addData("heading ", currentPose.heading());
            telemetry.update();
        }
    }
}
