package Logic.Algoritmos.Individuos;

import java.util.Comparator;

public class IndividuoComparator implements Comparator<Individuo> {

	boolean maximizacion;
	
	public IndividuoComparator(boolean maximizacion) {
		this.maximizacion = maximizacion;
	}
	
	@Override
	public int compare(Individuo indv1, Individuo indv2) {
		int re; // segun maximizacion o minimizaciion es 0 o 1
		double fit1,fit2;
		fit1 = indv1.getFitness();
		fit2 = indv2.getFitness();
		
		if (maximizacion) re = -1;
		else re = 1;
		
		if (fit1 > fit2) {
			return re;
		}	
		else if (fit2 > fit1) {
			return -1 * re;
		}
		return 0;
	}

}
