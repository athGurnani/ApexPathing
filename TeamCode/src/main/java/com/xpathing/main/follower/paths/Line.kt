package com.xpathing.main.follower.paths

import com.xpathing.util.math.Pose
import kotlin.math.pow
import kotlin.math.sqrt

class Line(override var startPose: Pose, override var endPose: Pose): Path(startPose, endPose) {
    override fun distance(): Double {
        return sqrt((endPose.y - startPose.y).pow(2) + (endPose.x - startPose.x).pow(2))
    }
}