package Logic.Algoritmos;

import java.util.ArrayList;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;

public class CruceMonopunto extends Cruce {

	public static final String ID = "Cruce Monopunto"; 
	
	public CruceMonopunto() {
		super(ID);
	}

	@Override
	public ArrayList<Individuo> doCruce(ArrayList<Individuo> poblacion, double pCruce) {
		
		ArrayList<Individuo> listaPadres;
		ArrayList<Individuo> hijos;
		
		int padres = (int) Math.round (poblacion.size() * pCruce);
		if (padres % 2 == 1) {
			padres++;
		}
		
		listaPadres = seleccionAleatoria(poblacion, padres);
		
		hijos = seleccionAleatoria(poblacion, poblacion.size() - padres); //restos
		
		for (int i = 0; i < padres; i +=2) {
			double pPunto = 1.0 / poblacion.get(0).getSize();
			double p = Math.random();
			int punto = (int) Math.floor(p/pPunto);
			
			ArrayList<Individuo> aux = cruza(listaPadres.get(i), listaPadres.get(i+1), punto);
			
			for (Individuo indv: aux ) {
				hijos.add(indv);
			}
		}
		
		return hijos;
	}
	
	private ArrayList<Individuo> cruza(Individuo p1, Individuo p2, int punto){

		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());

		for (int n = punto; n < p1.getSize(); n++) {
			hijos.get(0).setIndex(p2.getIndex(n), n);
			hijos.get(1).setIndex(p1.getIndex(n), n);
		}
		
		return hijos;
	}

	@Override
	public Cruce newInstance() {
		return new CruceMonopunto();
	}
	
	

}
