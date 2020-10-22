package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;

public class MutacionBasica extends Mutacion {

	public final static String ID = "Mutacion Basica";
	
	public MutacionBasica() {
		super(ID);
	}
	
	public void mutaIndividuo(Individuo indv) {
		double pPunto = 1.0 / indv.getSize();	
		int punto = (int) Math.floor(Math.random() / pPunto);
		indv.muteIndex(punto);
	}

	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionBasica();
	}

}
