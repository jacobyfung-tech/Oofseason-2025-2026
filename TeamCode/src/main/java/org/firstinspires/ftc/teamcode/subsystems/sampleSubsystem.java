package org.firstinspires.ftc.teamcode.subsystems;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;


public class sampleSubsystem implements Subsystem {

    // These are required to make it singletons. You don't really have to understand them, just make sure yyou have them!
    protected static sampleSubsystem INSTANCE = new sampleSubsystem();
    public sampleSubsystem() {

    }

    public boolean clawUP = false;


    private final ServoEx clawservo = new ServoEx("servoExample");
    private final ServoEx ARMservo = new ServoEx("servoExample2");



    public Command switchClawPos = new InstantCommand(() -> {
        if (clawUP)
        {
            ARMservo.setPosition(1);
        }
        else
        {
            ARMservo.setPosition(0);
        }
        clawUP = !clawUP;

    } ) ;


    public Command openClaw = new InstantCommand(() -> {
        clawservo.setPosition(0.7f);

    } ) ;

    public Command closeClaw = new InstantCommand(() -> {
        clawservo.setPosition(0);

    } ) ;


    public Command chain = //altogether now
            openClaw
                    .thenWait(0.5f)
                    .then(switchClawPos)
                    .thenWait(1.5f)
                    .then(closeClaw)
                    .thenWait(0.5f)
                    .then(switchClawPos);


    @Override
    public void initialize() {

    }
    @Override
    public void periodic() {

    }

}
