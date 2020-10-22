package Logic;

import java.util.Random;

public class IndividuoSchubert extends IndividuoBinario {

	final static int NUMVAR = 2;
	final static String ID = "Funcion Schubert";
	final static double[] INTERVALOS = {-10.0, 10.0, -10.0, 10.0};
	
	
	public IndividuoSchubert(double precision) {
		super(ID,INTERVALOS, NUMVAR, precision);
		setSize(precision);
		genoma = new Boolean[size];
		// TODO Auto-generated constructor stub
	}

	public IndividuoSchubert() {
		super(ID, INTERVALOS, NUMVAR);
	}

	@Override
	public IndividuoBinario duplicate() {
		IndividuoBinario clon;
		
		clon = new IndividuoSchubert(this.precision);
		clon.setGenoma(genoma);
		
		return clon;
	}

	@Override
	public double getFitness() {
		double[] var = this.getFenotipo();

		double aux1 = 0, aux2=0;
		for(int i=1;i<=5;i++)
		{
		aux1 = aux1 + (i*Math.cos((i+1)*var[0]+i));
		aux2 = aux2 + (i*Math.cos((i+1)*var[1]+i));
		}

		return round( aux1*aux2 );

	}

	@Override
	public double getFitnessNormalizado() {
		// TODO Auto-generated method stub
		return -1.0 * getFitness();
	}

	@Override
	public IndividuoBinario newInstance(double precision, int n) {
		return new IndividuoSchubert(precision) ;
	}

}
