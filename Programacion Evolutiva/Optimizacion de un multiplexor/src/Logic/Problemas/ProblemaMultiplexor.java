package Logic.Problemas;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import Logic.Algoritmos.Individuos.Arbol.Arbol;

public class ProblemaMultiplexor extends Problema {

	public static final String ID = "Problema Multiplexor";
	private final String[] funcionesIF = {"AND", "OR", "NOT", "IF"};
	private final String[] funcionesNotIF = {"AND", "OR", "NOT"};
	
	public boolean[] sol;
	public ArrayList<char[]>solCases;
	
	int nInput;
	boolean useIF;
	
	public ProblemaMultiplexor() {
		super(ID);
	}
	
	public ProblemaMultiplexor(boolean useIF, int nInput){
		super(ID);
		this.useIF = useIF;
		this.nInput = nInput;
		this.sol = null;
		solCases = null;
		if (useIF) Problema.funciones = funcionesIF; 
		else  Problema.funciones = funcionesNotIF; 
		Problema.terminales = creaTerminales(nInput);
		
		if (nInput < 4) {
			creaSolution();
		}
		
	}

	public Problema newInstance(boolean useIf, int nInput) {
		return new ProblemaMultiplexor(useIf, nInput);
	}
	
	public int getHijosFunc(String func) {
		int hijos;
		
		switch (func) {
		case "OR":
			hijos = 2; break;
		case "AND":
			hijos = 2; break;
		case "NOT":
			hijos = 1; break;
		case "IF":
			hijos = 3; break;
		default:
			hijos = 0; break;
		}
		return hijos;
	}
	
	private String[] creaTerminales(int nIn) {
		int casosTotales = (int) Math.pow(2, nIn);
		String cIn,cOut;
		cIn = "A";
		cOut = "D";
		
		String[] terminalesAux = new String[nIn+ casosTotales];
		
		for (int i = 0; i < nIn; i++ ) {
			terminalesAux[i] = cIn + i;
		}
		
		for (int i = nIn; i < nIn + casosTotales; i++ ) {
			terminalesAux[i] = cOut + (i - nIn);
		}
		return terminalesAux;
	}
	
	public boolean evaluaCaso(Arbol arbol, char[] multCase) {
		boolean evaluacion = false;
		if (arbol.isEsHoja()) {
			for (int i = 0; i < terminales.length; i++) {
				if (terminales[i].equals(arbol.getValor())) {
					if (multCase[i] == '1') {
						evaluacion = true;
						i = terminales.length;
					}
				}
			}
		}
		else if (arbol.getValor().equals("NOT")) {
			evaluacion = !evaluaCaso(arbol.getHijos().get(0),multCase);
		}
		else if (arbol.getValor().equals("OR")) {
			evaluacion = evaluaCaso(arbol.getHijos().get(0),multCase) || evaluaCaso(arbol.getHijos().get(1),multCase);;
		}
		else if (arbol.getValor().equals("AND")) {
			evaluacion = evaluaCaso(arbol.getHijos().get(0),multCase) && evaluaCaso(arbol.getHijos().get(1),multCase);
		}
		else if (arbol.getValor().equals("IF")) {
			if (evaluaCaso(arbol.getHijos().get(0),multCase)) {
				evaluacion = evaluaCaso(arbol.getHijos().get(1),multCase);
			}
			else {
				evaluacion = evaluaCaso(arbol.getHijos().get(1),multCase);
			}
		}
		return evaluacion;
	}
	
	
	
	//Metodos para eliminar intrones
	
	public Arbol simplificaArbol(Arbol p) {
		
		for (int i = 0; i < p.getHijos().size(); i++) {
			p.getHijos().set(i,simplificaArbol(p.getHijos().get(i)));
		}
		
		if (p.getValor().equals("NOT") && p.getHijos().get(0).getValor().equals("NOT") ) {
			p = p.getHijos().get(0).getHijos().get(0); // not(not(A) == A
			
			p.setProfundidad(p.getProf() - 2);
		}
		if (p.getValor().equals("OR") || p.getValor().equals("AND")) {
			if (sonIguales(p.getHijos().get(0),p.getHijos().get(1))) {
				p = p.getHijos().get(0);
				p.setProfundidad(p.getProf() - 1);
			}
		}
		
		if (p.getValor().equals("IF")) {
			if (sonIguales(p.getHijos().get(1),p.getHijos().get(2))) {
				p = p.getHijos().get(1);
				p.setProfundidad(p.getProf() - 1);
			}
		}
		return p;
	}
	
