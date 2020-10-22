package Logic.Bloating;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.Seleccion;

public abstract class Bloating {
	
	String id;
	boolean maximizacion;
	
	public Bloating(String id) {
		this.id = id;
	}
	
	public Bloating(String id, boolean maximizacion) {
		this.id = id;
		this.maximizacion = maximizacion;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public abstract Bloating newInstance(double penalizacion, boolean maximizacion);
	
	public abstract void setFitness(ArrayList<Individuo> poblacion);
}
