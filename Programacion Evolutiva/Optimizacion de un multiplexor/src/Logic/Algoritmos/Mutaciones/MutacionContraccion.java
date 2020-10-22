package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;

public class MutacionContraccion extends Mutacion {

	public final static String ID = "Mutacion De Contraccion";
	public final static double  PROB_FUNC= 2;
	
	public MutacionContraccion() {
		super(ID);
	}
	
	
	public MutacionContraccion(boolean maximizacion) {
		super(ID, PROB_FUNC, maximizacion);
	}
	
	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionContraccion(maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		Individuo indvAux;
		indvAux = indv.duplicate();
		ArrayList<Arbol> nodos = obtieneNodos(indvAux.getArbol());
		int p1 = (int) (Math.random()*nodos.size());
		
		int pos = (int) (Math.random()*Problema.terminales.length);

		nodos.get(p1).setValor(Problema.terminales[pos]);
		nodos.get(p1).getHijos().clear();
		nodos.get(p1).setNumHijos(0);
		nodos.get(p1).setEsHoja(true);
		nodos.get(p1).setEsRaiz(false);
		
		indvAux.setRecienModificado(true);
				
		indv = indvAux;

	}

}
