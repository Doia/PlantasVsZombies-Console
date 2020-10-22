package Logic;

public abstract class Individuo<T> {

	
	
	String id;
	
	int size;
	int numVar;
	double precision;
	
	//ArrayList<T> genoma;
	
	T[] genoma;
	double[] intervalo;
	
	int longVar[];
	
	public Individuo(String id, double[] intervalo, int numVar) {
		this.id = id;
		this.intervalo = intervalo;
		this.numVar = numVar;
	}
	
	public Individuo(String id, double[] intervalo, int numVar,  double precision) {
		this.id = id;
		this.intervalo = intervalo;
		this.numVar = numVar;
		this.precision = precision;
		this.longVar = new int[numVar];
		this.size = 0;
	}

	
	public abstract void init();
	
	public abstract void setSize(double precision);
	
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
	
	public abstract double[] getFenotipo();
	
	public double round(double n) {
		
		double aux;    
		
		double prec = (1.0 / this.precision);
		
		aux = Math.round( (n * prec) ) / prec;
		
		return aux;
		
	}
	
	public abstract void muteIndex(int i);
	
	public abstract double getFitness();
	
	public abstract double getFitnessNormalizado();
	
	public abstract Individuo newInstance(double precision, int n);

	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	
	
}
	
	
	
