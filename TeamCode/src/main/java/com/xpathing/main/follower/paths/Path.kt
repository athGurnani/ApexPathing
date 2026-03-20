package com.xpathing.main.follower.paths

import com.xpathing.util.math.Pose

abstract class Path(open var startPose: Pose, open var endPose: Pose) {
    /**
    * Get the distance the robot has to travel
    */
    abstract fun distance(): Double 
}