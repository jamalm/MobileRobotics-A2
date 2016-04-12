import lejos.robotics.subsumption.*;
import lejos.nxt.*;

/*
 * Assignment 2 for Mobile Robotics 
 * Implementing the Behavior API, allow the arbitrator to carry out behaviors
 * depending on the conditions
 * 
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class Main {
	public static void main(String[] args) {
		MapRoom map = new MapRoom();

		Behavior move = new Move();
		Behavior obj = new ObjectDetect(map.getLength(), map.getWidth());//detects objects
		Behavior surf = new SurfaceDetect(map.getFloorAvg());//detects surface type
		Behavior end = new HitWall();//end condition
		Behavior[] steps = {move, obj, surf, end};


		Arbitrator controller = new Arbitrator(steps);

		controller.start();
	}
}
