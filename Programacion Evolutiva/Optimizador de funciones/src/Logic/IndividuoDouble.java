package Logic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public abstract class IndividuoDouble extends Individuo<Double>{
	
	
	public IndividuoDouble(String id, double[] intervalo, int numVar) {
		super(id,intervalo,numVar);
	}
	
	public IndividuoDouble(String id, double[] intervalo, int numVar,  double precision) {
		super(id,intervalo,numVar,precision);
	}
	
	public void init() {
		double dif;
		
		for (int i = 0; i < size; i++) {
			dif = Math.abs(intervalo[i*2] - intervalo[(i*2)+1]);
				genoma[i] = round(Math.random() * dif + intervalo[i*2]);
		}
		
	}
	
	public abstract Individuo duplicate();
	
	public double[] getFenotipo() {
		double[] res = new double[size];
		for (int i = 0; i < size; i++) {
			res[i] = genoma[i].doubleValue();
		}
		return res;
	}
	
	
	public void muteIndex(int i) {
		double dif;
		dif = Math.abs(intervalo[i*2] - intervalo[i*2]);
		genoma[i] = Math.random() * dif + intervalo[i*2];
	}
	
	public void setSize(double precision) {
		this.size = numVar;
	}
	
	public abstract double getFitness();
	
	public abstract double getFitnessNormalizado();
	
	public abstract Individuo newInstance(double precision, int n);

	
	
	
}
