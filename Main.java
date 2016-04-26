
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
	private static float distance;//used to see the full width of a lap
	private static float position;//used to detect where the robot is on the lap
	private static int lightAverage;//used to detect a difference in lighting on the ground

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



	
	//Position getters and setters	
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

	
	
	
	//initial recognition lap function
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
				//LCD.drawString("" + dist, 0, 0);
			}
			//if it is the first length, record distance into static float distance
			if(i==0){
				setDistance(dist);
			}
			lightAverage += light.getLightValue();//adds light measurement to lightAverage
			pilot.rotate(90);//turn left after calculations are recorded
		}
		pilot.stop();	//stop after all corners are recorded
		lightAverage /= 4;	// get average of floor colour
		
	}
	
	
	
	
	
		
	
	public static void main(String[] args) {
		
		//map the room initially
		map();
		setPosition(0);//initialise Position of robot
		
		//init behaviors
		Behavior move = new Move(getDistance());//Base behavior 
		Behavior turn = new Turn();//turning at a wall behavior
		Behavior obj = new ObjectDetect();//detects objects and arcs around them
		Behavior surf = new SurfaceDetect(getLightAvg());//detects surface type and beeps
		Behavior end = new HitWall();//end condition(hit a wall)
		
		//create hierarchy for arbitration
		Behavior[] steps = {move, turn, obj, surf, end};

		//set up arbitration controller
		Arbitrator controller = new Arbitrator(steps);

		controller.start();//start running behaviors
	}
}
