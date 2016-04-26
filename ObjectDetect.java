import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
*/

public class ObjectDetect implements Behavior{
	//fields
	private boolean suppressed = false;
	private float distance, position;
	private ArcRotateMoveController pilot;
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
		pilot.rotate(75);

		pilot.steer(35, 75);

		pilot.rotate(75);
	}

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl(){
		return (sonar.getDistance() < 35) && (Main.getPosition() < distance);
	}
}
