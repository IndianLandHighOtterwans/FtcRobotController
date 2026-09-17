package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "QualAuto1", group = "Linear OpMode")
public class SimpleAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime driveRunTime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    static double constantSpeed = 0.75;

    @Override
    public void runOpMode() {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "TLD");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BLD");
        frontRightDrive = hardwareMap.get(DcMotor.class, "TRD");
        backRightDrive = hardwareMap.get(DcMotor.class, "BRD");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status:", "Prepared");
        telemetry.update();

        waitForStart();

        frontLeftDrive.setPower(constantSpeed);
        backLeftDrive.setPower(constantSpeed);
        frontRightDrive.setPower(constantSpeed);
        backRightDrive.setPower(constantSpeed);
        while (opModeIsActive() && (driveRunTime.seconds() < 2.0)) {
            telemetry.addData("Status:", "Going Forward");
        }
        frontLeftDrive.setPower(-constantSpeed);
        backLeftDrive.setPower(-constantSpeed);
        frontRightDrive.setPower(-constantSpeed);
        backRightDrive.setPower(-constantSpeed);
        driveRunTime.reset();
        while (opModeIsActive() && (driveRunTime.seconds() < 2.0)) {
            telemetry.addData("Status:", "Going Backward");
        }
        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);
    }
}
