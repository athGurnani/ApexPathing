package com.xpathing.util.math

import kotlin.math.PI
import kotlin.math.sqrt

data class Pose(
//===========XENON'S CODE======================
    var x : Double,
    var y : Double,
    var heading : Double

) {
    operator fun plus(other : Pose) : Pose {
        return Pose(
            x + other.x,
            y + other.y,
            Math.toRadians(normalize(heading + other.heading))
        )
    }
    operator fun minus(other : Pose) : Pose {
        return Pose(
            x - other.x,
            y - other.y,
            Math.toRadians(normalize(heading - other.heading))
        )
    }
    fun rotate(angle : Double) : Pose {
        val cosA = Math.cos(angle)
        val sinA = Math.sin(angle)

        return Pose(
            x * cosA - y * sinA,
            x * sinA + y * cosA,
            normalize(heading + angle)
        )
    }
    fun getDistance(other : Pose) : Double {
        val dX = other.x - x
        val dY = other.y - y
        return Math.hypot(dX, dY)
    }
    operator fun times(k: Double): Pose {
        return Pose(x * k, y * k, heading * k)
    }
    fun copyPose(): Pose {
        return Pose(x, y, heading)
    }
    fun reflectX(at: Double): Pose {
        y = 2 * at - y
        heading = normalize(-heading)
        return this
    }
    fun reflectY(at: Double): Pose {
        x = 2 * at - x
        heading = normalize(PI - heading)
        return this
    }
    fun reflectYX(at: Double): Pose {
        val oldX = x
        x = y - at
        y = oldX + at
        heading = normalize(PI / 2.0 - heading)
        return this
    }
    operator fun div(scalar: Double) = apply {
        x /= scalar
        y /= scalar
    }
    infix fun distanceTo(otherPose: com.xpathing.util.math.Pose): Double =
        sqrt(distanceSquaredFrom(otherPose))

    /**
     * Calculates the distance from another pose to this pose
     * Exactly the same as distanceTo() however is included for user flexibility
     *
     * @return the distance from another pose
     */
    infix fun distanceFrom(otherPose: com.xpathing.util.math.Pose): Double = distanceTo(otherPose)

    /**
     * Calculates the squared distance between one pose and another pose
     *
     * @return the squared distance to another pose
     */
    fun distanceSquaredFrom(otherPose: com.xpathing.util.math.Pose): Double {
        val deltaX = otherPose.x - this.x
        val deltaY = otherPose.y - this.y
        return deltaX * deltaX + deltaY * deltaY
    }

    /**
     * Converts the pose to a vector
     *
     * @return the vector form of the current pose
     */
    fun asVector(): Vector = Vector(x, y)

    /**
     * Converts this pose to a specified coordinate system
     *
     * @param coordSys the coordinate system to convert to
     * @return the pose converted to the specified coordinate system
     */

    fun inCoordinateSystem(current: CoordinateSystem, target: CoordinateSystem): Pose =
        target.fromApexCoordinates(current.toApexCoordinates(this))
    companion object {
        fun normalize(angle: Double): Double {
            var a = angle
            while (a > Math.PI) {
                a -= 2 * Math.PI
            }
            while (a < -Math.PI) {
                a += 2 * Math.PI
            }
            return a
        }
    }

}