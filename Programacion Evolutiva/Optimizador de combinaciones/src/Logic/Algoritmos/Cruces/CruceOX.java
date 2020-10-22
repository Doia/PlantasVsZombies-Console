package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class CruceOX extends Cruce {

	public static final String ID = "Cruce OX"; 
	public static final int PC = 2; 
	
	public CruceOX() {
		super(ID, PC);
	}

	@Override
	public Cruce newInstance(int pc) {
		return new CruceOX();
	}
	
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2,  ArrayList<Integer> puntosDeCorte){
		
		int n1,n2, pos1, pos2, tamGenoma, punto1,punto2;
		
		boolean[] colocados1 = new boolean[p1.getSize()];
		boolean[] colocados2 = new boolean[p2.getSize()];
		
		tamGenoma = p1.getSize();
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
			colocados1[i] = false;
			colocados2[i] = false;
		}
		
		for (int i = punto1; i <= punto2; i++) {
			
			n1 = (Integer)p1.getIndex(i);
			n2 = (Integer)p2.getIndex(i);
			
			colocados1[n2] = true;
			colocados2[n1] = true;
			
			hijos.get(0).setIndex(n2, i);
			hijos.get(1).setIndex(n1, i);
		
		}
		
		pos1 = (punto2+1) % tamGenoma;
		pos2 = (punto2+1) % tamGenoma;
		
		for (int i = pos1; i != punto1; i++) {
		
			while( colocados1[(Integer)p1.getIndex(pos1)]) {
				pos1 = (pos1 + 1) % tamGenoma;
			}

			while(colocados2[(Integer)p2.getIndex(pos2)]) {
				pos2 = (pos2 + 1) % tamGenoma;
			}
			
			n1 = (Integer)p1.getIndex(pos1);
			n2 = (Integer)p2.getIndex(pos2);
			
			hijos.get(0).setIndex(n1, i);
			hijos.get(1).setIndex(n2, i);
			
			colocados1[n1] = true;
			colocados2[n2] = true;
			
			if (i + 1 == tamGenoma) i = -1;
 			
		}
		return hijos;
	}

}
