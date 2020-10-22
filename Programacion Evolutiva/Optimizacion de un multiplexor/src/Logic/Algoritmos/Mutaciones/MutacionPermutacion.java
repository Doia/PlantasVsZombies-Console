package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class MutacionPermutacion extends Mutacion {

	public final static String ID = "Mutacion Permutacion";
	public final static double  PROB_FUNC= 2;
	
	public MutacionPermutacion() {
		super(ID);
	}
	
	
	public MutacionPermutacion(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionPermutacion(maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		Individuo indvAux;
		Arbol arbolAux1,arbolAux2,arbolAux3;
		
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		String v = nodos.get(p1).getValor();
		
		if (nodos.get(p1).isEsRaiz()) {
			arbolAux1 = nodos.get(p1).getHijos().get(0);
			arbolAux2 = nodos.get(p1).getHijos().get(0);
			arbolAux3 = nodos.get(p1).getHijos().get(0);
			if (v == "IF" ) {
				nodos.get(p1).getHijos().clear();
				nodos.get(p1).getHijos().add(arbolAux3);
				nodos.get(p1).getHijos().add(arbolAux1);
				nodos.get(p1).getHijos().add(arbolAux2);
			}
		}
		indvAux.setRecienModificado(true);
		indv = indvAux;
		


	}

}
