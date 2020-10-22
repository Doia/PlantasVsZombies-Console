package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class CruceCX extends Cruce {


	public static final String ID = "Cruce CX";
	public static final int PC = 0; 
	
	public CruceCX() {
		super(ID, PC);
	}

	@Override
	public Cruce newInstance(int pc) {
		return new CruceCX();
	}

	@Override
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte) {
		
		Object aux;
		int n, pos;
		
		boolean[] visitados = new boolean[p1.getSize()];
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());
		
		for (int i = 0; i < visitados.length; i++) {
			visitados[i] = false;
		}
		pos = 0;
		while (!visitados[pos]) {
			visitados[pos] = true;
			n = (int)p2.getIndex(pos);
			pos = 0;
			while ((int)p1.getIndex(pos) != n) {
				pos++;
			}
		}
		
		for (int i = 0; i < visitados.length; i++) {
			if (!visitados[i]) {
				aux = p1.getIndex(i);
				hijos.get(0).setIndex(p2.getIndex(i), i);
				hijos.get(1).setIndex(aux, i);
			}
		}
		
		return hijos;
	}


}
