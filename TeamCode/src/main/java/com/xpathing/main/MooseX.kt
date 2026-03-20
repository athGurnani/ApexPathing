package com.xpathing.main

import android.annotation.SuppressLint
import android.content.Context
import com.qualcomm.robotcore.eventloop.EventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier
import com.qualcomm.robotcore.util.RobotLog
import com.xpathing.main.localization.*
import com.xpathing.util.math.Pose
import java.util.concurrent.Executors

object MooseX : OpModeManagerNotifier.Notifications {
    var currentPosition = Pose(0.0, 0.0, 0.0)

    public var debug: Boolean = true
    private var running = false
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var manager: OpModeManagerImpl


    fun attach(context: Context, eventLoop: EventLoop) {
        if(debug) RobotLog.ii("MooseX", "attachEventLoop: Attached MooseX to Event Loop")
        eventLoop.opModeManager.registerListener(this)
        this.manager = eventLoop.opModeManager
    }

    override fun onOpModePreInit(opMode: OpMode) {
        if (opMode.javaClass.isAnnotationPresent(Pathing::class.java)) {
            PinpointLocalizer.hardwareMap = opMode.hardwareMap

            if (!running) {
                running = true
                executor.execute {
                    while (running) {
                        PinpointLocalizer.update()
                        currentPosition = PinpointLocalizer.currentPosition
                    }
                }
            }
        }
    }

    override fun onOpModePreStart(opMode: OpMode) {

    }

    override fun onOpModePostStop(opMode: OpMode) {
        running = false // Stop the background thread
    }
}