package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;

public class MutacionSimple extends Mutacion {

	public final static String ID = "Mutacion Simple";
	public final static double  PROB_FUNC= 0.5;
	
	public MutacionSimple() {
		super(ID);
	}
	
	
	public MutacionSimple(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionSimple(maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		Individuo indvAux;
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		String v = nodos.get(p1).getValor();
		
		if (nodos.get(p1).isEsRaiz()) {
			if (v.equals("OR") ) {
				nodos.get(p1).setValor("AND");
			}
			if (v.equals("AND") ) {
				nodos.get(p1).setValor("OR");
			}
			/*if (v == "IF" ) {
				ArrayList<Arbol> hijos = nodos.get(p1).getHijos();
				hijos.set
				nodos.get(p1).setValor("OR");
			}*/
		}
		else {
			int p2 = (int) (Math.random()*Problema.terminales.length);
			nodos.get(p1).setValor(Problema.terminales[p2]);
		}
		indvAux.setRecienModificado(true);
		
		indv = indvAux;
		
	}

}
