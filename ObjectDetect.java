import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/* Behavior used to react when an object is 
 * detected along the route to the wall
 * 
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class ObjectDetect implements Behavior{
	//fields
	private boolean suppressed = false;

	private float distance, position;
	private DifferentialPilot pilot;
	private UltrasonicSensor sonar;

	//constructor
	public ObjectDetect(){
		sonar = new UltrasonicSensor(SensorPort.S4);
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
		distance = Main.getDistance();
		distance -= (distance/3);	// take a third off the total 
	}

	public void action(){
		suppressed = false;

		LCD.clear();
		LCD.drawString("Object Detected!", 50, 50);

		pilot.stop();
		pilot.rotate(-90);//turn right

		pilot.steer(45, 180);//turn around a 45 radius

		pilot.rotate(-90);//turn right around again
	}

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl(){
		return (sonar.getDistance() < 35) && (Main.getPosition() < distance);	//returns true when object detected and not at the end of the length
	}
}
