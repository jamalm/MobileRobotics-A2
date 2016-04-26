import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.Motor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/*
 * Turn behavior is used to turn when 
 * the robot reaches a wall at the end
 * of the lap.
 * 
 * @author Jamal Mahmoud - C13730921
 * @author Minni Hiltunen - D15123113
 */

public class Turn implements Behavior {
	
	//variables
	private static boolean turn;//used to determine which direction to turn
	private boolean suppressed = false;
	private float distance;//full lap distance
	private DifferentialPilot pilot;//controller for motors
	
	public Turn(){
		turn = false;
		pilot = new DifferentialPilot(2.25f ,4.25f, Motor.A, Motor.B);
		distance = Main.getDistance();///store static distance locally also 
	}


	/* This function acts when the robot sees 
	 * a wall at the end of a lap. It instructs
	 * the robot to turn 90 degs, move 7 units 
	 * and turn 90 degrees again, resetting the
	 * position to 0 at the end.
	 * 
	 */
	
	public void action(){
		suppressed = false;
		
		//LCD.drawString("Pos2 :" + Main.getPosition(), 0 ,1);
		//LCD.drawString("dist :" + Main.getDistance(), 0 ,4);

		turn = !turn;//switch direction
		if(turn){//turn left
			pilot.rotate(90);
			pilot.travel(7);
			pilot.rotate(90);
		} else {//turn right
			pilot.rotate(-90);
			pilot.travel(7);
			pilot.rotate(-90);
		}
		Main.setPosition(0);//reset position

		//LCD.drawString("Pos2 :" + Main.getPosition(), 0 ,1);

		try{//take a break
			Thread.sleep(100);
		}catch(Exception e){
			
		}
		
	}
	
	public void suppress(){
		suppressed = true;
		
	}

	/*
	 * return true 
	 * if position has reached (or in some cases passed) the lap's full distance 
	 * AND 
	 * position is greater than/equal to 1
	 */
	
	public boolean takeControl(){
		return (Main.getPosition() >= distance-1 && Main.getPosition() >= 1);
	}
}
