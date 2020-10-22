package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class CruceERX extends Cruce {

	public static final String ID = "Cruce ERX";
	public static final int PC = 0; 
	
	public CruceERX() {
		super(ID, PC);
	}

	@Override
	public Cruce newInstance(int pc) {
		return new CruceERX();
	}

	@Override
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte) {
		ArrayList<ArrayList<Integer>> adj;	


		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());
		
		adj = generaMatrizAdj(p1,p2);

		generaIndividuo(hijos.get(0), adj);
		generaIndividuo(hijos.get(1), adj);
		
		return hijos;
	}
	
	public void generaIndividuo(Individuo indv, ArrayList<ArrayList<Integer>> adj) {
		int tamGenoma, pos, cont, max;
		double prob, ratio;
		boolean ok,find;
		tamGenoma = indv.getSize();
		boolean[] visitados = new boolean[tamGenoma];
		ArrayList<ArrayList<Integer>> block = new ArrayList<ArrayList<Integer>>();
		
		for ( int i = 0; i < tamGenoma; i++) {
			visitados[i] = false;
			block.add(new ArrayList<Integer>());
		}
		
		pos = (int) indv.getIndex(0);
		cont = 0; ok = false;
		
		while (!ok) {
			visitados[pos] = true;
			ArrayList<Integer> opciones = new ArrayList<Integer>();
			max = 10;
			for (int i : adj.get(pos)) {
				if (!visitados[i] && !block.get(cont).contains(i)) {
					if (adj.get(i).size() < max) {
						max = adj.get(i).size();
						opciones.clear();
						opciones.add(i);
					}
					else if (adj.get(i).size() == max) 	opciones.add(i);
				}
			}
			
			if (opciones.size() > 0) {
				prob = Math.random();
				ratio = 1.0 / opciones.size();
				
				find = false;
				for (int i = 0; i < opciones.size() && !find; i++) {
					if (prob <= (i+1)*ratio) { 
						pos = opciones.get(i);
						find = true;
					}
				}
				
				cont++;
				indv.setIndex(pos, cont);

			}
			else {
				if (cont + 1 == tamGenoma) {
					ok = true;
				}
				else {
					visitados[pos] = false;
					block.get(cont).clear();
					block.get(cont-1).add(pos);
					cont--;
					pos = (int) indv.getIndex(cont);
				}
			}
			
		}
	}
	
	public ArrayList<ArrayList<Integer>> generaMatrizAdj(Individuo p1, Individuo p2){
		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		
		
		for (int n = 0; n < p1.getSize(); n++) 	adj.add(new ArrayList<Integer>());
		for (int n = 0; n < p1.getSize(); n++) {
			
			int j = 0;
			while (n != (int) p2.getIndex(j)) j++;
			int i = 0;
			while (n != (int) p1.getIndex(i)) i++;
			
			
			//p1
			
			if (i == 0) {
				adj.get(n).add( (int)p1.getIndex(p1.getSize()-1) );
			}
			else {
				adj.get(n).add( (int)p1.getIndex(i-1) );
			}
			
			if (i == p1.getSize() - 1 ) {
				adj.get(n).add( (int)p1.getIndex(0) );
			}
			else {
				adj.get(n).add( (int)p1.getIndex(i+1) );
			}
			
			//p2
			
			if (j == 0) {
				if (!adj.get(n).contains((int)p2.getIndex(p2.getSize()-1))) {
				adj.get(n).add( (int)p2.getIndex(p2.getSize()-1));}
			}
			else {
				if (!adj.get(n).contains((int)p2.getIndex(j-1))) {
				adj.get(n).add( (int)p2.getIndex(j-1));}
			}
			
			if (j == p2.getSize() - 1) {
				if (!adj.get(n).contains((int)p2.getIndex(0))) {
				adj.get(n).add( (int)p2.getIndex(0));}
			}
			else {
				if (!adj.get(n).contains((int)p2.getIndex(j+1))) {
				adj.get(n).add( (int)p2.getIndex(j+1));}
			}
			
		}
		return adj;
	}

}
