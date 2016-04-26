import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 *
 */

public class HitWall implements Behavior{
	//fields
	private boolean suppressed = false;
	private TouchSensor touch;
	private DifferentialPilot pilot;
	
	public HitWall(){
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
		touch = new TouchSensor(SensorPort.S1);
	}

	public void action(){
		suppressed = false;
		pilot.stop();
		LCD.clear();
		LCD.drawString("END", 0, 0);
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
