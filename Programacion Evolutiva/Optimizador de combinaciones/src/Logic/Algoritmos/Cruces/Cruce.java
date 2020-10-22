package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public abstract class Cruce {
	
	protected String id;
	protected int pCorte;
	
	public Cruce(String id, int pCorte) {
		this.id = id;
		this.pCorte = pCorte;
	}
	

	
	public abstract Cruce newInstance(int pc);
		

	
	public abstract ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte);
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public ArrayList<Individuo> doCruce(ArrayList<Individuo> poblacion, double pCruce){
		double ratio;
		int punto1, punto2, n;

		ArrayList<Individuo> listaPadres;
		ArrayList<Individuo> hijos;
		ArrayList<Integer> puntosDeCorte;
		
		int padres = (int) Math.round (poblacion.size() * pCruce);
		if (padres % 2 == 1) {
			padres++;
		}
		
		listaPadres = seleccionAleatoria(poblacion, padres);
		
		hijos = seleccionAleatoria(poblacion, poblacion.size() - padres); //restos
		
		for (int i = 0; i < padres; i +=2) {
			
			ratio = 1.0 / poblacion.get(0).getSize();
			puntosDeCorte = this.CalculaPuntosDeCorte(ratio,this.pCorte);
			
			ArrayList<Individuo> aux = cruza(listaPadres.get(i), listaPadres.get(i+1), puntosDeCorte);
			
			for (Individuo indv: aux ) {
				hijos.add(indv);
			}
		}
		
		return hijos;
	}
	
	public ArrayList<Integer> CalculaPuntosDeCorte(double ratio,int n){
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < n; i++) {
			list.add( (int) Math.floor(Math.random() / ratio) );
		}
		
		return list;
		
	}

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
