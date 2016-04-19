package com.deadmadness.vacuum;

import lejos.nxt.Button;
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
		
		/*
		 * 
		 * Test 1 
		 * Case
		 * Check to see if distance returned is correct
		 */
		map = new MapRoom();                          

		LCD.drawString("Results from MapRoom", 8,20);
		LCD.drawString("Distance: " + getDistance(), 10, 20);
		Button.waitForAnyPress();
		/* 
		 * Test 1 complete 
		 * 
		 */
		
		
		/*
		 * 
		 * Test 2 start 
		 * CASE 
		 * Check to see distance travelled is being recorded in move function
		 * 
		 */
		
		Behavior move = new Move(distance);
		/*
		Behavior obj = new ObjectDetect(map.getLength(), map.getWidth());//detects objects
		Behavior surf = new SurfaceDetect(map.getFloorAvg());//detects surface type
		Behavior end = new HitWall();//end condition
		*/
		Behavior[] steps = {move};


		Arbitrator controller = new Arbitrator(steps);

		//LCD.drawString("start behaviors", 8,30);
		//LCD.clear();
		//controller.start();
	}
}