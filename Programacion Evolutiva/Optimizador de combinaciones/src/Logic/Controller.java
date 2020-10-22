package Logic;

import java.io.File;
import java.io.FileNotFoundException;

import Exceptions.NoDataLoaded;
import Logic.Algoritmos.Individuos.Individuo;

public class Controller {
	
	AlgoritmoEvolutivo alg;
	FunctionsFactories factories;
	DataLoader dataloader;
	
	public Controller() {
		alg = new AlgoritmoEvolutivo();
		factories = new FunctionsFactories();
		dataloader = new DataLoader();
	}
	
	public void run(double precision, int Npoblacion, int generaciones, double pCruce, double pMut, double pElitismo,
					String idCruce, String idMutacion, String idSeleccion, int participantes,Double pGMejor, boolean maximizacion) throws Exception {
		
		String idIndv = "Plano Hospital";
		Individuo indv = null;

		indv = factories.createIndividuo(idIndv, dataloader.getTamMatriz(), dataloader.getDistancias(), dataloader.getFlujo());
		
		
		alg.InitAlgoritmoEvolutivo(Npoblacion, generaciones, pCruce, pMut, pElitismo, factories.createSeleccion(idSeleccion, participantes, pGMejor, maximizacion),
								   factories.createCruce(idCruce, (int) 0.2*dataloader.getTamMatriz()),factories.createMutacion(idMutacion,  maximizacion),
								   indv, precision, maximizacion);

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
	
	public String getFileName() {
		return dataloader.getFileName();
	}
	
	public void setAlgorithm(String function,String sel,String mut,String cruce) {
		alg.setAlgorithm(function,sel,mut,cruce);
	}

	public void loadData(File file) throws Exception {
		dataloader.loadData(file);
	}
	
	public boolean isDataOk() {
		return dataloader.isDataOk();
	}
	public void onException(String msg) {
		alg.onException(msg);
	}
	
	
	
}
