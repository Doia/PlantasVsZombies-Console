package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {

	static private  final double g = 9.81; //Aceleración fija
	
	public FallingToCenterGravity() {
		
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for (Body b: bodies) {
			b.setAceleration(b.getPosition().direction().scale(-g));
		}
	}
	
	public String toString() {
		return "Falling to center gravity law.";
	}
}
