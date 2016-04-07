import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class SurfaceDetect implements Behavior{
	private boolean suppressed = false;
	private int floorColour
	
	public SurfaceDetect(int _floorColour){
		floorColour = _floorColour;
	}

	public void action(){
		suppressed = false;
		LCD.drawString("Carpet", 4, 0, 0);
		Sound.
	}

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl(){
		return light.getReading() > floorColour+9 || light.getLightValue() < floorColour-9;
	}
}
