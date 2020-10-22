package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class MutacionHoist extends Mutacion {

	public final static String ID = "Mutacion Hoist";
	public final static double  PROB_FUNC= 2;
	
	public MutacionHoist() {
		super(ID);
	}
	
	
	public MutacionHoist(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionHoist(maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		Individuo indvAux;
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		
		indvAux.setArbol(nodos.get(p1).copia());
		
		indvAux.setRecienModificado(true);
		
		indv = indvAux;
	}

}
