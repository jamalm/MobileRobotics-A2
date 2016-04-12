import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

/*
 *
 */

public class HitWall implements Behavior{
	//fields
	private boolean suppressed = false;
	private TouchSensor touch;
	private DifferentialPilot pilot;
	
	public HitWall(){
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		touch = new TouchSensor(SensorPort.S1);
	}

	public void action(){
		suppressed = false;
		pilot.stop();
		LCD.drawString("END", 50, 50);
		Button.waitForAnyPress();
		System.exit(0);
	}
	
	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl(){
		return touch.isPressed();
	}
}
