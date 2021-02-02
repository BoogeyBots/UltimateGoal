package org.firstinspires.ftc.teamcode.robot.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Mecanum
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.modules.MotorThrowerModule
import org.firstinspires.ftc.teamcode.modules.Recognition
import org.firstinspires.ftc.teamcode.modules.ServoThrowerModule
import org.firstinspires.ftc.teamcode.modules.WobbleGoalModule
import org.firstinspires.ftc.teamcode.vision.TensorFlowObjectDetection
import org.opencv.core.Mat

@Autonomous(name = "TwoWobbleGoal")
class TwoWobbleGoal : BBLinearOpMode(){

    override val modules: Robot = Robot(setOf(WobbleGoalModule(this, inAuto = true), ServoThrowerModule(this), MotorThrowerModule(this), Recognition(this)))


    override fun runOpMode() {
        val robot = Mecanum(hardwareMap)
        modules.modules.forEach(){
            it.init()
        }

        val nrRings = get<Recognition>().recognizeRings()

        val startPose = Pose2d(-63.0, -42.4)
        robot.poseEstimate = startPose

        waitForStart()

        if(nrRings == Recognition.NrRings.ONE) {
            val trajectory1 = robot.trajectoryBuilder(startPose)
                    .splineTo(Vector2d(-20.0, -52.0), Math.toRadians(0.0))
                    .splineTo(Vector2d(27.0, -42.0), Math.toRadians(0.0))

                    .addDisplacementMarker {
                        get<WobbleGoalModule>().move_vertically()
                        wait(.3)
                    }
                    .addDisplacementMarker {
                        get<WobbleGoalModule>().move_close()
                    }
                    .build()

            val trajectory2 = robot.trajectoryBuilder(Pose2d(15.0, -42.0, 0.0))
                    .lineTo(Vector2d(-1.0, -32.0))
                    .build()

            val trajectory3 = robot.trajectoryBuilder(Pose2d(-1.0, -32.0, Math.toRadians(180.0)))
                    .lineTo(Vector2d(-1.0, -26.2))
                    .build()

            val trajectory4 = robot.trajectoryBuilder(Pose2d(-1.0, -26.2, Math.toRadians(180.0)))
                    .lineTo(Vector2d(-32.9, -26.2))
                    .build()

            val trajectory5 = robot.trajectoryBuilder(Pose2d(-32.9, -26.2, Math.toRadians(0.0)))
                    .splineTo(Vector2d(18.0, -38.0), 0.00)
                    .build()

            robot.followTrajectory(trajectory1)
            robot.followTrajectory(trajectory2)
            robot.turn(Math.toRadians(180.0))
            get<MotorThrowerModule>().setPower(0.75)
            wait(0.75)

            for(i in 1..3){
                get<ServoThrowerModule>().open()
                wait(0.2 )
                get<ServoThrowerModule>().close()
                wait(0.3)
            }

            get<MotorThrowerModule>().setPower(0.0)

            robot.followTrajectory(trajectory3)
            robot.followTrajectory(trajectory4)
            get<WobbleGoalModule>().wobblegoal.position = 0.06

            wait(0.2)
            get<WobbleGoalModule>().move_close()
            wait(0.7)
            get<WobbleGoalModule>().wobblegoal.position = 0.12
            wait(.4)

            robot.turn(Math.toRadians(180.0))
            wait(.1)

            robot.followTrajectory(trajectory5)
            get<WobbleGoalModule>().move_close()
            wait(1.0)
        }

        if(nrRings == Recognition.NrRings.ZERO){
            val trajectory1 = robot.trajectoryBuilder(startPose)
                    .lineTo(Vector2d(0.0, -62.0))
                    .addDisplacementMarker {
                        get<WobbleGoalModule>().move_vertically()
                        wait(.3)
                    }
                    .addDisplacementMarker {
                        get<WobbleGoalModule>().move_close()
                    }
                    .build()

            val trajectory2 = robot.trajectoryBuilder(robot.poseEstimate)
                    .lineTo(Vector2d(-1.0, -32.0))
                    .build()

            val trajectory3 = robot.trajectoryBuilder(Pose2d(-1.0, -32.0, Math.toRadians(180.0)))
                    .lineTo(Vector2d(-1.0, -26.2))
                    .build()

            val trajectory4 = robot.trajectoryBuilder(Pose2d(-1.0, -26.2, Math.toRadians(180.0)))
                    .lineTo(Vector2d(-32.9, -26.2))
                    .build()

            val trajectory5 = robot.trajectoryBuilder(Pose2d(-32.9, -26.2, Math.toRadians(0.0)))
                    .lineTo(Vector2d(-6.0, -61.5))
                    .build()

            val trajectory6 = robot.trajectoryBuilder(Pose2d(-6.0, -61.5, 0.0))
                    .lineTo(Vector2d(-12.0, -61.5))
                    .build()

            val trajectory7 = robot.trajectoryBuilder(Pose2d(-12.0, -61.5, 0.0))
                    .lineTo(Vector2d(10.0, -37.0))
                    .build()



            robot.followTrajectory(trajectory1)
            wait(.5)
            robot.followTrajectory(trajectory2)
            robot.turn(Math.toRadians(180.0))
            get<MotorThrowerModule>().setPower(0.75)
            wait(0.75)

            for(i in 1..3){
                get<ServoThrowerModule>().open()
                wait(0.2 )
                get<ServoThrowerModule>().close()
                wait(0.3)
            }

            get<MotorThrowerModule>().setPower(0.0)

            robot.followTrajectory(trajectory3)
            robot.followTrajectory(trajectory4)
            get<WobbleGoalModule>().wobblegoal.position = 0.06
            wait(0.2)
            get<WobbleGoalModule>().move_close()
            wait(0.7)
            get<WobbleGoalModule>().wobblegoal.position = 0.12
            wait(.4)

            robot.turn(Math.toRadians(180.0))
            wait(.1)

            robot.followTrajectory(trajectory5)
            get<WobbleGoalModule>().move_close()
            wait(.5)
            robot.followTrajectory(trajectory6)
            robot.followTrajectory(trajectory7)
        }

    }


}