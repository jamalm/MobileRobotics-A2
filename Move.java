import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Move implements Behavior{
	
	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private static float travelled;	
	private float length;
	private boolean direction;

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
	}
	
	public void suppress(){
		Main.setDistance(pilot.getMovement().getDistanceTraveled());
		suppressed = true;
	}

	public boolean takeControl(){
		return true;
	}
}
