package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public abstract class Mutacion {
	
	String id;
	
	public Mutacion(String id) {
		this.id = id;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	public String getId() {
		return this.id;
	}
	
	public abstract Mutacion newInstance(boolean maximizacion);
	
	public abstract void mutaIndividuo(Individuo indv);
	
	public ArrayList<Individuo> doMutacion(ArrayList<Individuo> poblacion, double pMut) {
	
		for (int i = 0; i < poblacion.size();i++) {
			
			if ( Math.random() <= pMut) {
				mutaIndividuo(poblacion.get(i));
			}
	
		}
		return poblacion;
	}
}
