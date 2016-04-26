
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.Motor;

import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Move implements Behavior{
	
	private boolean suppressed = false;

	private ArcRotateMoveController pilot;

	private static float travelled;	
	private static float distance;

	private boolean direction;

	public Move(float _length){
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.B);
		travelled = 0;
		distance = _length;
		direction = false;
	}

	public void action(){
		suppressed = false;
		
		while(!suppressed){
			if(Main.getPosition() != distance){
			pilot.forward();
			}
			while(Main.getPosition() < distance){
				
				Main.setPosition(pilot.getMovement().getDistanceTraveled());
				LCD.drawString("MOVING: " + Main.getPosition(), 0, 0);
			}
			pilot.stop();
			
			direction = !direction;
			if(direction){
				pilot.rotate(90);
				pilot.travel(7);
				pilot.rotate(90);
				Main.setPosition(0);
			} else {
				pilot.rotate(-90);
				pilot.travel(7);
				pilot.rotate(-90);
				Main.setPosition(0);
			}
			Thread.yield();
		}
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		return Main.isMovingCheck();
	}
}
