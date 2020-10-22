package Logic;

import java.util.Random;

public class IndividuoF1 extends IndividuoBinario {

	final static int NUMVAR = 2;
	final static String ID = "Funcion Basica";
	final static double[] INTERVALOS = {-3.0, 12.1, 4.1, 5.8};
	
	int[] longVar; //longitud en binario de cada variable
	
	public IndividuoF1(double precision) {
		super(ID,INTERVALOS, NUMVAR, precision);
		setSize(precision);
		genoma = new Boolean[size];
		
	}
	public IndividuoF1() {
		super(ID, INTERVALOS, NUMVAR);
		
	}

	@Override
	public Individuo duplicate() {
		Individuo clon;
		
		clon = new IndividuoF1(this.precision);
		clon.setGenoma(genoma);
		
		return clon;
		
	}

	@Override
	public double getFitness() {
		double[] var = this.getFenotipo();
		
		double pi = round (Math.PI);
		
		return round( 21.5 + (var[0] * round(Math.sin(4*pi*var[0]))) + (var[1] * round(Math.sin(20*pi*var[1]))) );
	}
	
	public double getFitnessNormalizado() {
		return getFitness();
	}
	@Override
	public Individuo newInstance(double precision, int n) {
		// TODO Auto-generated method stub
		return new IndividuoF1(precision);
	}

}
