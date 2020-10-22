package Logic;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Cruces.CruceClasico;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Mutaciones.Mutacion;
import Logic.Algoritmos.Mutaciones.MutacionContraccion;
import Logic.Algoritmos.Mutaciones.MutacionDeArbol;
import Logic.Algoritmos.Mutaciones.MutacionExpansion;
import Logic.Algoritmos.Mutaciones.MutacionFuncionalSimple;
import Logic.Algoritmos.Mutaciones.MutacionHoist;
import Logic.Algoritmos.Mutaciones.MutacionPermutacion;
import Logic.Algoritmos.Mutaciones.MutacionSimple;
import Logic.Algoritmos.Mutaciones.MutacionTerminalSimple;
import Logic.Algoritmos.Selecciones.Seleccion;
import Logic.Algoritmos.Selecciones.SeleccionRanking;
import Logic.Algoritmos.Selecciones.SeleccionRuleta;
import Logic.Algoritmos.Selecciones.SeleccionTorneos;
import Logic.Algoritmos.Selecciones.SeleccionTorneosProbabilistica;
import Logic.Algoritmos.Selecciones.SeleccionUniversalEstocastica;
import Logic.Bloating.Bloating;
import Logic.Bloating.PenalizacionBienFundamentada;
import Logic.Bloating.SinBloating;
import Logic.Bloating.Tarpeian;
import Logic.Problemas.Problema;
import Logic.Problemas.ProblemaMultiplexor;

public class FunctionsFactories {
	
	public Cruce[] cruceList = {new CruceClasico()};
	public int cruceN = cruceList.length;
	
	public Problema[] problemaList = {new ProblemaMultiplexor()};
	public int problemaN = problemaList.length;
	
	public Individuo[] individuoList = {new Individuo()};
	public int individuoN = individuoList.length;
	
	public Mutacion[] mutacionList = {new MutacionSimple(), new MutacionFuncionalSimple(), new MutacionTerminalSimple(), new MutacionPermutacion(),
									  new MutacionHoist(), new MutacionDeArbol(), new MutacionExpansion(), new MutacionContraccion() };
	public int mutacionN = mutacionList.length;
	
	public Seleccion[] seleccionList = { new SeleccionRuleta(), new SeleccionUniversalEstocastica(), new SeleccionTorneos(),
										 new SeleccionTorneosProbabilistica(), new SeleccionRanking()};
	public int seleccionN = seleccionList.length;
	
	public Bloating[] bloatingList = { new SinBloating(), new Tarpeian(), new PenalizacionBienFundamentada()};
	public int bloatingN = bloatingList.length;
	
	public String[] problemList = {};
	
	public FunctionsFactories() {}
	
	public Cruce createCruce(String id, int pc, boolean maximizacion) {
		Cruce cruce = null;
		
		for (int i = 0; i < cruceN; i++) {
			if (cruceList[i].equals(id)) {
				return cruceList[i].newInstance(pc, maximizacion);
			}
		}
		
		return cruce;
	}
	
	public Individuo createIndividuo(String id, int profundidad, int tipoCreacion, Problema problema) {
		Individuo indv = null;
		
		for (int i = 0; i < individuoN; i++) {
			if (individuoList[i].equals(id)) {
				return individuoList[i].newInstance(profundidad, tipoCreacion, problema);
			}
		}
		
		return indv;
	}
	
	public Mutacion createMutacion(String id,  boolean maximizacion ) {
		Mutacion indv = null;
		
		for (int i = 0; i < mutacionN; i++) {
			if (mutacionList[i].equals(id)) {
				return mutacionList[i].newInstance(maximizacion);
			}
		}
		
		return indv;
	}
	
	public Seleccion createSeleccion(String id, int participantes, double pGMejor, boolean maximizacion) {
		Seleccion sel = null;
		
		for (int i = 0; i < seleccionN; i++) {
			if (seleccionList[i].equals(id)) {
				return seleccionList[i].newInstance(participantes, pGMejor, maximizacion);
			}
		}
		
		return sel;	
	}
	
	public Problema createProblema(String id, boolean useIf, int nInput) {
		Problema problema = null;
		
		for (int i = 0; i < problemaN; i++) {
			if (problemaList[i].equals(id)) {
				return problemaList[i].newInstance(useIf, nInput);
			}
		}
		
		return problema;	
	}
	
	public Bloating createBloating(String id, double penalizacion, boolean maximizacion) {
		Bloating bloating = null;
		
		for (int i = 0; i < bloatingN; i++) {
			if (bloatingList[i].equals(id)) {
				return bloatingList[i].newInstance(penalizacion, maximizacion);
			}
		}
		return bloating;
	}
	
	public String[] getBloatingName() {
		String[] str = new String[bloatingN];
		
		for (int i = 0; i < bloatingN; i++) {
			str[i] = bloatingList[i].getId();
		}
		
		return str;
	}
	
	public int getBloatingSize() {
		return bloatingN;
	}
	
	public String[] getProblemsName() {
		String[] str = new String[problemaN];
		
		for (int i = 0; i < problemaN; i++) {
			str[i] = problemaList[i].getId();
		}
		
		return str;
	}
	public int getProblemsSize() {
		return problemaN;
	}

	public int getIndividuosSize() {
		return individuoN;
	}

	public String[] funtionsName() {
		String[] str = new String[individuoN];
		
		for (int i = 0; i < individuoN; i++) {
			str[i] = individuoList[i].getId();
		}
		
		return str;
	}
	public int getCrucesSize() {
		return cruceN;
	}

	public String[] CrucesName() {
		
		String[] str = new String[cruceN];
		
		for (int i = 0; i < cruceN; i++) {
			str[i] = cruceList[i].getId();
		}
		
		return str;
	}
	public int getSeleccionesSize() {
		return cruceN;
	}

	public String[] SeleccionesName() {
		String[] str = new String[seleccionN];
		
		for (int i = 0; i < seleccionN; i++) {
			str[i] = seleccionList[i].getId();
		}
		
		return str;
	}
	public int getMutacionesSize() {
		return cruceN;
	}

	public String[] MutacionesName() {
		String[] str = new String[mutacionN];
		
		for (int i = 0; i < mutacionN; i++) {
			str[i] = mutacionList[i].getId();
		}
		
		return str;
	}
	
	public String[] getInicializacionName() {
		String inicializacion[] = {"Inicializacion Completa","Inicializacion Creciente","Inicializacion Ramped and Half"};
		return inicializacion;
	}
	
}
