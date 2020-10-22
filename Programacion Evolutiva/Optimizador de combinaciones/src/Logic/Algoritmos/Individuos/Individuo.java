package Logic.Algoritmos.Individuos;

public abstract class Individuo<T> {

	String id;
	int size;
	T[] genoma;
	
	public Individuo(String id) {
		this.id = id;
	}
	
	public Individuo(String id, int n) {
		this.id = id;
		this.size = n;
	}
	
	public abstract void init();
	
	public void setIndex(T v, int i) {
		genoma[i] = v;
	}
	
	public T getIndex(int i) {
		return genoma[i];
	}
	
	public T[] getGenoma(){
		return genoma;
	}
	
	public void setGenoma(T[] genoma) {
		for (int i = 0; i < this.size; i++) {
			this.genoma[i] = genoma[i];
		}
	}
	
	public boolean equals(String id) {
		return this.id == id;
	}
	
	public abstract Individuo duplicate();
	
	public int getSize() {
		return this.size;
	}
	
	public abstract void muteIndex(int i);
	
	public abstract double getFitness();
	
	public abstract Individuo newInstance(int n, int[][] dist, int[][]flujo);

	public String getId() {
		return this.id;
	}
	
	
	
}
	
	
	
