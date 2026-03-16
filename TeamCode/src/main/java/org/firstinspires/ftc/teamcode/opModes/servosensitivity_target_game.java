package org.firstinspires.ftc.teamcode.opModes;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Drawing;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;


import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.MotorEx;


/**
 * Here is a sample teleOp, following the NextFTC format.
 */
@TeleOp(name = "Sample TeleOp")
public class servosensitivity_target_game extends NextFTCOpMode {
    /**
     * Here is where you would add components to the op mode. I've added the
     * pedro,
     * bindings (necessary for using NextBindings),
     * bulk read (decreases loop anglegood_streak), and
     * command manager (which stores and organizes all the commands scheduled)
     * In your real opModes, you must include ALL the subsystems you create.
     */


    public servosensitivity_target_game() {
        telemetry.addLine("Init was Pressed!");
        addComponents(
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE,
                CommandManager.INSTANCE
        );
    }

    private final MotorEx motorExample = new MotorEx("motorExample");

    @Override
    public void onInit() {
        // Runs ONCE when INIT is pressed
        telemetry.addLine("Init was Pressed!");
        telemetry.update();
    }
    @Override
    public void onWaitForStart() {
        // Loops until start is pressed, after init is pressed
        telemetry.addLine("Init was Pressed!, but start wasnt!");
        telemetry.addData("This outputs the gamepad value of the left stick x, constantly updating, ", gamepad1.left_stick_x);
        telemetry.update();
    }
    public double targetrot = 180;
    public int exponent = 3;
    public final double motorT = 537.7;
    public double deg = 0;
    public int buffer= 0;
    public int anglegood_streak = 0;
    public double prevtime = 0;
    public int tolerance = 6;
    @Override
    public void onStartButtonPressed() {
        // Runs ONCE when START is pressed
        // Put your bindings here!
        motorExample.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addLine("Start was pressed!, This will most likely disappear when the loop telemetry update removes it!");
        telemetry.update();
    }


    @Override
    public void onUpdate() {
        follower().update();
        Drawing.drawDebug(follower());
        BindingManager.update();

        if (Gamepads.gamepad1().cross().toggleOnBecomesTrue().get() && exponent > 1){
            exponent -= 2;

        }
        else if (Gamepads.gamepad1().triangle().toggleOnBecomesTrue().get()){
            exponent += 2;
        }

        motorExample.setPower(Math.pow(gamepad1.left_stick_x, exponent));
        deg = ((motorExample.getCurrentPosition()/motorT) + 360) % 360;

        if (Math.abs(targetrot - deg) < tolerance || Math.abs((targetrot + 360) - deg) < tolerance) { //win condition if close enough mode 360
            anglegood_streak += 1;
        }
        else //display target angle
        {
            anglegood_streak = 0;
            buffer = 0;
            telemetry.addLine("Pos: " + deg);
            telemetry.addLine("New Target: " + targetrot);
        }

        if (anglegood_streak > 180){ //if won for long enough

            if (buffer < 180) {
                telemetry.addLine("Good Enough!");
                telemetry.addLine("Time elapsed: " + (this.getRuntime() - prevtime));
                prevtime = this.getRuntime();
                buffer += 1; //idk
            }
            else{

                buffer = 0 ;

                targetrot = Math.random() * 360;
                if (Math.abs(targetrot - deg) < tolerance || Math.abs(targetrot - 360 - deg) < tolerance){ //wraparounds are kinda annoying
                    targetrot += Math.random()*120; //get away from current rotation if new target is too close
                }
            }
        }
        telemetry.update();


        if (exponent % 2 == 0){ //juuuuuust in case
            exponent += 1;
        }
    }
    @Override
    public void onStop() {
        // Runs ONCE when stop is pressed
        telemetry.addLine("Stopping!");
        telemetry.update();
    }
}
