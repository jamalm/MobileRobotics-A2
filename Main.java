
import lejos.nxt.LCD;
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
	
	public static void setDistance(float travelled){
		distance = travelled;
	}
	
	public static float getDistance(){
		return distance;
	}
	
	public static void main(String[] args) {
		map = new MapRoom();                          

		LCD.drawString("Behaviours initialised", 8,20);
		Behavior move = new Move(map.getLength());
		Behavior obj = new ObjectDetect(map.getLength(), map.getWidth());//detects objects
		Behavior surf = new SurfaceDetect(map.getFloorAvg());//detects surface type
		Behavior end = new HitWall();//end condition
		Behavior[] steps = {move, obj, surf, end};


		Arbitrator controller = new Arbitrator(steps);

		LCD.drawString("start behaviors", 8,30);
		controller.start();
	}
}
