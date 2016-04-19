
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
	
	public static MapRoom map;
	public static float distance;
	public static float temp;
	public static ArcRotateMoveController pilot;
	public static UltrasonicSensor sonar;
	public static LightSensor light;
	
	private static int lightAverage;
	private static float[] lengths = new float[4];
	private static float length ,width;
	
	
	
	
	
	
	public static void setDistance(float travelled){
		distance = travelled;
	}
	
	public static float getDistance(){
		return distance;
	}
	
	public static void setTempDist(float _temp){
		temp = _temp;
	}
	
	public static float getTempDist(){
		return temp;
	}
	
	public static void setLightAvg(int _light){
		lightAverage = _light;
	}
	
	public static int getLightAvg(){
		return lightAverage;
	}
	
	
	
	
	
	
	
	public static void map(){
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.C);
		sonar = new UltrasonicSensor(SensorPort.S4);
		light = new LightSensor(SensorPort.S3);
		float dist = 0;
		for(int i=0;i<4;i++){
			pilot.forward();
			while(sonar.getDistance() > 35){
				
				dist = pilot.getMovement().getDistanceTraveled();
				
				LCD.drawString("" + dist, 0, 0);
			}
			if(i==0){
				setDistance(dist);
			}
			lightAverage += light.getLightValue();
			lengths[i] = dist;
			pilot.rotate(90);
		}
		pilot.stop();
		lightAverage /= 4;
		
		
		
		
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
		
		map();
		LCD.clear();
		LCD.drawString("total: " + distance, 0, 0);
		
		
		Behavior move = new Move(distance);
		//Behavior obj = new ObjectDetect();//detects objects
		Behavior surf = new SurfaceDetect(lightAverage);//detects surface type
		Behavior end = new HitWall();//end condition
		
		
		Behavior[] steps = {move ,surf, end};


		Arbitrator controller = new Arbitrator(steps);

		controller.start();
	}
}