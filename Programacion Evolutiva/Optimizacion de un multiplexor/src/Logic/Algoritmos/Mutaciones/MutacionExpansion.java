package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class MutacionExpansion extends Mutacion {

	public final static String ID = "Mutacion De Expansion";
	public final static double  PROB_FUNC= -1;
	
	public MutacionExpansion() {
		super(ID);
	}
	
	
	public MutacionExpansion(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionExpansion(maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		Arbol arbol;
		Individuo indvAux;
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		
		int prof = (int) (Math.random()*indv.getProfMax() + 1);
		arbol = new Arbol(prof);
		arbol.inicializacionCreciente(0, 0);
		nodos.get(p1).setValor(arbol.getValor());
		nodos.get(p1).setHijos(arbol.getHijos());
		nodos.get(p1).setNumHijos(arbol.getHijos().size());
		nodos.get(p1).setEsHoja(arbol.isEsHoja());
		nodos.get(p1).setEsRaiz(arbol.isEsRaiz());
		
		indvAux.setRecienModificado(true);
				
		indv = indvAux;

	}

}
