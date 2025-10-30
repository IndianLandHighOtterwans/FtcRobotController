package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

// VisionPortal imports
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;


//Class Setup
@Autonomous(name = "My AprilTag OpMode", group = "Vision")
public class SkCameraCode extends LinearOpMode {

    // Variables: The VisionPortal controls the camera and the vision processors
    private VisionPortal visionPortal;

    // April Tag Variable: The AprilTagProcessor handles AprilTag detection
    private AprilTagProcessor aprilTagProcessor;

    @Override
    public void runOpMode() {

        //  Step 1: Create and configure the AprilTagProcessor 
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // Tag # 
                .setDrawTagID(true) // Draw tag ID on the preview stream
                .build();

        // Step 2: Create the VisionPortal and connect the webcam 
        visionPortal = new VisionPortal.Builder()
                .addProcessor(aprilTagProcessor) // Add AprilTag processor
                .setCamera(hardwareMap.get(WebcamName.class, "AprilTagCam")) // Use configured webcam
                .enableLiveView(true) // Optional: allows live preview in DS
                .build();
//Telemetry Updating before starting
        telemetry.addLine("AprilTag camera initialized. Press START to begin.");
        telemetry.update();

        // Step 3: Waiting for Start
        waitForStart();

        //Step 4: Main OpMode loop 
        while (opModeIsActive()) {

            // Get a list of all currently detected tags
            List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

            // Loop through each detection
            for (AprilTagDetection detection : currentDetections) {

                if (detection.metadata != null) { // Check if metadata is available
                    // Basic info- It reads the tags info
                    int tagId = detection.id; // Tag numeric ID
                    String tagName = detection.metadata.name; // Tag name 

                    // Pose (position/orientation relative to camera)
                    //These 6 numbers describe exactly where and how the tag sits to our camera
                    double x = detection.ftcPose.x;      // left/right (mm)
                    double y = detection.ftcPose.y;      // forward/backward (mm)
                    double z = detection.ftcPose.z;      // up/down (mm)
                    double yaw = detection.ftcPose.yaw;  // rotation around vertical axis (deg)
                    double pitch = detection.ftcPose.pitch; // rotation around lateral axis
                    double roll = detection.ftcPose.roll;   // rotation around forward axis

                    // Send data to Driver Station
                    telemetry.addData("Tag ID", tagId);
                    telemetry.addData("Tag Name", tagName);
                    telemetry.addData("X (mm)", x);
                    telemetry.addData("Y (mm)", y);
                    telemetry.addData("Z (mm)", z);
                    telemetry.addData("Yaw (deg)", yaw);
                    telemetry.addData("Pitch (deg)", pitch);
                    telemetry.addData("Roll (deg)", roll);
                }
            }

            telemetry.update();
            sleep(20); // delay for stability
        }

        // Step 5: Clean up the camera when done 
        visionPortal.close();
    }
}

