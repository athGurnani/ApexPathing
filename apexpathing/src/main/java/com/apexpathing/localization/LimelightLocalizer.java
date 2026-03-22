package com.apexpathing.localization;

import com.apexpathing.geometry.Pose2d;
import com.apexpathing.util.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A localizer using Limelight vision data.
 */
public class LimelightLocalizer implements Localizer {
    private Pose currentPose = new Pose(0, 0, 0);
    private Pose currentVelocity = new Pose(0, 0, 0);

    public LimelightLocalizer(HardwareMap hardwareMap) {
        // Initialization logic for Limelight would go here
    }

    @Override
    public void update() {
        // Limelight update logic:
        // 1. Get latest vision result
        // 2. Transform to field coordinates
        // 3. Update currentPose
        // For now, this is a skeleton.
    }

    /**
     * Gets a pose 2d from the limelight assuming it's setup correctly
     * @return the limelight in Pose 2D,
     */
    public Pose getPose() {
        Pose3D botPose = limelightResult.getBotpose_MT2();
        double x = botPose.getPosition().x;
        double y = botPose.getPosition().y;
        double heading = botPose.getOrientation().getYaw(AngleUnit.RADIANS);
        Pose returnPose = new Pose(x, y, heading);
        return returnPose;
    }

    @Override
    public Pose getVelocity() {
        return currentVelocity;
    }

    @Override
    public void setPose(Pose pose) {
        this.currentPose = pose;
    }

    public Pose updateWithVision(double x, double y, double heading) {
        Pose returnPose = new Pose(x, y, heading);
        this.currentPose = returnPose;
        return returnPose;
    }
}
