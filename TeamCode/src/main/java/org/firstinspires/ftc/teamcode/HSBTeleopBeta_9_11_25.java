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

@TeleOp(name="Basic: Omni Linear OpMode", group="Linear OpMode")
public class HSBTeleopBeta_9_11_25 extends LinearOpMode {

    // Actually create that variables for the 4 main drive motors, and create a stored runtime variable for later display
    private final ElapsedTime runtime = new ElapsedTime();
    //Set to null in order to make it present error if not set.
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() {

        //The following initializes the formerly created variables, but I need to get the actual names of the motors as they were initialized by the robot
        frontLeftDrive = hardwareMap.get(DcMotor.class, "TLD");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BLD");
        frontRightDrive = hardwareMap.get(DcMotor.class, "TRD");
        backRightDrive = hardwareMap.get(DcMotor.class, "BRD");

        //Test motors to make sure stuff works
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        //Uses the robot's telemetry system to get when the program is started by the driver/other user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        //This function just means that the while loop runs until the stop button is hit
        while (opModeIsActive()) {
            //Uses the left joystick to create a forward movement on the y-axis(forward variable) and a strafe effect on the x-axis(lateral)
            //Uses the right joystick to be able to rotate and turn(rotational)
            double forward   = -gamepad1.left_stick_y; 
            double lateral =  gamepad1.left_stick_x;
            double rotational =  gamepad1.right_stick_x;

            // Combine formerly gained values to calculate the power needed for motor
            double frontLeftPower  = forward + lateral + rotational;
            double frontRightPower = forward - lateral - rotational;
            double backLeftPower   = forward - lateral + rotational;
            double backRightPower  = forward + lateral - rotational;
            //This is the max power given to every wheel, sets speed
            
            //The following code uses the 
            double maxPowerOutput = 0.75;
            if (Math.abs(frontLeftPower) > maxPowerOutput) {
                if (frontLeftPower > maxPowerOutput) {
                    frontLeftPower = maxPowerOutput;
                }
                else if (frontLeftPower < -maxPowerOutput) {
                    frontLeftPower = -maxPowerOutput;
                }
            }
            if (Math.abs(frontRightPower) > maxPowerOutput) {
                if (frontRightPower > maxPowerOutput) {
                    frontRightPower = maxPowerOutput;
                }
                else if (frontRightPower < -maxPowerOutput) {
                    frontRightPower = -maxPowerOutput;
                }
            }
            if (Math.abs(backLeftPower) > maxPowerOutput) {
                if (backLeftPower > maxPowerOutput) {
                    backLeftPower = maxPowerOutput;
                }
                if (backLeftPower < -maxPowerOutput) {
                    backLeftPower = -maxPowerOutput;
                }
            }
            if (Math.abs(backRightPower) > maxPowerOutput) {
                if (backRightPower > maxPowerOutput) {
                    backRightPower = maxPowerOutput;
                }
                if (backRightPower < -maxPowerOutput) {
                    backRightPower = -maxPowerOutput;
                }
            }
            // Send calculated power to wheels
            if (Math.abs(frontLeftPower) > 0) {
                if (frontLeftPower < 0) {
                    frontLeftDrive.setPower(frontLeftPower);
                }
                else if (frontLeftPower > 0) {
                    frontLeftDrive.setPower(frontLeftPower);
                }
            }
            else {
                frontLeftDrive.setPower(0);
            }
            frontRightDrive.setPower(frontRightPower);
            if (Math.abs(backLeftPower) > 0) {
                if (backLeftPower < 0) {
                    backLeftDrive.setPower(backLeftPower);
                }
                else if (backLeftPower > 0) {
                    backLeftDrive.setPower(backLeftPower);
                }
            }
            else {
                backLeftDrive.setPower(0);
            }
            backRightDrive.setPower(backRightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("FrontLeftPower", String.valueOf(frontLeftPower));
            telemetry.addData("BackLeftPower", String.valueOf(backLeftPower));
            telemetry.addData("FrontRightPower", String.valueOf(frontRightPower));
            telemetry.addData("BackRightPower", String.valueOf(backRightPower));

            telemetry.update();
        }
    }}
