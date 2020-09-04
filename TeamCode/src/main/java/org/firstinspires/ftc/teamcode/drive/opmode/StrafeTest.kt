package org.firstinspires.ftc.teamcode.drive.opmode

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive

/*
 * This is a simple routine to test translational drive capabilities.
 */
@Config
@Autonomous(group = "drive")
class StrafeTest : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        val trajectory = drive.trajectoryBuilder(Pose2d())
                .strafeRight(DISTANCE)
                .build()
        waitForStart()
        if (isStopRequested) return
        drive.followTrajectory(trajectory)
    }

    companion object {
        var DISTANCE = 60.0 // in
    }
}