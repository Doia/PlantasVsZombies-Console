package Extras;

import Logic.AlgoritmoEvolutivo;
import Logic.FunctionsFactories;


public class Controller {
	
	AlgoritmoEvolutivo alg;
	FunctionsFactories factories;
	
	public Controller() {
		alg = new AlgoritmoEvolutivo();
		factories = new FunctionsFactories();
	}
	
	public void run(double precision, int Npoblacion, int generaciones, double pCruce, double pMut, double pElitismo,
					String idCruce, String idIndv, String idMutacion, String idSeleccion, int numVar) {
		
		alg.InitAlgoritmoEvolutivo(Npoblacion, generaciones, pCruce, pMut, pElitismo, 
									factories.createSeleccion(idSeleccion),factories.createCruce(idCruce),
									factories.createMutacion(idMutacion), factories.createIndividuo(idIndv, precision, numVar));

		alg.run();
		
	}

	public void addObserver(Observer observer) {
		alg.addObserver(observer);
	}

	public int getFuntionsSize() {
		
		return factories.getFuntionsSize();
	}

	public String[] getFuntionsName() {
		
		return factories.funtionsName();
	}
	public int getCrucesSize() {
		
		return factories.getCrucesSize();
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
	public int getMutacionesSize() {
		
		return factories.getMutacionesSize();
	}

	public String[] getMutacionesName() {
		
		return factories.MutacionesName();
	}
	
	public void setAlgorithm(String function,String sel,String mut,String cruce) {
		alg.setAlgorithm(function,sel,mut,cruce);
	}
	
	
	
}
