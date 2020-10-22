package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;
import java.util.Random;

import Logic.Algoritmos.Individuos.Individuo;

public abstract class Seleccion {
	
	String id;
	boolean maximizacion;
	
	public Seleccion(String id) {
		this.id = id;
	}
	
	public Seleccion(String id, boolean maximizacion) {
		this.id = id;
		this.maximizacion = maximizacion;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public double[] calculaProbabilidades(double fitness[]) { //true maximiza / false minimiza 
		
		double total = 0;
		double pSeleccion[] = new double[fitness.length];
		
		for (int i = 0; i < fitness.length; i++) {
			total += fitness[i];
		}
		
		double pAct = 0.0;
		for (int i = 0; i < pSeleccion.length; i++) {
			pSeleccion[i] = pAct + fitness[i]/total;
			pAct = pSeleccion[i];
		}
		
		return pSeleccion;
		
	}
	
	public double[] desplazarFitness(double fitness[], double desplazamiento) {

		for (int i = 0; i < fitness.length; i++) {
			fitness[i] += desplazamiento;
		}
		return fitness;
	}
	
	
	
	public abstract Seleccion newInstance(int participantes, double pGmejor ,boolean maximizacion);
	
	
	public abstract ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion);
}
