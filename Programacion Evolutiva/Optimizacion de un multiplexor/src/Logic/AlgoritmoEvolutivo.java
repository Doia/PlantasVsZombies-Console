package Logic;

import java.time.Clock;
import java.util.ArrayList;
import java.util.PriorityQueue;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.IndividuoComparator;
import Logic.Algoritmos.Individuos.IndividuoComparatorPunt;
import Logic.Algoritmos.Mutaciones.Mutacion;
import Logic.Algoritmos.Selecciones.Seleccion;
import Logic.Bloating.Bloating;

public class AlgoritmoEvolutivo {

	public static Clock clk1, clk;
	public static long Ttotal;
	
	public static double INF = 10E15;
	
	private Cruce cruce;
	private Seleccion seleccion;
	private Mutacion mutacion;
	
	
	ArrayList<Individuo> poblacion;
	ArrayList<Individuo> elite;
	ArrayList<double[]> evaluation;
	Individuo mejor, example;
	Bloating bloating;
	
	boolean maximizacion;
	int Npoblacion;
	int generaciones;
	double pCruce;
	double pMut;
	double pElitismo;
	

	
	double precision;
	int contReinicializacion;
	
	ArrayList<Observer> obs;
	
	public AlgoritmoEvolutivo() {
		obs = new ArrayList<Observer>();
		this.evaluation = new ArrayList<double[]>();
		this.poblacion = new ArrayList<Individuo>();
	}
	
	
	
	public void InitAlgoritmoEvolutivo(int Npoblacion, int generaciones, double pCruce, double pMut, double pElitismo,	Seleccion sel,
									   Cruce c, Mutacion mut, Individuo indv, Bloating tipoBloating, double precision, boolean maximizacion) {
		
		this.precision = precision;
		this.maximizacion = maximizacion;
		
		this.generaciones = generaciones;
		this.Npoblacion = Npoblacion;
		
		this.cruce = c;
		this.mutacion = mut;
		this.seleccion = sel;
		this.example = indv;
		this.bloating = tipoBloating;
		
		this.pCruce = pCruce;
		this.pMut = pMut;
		this.pElitismo = pElitismo;
		
		for (Observer o: obs) {
			o.onStart();
		}
		
		this.evaluation.clear();
		
		this.evaluation.add(new double[generaciones]);  //máximo
		this.evaluation.add(new double[generaciones]);  //mínimo
		this.evaluation.add(new double[generaciones]);  //mejor
		this.evaluation.add(new double[generaciones]);  //media
		this.evaluation.add(new double[generaciones]);  //media tamaño poblacion
		this.evaluation.add(new double[generaciones]);  //Presion Selectiva

		
		initPoblacion(example);
	
	}
	
	public void addObserver(Observer o) {
		obs.add(o);
	}
	
	public void initPoblacion(Individuo indv) {
		Individuo clon;
		
		this.poblacion.clear();
		
		int prof = indv.getProfMax();
		
		if ( indv.getTipoInicializacion() == 2) { //ramped and half
			prof = 0;
		}
		mejor = indv.duplicate();
		for (int i = 0; i < Npoblacion; i++) {
			
			if (indv.getTipoInicializacion() == 2 && i % (Npoblacion / indv.getProfMax()) == 0) prof++;
			
			clon = indv.duplicate();
			clon.setProfMax(prof);
			clon.init();
			this.poblacion.add(clon);
			
			if (!maximizacion && clon.getPunt() < mejor.getPunt()) {
				mejor = clon.duplicate();
			}
			if (maximizacion && clon.getPunt() > mejor.getPunt()) {
				mejor = clon.duplicate();
			}
			if (clon.getPunt() == mejor.getPunt() && clon.getArbol().getNumNodos() < mejor.getArbol().getNumNodos()) {
				mejor = clon.duplicate();
			}
			
		}

		this.mejor = poblacion.get(0).duplicate();
		bloating.setFitness(poblacion);
	}
	
	public void run() {
		int pElite;
		elite = null;
		
		boolean ok = true;
		this.contReinicializacion = 0;
		
		for (int i = 0; i < generaciones; i++) {
			
			pElite = (int) Math.floor(pElitismo * Npoblacion);
			
			if (pElite > 0) {
				elite = this.seleccionElitistas(pElite);
			}
			
			poblacion = seleccion.doSeleccion(poblacion);
			
			poblacion = cruce.doCruce(poblacion, pCruce);
			
			poblacion = mutacion.doMutacion(poblacion, pMut);
			
			if (pElite > 0) {
				poblacion = this.ReintegraElite(elite);
			}
			
			this.evaluatePoblacion(i);
			
			for (Observer o: obs) {
				o.onGeneration(evaluation, i, mejor);
			}
		}
		for (Observer o: obs) {
			o.onFinished(evaluation, this.generaciones, mejor);
		}
		
	}
	
