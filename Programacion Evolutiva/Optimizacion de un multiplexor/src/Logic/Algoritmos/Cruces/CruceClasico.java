package Logic.Algoritmos.Cruces;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.IndividuoComparatorPunt;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class CruceClasico extends Cruce {

	
	public final static String ID = "Cruce Clasico";
	public final static int PC = 2;
	
	public CruceClasico() {
		super(ID,PC);
	}
	
	public CruceClasico(boolean maximizacion) {
		super(ID,PC, maximizacion);
	}
	
	@Override
	public CruceClasico newInstance(int pc, boolean maximizacion) {
		return new CruceClasico(maximizacion);
	}

	@Override
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte) {
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		Individuo hijo1 = new Individuo();
		Individuo hijo2 = new Individuo();
		ArrayList<Arbol> nodos_selec1 = new ArrayList<Arbol>();
		ArrayList<Arbol> nodos_selec2 = new ArrayList<Arbol>();
		
		//Seleccionamos los nodos más relevante según la probabilidad
		//0.9 se cruzará en una función
		//resto se cruzará en un terminal
		
		nodos_selec1 = obtieneNodos(p1.getArbol().copia());
		nodos_selec2 = obtieneNodos(p2.getArbol().copia());

		//obtenemos los puntos de cruce a partir de los nodos seleccionados
		int puntoCruce1 = (int) (Math.random()*nodos_selec1.size());
		int puntoCruce2 = (int) (Math.random()*nodos_selec2.size());
		
		//copiamos los cromosomas padre en los hijos
		hijo1 = p1.duplicate();
		hijo2 = p2.duplicate();
		
		//Cogemos los nodos de cruce seleccionados
		
		Arbol temp1 = nodos_selec1.get(puntoCruce1).copia();
		Arbol temp2 = nodos_selec2.get(puntoCruce2).copia();
		
		
		//realizamos el corte sobre los arboles de los hijos
		corte(hijo1, temp2, puntoCruce1, temp1.isEsRaiz());
		corte(hijo2, temp1, puntoCruce2, temp2.isEsRaiz());
		
		
		
		int nodos = hijo1.getArbol().obtieneNodos(hijo1.getArbol(), 0);
		hijo1.getArbol().setNumNodos(nodos);
		nodos = hijo2.getArbol().obtieneNodos(hijo2.getArbol(), 0);
		hijo2.getArbol().setNumNodos(nodos);
		hijo1.setRecienModificado(true);
		hijo2.setRecienModificado(true);
		
		
		hijos.add(hijo1);
		hijos.add(hijo2);

		
		return hijos;
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

}

