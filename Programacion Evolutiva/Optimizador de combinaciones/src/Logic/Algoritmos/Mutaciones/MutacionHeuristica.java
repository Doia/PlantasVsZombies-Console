package Logic.Algoritmos.Mutaciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;

public class MutacionHeuristica extends Mutacion {

	public final static String ID = "Mutacion Heurística";
	
	public final static int[][] permutaciones2 = {{0,1},{1,0}};
	
	public final static int[][] permutaciones3 = {{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,1,0},{2,0,1}};
	
	boolean maximizacion;
	int perm;
	
	public MutacionHeuristica(int permutaciones) {
		super(ID + " con " + permutaciones + " permutaciones");
		perm = permutaciones;
	}
	
	public MutacionHeuristica(int permutaciones, boolean maximizacion) {
		super(ID + " con " + permutaciones + " permutaciones");
		this.maximizacion = maximizacion;
		perm = permutaciones;
	}

	@Override
	public Mutacion newInstance(boolean maximizacion) {
		return new MutacionHeuristica(this.perm, maximizacion);
	}

	@Override
	public void mutaIndividuo(Individuo indv) {
		
		ArrayList<Integer> puntos = calculaPuntos(1.0/indv.getSize());	
		PriorityQueue<Individuo> q = new PriorityQueue<Individuo>(new IndividuoComparator(this.maximizacion));
		Individuo aux;
		int[][] permutaciones = getPermutaciones();
		
		for (int i = 0; i < permutaciones.length; i++) {
			aux = indv.duplicate();
			for (int j = 0; j < permutaciones[i].length; j++) {
				aux.setIndex(indv.getIndex(puntos.get(permutaciones[i][j])), puntos.get(j));
			}
			q.add(aux);
		}
		aux = q.poll();
		indv.setGenoma(aux.getGenoma());
	}
	
	private ArrayList<Integer> calculaPuntos(double ratio){
		ArrayList<Integer> list = new ArrayList<Integer>();
		int pos;
		for (int i = 0; i < perm; i++) {
			pos = (int) Math.floor(Math.random() / ratio);
			while (list.contains(pos)) {pos = (int) Math.floor(Math.random() / ratio);}
			list.add(pos);
		}
		return list;
		
	}
	
	public int[][] getPermutaciones(){
		switch (this.perm) {
		case 2:
			return permutaciones2;
		case 3:
			return permutaciones3;
		default:
			return null;
		}
		
	}

}
