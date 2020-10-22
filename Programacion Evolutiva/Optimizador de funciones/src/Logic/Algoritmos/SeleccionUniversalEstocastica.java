package Logic.Algoritmos;

import java.util.ArrayList;
import java.util.Random;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;
import Logic.Seleccion;

public class SeleccionUniversalEstocastica extends Seleccion {

	public final static String ID = "Seleccion Universal Estocástica";
	public final static double INF = 10E9;
	
	public SeleccionUniversalEstocastica() {
		super(ID);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {

		double pAct;
		double fit;
		double min = INF;
		double total = 0;
		double[] fitness = new double[poblacion.size()];
		double[] pSeleccion = new double[poblacion.size()];
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		
		double distMarcas = 1.0/poblacion.size();		
		
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
	public Seleccion newInstance() {
		// TODO Auto-generated method stub
		return new SeleccionUniversalEstocastica();
	}

}
