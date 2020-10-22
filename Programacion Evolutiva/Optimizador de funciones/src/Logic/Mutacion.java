package Logic;

import java.util.ArrayList;

public abstract class Mutacion {
	
	String id;
	
	public Mutacion(String id) {
		this.id = id;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	public String getId() {
		return this.id;
	}
	
	public abstract Mutacion newInstance();
	
	public abstract ArrayList<Individuo> doMutacion(ArrayList<Individuo> poblacion, double pMut);
}
