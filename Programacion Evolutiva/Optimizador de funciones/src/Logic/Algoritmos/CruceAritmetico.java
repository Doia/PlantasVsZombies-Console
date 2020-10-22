package Logic.Algoritmos;

import java.util.ArrayList;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;

public class CruceAritmetico extends Cruce {

	public static final String ID = "Cruce Aritmetico"; 
	
	public CruceAritmetico() {
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
		double aux1,aux2, r;
		
		
		for (int i = 0; i < p1.getSize(); i++) {
			r = 0.6;
			aux1 = (double) hijos.get(0).getIndex(i);
			aux2 = (double) hijos.get(1).getIndex(i);
			
			hijos.get(0).setIndex(hijos.get(0).round(aux1*(r)+aux2*(1-r)), i);
			hijos.get(1).setIndex(hijos.get(0).round(aux1*(r)+aux2*(1-r)), i);
		}
		
		return hijos;
	}

	@Override
	public Cruce newInstance() {
		return new CruceAritmetico();
	}
	
	

}
