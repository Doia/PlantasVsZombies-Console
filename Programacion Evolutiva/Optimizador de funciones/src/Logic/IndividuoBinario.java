package Logic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public abstract class IndividuoBinario extends Individuo<Boolean>{
	
	
	public IndividuoBinario(String id, double[] intervalo, int numVar) {
		super(id,intervalo,numVar);
	}
	
	public IndividuoBinario(String id, double[] intervalo, int numVar,  double precision) {
		super(id,intervalo,numVar,precision);
	}
	
	public void init() {
		for (int i = 0; i < size; i++) {
			if (Math.random() <= 0.5) {
				genoma[i] = true;
			}
			else {
				genoma[i] = false;
			}
		}
	}
	
	public void setSize(double precision) {
		double num;
		int numEntero;
		int res = 0;
		
		for (int i = 0; i < numVar; i++) {
			num = 1 + ( ( intervalo[(i*2)+1] - intervalo[i*2] ) / precision );
			numEntero = (int) Math.floor (Math.log(num) / Math.log(2));
			
			longVar[i] = numEntero;
			res += numEntero;
		}
		this.size = res;
	}
	
	public abstract Individuo duplicate();
	
	public double bin2Decimal(int ini, int fin) {
		double res = 0;
		int aux = 1;
		for ( int i = fin - 1; i >= ini; i--) {
			if (genoma[i]) {
				res += aux;
			}
			aux *= 2;
		}
		return res;
	}
	
	public double[] getFenotipo() {
		int ini,fin;
		double[] fenotipo = new double[numVar];

		ini = 0;
		for (int i = 0; i < numVar; i++) {	
			fin = ini + longVar[i];
			fenotipo[i] = round (intervalo[i*2] + ( bin2Decimal(ini, fin) * (intervalo[(i*2)+1]- intervalo[i*2]) / ( Math.pow(2, fin - ini) - 1) ));
			ini = fin;
		}
		return fenotipo;
	}
	
	public void muteIndex(int i) {
		genoma[i] = !genoma[i].booleanValue();
	}
	
	
	public abstract double getFitness();
	
	public abstract double getFitnessNormalizado();
	
	public abstract Individuo newInstance(double precision, int n);
	
}
