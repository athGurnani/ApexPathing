import com.qualcomm.robotcore.hardware.HardwareMap
import com.xpathing.main.localization.Localizer
import com.xpathing.main.localization.LocalizerBase
import com.xpathing.util.math.Pose

// Imports
@Localizer

object PinpointLocalizer: LocalizerBase() {
    // Figure out how to get the hardware map in without having to pass it in as a parameter

    lateinit var hardwareMap: HardwareMap

    override fun update() {
        // Figure out the math to get pinpoint pose
    }

    override fun setPose(pose: Pose) {
        TODO("Not yet implemented")
    }
}
