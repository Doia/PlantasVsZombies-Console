package simulator.model;


import simulator.misc.Vector;

public class Body {
	
	protected String type;
	protected String id;
	protected Vector v;//velocidad
	protected Vector a;//aceleracion
	protected Vector p;//posicion
	protected double m;//masa
	
	//constructor TODO
	public Body(String id, Vector v, Vector a, Vector p, double m){
		this.id = id;
		this.v = v;
		this.a = a;
		this.p = p;
		this.m = m;
		this.type = "basic";
	}
	
	public String getId() {
		return this.id;
	}
	
	public Vector getVelocity() {
		return this.v;
	}
	
	public Vector getAceleration() {
		return this.a;
	}
	
	public Vector getPosition() {
		return this.p;
	}
	
	public String getType() {
		return this.type;
	}
	
	public double getMass() {
		return this.m;
	}
	
	public void setVelocity(Vector v) {
		this.v = v;
	}
	
	public void setAceleration(Vector a) {
		this.a = a;
	}
	
	public void setPosition(Vector p) {
		this.p = p;
	}
	
	public void move(double t) {
		
		//p + v*t + 1/2*a*t^2
		this.p = this.p.plus(this.v.scale(t)).plus(this.a.scale((t*t)/2));
		
		//v + a*t
		this.v = this.v.plus(this.a.scale(t));
	}
	
	public String toString() {
		return "{\"id\": \"" + this.id  + "\", \"mass\": " + this.m + ", \"pos\": " + this.p + ", \"vel\": " + this.v + ", \"acc\": " + this.a + " } ";
	}
	
}
