
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.Motor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Move implements Behavior{
	
	private boolean suppressed = false;

	private DifferentialPilot pilot;

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
		
		while(Main.getPosition() < distance && !suppressed){ 
			pilot.forward();
			Main.setPosition(Main.getPosition() + (pilot.getMovement().getDistanceTraveled()*2));
			LCD.drawString("Pos :" + Main.getPosition(), 0 ,0);
			LCD.drawString("dist :" + Main.getDistance(), 0 ,4);
		}
		
		Thread.yield();
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		return true;
	}
}
