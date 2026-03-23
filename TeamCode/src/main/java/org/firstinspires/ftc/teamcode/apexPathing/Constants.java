package org.firstinspires.ftc.teamcode.apexPathing;

import com.apexpathing.drivetrain.MecanumConstants;
import com.apexpathing.drivetrain.MecanumDrive;
import com.apexpathing.localization.PinpointLocalizer;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Configurable
public class Constants {
    public static PinpointLocalizer localizer = null;
    public static MecanumConstants dtConstants = null;
    public Constants(HardwareMap hardwareMap) {
        localizer = new PinpointLocalizer(hardwareMap, "pp", 0, 0);
        dtConstants = new MecanumConstants();
    }

    public static void init() {
        localizer.init();
    }
}
