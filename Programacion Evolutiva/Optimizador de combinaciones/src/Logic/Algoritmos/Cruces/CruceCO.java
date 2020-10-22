package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class CruceCO extends Cruce {

	public static final String ID = "Cruce CO";
	public static final int PC = 1; 
	
	public CruceCO() {
		super(ID, PC);
	}

	@Override
	public Cruce newInstance(int pc) {
		return new CruceCO();
	}

	@Override
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte) {
		
		int[] indvCode1, indvCode2;
		int tamGenoma, aux;
		tamGenoma = p1.getSize();
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());
		
		indvCode1 = codificaIndv(p1);
		indvCode2 = codificaIndv(p2);
		
		for (int i = puntosDeCorte.get(0); i < p1.getSize(); i++) {
			aux = indvCode1[i];
			indvCode1[i] = 	indvCode2[i];
			indvCode2[i] = 	aux;
		}
		
		decodifica(hijos.get(0), indvCode1);
		decodifica(hijos.get(1), indvCode1);
		
		return hijos;
	}
	
	public int[] codificaIndv(Individuo indv) {
		
		int pos, n;
		int[] res = new int[indv.getSize()];
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean ok;
		
		for (int i = 0; i < indv.getSize(); i++) {
			list.add(i);
		}
		
		for (int i = 0; i < indv.getSize(); i++) {
			n = (int)indv.getIndex(i);
			pos = 0;
			ok = false;
			while(!ok) {
				if (list.get(pos) == n) ok = true;
				else pos++;
			}
			list.remove(pos);
			res[i] = pos;
		}
		return res;
	}
	
	public void decodifica(Individuo indv, int[] genomaCodificado) {
		int pos, n;
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean ok;
		
		for (int i = 0; i < indv.getSize(); i++) {
			list.add(i);
		}
		
		for (int i = 0; i < indv.getSize(); i++) {
			indv.setIndex(list.get(genomaCodificado[i]), i);
			list.remove(genomaCodificado[i]);
		}
	}

}
