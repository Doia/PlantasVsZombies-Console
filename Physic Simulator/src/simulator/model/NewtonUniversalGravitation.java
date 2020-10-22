package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {
	
	static private final double G = 6.67E-11; //Constante gravitacional
	
	public NewtonUniversalGravitation() {
	}
	
	private Vector force(Body a, Body b) {
		double f;
		Vector F = new Vector(a.getPosition().dim());
		double dis = (b.getPosition().distanceTo(a.getPosition()));
		f= (G * a.getMass() * b.getMass() ) / Math.pow(dis, 2);
		
		F = ( ( b.getPosition().minus(a.getPosition()) ).direction() ).scale(f);
		
		return F;
	}
	
	@Override
	public void apply(List<Body> bodies) {
		
		Vector fTotal;
		
		for (int i = 0; i < bodies.size(); i++) {
			if(bodies.get(i).getMass() > 0)  {
				fTotal = new Vector(bodies.get(i).getPosition().dim());
				
				for(int j = 0; j < bodies.size(); j++) {
					if(i != j) {
						fTotal = fTotal.plus(force(bodies.get(i), bodies.get(j)));
					}	
				}
				
				bodies.get(i).setAceleration(fTotal.scale(1/bodies.get(i).getMass()));
			}
			else if(bodies.get(i).getMass() == 0) {
				
				bodies.get(i).setAceleration(new Vector(bodies.get(i).getPosition().dim()));
				bodies.get(i).setVelocity(new Vector(bodies.get(i).getPosition().dim()));
			}
		}
	}
	
	public String toString() {
		return "Newton's law of universal gravitation.";
	}
	
}
