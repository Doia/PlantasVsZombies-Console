package Logic.Problemas;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class HebraEvaluaMultiplexor extends Thread {
	
	int a;
	ArrayList<char[]> caso;
	boolean[] sol;
	Arbol arbol;
	AtomicInteger aciertos;
	
	
	int ini,fin;
	
	public HebraEvaluaMultiplexor(Arbol arbol, ArrayList<char[]> caso, boolean[] sol , AtomicInteger aciertos, int ini, int fin) {
		this.arbol = arbol.copia();
		this.caso = caso;
		this.sol = sol;
		this.aciertos = aciertos;
		this.ini = ini;
		this.fin = fin;
	}

	public void run() {
		int i = ini;
		while (i <fin) {
			//if (evaluaCaso(arbol,this.caso.get(i)) == sol[i])  aciertos.incrementAndGet();
			i++;
		}
	}
	
}
