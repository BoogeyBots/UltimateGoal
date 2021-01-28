package org.firstinspires.ftc.teamcode.robot.teleop.advanced

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.IntakeModule
import org.firstinspires.ftc.teamcode.modules.MotorThrowerModule
import org.firstinspires.ftc.teamcode.modules.ServoThrowerModule
import org.firstinspires.ftc.teamcode.modules.WobbleGoalModule

@TeleOp()
class TeleOpWithModules : BBLinearOpMode(){
    override val modules = Robot(setOf( WobbleGoalModule(this), MotorThrowerModule(this), ServoThrowerModule(this), IntakeModule(this)))

    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        modules.modules.forEach(){
            it.init()
        }


        waitForStart()

        while (!isStopRequested) {
            drive.setWeightedDrivePower(
                    Pose2d(
                            (-gamepad1.left_stick_y).toDouble(),
                            (-gamepad1.left_stick_x).toDouble(),
                            (-gamepad1.right_stick_x).toDouble()
                    )
            )

            drive.update()


            if(gamepad1.right_bumper){
                get<WobbleGoalModule>().move_vertically()
            }
            if(gamepad1.left_bumper){
                get<WobbleGoalModule>().move_close()
            }


            if(gamepad2.y and (motorPower < 1 && timeElapsed.seconds() > 0.1)) {
                motorPower += 0.05
                timeElapsed.reset()
            }
            else if(gamepad2.a and (motorPower > 0 && timeElapsed.seconds() > 0.1)){
                motorPower -= 0.05
                timeElapsed.reset()
            }
            if (gamepad2.x){
                get<MotorThrowerModule>().setPower(motorPower)
            }
            else{
                get<MotorThrowerModule>().setPower(0.0)
            }

            if (gamepad2.left_bumper){
                get<ServoThrowerModule>().close()
            }
            else if(gamepad2.right_bumper){
                get<ServoThrowerModule>().open()
            }

            if(gamepad2.dpad_down){
                get<IntakeModule>().move(true)
            }
            else if(gamepad2.dpad_up){
                get<IntakeModule>().move(false)
            }
            else{
                get<IntakeModule>().stop()
            }

            telemetry.addData("Putere Teoretica: ", motorPower)
            telemetry.update()

        }
    }

    companion object{
        var timeElapsed = ElapsedTime()
        var motorPower: Double = 0.0
    }
}