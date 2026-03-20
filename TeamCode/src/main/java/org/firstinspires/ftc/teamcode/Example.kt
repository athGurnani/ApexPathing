package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
// @Pathing // this is what calls our localizer to be attached, from there you have to create a follower and assign it to the robot
class Op(): OpMode() {
    override fun init() {}

    override fun loop() {
        telemetry.run {
            addData("Current Position", MooseX.currentPosition)
            addData("Current Velocity", MooseX.currentVelocity)
            addData("Current Acceleration", MooseX.currentAcceleration)

            update()
        }
    }
}