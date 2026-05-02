package org.firstinspires.ftc.teamcode.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp
public class RobotHWIntake extends OpMode {
    public byte frame = 0; //animation frame
    public double InputVal; //right trigger value
    public DcMotor LeftM;
    public DcMotor RightM;
    @Override
    public void init() {
        LeftM = hardwareMap.get(DcMotor.class, "LeftMotor");
        RightM = hardwareMap.get(DcMotor.class, "RightMotor");
        LeftM.setDirection(DcMotorSimple.Direction.REVERSE); //for an intake to work, one motor has to spin the opposite direction
    }
    @Override
    public void loop() {
        InputVal = gamepad1.right_trigger - gamepad1.left_trigger;

        LeftM.setPower(InputVal);
        RightM.setPower(InputVal);

        telemetry.addData("[1] | Stats! ","| Trigger Value: " + Math.round(100f * InputVal) / 100f + "%! |");
        telemetry.addData("[2] | More Stats! ", " | Motor Power: " + Math.round(100f * LeftM.getPower()) / 100f + "%! |");


        if (InputVal <= 0.02f)
        {
            telemetry.addData("[3]", "zzz");
        }
        else if (InputVal > 0.02f && InputVal < 0.5f)
        {
            telemetry.addData("[3]", "bleh");
        }
        else if (InputVal >= 0.5f && InputVal < 0.94f)
        {
            telemetry.addData("[3]", "why not full power?");
        }
        else {
            telemetry.addData("[3]", "yaaaaaa");
        }
        
        switch (frame) {
            case 1:
                telemetry.addData("[4]", "[-   ]");
                break;
            case 2:
                telemetry.addData("[4]", "[+-  ]");
                break;
            case 3:
                telemetry.addData("[4]", "[-+- ]");
                break;
            case 4:
                telemetry.addData("[4]", "[ -+-]");
                break;
            case 5:
                telemetry.addData("[4]", "[  -+]");
                break;
            case 6:
                telemetry.addData("[4]", "[   -]");
                break;
            case 7:
                frame = 0;
                telemetry.addData("[4]", "[    ]");
                break;
        }
        
        frame += 1;

        telemetry.update();
    }
}
