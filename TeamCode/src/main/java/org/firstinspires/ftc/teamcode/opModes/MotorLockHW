import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "LockingMotor")
public class LockingMotor extends OpMode {
   public DcMotorEx LeftM;
   public DcMotorEx RightM;
   private final int TPS = 2779;
   public boolean Xbt;
   public int limitPos = 3025;
   public double trig;
   public double rightPos;
   public double leftPos;
   public boolean PXbt;
   public enum RunStates {
       ON,
       OFF,
   }
   private RunStates RunState;
   @Override
   public void init() {
       LeftM = hardwareMap.get(DcMotorEx.class, "leftMotor");
       RightM = hardwareMap.get(DcMotorEx.class, "rightMotor");


       LeftM.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
       RightM.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


       LeftM.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
       RightM.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


       RightM.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       LeftM.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       LeftM.setDirection(DcMotorSimple.Direction.REVERSE);
       RunState = RunStates.OFF; //Default to OFF
   }


   @Override
   public void loop() {
       Xbt = gamepad1.x;


       trig = -gamepad1.left_stick_y;


       leftPos = LeftM.getCurrentPosition();
       rightPos = RightM.getCurrentPosition();


       switch(RunState){
           case ON:
               if (Xbt && !PXbt) {
                   RightM.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                   LeftM.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


                   RunState = RunStates.OFF;
               }
               telemetry.addData("[1] Motor Mode : ", "AUTO");
               break;
           case OFF:
               if (Xbt && !PXbt) {
                   LeftM.setTargetPositionTolerance(25);
                   RightM.setTargetPositionTolerance(25);


                   LeftM.setTargetPosition(limitPos);
                   RightM.setTargetPosition(limitPos);


                   LeftM.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                   RightM.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);


                   LeftM.setVelocity(TPS);
                   RightM.setVelocity(TPS);


                   RunState = RunStates.ON;
               }
               else {
                   LeftM.setPower(trig);
                   RightM.setPower(trig);
               }
               telemetry.addData("[1] Motor Mode: ", "NORMAL");
               break;
       }
       telemetry.addData("[3] Left  Motor Position: ", Math.round(leftPos));
       telemetry.addData("[4] Right Motor Position: ", Math.round(rightPos));
       telemetry.update();
       PXbt = Xbt;
   }
}

