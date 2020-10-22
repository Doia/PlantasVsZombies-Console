package Logic.Algoritmos.Cruces;

import java.util.ArrayList;
import java.util.Hashtable;

import Logic.Algoritmos.Individuos.Individuo;

public class CrucePMX extends Cruce {

	public static final String ID = "Cruce PMX";
	public static final int PC = 2; 
	
	public CrucePMX() {
		super(ID, PC);
	}
	
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2,  ArrayList<Integer> puntosDeCorte){
		int n1,n2, punto1, punto2, tamGenoma;
		
		tamGenoma = p1.getSize();
		
		int[] complementario1 = new int[tamGenoma];
		int[] complementario2 = new int[tamGenoma];
		

		
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());
		
		if(puntosDeCorte.get(0) > puntosDeCorte.get(1)) {
			punto1 = puntosDeCorte.get(1);
			punto2 = puntosDeCorte.get(0);
		}
		else {
			punto1 = puntosDeCorte.get(0);
			punto2 = puntosDeCorte.get(1);
		}
		
		for (int i = 0; i < tamGenoma; i++) {
			complementario1[i] = -1;
			complementario2[i] = -1;
		}
		
		for (int i = punto1; i <= punto2; i++) {
			
			n1 = (Integer)p1.getIndex(i);
			n2 = (Integer)p2.getIndex(i);
			complementario1[n1] = n2;
			complementario2[n2] = n1;
			
			hijos.get(0).setIndex(n2, i);
			hijos.get(1).setIndex(n1, i);
		
		}
		
		for (int i = 0; i < tamGenoma; i++) {
			if ( i == punto1) {
				i = punto2;
			}
			else {
				n1 = (Integer)p1.getIndex(i);
				while (complementario2[n1] != -1) {
					n1 = complementario2[n1];
				}
				hijos.get(0).setIndex(n1, i);
				
				n2 = (Integer)p2.getIndex(i);	
				while (complementario1[n2] != -1) {
					n2 = complementario1[n2];
				}
				hijos.get(1).setIndex(n2, i);
			}
		}
		
		return hijos;
	}
	
	@Override
	public Cruce newInstance(int pc) {
		return new CrucePMX();
	}



}
