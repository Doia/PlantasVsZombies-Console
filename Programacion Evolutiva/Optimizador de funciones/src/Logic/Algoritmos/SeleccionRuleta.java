package Logic.Algoritmos;

import java.util.ArrayList;
import java.util.Random;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;
import Logic.Seleccion;

public class SeleccionRuleta extends Seleccion {

	public final static String ID = "Seleccion de Ruleta";
	public final static double INF = 10E9;
	
	public SeleccionRuleta() {
		super(ID);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		double prob;
		double pAct;
		double fit;
		double min = INF;
		double total = 0;
		double[] fitness = new double[poblacion.size()];
		double[] pSeleccion = new double[poblacion.size()];
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		
		for ( int i = 0; i < poblacion.size(); i++) {
			fit = poblacion.get(i).getFitnessNormalizado();
			min = Math.min(min, fit );
			fitness[i] = fit;
			total += fit;
		}
		
		if (min < 0) {
			total = 0;
			for ( int i = 0; i < poblacion.size(); i++) {
				fitness[i] -= min;
				total += fitness[i];
			}
		}
		
		pAct = 0.0;
		for (int i = 0; i < poblacion.size(); i++) {
			pSeleccion[i] = pAct + fitness[i]/total;
			pAct = pSeleccion[i];
		}
		
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
	public Seleccion newInstance() {
		// TODO Auto-generated method stub
		return new SeleccionRuleta();
	}

}
