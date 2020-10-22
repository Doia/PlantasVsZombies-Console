package Logic.Bloating;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.Seleccion;

public class PenalizacionBienFundamentada extends Bloating {

	public static final String ID = "Penalizacion Bien Fundamentada";
	
	public PenalizacionBienFundamentada() {
		super(ID);
	}
	public PenalizacionBienFundamentada(boolean maximizacion) {
		super(ID, maximizacion);
	}

	@Override
	public void setFitness(ArrayList<Individuo> poblacion) {
		double covIF, varI;
		double C;
		
		covIF = covarianza(poblacion);
		varI = varianza(poblacion);
		C = covIF / varI;

		if (maximizacion) {
			C *= -1;
		}
		
		for (Individuo indv : poblacion) {
			indv.setFitness(indv.getPunt() + (C*indv.getArbol().getNumNodos()));
		}
		
	}
	@Override
	public Bloating newInstance(double penalizacion, boolean maximizacion) {
		return new PenalizacionBienFundamentada(maximizacion);
	}
	
	
	public double covarianza(ArrayList<Individuo> poblacion) {
		double ret = 0;
		double mediaTam = 0;
		double mediaPunt = 0;
		for (Individuo indv : poblacion) {
			mediaTam += indv.getArbol().getNumNodos();
			mediaPunt += indv.getPunt();
		}
		mediaTam /= poblacion.size(); mediaPunt /= poblacion.size();
		
		for (Individuo indv : poblacion) {
			ret += (indv.getArbol().getNumNodos()-mediaTam)*(indv.getPunt()-mediaPunt);
		}
		ret /= poblacion.size();
		return ret;
	}
	
	public double varianza(ArrayList<Individuo> poblacion) {
		double ret = 0;
		double mediaPunt = 0;
		for (Individuo indv : poblacion) {
			mediaPunt += indv.getPunt();
		}
		mediaPunt /= poblacion.size();
		
		for (Individuo indv : poblacion) {
			ret += Math.pow((indv.getPunt()-mediaPunt), 2);
		}
		ret /= poblacion.size();
		return ret;
	}

}
