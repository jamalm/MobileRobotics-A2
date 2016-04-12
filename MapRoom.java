import lejos.robotics.subsumption.*;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

import lejos.robotics.navigation.DifferentialPilot;


/*
 *	Behavior class to control the initial recognition lap of the room
 *
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class MapRoom {
	private DifferentialPilot pilot;
	private UltrasonicSensor sonar;
	private LightSensor light;
	private int lightAverage = 0;
	private float[] lengths = new float[4];
	private float length ,width;

	//constructor
	public MapRoom(){

		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		sonar = new UltrasonicSensor(SensorPort.S4);
		light = new LightSensor(SensorPort.S3);
		calcRoom();
	}
	
	private void calcRoom(){
	
		for(int i=0;i<4;i++){
				pilot.reset();
				pilot.forward();
				if(sonar.getDistance() < 20){
					lightAverage += light.getLightValue();
					lengths[i] = pilot.getMovement().getDistanceTraveled();
					pilot.rotate(90);
				}
			}
			
			length = lengths[0];
			width = lengths[1];
			lightAverage /= 4;
	}
	
	public float getLength(){
		return length;
	}

	public float getWidth(){
		return width;
	}

	public int getFloorAvg(){
		return lightAverage;
	}
}
