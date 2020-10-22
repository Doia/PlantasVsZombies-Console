package Logic;

import Logic.Algoritmos.CruceAritmetico;
import Logic.Algoritmos.CruceDiscretoUniforme;
import Logic.Algoritmos.CruceMonopunto;
import Logic.Algoritmos.MutacionBasica;
import Logic.Algoritmos.SeleccionRuleta;
import Logic.Algoritmos.SeleccionTorneos;
import Logic.Algoritmos.SeleccionUniversalEstocastica;

public class FunctionsFactories {
	
	public Cruce[] cruceList = { new CruceMonopunto(), new CruceDiscretoUniforme(), new CruceAritmetico()};
	public int cruceN = cruceList.length;
	
	public Individuo[] individuoList = { new IndividuoF1(),new IndividuoHolderTable(), new IndividuoSchubert(),
									   new IndividuoMichalewicz(), new IndividuoMichalewiczDouble()};
	public int individuoN = individuoList.length;
	
	public Individuo[] individuoListBinario = { new IndividuoF1(),new IndividuoHolderTable(), new IndividuoSchubert(),
			   new IndividuoMichalewicz()};
	
	public Individuo[] individuoListDoubles = { new IndividuoF1(),new IndividuoHolderTable(), new IndividuoSchubert(),
			   new IndividuoMichalewicz(), new IndividuoMichalewiczDouble()};
	
	public Mutacion[] mutacionList = { new MutacionBasica()};
	public int mutacionN = mutacionList.length;
	
	public Seleccion[] seleccionList = { new SeleccionRuleta(), new SeleccionTorneos(), new SeleccionUniversalEstocastica()};
	public int seleccionN = seleccionList.length;
	
	public FunctionsFactories() {}
	
	public Cruce createCruce(String id) {
		Cruce cruce = null;
		
		for (int i = 0; i < cruceN; i++) {
			if (cruceList[i].equals(id)) {
				return cruceList[i].newInstance();
			}
		}
		
		return cruce;
	}
	
	public Individuo createIndividuo(String id, double precision, int n) {
		Individuo indv = null;
		
		for (int i = 0; i < individuoN; i++) {
			if (individuoList[i].equals(id)) {
				return individuoList[i].newInstance(precision, n);
			}
		}
		
		return indv;
	}
	
	public Mutacion createMutacion(String id) {
		Mutacion indv = null;
		
		for (int i = 0; i < mutacionN; i++) {
			if (mutacionList[i].equals(id)) {
				return mutacionList[i].newInstance();
			}
		}
		
		return indv;
	}
	
	public Seleccion createSeleccion(String id) {
		Seleccion sel = null;
		
		for (int i = 0; i < seleccionN; i++) {
			if (seleccionList[i].equals(id)) {
				return seleccionList[i].newInstance();
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
