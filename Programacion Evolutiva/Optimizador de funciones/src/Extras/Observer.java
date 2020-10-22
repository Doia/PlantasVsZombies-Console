package Extras;

import java.util.ArrayList;

import Logic.Individuo;

public interface Observer {
	public void onRegister();
	void onException(String exMsg);
	void onFinished(ArrayList<double[]> evaluation, int gen, Individuo mejor );
	void onAlgorithmChanged(String function,String sel,String mut,String cruce);
	
	
}
