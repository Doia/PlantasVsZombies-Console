package Logic;

import java.util.ArrayList;

public abstract class Cruce {
	
	protected String id;
	
	public Cruce(String id) {
		this.id = id;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	public String getId() {
		return this.id;
	}
	
	public abstract Cruce newInstance();
	
	public abstract ArrayList<Individuo> doCruce(ArrayList<Individuo> poblacion, double pCruce);
	
	public ArrayList<Individuo> seleccionAleatoria(ArrayList<Individuo> poblacion, int n){
		double pIndv;
		int res;
		ArrayList<Individuo> seleccion = new ArrayList<Individuo>();
		
		pIndv = 1.0 / poblacion.size();
		
		for (int i = 0; i < n; i++) {
			res = (int) Math.floor(Math.random()/pIndv);
			seleccion.add(poblacion.get(res));
		}
		
		return seleccion;
	}
}
