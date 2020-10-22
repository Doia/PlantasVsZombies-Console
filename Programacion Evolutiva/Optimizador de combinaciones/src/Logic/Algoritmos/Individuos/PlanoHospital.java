package Logic.Algoritmos.Individuos;

import java.util.ArrayList;

public class PlanoHospital extends Individuo<Integer> {

	public static final String ID = "Plano Hospital";

	int[][] mDist;
	int[][] mflujo;
	
	public PlanoHospital() {
		super(ID);
	}
	
	public PlanoHospital(int tam, int[][] mDist, int[][] mFlujo) {
		super(ID, tam);
		genoma = new Integer[this.size];
		this.mDist = mDist;
		this.mflujo = mFlujo;
	}

	@Override
	public void init() {
		int punto;
		double ratio;
		ArrayList<Integer> disponibles = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			disponibles.add(i);
		}
		
		for (int i = 0; i < size; i++) {
			ratio = 1.0 / disponibles.size();
			punto = (int) Math.floor(Math.random() / ratio);		
			genoma[i] = disponibles.get(punto);
			
			disponibles.remove(punto);
		}

	}

	@Override
	public Individuo duplicate() {
		
		Individuo clon = new PlanoHospital(this.size, this.mDist, this.mflujo);
		clon.setGenoma(genoma);
		return clon;
	}
	

	@Override
	public void muteIndex(int i) {
		int punto, valor;
		double ratio;

		ratio = 1.0 / size;
		punto = (int) Math.floor(Math.random() / ratio);
		
		valor = genoma[punto];
		genoma[punto] = genoma[i];
		genoma[i] = valor;
	}

	@Override
	public double getFitness() {
		double res = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				res += mDist[i][j] * mflujo[genoma[i]][genoma[j]];
			}
		}
		return res;
	}


	@Override
	public Individuo newInstance(int n, int[][] dist, int[][]flujo) {
		return new PlanoHospital(n,dist,flujo);
	}

}
