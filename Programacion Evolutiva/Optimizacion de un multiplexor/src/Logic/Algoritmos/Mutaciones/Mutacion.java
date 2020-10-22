package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public abstract class Mutacion {
	
	String id;
	boolean maximizacion;
	double prob_func;
	
	public Mutacion(String id) {
		this.id = id;
	}
	
	public Mutacion(String id, double prob_func, boolean maximizacion) {
		this.id = id;
		this.prob_func = prob_func;
		this.maximizacion = maximizacion;
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
	
	private void corte(Individuo hijo, Arbol temp, int puntoCruce, boolean esRaiz) {
		if(!esRaiz){
		//dependiendo de qué tipo era el nodo que ya no va a estar, se inserta el nuevo
			hijo.getArbol().insertTerminal(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		}
		else{
			hijo.getArbol().insertFuncion(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		}
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
		if(prob < prob_func){
			return true;
		}
		else{
			return false;
		}
	}
}
