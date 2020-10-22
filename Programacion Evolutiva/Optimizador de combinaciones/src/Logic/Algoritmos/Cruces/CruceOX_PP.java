package Logic.Algoritmos.Cruces;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class CruceOX_PP extends Cruce {

	public static final String ID = "Cruce OX-PP"; 
	//public static final int PC = 2; 
	
	private int pc;
	
	public CruceOX_PP() {
		super(ID,0);
	}
	
	public CruceOX_PP(int pc) {
		super(ID,pc);
	}

	@Override
	public Cruce newInstance(int pc) {
		return new CruceOX_PP(pc);
	}

	@Override
	public ArrayList<Individuo> cruza(Individuo p1, Individuo p2, ArrayList<Integer> puntosDeCorte) {
		
		int n1,n2, pos1, pos2, tamGenoma, ult;
		
		boolean[] PCini = new boolean[p1.getSize()];
		boolean[] colocados1 = new boolean[p1.getSize()];
		boolean[] colocados2 = new boolean[p2.getSize()];
		
		tamGenoma = p1.getSize();
		ArrayList<Individuo> hijos = new ArrayList<Individuo>();
		hijos.add(p1.duplicate()); hijos.add(p2.duplicate());
		
		
		for (int i = 0; i < tamGenoma; i++) {
			PCini[i] = false;
			colocados1[i] = false;
			colocados2[i] = false;
		}
		ult = 0;
		for (Integer p : puntosDeCorte) {
			
			n1 = (Integer)p1.getIndex(p.intValue());
			n2 = (Integer)p2.getIndex(p.intValue());
			
			colocados1[n2] = true;
			colocados2[n1] = true;
			
			hijos.get(0).setIndex(n2, p.intValue());
			hijos.get(1).setIndex(n1, p.intValue());
			
			PCini[p.intValue()] = true;
			ult = Math.max(ult, p.intValue());
		
		}
		
		pos1 = (ult+1) % tamGenoma;
		pos2 = (ult+1) % tamGenoma;
		
		for (int i = pos1; i != ult; i++) {
			if (!PCini[i]) {
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
			}
			if (i + 1 == tamGenoma) i = -1;
		}
		return hijos;
	}
}