	public boolean sonIguales(Arbol a, Arbol b) {
		
		Arbol hijo1A, hijo2A,hijo1B, hijo2B, hijo3A, hijo3B;  //hijo x del arbol a/b
		boolean isEq = false;
		boolean aux = false;
		if (b.getValor().equals("NOT") && a.getValor().equals("NOT")) {		
			hijo1A = a.getHijos().get(0);
			hijo1B = b.getHijos().get(0);
			isEq = sonIguales(hijo1A, hijo1B);
			//isEq = hijo1A.equals(a.getHijos().get(0));
		}
		else if (b.getValor().equals("OR") && a.getValor().equals("OR") || b.getValor().equals("AND") && a.getValor().equals("AND") ) {
			
			hijo1A = a.getHijos().get(0); hijo1B = b.getHijos().get(0);
			hijo2A = a.getHijos().get(1); hijo2B = b.getHijos().get(1);
			
			if ( hijo2A.getValor().equals(hijo1B.getValor()) && hijo1A.getValor().equals(hijo2B.getValor()) ){
				isEq= sonIguales(hijo2A, hijo1B) && sonIguales(hijo1A, hijo2B);
			}
			
			
			else if ( hijo2A.getValor().equals(hijo2B.getValor()) && hijo1A.getValor().equals(hijo1B.getValor()) ) {
				isEq= sonIguales(hijo2A, hijo2B) && sonIguales(hijo1A, hijo1B);
			}
			
		}	
		else if (b.getValor().equals("IF") && a.getValor().equals("IF")) {
			
			hijo1A = a.getHijos().get(0); hijo1B = b.getHijos().get(0);
			hijo2A = a.getHijos().get(1); hijo2B = b.getHijos().get(1);
			hijo3A = a.getHijos().get(2); hijo3B = b.getHijos().get(2);
				
			if ( hijo2A.getValor().equals(hijo3B.getValor()) && hijo3A.getValor().equals(hijo2B.getValor()) ){
				aux= sonIguales(hijo2A, hijo3B) && sonIguales(hijo3A, hijo2B);
			}
			
			else if ( hijo2A.getValor().equals(hijo2B.getValor()) && hijo3A.getValor().equals(hijo3B.getValor()) ) {
				aux= sonIguales(hijo2A, hijo2B) && sonIguales(hijo3A, hijo3B);
			}
			isEq = aux && sonIguales(hijo1A, hijo1B) ; 
		}
		else if (b.getValor().equals(a.getValor())) {
			isEq = true;
		}
		
		return isEq;
		
	}
	
	//Fin metodos para eliminar intrones
	
	//Metodos para evaluar
	
	public int evalua(Arbol arbol) {
		if (sol == null) return evaluaSinSolution(arbol);
		else return evaluaConSolution(arbol);
	}
	
	public int evaluaSinSolution(Arbol arbol) {
		int aciertos = 0;
		
		char[] multCase = new char[Problema.terminales.length]; 		
		for (int i = 0 ; i < Problema.terminales.length ; i++) {
			multCase[i] = '0';
		}	
		while (!multCaseAllTrue(multCase)) {	
			if (evaluaCaso(arbol,multCase) == salidaMultCase(multCase)) {
				aciertos++;
			}		
			multCase = multCaseAdd1(multCase);
		}	
		if (evaluaCaso(arbol, multCase) == salidaMultCase(multCase)) {
			aciertos++;
		}
		
		return aciertos;
	}
	
