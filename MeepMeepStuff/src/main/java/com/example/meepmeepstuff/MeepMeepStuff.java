package com.example.meepmeepstuff;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.Vector2d;

public class MeepMeepStuff {
    public static Vector2d position = null;
    public static int testApril = 2;
    public static void main(String[] args) {
        if (testApril == 0) {
            position = new Vector2d(36,35);
        }
        else if (testApril == 1) {
            position = new Vector2d(12,35);
        }
        else if (testApril == 2) {
            position = new Vector2d(-12,35);
        }
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(38, -35, 0))
                .turn(Math.toRadians(90))
                .lineToY(0)
                .turn(Math.toRadians(90))
                .lineToX(0)
                .lineToX(-34)
                .turn(Math.toRadians(-90))
                .lineToY(35)
                .strafeTo(position)
                .turn(Math.toRadians(180))
                .lineToY(56)
                .strafeTo(new Vector2d(-12,35))
                .turn(Math.toRadians(-45))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}