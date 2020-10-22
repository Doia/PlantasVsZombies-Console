package Logic.Algoritmos.Individuos;

import java.time.Clock;
import java.util.Random;

import Logic.AlgoritmoEvolutivo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;

public class Individuo {
	
	public static final String ID = "Individuo Multiplexor";
	
	
	private Arbol arbol;
	private double fitness;
	//private double fitness_bruto; //Aptitud antes de transformarla
	private double punt;
	private double puntAcum;
	private String fenotipo;
	
	private Problema problema;
	private int tipoCreacion;
	private int profMax;
	
	private boolean recienModificado;
	
	private String id;
	
	public Individuo() {
			id = ID;
	}
	
	public Individuo(Arbol a, double fitness,/* double fitnessBruto,*/ double punt, double puntAcum, String fenotipo, int tipoCreacion, int prof, Problema problema, boolean recienModificado) {
		this.arbol = a; this.fitness = fitness;// this.fitness_bruto = fitness;
		this.punt = punt; this.puntAcum = puntAcum;
		this.fenotipo = fenotipo; this.id = ID; this.profMax = prof;
		this.tipoCreacion = tipoCreacion; this.problema = problema; this.recienModificado = recienModificado;
	}
	
	public Individuo(int profundidad, int tipoCreacion, Problema problema) {
		
		id = ID;
		this.problema = problema;
		this.tipoCreacion = tipoCreacion;
		this.profMax = profundidad;
		this.punt = 0;
		this.recienModificado = true;
		this.fenotipo = "Individuo Vacio";
		
	}
	
	public void init() {
		
		arbol = new Arbol(profMax);
		switch(tipoCreacion){
			case 0:
				arbol.inicializacionCreciente(0,0);
				break;
			case 1:
				arbol.inicializacionCompleta(0,0);
				break;
			case 2:
				int ini = new Random().nextInt(2);
				if(ini == 0) arbol.inicializacionCreciente(0,0);
				else arbol.inicializacionCompleta(0,0);
				break;
		}
		evalua();
	}
	
	public void eliminaIntrones() {
		arbol = problema.simplificaArbol(arbol);	
		while (arbol.getNumHijos() == 0) {
			init();
			arbol = problema.simplificaArbol(arbol);
		}
	}
	
	public void evalua() {	
			this.arbol.recalculaNumNodosYprof(0);
			this.punt = problema.evalua(arbol);
			//this.fitness = punt;// - 0.05 * this.arbol.getNumNodos();
			this.fenotipo = arbol.toString();
			recienModificado = false;
	}

	
	public boolean equals(String id) {
		return this.id == id;
	}
	
	public Individuo duplicate() {
		return new Individuo(this.arbol.copia(), this.fitness, /*this.fitness_bruto,*/ this.punt, this.puntAcum, this.fenotipo, this.tipoCreacion, this.profMax, this.problema, this.recienModificado);
		
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	public double getPunt() {
		return this.punt;
	}
	
	public Arbol getArbol() {
		return arbol;
	}
	
	public int getTipoInicializacion() {
		return this.tipoCreacion;
	}
	
	public int getProfMax() {
		return this.profMax;
	}
	
	public boolean getRecienModificado() {
		return this.recienModificado;
	}
	
	public Individuo newInstance(int profundidad, int tipoCreacion, Problema problema) {
		return new Individuo(profundidad, tipoCreacion, problema);
	}

	public String getId() {
		return this.id;
	}
	
	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}
	
	public void setRecienModificado(boolean recienModificado) {
		this.recienModificado = recienModificado;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void setFenotipo() {		
		this.fenotipo = arbol.toString();
	}
	
	public void setProfMax(int prof) {
		this.profMax = prof;
	}
	
	public String toString() {		
		return this.fenotipo;
	}

	
}

