//Utilizes enums and switch cases (Finite State Machine) to allow the intake to take-in and expel objets
//automatically while maintaining control over the robot
//State diagram attached soon

switch (intakeState)
{
   case INTAKE_START:
       if(gamepad1.a)
       {
           intake.setPower(1.0);
           intakePauser.reset();
           intakeState = IntakeState.INTAKE_PAUSE1;
       }

       if(gamepad1.b)
       {
           cam.setPosition(CAM_ON);
           intake.setPower(-1.0);
           intakeTimer.reset();
           intakeState = IntakeState.INTAKE_RESET;
       }
       break;
   case INTAKE_PAUSE1:
       if(intakePauser.seconds() >= INTAKE_PAUSE_TIME)
       {
           intakeState = IntakeState.INTAKE_ON;
       }
       break;
   case INTAKE_PAUSE2:
       if(intakePauser.seconds() >= INTAKE_PAUSE_TIME)
       {
           intakeState = IntakeState.INTAKE_START;
       }
       break;
   case INTAKE_ON:
       if(gamepad1.a)
       {
           intake.setPower(0.0);
           intakePauser.reset();
           intakeState = IntakeState.INTAKE_PAUSE2;
       }

       if(gamepad1.b)
       {
           cam.setPosition(CAM_ON);
           intake.setPower(-1.0);
           intakeTimer.reset();
           intakeState = IntakeState.INTAKE_RESET;
       }
       break;
   case INTAKE_RESET:
       if(intakeTimer.seconds() >= INTAKE_TIME)
       {
           intake.setPower(0.0);
           cam.setPosition(CAM_IDLE);
           intakeState = IntakeState.INTAKE_START;
       }
       break;
   default:
       intakeState = IntakeState.INTAKE_START;
}
//Safety button to reset the states
if (gamepad1.x && liftState != LiftState.LIFT_START)
{
   intake.setPower(0.0);
   cam.setPosition(CAM_IDLE);
   intakeState = IntakeState.INTAKE_START;
}
