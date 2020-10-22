package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;

public class MutacionTerminalSimple extends Mutacion {

	public final static String ID = "Mutacion Terminal Simple";
	public final static double  PROB_FUNC= -1;
	
	public MutacionTerminalSimple() {
		super(ID);
	}
	
	public MutacionTerminalSimple(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionTerminalSimple(maximizacion);
	}

	public void mutaIndividuo(Individuo indv) {
		Individuo indvAux;
		
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		if (nodos.get(p1).isEsHoja()) {
			int p2 = (int) (Math.random()*Problema.terminales.length);
			nodos.get(p1).setValor(Problema.terminales[p2]);
		}
		indvAux.setRecienModificado(true);
		indv = indvAux;
			

	}

}
