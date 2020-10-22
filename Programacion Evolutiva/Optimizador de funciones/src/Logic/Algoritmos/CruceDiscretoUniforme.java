package Logic.Algoritmos;

import java.util.ArrayList;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;

public class CruceDiscretoUniforme extends Cruce {

	public static final String ID = "Cruce Discreto Uniforme"; 
	
	public CruceDiscretoUniforme() {
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
			
			ArrayList<Individuo> aux = cruza(listaPadres.get(i), listaPadres.get(i+1));
			
			for (Individuo indv: aux ) {
				hijos.add(indv);
			}
		}
		
		return hijos;
	}
	
	private ArrayList<Individuo> cruza(Individuo p1, Individuo p2){

		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());

		for (int i = 0; i < p1.getSize(); i++) {
			double camb = Math.random();
			if(camb<=0.5) {
				hijos.get(0).setIndex(p2.getIndex(i), i);
				hijos.get(1).setIndex(p1.getIndex(i), i);
			}
		}
		
		return hijos;
	}

	@Override
	public Cruce newInstance() {
		
		return new CruceDiscretoUniforme();
	}

}
