package Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Exceptions.NoDataLoaded;

public class DataLoader {

	String fileName;
	int tamMatriz;
	int[][] mDistancias;
	int[][] mFlujo;
	boolean dataOk;
	
	public DataLoader() {
		
		dataOk = false;
		this.fileName = "No hay archivos cargados";
		this.tamMatriz = 0;
		this.mDistancias = null;
		this.mFlujo = null;
	}
	
	public void loadData(File f) throws Exception {
		
		dataOk = false;
		this.fileName = "DATOS DAÑADOS!!";
		
		mDistancias = null;
		mFlujo = null;
		
		Scanner s = new Scanner(f);
		
		tamMatriz = s.nextInt();
		
		mDistancias = new int[tamMatriz][tamMatriz];
		mFlujo = new int[tamMatriz][tamMatriz];
		
		for (int i = 0; i < tamMatriz;i++) {
			for (int j = 0; j < tamMatriz;j++) {
				mDistancias[i][j] = s.nextInt();
			}
		}
		
		for (int i = 0; i < tamMatriz;i++) {
			for (int j = 0; j < tamMatriz;j++) {
				mFlujo[i][j] = s.nextInt();
			}
		}
		this.fileName = f.getName();
		dataOk = true;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public boolean isDataOk() {
		return dataOk;
	}
	
	int getTamMatriz() throws NoDataLoaded{
		if (tamMatriz == 0) {
			throw new NoDataLoaded("Tamaño de las Matrices nulos");
		}
		return tamMatriz;
	}
	
	int[][] getDistancias() throws NoDataLoaded{
		if (mDistancias == null) {
			throw new NoDataLoaded("La Matriz de distancias no esta cargada");
		}
		return mDistancias;
	}
	int[][] getFlujo() throws NoDataLoaded{
		if (mFlujo == null) {
			throw new NoDataLoaded("La Matriz de flujo no esta cargada");
		}
		return mFlujo;
	}
	
	
}
