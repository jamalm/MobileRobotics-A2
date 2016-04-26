
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
		LCD.clear();
		LCD.drawString("Position: " + Main.getPosition(), 0, 0);
		Button.waitForAnyPress();
		LCD.clear();
		while(!suppressed){
			if(Main.getPosition() != 0)
			{
				Main.setPosition(travelled);
			}
			if(Main.getPosition() != distance && !suppressed){
				pilot.forward();
				pilot.stop();
				LCD.drawString("Pos: " + Main.getPosition(), 0, 0);
				Button.waitForAnyPress();
				LCD.clear();
			}

			
			while(Main.getPosition() < distance && !suppressed){
		
				Main.setPosition(Main.getPosition() + pilot.getMovement().getDistanceTraveled());
			}
			LCD.drawString("Post-Pos: " + Main.getPosition(), 0, 0);
			pilot.stop();
			Button.waitForAnyPress();
			LCD.clear();
			Thread.yield();

			if(Main.getPosition() > (distance - (distance/3)) && !suppressed){
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
			}
			Thread.yield();
		}
		
	}
	
	public void suppress(){
		travelled = Main.getPosition();
		suppressed = true;
	}
	
	public boolean takeControl(){
		return true;
	}
}
