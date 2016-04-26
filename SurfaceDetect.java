import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

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
		Main.setMove(false);
		
		while(!suppressed){
			LCD.drawString("Carpet", 50, 50);
			Sound.beep();
			Main.setMove(true);
			Thread.yield();
		}
	}

	//suppresses behavior
	public void suppress(){
		suppressed = true;
	}

	//activates behavior 
	public boolean takeControl(){
		return (((light.getLightValue()) > (Main.getLightAvg()+9)) || ((light.getLightValue()) < (Main.getLightAvg()-9)));
	}
}
