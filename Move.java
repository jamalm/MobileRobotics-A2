import lejos.robotics.subsumption.*;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class Move implements Behavior{
	
	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private static float travelled;	
	private float length;
	private boolean direction;

	public Move(){
		//for referencing to Object
	}
	public Move(float _length){
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		travelled = 0;
		length = _length;
		direction = true;
	}

	public void action(){
		suppressed = false;
		pilot.reset();
		
		while(pilot.getMovement().getDistanceTraveled() < length - 5){
			pilot.forward();
		}
		direction = !direction;
		if(direction){
			pilot.rotate(75);
			pilot.travel(7);
			pilot.rotate(75);
		} else {
			pilot.rotate(-75);
			pilot.travel(7);
			pilot.rotate(-75);
		}
		travelled = 0;
	}
	
	public void suppress(){
		travelled = pilot.getMovement().getDistanceTraveled();
		suppressed = true;
	}

	public boolean takeControl(){
		return true;
	}
	
	public float getPosition(){
		return travelled;
	}
}
