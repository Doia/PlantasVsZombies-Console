package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;
import java.util.Random;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;

public class SeleccionRuleta extends Seleccion {

	public final static String ID = "Seleccion de Ruleta";
	public final static double INF = 10E9;
	
	public SeleccionRuleta() {
		super(ID);
	}
	
	public SeleccionRuleta(boolean maximizacion) {
		super(ID, maximizacion);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		double prob;
		double fit, desp;
		double min = INF;
		double max = -INF;
		double[] pSeleccion;
		double[] fitness = new double[poblacion.size()];
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		int re;
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
		
		boolean ok;
		for (int i = 0; i < poblacion.size(); i++) {
			prob = Math.random();
			ok = false;
			int j = 0;
			while (j < poblacion.size() && !ok) {
				if (pSeleccion[j] >= prob) {
					poblacionNueva.add(poblacion.get(j).duplicate());
					ok = true;
				}
				j++;
			}
			
		}
		
		return poblacionNueva;
	}

	@Override
	public Seleccion newInstance(int participantes, double pGMejor, boolean maximizacion) {
		return new SeleccionRuleta(maximizacion);
	}

}
