package com.xpathing.util.controllers

import java.util.function.Supplier

class PIDController(override var position: Supplier<Double>, override var target: Supplier<Double>): Controller(position, target) {
    override fun update() {
        TODO("Not yet implemented")
    }
}