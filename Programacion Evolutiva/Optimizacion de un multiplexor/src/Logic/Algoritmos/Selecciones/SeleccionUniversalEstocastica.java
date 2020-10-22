package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;
import java.util.Random;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;

public class SeleccionUniversalEstocastica extends Seleccion {

	public final static String ID = "Seleccion Universal Estocástica";
	public final static double INF = 10E9;
	
	public SeleccionUniversalEstocastica() {
		super(ID);
	}
	
	public SeleccionUniversalEstocastica(boolean maximizacion) {
		super(ID,maximizacion);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {

		double fit, desp;
		double max = -INF;
		double min = INF;

		double[] pSeleccion;
		double[] fitness = new double[poblacion.size()];
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		
		double distMarcas = 1.0/poblacion.size();		
		
		for ( int i = 0; i < poblacion.size(); i++) {
			fit = poblacion.get(i).getFitness();
			min = Math.min(min, fit);
			max = Math.max(max, fit);
			fitness[i] = fit;
		}
		
		if (this.maximizacion) desp = -Math.abs(min+1);
		else  desp =  -Math.abs(max+1);
		fitness = desplazarFitness(fitness, desp);
		
		pSeleccion = this.calculaProbabilidades(fitness);
		
		double ind= 0;
		int i=0;
		while ( ind <= 1.0) {
			if (pSeleccion[i]>=ind) {
				poblacionNueva.add(poblacion.get(i).duplicate());
				ind += distMarcas;
			}
			else {
				i++;
			}
			
		}
		
		return poblacionNueva;
	}

	@Override
	public Seleccion newInstance(int participantes, double pGMejor, boolean maximizacion) {
		// TODO Auto-generated method stub
		return new SeleccionUniversalEstocastica(maximizacion);
	}

}
