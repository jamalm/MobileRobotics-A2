import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 *
 */

public class HitWall implements Behavior, SensorPortListener {
	//fields
	private boolean suppressed = false;
	private TouchSensor touch;
	private DifferentialPilot pilot;
	private boolean hasBumped;
	
	public HitWall(){
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		touch = new TouchSensor(SensorPort.S1);
		SensorPort.S2.addSensorPortListener(this);
		hasBumped = false;
	}

	public void stateChanged(SensorPort port, int oldValue, int newValue){
		if(touch.isPressed()){
			hasBumped = true;
		}
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
		return hasBumped;
	}
}
