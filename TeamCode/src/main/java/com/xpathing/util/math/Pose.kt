package com.xpathing.util.math

import kotlin.math.PI

class Pose(var x: Double, var y: Double, var theta:Double = 0.0, var z: Double = 0.0) {
    /**
    *@param at A [Double] that we can flip the pose over (y = at)
    *@return A [Pose] that contains the reflection of the pose over the x-axis parallel line
    */
    fun reflectX(at: Double): Pose {
        y = 2 * at - y
        theta = normalize(-theta)
        return this
    }

    /**
    *@param at A [Double] that we can flip the pose over (x = at)
    *@return A [Pose] that contains the reflection of the pose over the y-axis parallel line
    */
    fun reflectY(at: Double): Pose {
        x = 2 * at - x
        theta = normalize(PI - theta)
        return this
    }

    /**
    *@param at A [Double] offset for the line y = x + at
    *@return A [Pose] that contains the reflection of the pose over the line y = x + at
    */
    fun reflectYX(at: Double): Pose { 
        val oldX = x
        x = y - at
        y = oldX + at
        theta = normalize(PI / 2.0 - theta)
        return this
    }

    fun normalize(angle: Double): Double {
        var a = angle % (2 * PI)
        if (a > PI) a -= 2 * PI
        if (a <= -PI) a += 2 * PI
        return a
    }
}   