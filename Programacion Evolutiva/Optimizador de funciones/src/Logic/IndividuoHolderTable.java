package Logic;

import java.util.Random;

public class IndividuoHolderTable extends IndividuoBinario {

	final static int NUMVAR = 2;
	final static String ID = "Funcion Holder Table";
	final static double[] INTERVALOS = {-10.0, 10.0, -10.0, 10.0};
	
	public IndividuoHolderTable(double precision) {
		super(ID,INTERVALOS, NUMVAR, precision);
		setSize(precision);
		genoma = new Boolean[size];
	}

	public IndividuoHolderTable() {
		super(ID, INTERVALOS, NUMVAR);
	}

	@Override
	public Individuo duplicate() {
		Individuo clon;
		
		clon = new IndividuoHolderTable(this.precision);
		clon.setGenoma(genoma);
		
		return clon;
	}

	@Override
	public double getFitness() {
		double[] var = this.getFenotipo();
		
		double exp = Math.pow(Math.E, Math.abs(1 - (Math.sqrt( Math.pow(var[0], 2) + Math.pow(var[1], 2) ) /Math.PI)  ) );
		
		double res = round (-1.0* Math.abs(Math.sin(var[0]) * Math.cos(var[1]) * exp));
		return res;
	}

	public double getFitnessNormalizado() {
		return -1.0 * getFitness();
	}

	@Override
	public Individuo newInstance(double precision, int n) {
		// TODO Auto-generated method stub
		return new IndividuoHolderTable(precision);
	}

}
