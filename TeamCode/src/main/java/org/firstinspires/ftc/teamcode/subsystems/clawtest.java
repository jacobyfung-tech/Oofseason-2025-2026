package org.firstinspires.ftc.teamcode.subsystems;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;


public class sampleSubsystem implements Subsystem {

    // These are required to make it singletons. You don't really have to understand them, just make sure yyou have them!
    public static sampleSubsystem INSTANCE = new sampleSubsystem();
    private sampleSubsystem() {

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


    public Command openclaw = new InstantCommand(() -> {
        clawservo.setPosition(0.7f);

    } ) ;

    public Command closeclaw = new InstantCommand(() -> {
        clawservo.setPosition(0);

    } ) ;


    public Command chain = //altogether now
            openclaw
                    .thenWait(0.5f)
                    .then(switchClawPos)
                    .thenWait(2.5f)
                    .then(closeclaw)
                    .thenWait(0.5f)
                    .then(switchClawPos);


    @Override
    public void initialize() {
        
    }
    @Override
    public void periodic() {

    }

}
