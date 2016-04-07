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

	//constructor	
	public SurfaceDetect(int _floorColour){
		//takes in average floor colour
		floorColour = _floorColour;
	}

	//if different surface colour is detected
	public void action(){
		suppressed = false;
		LCD.drawString("Carpet", 4, 0, 0);
		Sound.beep();
	}

	//suppresses behavior
	public void suppress(){
		suppressed = true;
	}

	//activates behavior 
	public boolean takeControl(){
	return light.getReading() > (floorColour+9) || light.getLightValue() < (floorColour-9);
	}
}