	public void evaluatePoblacion(int generacion){
		
		double maximo, minimo, media, aux, presionSelectiva, mediaTamPoblacion;
		
		double[] fitness = new double[this.Npoblacion];
		
		maximo = -1 * INF;
		minimo = INF;
		media = 0;
		mediaTamPoblacion = 0;
		for (int i = 0; i < Npoblacion; i++) {
			if (poblacion.get(i).getRecienModificado()) {
				poblacion.get(i).eliminaIntrones(); //Eliminamos el codigo inutuil y evaluamos
				poblacion.get(i).evalua();
			}
		}
		
		bloating.setFitness(poblacion); //Calculamos un fitnees segun el control de bloating que tengamos
		
		for (int i = 0; i < Npoblacion; i++) {

			aux = poblacion.get(i).getPunt();
			
			fitness[i] = aux;
			
			if (!maximizacion && poblacion.get(i).getPunt() < mejor.getPunt()) {
				mejor = poblacion.get(i).duplicate();
			}
			if (maximizacion && poblacion.get(i).getPunt() > mejor.getPunt()) {
				mejor = poblacion.get(i).duplicate();
			}
			if (poblacion.get(i).getPunt() == mejor.getPunt() && poblacion.get(i).getArbol().getNumNodos() < mejor.getArbol().getNumNodos()) {
				mejor = poblacion.get(i).duplicate();
			}
			
			if (aux > maximo) {
				maximo = aux;
			}
				
			if (aux < minimo) {
				minimo = aux;
			}
			media += aux;
			mediaTamPoblacion += poblacion.get(i).getArbol().getNumNodos();
		}
			
		media = round(media / Npoblacion);
		mediaTamPoblacion = round(mediaTamPoblacion / Npoblacion);
		presionSelectiva = round (calculaPresionSelectiva(fitness, minimo, maximo));
		
		if (presionSelectiva <= 1) this.contReinicializacion++;
		else this.contReinicializacion = 0; 
		if (this.contReinicializacion >= 1) {
			System.out.println("R");
			int  pElite = (int) Math.floor(pElitismo * Npoblacion);
			if (pElite > 0) {
				elite = this.seleccionElitistas(pElite);
			}
			
			reinicializaPoblacion(0.3);
			
			if (pElite > 0) {
				poblacion = this.ReintegraElite(elite);
			}

		}
		
		mejor.eliminaIntrones();
		
		this.evaluation.get(0)[generacion] = maximo;
		this.evaluation.get(1)[generacion] = minimo;
		this.evaluation.get(2)[generacion] = mejor.getPunt();
		this.evaluation.get(3)[generacion] = media;
		this.evaluation.get(4)[generacion] = mediaTamPoblacion;
		this.evaluation.get(5)[generacion] = presionSelectiva;
		
	}
	
	public void reinicializaPoblacion(double pInit) {
		for (int i = 0; i < poblacion.size();i++) {
			if ( Math.random() <= pInit) {
				poblacion.get(i).init();
			}
		}
		bloating.setFitness(poblacion);
	}
	
	public double calculaPresionSelectiva(double[] fitness, double minimo, double maximo) {
		double mediaDesp, desp, mejor;
		
		if (maximizacion) {desp = -Math.abs(minimo + 1); mejor = -1*INF;}
		else {desp = -Math.abs(maximo + 1); mejor = INF;}

		mediaDesp = 0;
		for ( int i = 0; i < Npoblacion; i++) {
			fitness[i] += desp;
			mediaDesp += fitness[i];
			if (maximizacion) mejor = Math.max(mejor, fitness[i]);
			else mejor = Math.min(mejor, fitness[i]);
		}
		
		mediaDesp /= Npoblacion;
		
		return mejor / mediaDesp;
	}
	
	public ArrayList<Individuo> seleccionElitistas(int pElite) {
		
		ArrayList<Individuo> elite = new ArrayList<Individuo>();
		PriorityQueue<Individuo> q = new PriorityQueue<Individuo>(Npoblacion, new IndividuoComparatorPunt(this.maximizacion));
		
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
			poblacion.add(indv.duplicate());
		}
		
		return poblacion; 
		
	}

	public void setAlgorithm(String problema, String sel, String mut, String cruce, String tCB, String tIni) {
		for (Observer o: obs) {
			o.onAlgorithmChanged(problema,sel, mut, cruce, tCB, tIni);
		}
	}
	
	private double round(double n) {
		double ratio = 1.0 / this.precision;
		return (int)(n*ratio) / ratio;
	}
	
	public void onException(String msg) {
		for (Observer o: obs) {
			o.onException(msg);
		}
	}
	
}