	public int evaluaConSolution(Arbol arbol){		
		int aciertos = 0;
		int i = 0;
		while (i < sol.length) {
			if (evaluaCaso(arbol, this.solCases.get(i)) == sol[i]) aciertos++;
			i++;
		}
		return aciertos;
	}
	/*
	public int evaluaSinSolution(Arbol arbol) {
		int aciertos = 0;
		
		char[] multCase = new char[Problema.terminales.length]; 		
		for (int i = 0 ; i < Problema.terminales.length ; i++) {
			multCase[i] = '0';
		}	
		while (!multCaseAllTrue(multCase)) {	
			if (arbol.evalua(multCase) == salidaMultCase(multCase)) {
				aciertos++;
			}		
			multCase = multCaseAdd1(multCase);
		}	
		if (arbol.evalua(multCase) == salidaMultCase(multCase)) {
			aciertos++;
		}
		
		return aciertos;
	}
	
	public int evaluaConSolution(Arbol arbol){		
		int aciertos = 0;
		int i = 0;
		while (i < sol.length) {
			if (arbol.evalua(this.solCases.get(i)) == sol[i]) aciertos++;
			i++;
		}
		return aciertos;
	}*/
	
	public int evaluaConSolutionC(Arbol arbol){		
		
		ArrayList<HebraEvaluaMultiplexor> hebras = new ArrayList<HebraEvaluaMultiplexor>();
		AtomicInteger aciertos = new AtomicInteger(0);
		
		for ( int i = 0; i < 1; i++) {
			HebraEvaluaMultiplexor thread = new HebraEvaluaMultiplexor(arbol, this.solCases, sol, aciertos, 0, sol.length);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return aciertos.intValue();
	}
	
		
		
		
		
		
		/*
		ArrayList<HebraEvaluaMultiplexor> hebras = new ArrayList<HebraEvaluaMultiplexor>();
		AtomicInteger aciertos = new AtomicInteger(0);
		int i = 0;
		while (i < sol.length) {
			for ( int j = 0; j < 2 && i < sol.length; j++) {
				HebraEvaluaMultiplexor thread = new HebraEvaluaMultiplexor(arbol, this.solCases.get(i), sol[i], aciertos);
				hebras.add(thread);
				i++;
			}
			for (HebraEvaluaMultiplexor hebra : hebras) hebra.run();
			for (HebraEvaluaMultiplexor hebra : hebras) {
				try { hebra.join(); }
				catch (InterruptedException e) {e.printStackTrace();}
			}
			hebras.clear();
		}
		return aciertos.get();
		
	}*/
	
	
	private void creaSolution() {
		int casosTotales = (int) Math.pow(2, Problema.terminales.length);
		this.sol = new boolean[casosTotales];
		this.solCases = new ArrayList<char[]>();
		
		char[] multCase = new char[Problema.terminales.length]; 		
		for (int i = 0 ; i < Problema.terminales.length ; i++) {
			multCase[i] = '0';
		}
		int i = 0;
		while (i < casosTotales) {	
			this.sol[i] = salidaMultCase(multCase);
			this.solCases.add(multCase.clone());	
			i++;
			if (i != casosTotales) multCase = multCaseAdd1(multCase);
		}	
	}
	
	
	private boolean salidaMultCase(char[] multCase) {
		int pos = 0;
		
		for ( int i = 0; i < nInput; i++) {
			if (multCase[i] == '1') pos += Math.pow(2, nInput - (i+1));
		}
		
		if (multCase[nInput + pos] == '1' ) return true;
		return false;
	}
	
	private boolean multCaseAllTrue(char[] multCase) {
		for (int i = 0 ; i < Problema.terminales.length ; i++) {
			if (multCase[i] == '0') return false;
		}
		return true;
	}
	
	private char[] multCaseAdd1(char[] multCase) {
		boolean end = false;
		int i = Problema.terminales.length - 1;
		while (!end && i >= 0) {
			
			if (multCase[i] == '0') {end = true; multCase[i] = '1';}
			else {multCase[i]= '0';}
			i--;
		}
		return multCase;
	}
	
	//FIN Funciones de evaluacion
	
}
