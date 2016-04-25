
import lejos.nxt.*;

import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/*
 * Assignment 2 for Mobile Robotics 
 * Implementing the Behavior API, allow the arbitrator to carry out behaviors
 * depending on the conditions
 * 
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class Main {
	//static variables
	private static float distance;
	private static float position;
	private static int lightAverage;

	private static float length ,width;

	// Hardware Sensors
	public static ArcRotateMoveController pilot;
	public static UltrasonicSensor sonar;
	public static LightSensor light;
	
	
	//Distance setters and getters
	public static void setDistance(float travelled){
		distance = travelled;
	}
	
	public static float getDistance(){
		return distance;
	}

	
	//Temp getters and setters	
	public static void setPosition(float pos){
		position = pos;
	}
	
	public static float getPosition(){
		return position;
	}
	

	//Light getters and setters
	public static void setLightAvg(int _light){
		lightAverage = _light;
	}
	
	public static int getLightAvg(){
		return lightAverage;
	}
	
	
	
	
	
	
	
	public static void map(){
		//init hardware sensors
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
		sonar = new UltrasonicSensor(SensorPort.S4);
		light = new LightSensor(SensorPort.S3);
		//local distance variable
		float dist = 0;

		//measure four lengths
		for(int i=0;i<4;i++){
			pilot.forward();//move forward
			while(sonar.getDistance() > 35){//break only if robot comes close to a wall
				
				dist = pilot.getMovement().getDistanceTraveled();//store distance travelled
				LCD.drawString("" + dist, 0, 0);
			}
			//if it is the first length, record distance
			if(i==0){
				setDistance(dist);
			}
			lightAverage += light.getLightValue();//adds light measurement to lightAverage
			pilot.rotate(90);//turn left after calculations are recorded
		}
		pilot.stop();	//stop after all corners are recorded
		lightAverage /= 4;	// get average of floor colour
		
		//pilot.reset();
		//LCD.drawString("CalcRoom", 4,0);
		/*for(int i=0;i<4;i++){
			while(sonar.getDistance() > 35){
				pilot.forward();
			}
			dist = pilot.getMovement().getDistanceTraveled();
			lightAverage += light.getLightValue();
			lengths[i] = dist;
			pilot.rotate(70);
		}
		setDistance(lengths[0]);
		width = lengths[1];
		lightAverage /= 4;*/
	}
	
	
	
	
	
		
	
	public static void main(String[] args) {
		
		//map the room initially
		map();

		LCD.clear();
		LCD.drawString("total: " + getDistance(), 0, 0);
		
		//init behaviors
		Behavior move = new Move(getDistance());//default behavior 
		//Behavior obj = new ObjectDetect();//detects objects
		Behavior surf = new SurfaceDetect(getLightAvg());//detects surface type
		Behavior end = new HitWall();//end condition
		
		
		Behavior[] steps = {move ,surf, end};


		Arbitrator controller = new Arbitrator(steps);

		controller.start();
	}
}
