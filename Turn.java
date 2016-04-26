import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.Motor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Turn implements Behavior {
	
	private static boolean turn;
	private boolean suppressed = false;
	private float distance;
	private DifferentialPilot pilot;
	
	public Turn(){
		turn = false;
		pilot = new DifferentialPilot(2.25f ,4.25f, Motor.A, Motor.B);
		distance = Main.getDistance();
	}
	
	public void action(){
		suppressed = false;
		
		LCD.drawString("Pos2 :" + Main.getPosition(), 0 ,1);
		LCD.drawString("dist :" + Main.getDistance(), 0 ,4);
		while(!suppressed){
			turn = !turn;
			if(turn){
				pilot.rotate(90);
				pilot.travel(7);
				pilot.rotate(90);
				Main.setPosition(0);
				suppressed = true;
			} else {
				pilot.rotate(-90);
				pilot.travel(7);
				pilot.rotate(-90);
				Main.setPosition(0);
				suppressed = true;
			}
		}
		Main.setPosition(0);
		LCD.drawString("Pos2 :" + Main.getPosition(), 0 ,1);
		try{
			Thread.sleep(100);
		}catch(Exception e){
			
		}
		
	}
	
	public void suppress(){
		suppressed = true;
		
	}
	
	public boolean takeControl(){
		return (Main.getPosition() >= distance-1 && Main.getPosition() >= 1);
	}
}