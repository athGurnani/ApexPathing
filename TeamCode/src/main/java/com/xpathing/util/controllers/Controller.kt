package com.xpathing.util.controllers

import java.util.function.Supplier

abstract class Controller(open var position: Supplier<Double>, open var target: Supplier<Double>) {
    abstract fun update()
}