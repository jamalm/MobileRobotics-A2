import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


/*
 * Behavior that reacts to a change in colour of floor
 * Makes a beep and prints "Carpet" to the screen
 *
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class SurfaceDetect implements Behavior{
	private boolean suppressed = false;
	private int floorColour;
	private LightSensor light;

	//constructor	
	public SurfaceDetect(int _floorColour){
		//takes in average floor colour
		floorColour = _floorColour;
		light = new LightSensor(SensorPort.S3);
	}

	//if different surface colour is detected
	public void action(){
		suppressed = false;
		LCD.drawString("Carpet", 50, 50);
		Sound.beep();
	}

	//suppresses behavior
	public void suppress(){
		suppressed = true;
	}

	//activates behavior 
	public boolean takeControl(){
	return light.getLightValue() > (floorColour+9) || light.getLightValue() < (floorColour-9);
	}
}
