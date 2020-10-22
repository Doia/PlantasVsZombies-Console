package Logic;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Cruces.CruceCO;
import Logic.Algoritmos.Cruces.CruceCX;
import Logic.Algoritmos.Cruces.CruceERX;
import Logic.Algoritmos.Cruces.CruceOX;
import Logic.Algoritmos.Cruces.CruceOX_PP;
import Logic.Algoritmos.Cruces.CrucePMX;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.PlanoHospital;
import Logic.Algoritmos.Mutaciones.Mutacion;
import Logic.Algoritmos.Mutaciones.MutacionBasica;
import Logic.Algoritmos.Mutaciones.MutacionHeuristica;
import Logic.Algoritmos.Selecciones.Seleccion;
import Logic.Algoritmos.Selecciones.SeleccionRanking;
import Logic.Algoritmos.Selecciones.SeleccionRuleta;
import Logic.Algoritmos.Selecciones.SeleccionTorneos;
import Logic.Algoritmos.Selecciones.SeleccionTorneosProbabilistica;
import Logic.Algoritmos.Selecciones.SeleccionUniversalEstocastica;

public class FunctionsFactories {
	
	public Cruce[] cruceList = { new CrucePMX(), new CruceOX(), new CruceOX_PP(), new CruceCO(), new CruceCX(), new CruceERX()};
	public int cruceN = cruceList.length;
	
	public Individuo[] individuoList = {new PlanoHospital()};
	public int individuoN = individuoList.length;
	
	public Mutacion[] mutacionList = { new MutacionBasica(), new MutacionHeuristica(2), new MutacionHeuristica(3)};
	public int mutacionN = mutacionList.length;
	
	public Seleccion[] seleccionList = { new SeleccionRuleta(), new SeleccionUniversalEstocastica(), new SeleccionTorneos(),
										 new SeleccionTorneosProbabilistica(), new SeleccionRanking()};
	public int seleccionN = seleccionList.length;
	
	public String[] problemList = {};
	
	public FunctionsFactories() {}
	
	public Cruce createCruce(String id, int pc) {
		Cruce cruce = null;
		
		for (int i = 0; i < cruceN; i++) {
			if (cruceList[i].equals(id)) {
				return cruceList[i].newInstance(pc);
			}
		}
		
		return cruce;
	}
	
	public Individuo createIndividuo(String id, int n, int[][]dist, int[][] flujo ) {
		Individuo indv = null;
		
		for (int i = 0; i < individuoN; i++) {
			if (individuoList[i].equals(id)) {
				return individuoList[i].newInstance(n,dist,flujo);
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

	public int getFuntionsSize() {
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
}
