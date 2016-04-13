import lejos.robotics.subsumption.*;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.LCD;

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
	private static float length ,width;

	//constructor
	public MapRoom(){

		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		sonar = new UltrasonicSensor(SensorPort.S4);
		light = new LightSensor(SensorPort.S3);
		
		LCD.drawString("CalcRoom", 4,0);
		for(int i=0;i<4;i++){
			pilot.reset();
			while(sonar.getDistance() > 35){
				pilot.forward();
			}
			lightAverage += light.getLightValue();
			lengths[i] = pilot.getMovement().getDistanceTraveled();
			pilot.rotate(75);
		}
		length = lengths[0];
		width = lengths[1];
		lightAverage /= 4;	
		
		LCD.clear();
		LCD.drawString("MapRoom Calculated", 8,10);
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
