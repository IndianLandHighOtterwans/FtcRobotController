/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Finished Untested TeleOP", group="Linear OpMode")
public class HSBTeleopBeta_TANK extends LinearOpMode {

    // Actually create that variables for the 4 main drive motors, and create a stored runtime variable for later display
    private final ElapsedTime runtime = new ElapsedTime();
    //Set to null in order to make it present error if not set.
    private DcMotor LeftDrive = null;
    private DcMotor RightDrive = null;
    private DcMotor input = null;
    double maxPowerOutput = 0.75;

    @Override
    public void runOpMode() {

        //The following initializes the formerly created variables, but I need to get the actual names of the motors as they were initialized by the robot
        LeftDrive = hardwareMap.get(DcMotor.class, "LD");
        RightDrive = hardwareMap.get(DcMotor.class, "RD");
        input = hardwareMap.get(DcMotor.class, "IN");
        //Test motors to make sure stuff works
        LeftDrive.setDirection(DcMotor.Direction.FORWARD);
        RightDrive.setDirection(DcMotor.Direction.FORWARD);
        input.setDirection(DcMotor.Direction.REVERSE);
        //Uses the robot's telemetry system to get when the program is started by the driver/other user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        //This function just means that the while loop runs until the stop button is hit
        while (opModeIsActive()) {
            //Enables Input and Output
            if (gamepad1.leftBumperWasPressed()) {
                input.setPower(1);
            }
            if (gamepad1.leftBumperWasReleased()) {
                input.setPower(0);
            }
            //Uses the left joystick to create a forward movement on the y-axis(forward variable) and a strafe effect on the x-axis(lateral)
            //Uses the right joystick to be able to rotate and turn(rotational)

            // Combine formerly gained values to calculate the power needed for motor

            //This is the max power given to every wheel, sets speed
            
            //The following code uses the maxPowerOutput in order to cap motor power at 75%
            double leftforward = gamepad1.left_stick_y;
            double leftbackward = -gamepad1.left_stick_y;
            double rightforward = gamepad1.right_stick_y;
            double rightbackward = -gamepad1.left_stick_y;
            double LeftPower = leftforward + leftbackward;
            double RightPower = rightforward + rightbackward;
            if (maxPowerOutput < 0.75 && gamepad1.aWasPressed()) {
                maxPowerOutput = maxPowerOutput + 0.05;
            }
            else if (maxPowerOutput > 0.00 && gamepad1.bWasPressed()) {
                maxPowerOutput = maxPowerOutput - 0.05;
            }
            if (Math.abs(LeftPower) > maxPowerOutput) {
                if (LeftPower > maxPowerOutput) {
                    LeftPower = maxPowerOutput;
                }
                else if (LeftPower < -maxPowerOutput) {
                    LeftPower = -maxPowerOutput;
                }
            }
            if (Math.abs(RightPower) > maxPowerOutput) {
                if (RightPower > maxPowerOutput) {
                    RightPower = maxPowerOutput;
                }
                else if (RightPower < -maxPowerOutput) {
                    RightPower = -maxPowerOutput;
                }
            }
            // Send calculated power to wheels
            //Zeros are here to ensure that there is a clean break when we actually stop
            if (Math.abs(LeftPower) > 0) {
                if (LeftPower < 0) {
                    LeftDrive.setPower(LeftPower);
                }
                else if (LeftPower > 0) {
                    LeftDrive.setPower(LeftPower);
                }
            }
            else {
                LeftDrive.setPower(0);
            }
            LeftDrive.setPower(LeftPower);
            if (Math.abs(RightPower) > 0) {
                if (RightPower < 0) {
                    RightDrive.setPower(RightPower);
                }
                else if (RightPower > 0) {
                    RightDrive.setPower(RightPower);
                }
            }
            else {
                RightDrive.setPower(0);
            }
            RightDrive.setPower(RightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("LeftPower", String.valueOf(LeftPower));
            telemetry.addData("RightPower", String.valueOf(RightPower));
            telemetry.addData("Max Power", String.valueOf(maxPowerOutput*100));
            telemetry.update();


        }
    }}
