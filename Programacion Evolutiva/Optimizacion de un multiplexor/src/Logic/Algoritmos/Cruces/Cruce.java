package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public abstract class Cruce {
	
	
	public final double PROB_FUNC = 0.9;
	
	protected String id;
	protected int pCorte;
	protected boolean maximizacion;
	
	public Cruce(String id, int pCorte, boolean maximizacion) {
		this.id = id;
		this.pCorte = pCorte;
		this.maximizacion = maximizacion;
	}
	public Cruce(String id, int pCorte) {
		this.id = id;
		this.pCorte = pCorte;
	}
	

	
	public abstract Cruce newInstance(int pc, boolean maximizacion);
		

	
	public abstract ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte);
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public ArrayList<Individuo> doCruce(ArrayList<Individuo> poblacion, double pCruce){
		double ratio;
		int punto1, punto2, n, puntoCruce;
		
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		ArrayList<Integer> puntosDeCorte;

		
		for (int i = 0; i < poblacion.size(); i +=2) {
			
			if (Math.random() <= pCruce) {
				puntosDeCorte = new ArrayList<Integer>();
				ArrayList<Individuo> aux = cruza(poblacion.get(i), poblacion.get(i+1), puntosDeCorte);
				for (Individuo indv: aux ) {
					hijos.add(indv);
				}
			}
			else {
				hijos.add(poblacion.get(i));
				hijos.add(poblacion.get(i+1));
			}
			

		}
		
		return hijos;
	}
	
	public ArrayList<Integer> CalculaPuntosDeCorte(int ratio,int n){
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
	
	protected ArrayList<Arbol> obtieneNodos(Arbol arbol) {
		ArrayList<Arbol> nodos = new ArrayList<Arbol>();
		
		//Obtenemos una probabilidad al azar
		if(seleccionaFunciones()){//Si devuelve true, el corte se hará en una función
			arbol.getFunciones(arbol.getHijos(), nodos);
			if(nodos.size() == 0){//Si no existen funciones, se seleccionan los terminales
				arbol.getTerminales(arbol.getHijos(), nodos);
			}
			
		}
		else{//Si devuelve false, el corte se hará por un terminal
			arbol.getTerminales(arbol.getHijos(), nodos);
		}
		return nodos;
	}
	
	
	private boolean seleccionaFunciones(){
		double prob = Math.random();
		if(prob < PROB_FUNC){
			return true;
		}
		else{
			return false;
		}
	}

	
	
	
	
	
	
	
	
}
