package Logic;

import java.io.File;
import java.io.FileNotFoundException;

import Exceptions.NoDataLoaded;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Problemas.Problema;

public class Controller {
	
	AlgoritmoEvolutivo alg;
	FunctionsFactories factories;
	Problema problema;
	
	public Controller() {
		alg = new AlgoritmoEvolutivo();
		factories = new FunctionsFactories();
		problema = null;
	}
	
	public void run(double precision, int Npoblacion, int generaciones, double pCruce, double pMut, double pElitismo,
					String idCruce, String idMutacion, String idSeleccion, int participantes,Double pGMejor, boolean maximizacion,
					String problem, String tipoInicializacion, String tipoBloating, int maxProf, int nInput, boolean useIF, double penalizacion) /*throws Exception*/ {
		
		String idIndv = factories.individuoList[0].getId();
		String idProblema = factories.problemaList[0].getId();
		Individuo indv = null;
		int tipo = getTipoInicializacion(tipoInicializacion);
		problema = factories.createProblema(idProblema, useIF, nInput);
		indv = factories.createIndividuo(idIndv, maxProf , tipo, problema);
		indv.init();
		
		alg.InitAlgoritmoEvolutivo(Npoblacion, generaciones, pCruce, pMut, pElitismo, factories.createSeleccion(idSeleccion, participantes, pGMejor, maximizacion),
								   factories.createCruce(idCruce, (int) 0.2*2, maximizacion),factories.createMutacion(idMutacion,  maximizacion),
								   indv, factories.createBloating(tipoBloating, penalizacion, maximizacion),precision, maximizacion);

		alg.run();
		
		
	}

	private int getTipoInicializacion(String tipoInicializacion) {
		if (tipoInicializacion.equals("Inicializacion Creciente")) {
			return 0;
		}
		else if (tipoInicializacion.equals("Inicializacion Completa")) {
			return 1;
		}
		else if (tipoInicializacion.equals("Inicializacion Ramped and Half")) {
			return 2;
		}
		else {
			return 1;
		}
	}

	public void addObserver(Observer observer) {
		alg.addObserver(observer);
	}

	public int getIndividuosSize() {
		return factories.getIndividuosSize();
	}
	
	public int getProblemsSize() {
		return factories.getProblemsSize();
	}

	public String[] getFuntionsName() {
		return factories.funtionsName();
	}
	public int getCrucesSize() {
		return factories.getCrucesSize();
	}
	
	public String[] getProblemsName() {
		return factories.getProblemsName();
	}

	public String[] getCrucesName() {
		return factories.CrucesName();
	}
	public int getSeleccionesSize() {
		return factories.getSeleccionesSize();
	}

	public String[] getSeleccionesName() {
		return factories.SeleccionesName();
	}
	
	public int getBloatingSize() {
		return factories.getBloatingSize();
	}

	public String[] getBloatingName() {
		return factories.getBloatingName();
	}
	
	public int getMutacionesSize() {
		return factories.getMutacionesSize();
	}

	public String[] getMutacionesName() {
		return factories.MutacionesName();
	}
	
	public String[] getInicializacionName() {
		return this.factories.getInicializacionName();
	}
	
	public void setAlgorithm(String problema,String sel,String mut,String cruce, String tBloating, String tIni) {
		alg.setAlgorithm(problema,sel,mut,cruce, tBloating, tIni);
	}
	
	public void onException(String msg) {
		alg.onException(msg);
	}
	
	
	
}
