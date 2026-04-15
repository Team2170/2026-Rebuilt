// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.vision.components;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotController;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/** IO implementation for real Limelight hardware. */
public class VisionIOLimelight implements VisionIO {

     //----- Hub Tag ID's -----  
    private static final int RED_TAG_A  = 9;
    private static final int RED_TAG_B  = 10;
    private static final int BLUE_TAG_A = 25;
    private static final int BLUE_TAG_B = 26;

    //------ Rawfiducials ------
    private static final int RAW_STRIDE = 7;


    private final Supplier<Rotation2d> rotationSupplier; 
    private final DoubleArrayPublisher orientationPublisher;

    private final DoubleSubscriber latencySubscriber;
    private final DoubleSubscriber txSubscriber;
    private final DoubleSubscriber tySubscriber;
    private final DoubleSubscriber tvSubscriber;
    private final DoubleArraySubscriber megatag1Subscriber;
    private final DoubleArraySubscriber megatag2Subscriber;

 
    //Subscribes to the raw per-tag fiducial data from the Limelight.
    private final DoubleArraySubscriber rawFiducialsSubscriber; 

    


    /**
     * Creates a new VisionIOLimelight.
     *
     * @param name The configured name of the Limelight.
     * @param rotationSupplier Supplier for the current estimated rotation, used for MegaTag 2.
     */
    public VisionIOLimelight(String name, Supplier<Rotation2d> rotationSupplier) {
        var table = NetworkTableInstance.getDefault().getTable(name);
        this.rotationSupplier = rotationSupplier;
        orientationPublisher = table.getDoubleArrayTopic("robot_orientation_set").publish();
        latencySubscriber = table.getDoubleTopic("tl").subscribe(0.0);
        txSubscriber = table.getDoubleTopic("tx").subscribe(0.0);
        tySubscriber = table.getDoubleTopic("ty").subscribe(0.0);
        tvSubscriber = table.getDoubleTopic("tv").subscribe(0.0);
        megatag1Subscriber = table.getDoubleArrayTopic("botpose_wpiblue").subscribe(new double[] {});
        megatag2Subscriber = table.getDoubleArrayTopic("botpose_orb_wpiblue").subscribe(new double[] {});
        
        //Subscribe to get raw fiducial data for hub tag alignment
        rawFiducialsSubscriber = table.getDoubleArrayTopic("rawfiducials").subscribe(new double[]{});
    }




    @Override
    public void updateInputs(VisionIOInputs inputs) {
        // Update connection status based on whether an update has been seen in the last 250ms
        inputs.connected = ((RobotController.getFPGATime() - latencySubscriber.getLastChange()) / 1000) < 250;

        inputs.hasTarget = tvSubscriber.get() == 1.0;

        // Update target observation
        inputs.latestTargetObservation = new TargetObservation(
                Rotation2d.fromDegrees(txSubscriber.get()), Rotation2d.fromDegrees(tySubscriber.get()));

        // Update orientation for MegaTag 2
        orientationPublisher.accept(new double[] {rotationSupplier.get().getDegrees(), 0.0, 0.0, 0.0, 0.0, 0.0});
        NetworkTableInstance.getDefault().flush(); // Increases network traffic but recommended by Limelight

        // Read new pose observations from NetworkTables
        Set<Integer> tagIds = new HashSet<>();
        List<PoseObservation> poseObservations = new LinkedList<>();
        for (var rawSample : megatag1Subscriber.readQueue()) {
            if (rawSample.value.length == 0) continue;
            for (int i = 11; i < rawSample.value.length; i += 7) {
                tagIds.add((int) rawSample.value[i]);
            }
            poseObservations.add(new PoseObservation(
                    // Timestamp, based on server timestamp of publish and latency
                    rawSample.timestamp * 1.0e-6 - rawSample.value[6] * 1.0e-3,

                    // 3D pose estimate
                    parsePose(rawSample.value),

                    // Ambiguity, using only the first tag because ambiguity isn't applicable for multitag
                    rawSample.value.length >= 18 ? rawSample.value[17] : 0.0,

                    // Tag count
                    (int) rawSample.value[7],

                    // Average tag distance
                    rawSample.value[9],

                    // Observation type
                    PoseObservationType.MEGATAG_1));
        }
        for (var rawSample : megatag2Subscriber.readQueue()) {
            if (rawSample.value.length == 0) continue;
            for (int i = 11; i < rawSample.value.length; i += 7) {
                tagIds.add((int) rawSample.value[i]);
            }
            poseObservations.add(new PoseObservation(
                    // Timestamp, based on server timestamp of publish and latency
                    rawSample.timestamp * 1.0e-6 - rawSample.value[6] * 1.0e-3,

                    // 3D pose estimate
                    parsePose(rawSample.value),

                    // Ambiguity, zeroed because the pose is already disambiguated
                    0.0,

                    // Tag count
                    (int) rawSample.value[7],

                    // Average tag distance
                    rawSample.value[9],

                    // Observation type
                    PoseObservationType.MEGATAG_2));
        }

        // Save pose observations to inputs object
        inputs.poseObservations = new PoseObservation[poseObservations.size()];
        for (int i = 0; i < poseObservations.size(); i++) {
            inputs.poseObservations[i] = poseObservations.get(i);
        }

        // Save tag IDs to inputs objects
        inputs.tagIds = new int[tagIds.size()];
        int i = 0;
        for (int id : tagIds) {
            inputs.tagIds[i++] = id;
        }




        


        /* -------- Hub Tag Alignment -------- */           //MAIN LOGIC HERE TO ANGLE BETWEEN TWO APRILTAGS
        boolean isRed = DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red;
        int hubTagIdA = isRed ? 9 : 25;
        int hubTagIdB = isRed ? 10 : 26;

        //Temporary variables to store data in
        double txA = 0, txB = 0, distA = 0, distB = 0;
        boolean sawA = false, sawB = false;

        double[] raw = rawFiducialsSubscriber.get();
        for (int j = 0; j + 7 <= raw.length; j += 7) {
            int id = (int) raw[j];

            //If tag matches ID A (9 or 25)
            if (id == hubTagIdA) {
                txA = raw[j + 1];
                distA = raw[j + 5];
                sawA = true;
            }

            //If tag matches ID B (10 or 26)
            if (id == hubTagIdB) {
                txB = raw[j + 1];
                distB = raw[j + 5];
                sawB = true;
            }
        }

        inputs.hubTagsVisible = sawA && sawB;

        if (sawA && sawB) {
            // Both tags visible — average their angles to find the true center of the hub opening, and average their distances for range estimation
            inputs.hubMidpointTx = (txA + txB) / 2.0;
            inputs.hubTagDistance = (distA + distB) / 2.0;
        } else if (sawA) {
            //Populate fields for logging anyways although only one tag was detected
            inputs.hubMidpointTx = txA;
            inputs.hubTagDistance = distA;
        } else if (sawB) {
            //Populate fields for logging anyways although only one tag was detected
            inputs.hubMidpointTx = txB;
            inputs.hubTagDistance = distB;
        } else {
            //No tags visible
            inputs.hubMidpointTx = 0.0;
            inputs.hubTagDistance = 0.0;
        }
    }



   


    /** Parses the 3D pose from a Limelight botpose array. */
    private static Pose3d parsePose(double[] rawLLArray) {
        return new Pose3d(
                rawLLArray[0],
                rawLLArray[1],
                rawLLArray[2],
                new Rotation3d(
                        Units.degreesToRadians(rawLLArray[3]),
                        Units.degreesToRadians(rawLLArray[4]),
                        Units.degreesToRadians(rawLLArray[5])));
    }
}