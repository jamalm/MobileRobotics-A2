import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.DifferentialPilot;

/*
 * Behavior that reacts to a change in colour of floor
 * Makes a beep and prints "Carpet" to the screen
 *
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class SurfaceDetect implements Behavior{
	//variables
	private boolean suppressed = false;

	private int floorColour;
	private LightSensor light;
	private ArcRotateMoveController pilot;

	//constructor	
	public SurfaceDetect(int _floorColour){
		//takes in average floor colour
		floorColour = _floorColour;
		light = new LightSensor(SensorPort.S3);
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
	}

	//if different surface colour is detected
	public void action(){
		suppressed = false;
		Sound.playTone(500, 400);//beep
		LCD.drawString("Carpet", 0, 0);//print
		//LCD.drawString("Pos3 :" + Main.getPosition(), 0 ,3);
	}

	//suppresses behavior
	public void suppress(){
		suppressed = true;
	}

	//activates behavior 
	/*
	 * Returns true
	 * if current light value is > avg+10 
	 * OR 
	 * if current light value is < avg-10
	 */
	public boolean takeControl(){
		return (((light.getLightValue()) > (Main.getLightAvg()+9)) || ((light.getLightValue()) < (Main.getLightAvg()-9)));
	}
}
