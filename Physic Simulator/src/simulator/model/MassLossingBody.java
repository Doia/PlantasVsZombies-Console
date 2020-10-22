package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	
	private double lossFactor;
	private double lossFrequency;
	private double count;//_accumulatedTime
	
	public MassLossingBody(String id, Vector v, Vector a, Vector p, double m, double freq, double fac) {
		super(id, v, a, p , m);
		this.lossFactor = fac;
		this.lossFrequency = freq;
		this.count = 0.0;
		this.type = "mlb";
	}
	
	public double getLossFactor() {
		return this.lossFactor;
	}
	
	public double getLossFrequency() {
		return this.lossFrequency;
	}
	
	public double getCount() {
		return this.count;
	}
	
	public void setLossFactor(double lFactor) {
		this.lossFactor = lFactor;
	}
	
	public void setlossFrequency(double lFrequency) {
		this.lossFrequency = lFrequency;
	}
	
	public void setCount(double count) {
		this.count = count;
	}
	
	public void move(double t) {
		super.move(t);
		this.count += t;
		
		if (this.count >= this.lossFrequency) {
			this.m = m * (1-this.lossFactor);
			this.count = 0.0;
		}
	}
}
