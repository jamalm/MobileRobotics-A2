
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.Motor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;



/*
 * Move behavior is the base behavior which takes 
 * over when no other behaviors take control
 * This behavior moves the robot forward and 
 * records it's position during movement.
 *
 *
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 *
 */
public class Move implements Behavior{
	//boolean to relieve control of resources
	private boolean suppressed = false;
	//motor controller
	private DifferentialPilot pilot;

	private static float distance;//distance of full lap

	//constructor passes in float which stores distance of a full stretch/lap
	public Move(float _length){
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
		distance = _length;
	}

	public void action(){
		suppressed = false;
		
		/* The robot is instructed to move forward and 
		 * it increments the position while it's moving
		 */
		while(Main.getPosition() < distance && !suppressed){ 
			pilot.forward();
			Main.setPosition(Main.getPosition() + (pilot.getMovement().getDistanceTraveled()*2));
			//LCD.drawString("Pos :" + Main.getPosition(), 0 ,0);
			//LCD.drawString("dist :" + Main.getDistance(), 0 ,4);
		}
		
		Thread.yield();
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		return true;//base behavior always returns true
	}
}
