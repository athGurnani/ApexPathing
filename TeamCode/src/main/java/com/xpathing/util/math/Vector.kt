package com.xpathing.util.math

import kotlin.math.atan2
import kotlin.math.sqrt

class Vector(var x: Double, var y: Double, var z: Double = 0.0) {
    var theta:Double = atan2(y,x)

    var phi: Double

    init {
        val r = sqrt(x * x + y * y)
        phi = atan2(z, r)
    } 
}
