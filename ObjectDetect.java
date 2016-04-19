import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
*/

public class ObjectDetect implements Behavior{
	//fields
	private boolean suppressed = false;
	private float length, position;
	private DifferentialPilot pilot;
	private UltrasonicSensor sonar;

	//constructor
	public ObjectDetect(){
		sonar = new UltrasonicSensor(SensorPort.S4);
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		length = Main.distance;
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
		return sonar.getDistance() < 35 && Main.getTempDist() < (length - (length/3));
	}
}
