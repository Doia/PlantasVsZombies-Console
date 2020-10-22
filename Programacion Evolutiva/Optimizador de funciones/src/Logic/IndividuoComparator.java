package Logic;

import java.util.Comparator;

public class IndividuoComparator implements Comparator<Individuo> {

	@Override
	public int compare(Individuo indv1, Individuo indv2) {
		double fit1,fit2;
		fit1 = indv1.getFitnessNormalizado();
		fit2 = indv2.getFitnessNormalizado();
		
		if (fit1 < fit2) {
			return 1;
		}	
		else if (fit2 < fit1) {
			return -1;
		}
		return 0;
	}

}
