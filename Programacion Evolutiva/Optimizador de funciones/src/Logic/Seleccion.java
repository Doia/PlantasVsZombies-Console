package Logic;

import java.util.ArrayList;
import java.util.Random;

public abstract class Seleccion {
	
	String id;
	
	public Seleccion(String id) {
		this.id = id;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public abstract Seleccion newInstance();
	
	
	public abstract ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion);
}
