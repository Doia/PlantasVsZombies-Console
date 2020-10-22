package Logic;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public interface Observer {
	public void onRegister();
	void onException(String exMsg);
	void onFinished(ArrayList<double[]> evaluation, int gen, Individuo mejor );
	void onStart();
	void onGeneration(ArrayList<double[]> evaluation, int gen, Individuo mejor );
	void onAlgorithmChanged(String fileName,String sel,String mut,String cruce);
	
	
}
