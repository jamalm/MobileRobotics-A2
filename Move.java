import lejos.robotics.subsumption.*;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class Move implements Behavior{
	
	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private float travelled;	

	
	public Move(){
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
	}

	public void action(){
		suppressed = false;
		pilot.reset();
		pilot.forward();
	}
	
	public void suppress(){
		suppressed = true;
		travelled = pilot.getMovement().getDistanceTraveled();
	}

	public boolean takeControl(){
		return true;
	}
	
	public float getPosition(){
		return travelled;
	}
}
