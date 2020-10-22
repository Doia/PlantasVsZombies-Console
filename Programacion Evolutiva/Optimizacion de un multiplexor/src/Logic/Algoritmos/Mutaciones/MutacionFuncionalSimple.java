package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;

public class MutacionFuncionalSimple extends Mutacion {

	public final static String ID = "Mutacion Funcional Simple";
	public final static double  PROB_FUNC= 2;
	
	public MutacionFuncionalSimple() {
		super(ID);
	}
	
	
	public MutacionFuncionalSimple(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionFuncionalSimple(maximizacion);
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
		}
		indvAux.setRecienModificado(true);
		indv = indvAux;
		

	}

}
