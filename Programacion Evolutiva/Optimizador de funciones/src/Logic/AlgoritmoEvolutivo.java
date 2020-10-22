package Logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Extras.Observer;

public class AlgoritmoEvolutivo {

	public static double INF = 10E9;
	
	private Cruce cruce;
	private Seleccion seleccion;
	private Mutacion mutacion;
	
	
	ArrayList<Individuo> poblacion;
	ArrayList<double[]> evaluation;
	Individuo mejor, example;
	
	int Npoblacion;
	int generaciones;
	double pCruce;
	double pMut;
	double pElitismo;
	
	ArrayList<Observer> obs;
	
	public AlgoritmoEvolutivo() {
		obs = new ArrayList<Observer>();
		this.evaluation = new ArrayList<double[]>();
		this.poblacion = new ArrayList<Individuo>();
	}
	
	
	
	public void InitAlgoritmoEvolutivo(int Npoblacion, int generaciones, double pCruce, double pMut, double pElitismo,
								Seleccion sel, Cruce c, Mutacion mut, Individuo indv) {
		
		this.generaciones = generaciones;
		this.Npoblacion = Npoblacion;
		
		this.cruce = c;
		this.mutacion = mut;
		this.seleccion = sel;
		this.example = indv;
		
		this.pCruce = pCruce;
		this.pMut = pMut;
		this.pElitismo = pElitismo;
		
		this.evaluation.clear();
		
		this.evaluation.add(new double[generaciones]);  //maximo
		this.evaluation.add(new double[generaciones]);  //minimo
		this.evaluation.add(new double[generaciones]);  //mejor
		this.evaluation.add(new double[generaciones]);  //media

		
		initPoblacion(example);
	
	}
	
	public void addObserver(Observer o) {
		obs.add(o);
	}
	
	public void initPoblacion(Individuo indv) {
		Individuo clon;
		
		this.poblacion.clear();
		
		for (int i = 0; i < Npoblacion; i++) {
			clon = indv.duplicate();
			clon.init();
			this.poblacion.add(clon);
		}
		
		this.mejor = poblacion.get(0).duplicate(); 
	}
	
	public void run() {
		int pElite;
		double[] var = null;
		ArrayList<Individuo> elite = null;
		
		for (int i = 0; i < generaciones; i++) {
			
			pElite = (int) Math.floor(pElitismo * Npoblacion);
			
			if (pElite > 0) {
				elite = this.seleccionElitistas(pElite);
				//System.out.println("Fitness del elitista: "+ elite.get(0).getFitness());
			}
			
			poblacion = seleccion.doSeleccion(poblacion);
			
			poblacion = cruce.doCruce(poblacion, pCruce);
			
			poblacion = mutacion.doMutacion(poblacion, pMut);
			
			if (pElite > 0) {
				poblacion = this.ReintegraElite(elite);
			}
			
			this.evaluatePoblacion(i);

			var = mejor.getFenotipo();
			
		}
		for (Observer o: obs) {
			o.onFinished(evaluation, this.generaciones, mejor);
		}
	}
	
	public void evaluatePoblacion(int generacion){
		
		double maximo, minimo, media, aux;
		maximo = -1 * INF;
		minimo = INF;
		media = 0;
		
		for (int i = 0; i < Npoblacion; i++) {
			aux = poblacion.get(i).getFitness();
			
			if (poblacion.get(i).getFitnessNormalizado() > mejor.getFitnessNormalizado()) {
				mejor = poblacion.get(i).duplicate();
			}
			
			if (aux > maximo) {
				maximo = aux;
			}
				
			if (aux < minimo) {
				minimo = aux;
			}
			media += aux;
		}
		
		media = Math.round( (media / Npoblacion)*1000.0) / 1000.0;
		
		this.evaluation.get(0)[generacion] = maximo;
		this.evaluation.get(1)[generacion] = minimo;
		this.evaluation.get(2)[generacion] = mejor.getFitness();
		this.evaluation.get(3)[generacion] = media;
		
		
	}
	
	public ArrayList<Individuo> seleccionElitistas(int pElite) {
		
		ArrayList<Individuo> elite = new ArrayList<Individuo>();
		PriorityQueue<Individuo> q = new PriorityQueue<Individuo>(Npoblacion, new IndividuoComparator());
		
		for (Individuo indv : poblacion) {
			q.add(indv);
		}
		for (int i = 0; i < pElite; i++) {
			elite.add((q.poll()).duplicate());
		}
		return elite;
	}
	
	public ArrayList<Individuo> ReintegraElite(ArrayList<Individuo> elite) {
		double pIndv;
		int punto;
		for (int i = 0; i < elite.size(); i++) {
			pIndv = 1.0 / poblacion.size();
			punto = (int) Math.floor(Math.random()/pIndv);
			poblacion.remove(punto);
		}
		
		for (Individuo indv: elite) {
			poblacion.add(indv);
		}
		
		return poblacion; 
		
	}

	public void setAlgorithm(String function, String sel, String mut, String cruce) {
		for (Observer o: obs) {
			o.onAlgorithmChanged(function,sel, mut, cruce);
		}
	}
	
	
}
