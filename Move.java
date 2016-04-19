
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
	private static float length;
	private boolean direction;

	public Move(float _length){
		pilot = new DifferentialPilot(2.25f, 4.25f, Motor.A, Motor.C);
		travelled = 0;
		length = _length;
		direction = false;
	}

	public void action(){
		suppressed = false;
		
		while(!suppressed){
			if(Main.getTempDist() != length){
			pilot.forward();
			}
			while(Main.getTempDist() < length){
				
				Main.setTempDist(pilot.getMovement().getDistanceTraveled());
				LCD.drawString("MOVING: " + Main.getTempDist(), 0, 0);
			}
			pilot.stop();
			
			direction = !direction;
			if(direction){
				pilot.rotate(90);
				pilot.travel(7);
				pilot.rotate(90);
				Main.setTempDist(0);
			} else {
				pilot.rotate(-90);
				pilot.travel(7);
				pilot.rotate(-90);
				Main.setTempDist(0);
			}
		}
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		return true;
	}
}