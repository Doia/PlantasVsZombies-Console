package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;

public class SeleccionRanking extends Seleccion {


	public final static String ID = "Seleccion de Ranking";
	public final static double INF = 10E9;
	
	public SeleccionRanking() {
		super(ID);
	}
	
	public SeleccionRanking(boolean maximizacion) {
		super(ID, maximizacion);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		
		double total, presionSelectiva, desp;
		double prob;
		double fit;
		double min = INF;
		double max = -INF;
		double[] pSeleccion;
		double[] fitness = new double[poblacion.size()];
		
		ArrayList<Individuo> poblacionOrdenada = new ArrayList<Individuo>();
		ArrayList<Individuo> poblacionNueva = new ArrayList<Individuo>();
		
		
		for ( int i = 0; i < poblacion.size(); i++) {
			fit = poblacion.get(i).getFitness();
			min = Math.min(min, fit);
			max = Math.max(max, fit);
			fitness[i] = fit;
		}
		
		if (this.maximizacion) desp = -Math.abs(min+1);
		else  desp =  -Math.abs(max+1);
		fitness = desplazarFitness(fitness, desp);
		
		//ordena la poblacion
		PriorityQueue<Individuo> q = new PriorityQueue<Individuo>(poblacion.size(), new IndividuoComparator(this.maximizacion));
		for (int i = 0; i < poblacion.size(); i++) {
			q.add(poblacion.get(i));
		}
		for (int i = 0; i < poblacion.size(); i++) {
			poblacionOrdenada.add(q.poll());
		}
		
		//total y presion selectiva
		total = 0;
		for (int i = 0; i < poblacion.size(); i++) {
			total += fitness[i];
		}
		
		presionSelectiva = (poblacionOrdenada.get(0).getFitness() + desp)  / (total/poblacion.size());
		
		pSeleccion = this.calculaProbabilidades(poblacion.size(), Math.min(presionSelectiva, 2.0));
		
		
		boolean ok;
		for (int i = 0; i < poblacion.size(); i++) {
			prob = Math.random();
			ok = false;
			int j = 0;
			while (j < poblacion.size() && !ok) {
				if (pSeleccion[j] >= prob) {
					poblacionNueva.add(poblacionOrdenada.get(j).duplicate());
					ok = true;
				}
				j++;
			}
			
		}
		
		return poblacionNueva;
	}
	
	private double[] calculaProbabilidades(int n, double b) { // b = presion selectiva
		double[] res = new double[n];
		double total = 0.0;
		for (int i = 0; i < n; i++) {
			total += (1.0/n) * ( b - (2 * (b-1)) * ( (double)(i - 1) / (n - 1) ) );
			res[i] = total;
		}
		
		return res;
	}

	@Override
	public Seleccion newInstance(int participantes, double pGMejor, boolean maximizacion) {
		return new SeleccionRanking(maximizacion);
	}

}
