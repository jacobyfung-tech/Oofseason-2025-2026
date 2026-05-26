package org.firstinspires.ftc.teamcode.opModes;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import dev.nextftc.core.commands.Command;

import org.firstinspires.ftc.teamcode.pedroPathing.Drawing;
import org.firstinspires.ftc.teamcode.subsystems.sampleSubsystem;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.ftc.NextFTCOpMode;

public class clawreal extends NextFTCOpMode {
    sampleSubsystem clawCom;

    public String lastAct = "";

    public void onInit() {
        telemetry.addLine("Init was Pressed!");
        telemetry.update();

        clawCom = new sampleSubsystem();

    }
    @Override
    public void onUpdate() {
        // Loops after start until stop

        // Here are three important things to put in your update:
        // follower().update(), which requires the import static dev.nextftc.extensions.pedro.PedroComponent.follower; and updates your localization constantly!
        // Drawing.drawDebug(follower()), which draws the robot and paths to Panels.
        // BindingManager.update(), which constantly checks for bindings and allows your previously made binds to work!

        follower().update();
        Drawing.drawDebug(follower());
        BindingManager.update();



        if (gamepad1.triangleWasPressed()){
            CommandManager.INSTANCE.scheduleCommand(clawCom.chain);
        }


        if (CommandManager.INSTANCE.isScheduled(clawCom.chain)){
            telemetry.addLine("doing other process");
            telemetry.update();
            return;
            //chain is the only command that requires more than 1 frame to execute
            //don't accept input until done
        }


        if (gamepad1.squareWasPressed()){
            CommandManager.INSTANCE.scheduleCommand(clawCom.switchClawPos);
        }
        else if (gamepad1.circleWasPressed()){
            CommandManager.INSTANCE.scheduleCommand(clawCom.openClaw);
        }
        else if (gamepad1.crossWasPressed()){
            CommandManager.INSTANCE.scheduleCommand(clawCom.closeClaw);
        }

        telemetry.addLine("not automated");

        telemetry.update();


    }

}
