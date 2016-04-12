import lejos.robotics.subsumption.*;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


/*

*/

public class ObjectDetect implements Behavior{
	//fields
	private boolean suppressed = false;
	private float length, width;
	private DifferentialPilot pilot;
	private UltrasonicSensor sonar;
	private Move position;

	//constructor
	public ObjectDetect(float _length, float _width){
		sonar = new UltrasonicSensor(SensorPort.S4);
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		length = _length;
		width = _width;
		position = new Move();
	}

	public void action(){
		suppressed = false;
		
		if(position.getPosition() < (length - (length/4))){
			pilot.stop();
			pilot.rotate(90);

			pilot.steer(35, 90);

			pilot.rotate(90);
		}
		else{
			pilot.rotate(90);
			pilot.travel(7);
			pilot.rotate(90);
		}
	}

	public void suppress(){
		suppressed = true;
	}

	public boolean takeControl(){
		return sonar.getDistance() < 35;
	}
}
