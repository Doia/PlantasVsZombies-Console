package Logic.Algoritmos;

import java.util.ArrayList;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;
import Logic.Mutacion;

public class MutacionBasica extends Mutacion {

	public final static String ID = "Mutacion Basica";
	
	public MutacionBasica() {
		super(ID);
	}
	
	@Override
	public ArrayList<Individuo> doMutacion(ArrayList<Individuo> poblacion, double pMut) {
	
		for (int i = 0; i < poblacion.size();i++) {
			
			if ( Math.random() <= pMut) {
				mutaIndividuo(poblacion.get(i));
			}
	
		}
		return poblacion;
	}

	private void mutaIndividuo(Individuo indv) {
		double pPunto = 1.0 / indv.getSize();	
		int punto = (int) Math.floor(Math.random() / pPunto);
		indv.muteIndex(punto);
	}

	@Override
	public Mutacion newInstance() {
		// TODO Auto-generated method stub
		return new MutacionBasica();
	}

}
