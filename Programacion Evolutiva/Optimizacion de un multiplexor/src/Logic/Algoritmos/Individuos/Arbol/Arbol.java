package Logic.Algoritmos.Individuos.Arbol;

import java.util.ArrayList;
import java.util.Random;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Problemas.Problema;

public class Arbol {

	private String valor;
	private ArrayList<Arbol> hijos;
	private int numHijos;
	private int numNodos;
	private int max_prof; 
	private int profundidad;
	private boolean esHoja;
	private boolean esRaiz; 
	
	
	public Arbol(int maxProf) {
		hijos = new ArrayList<Arbol>();
		this.max_prof = maxProf;
	}
	public Arbol(String v) {
		hijos = new ArrayList<Arbol>();
		this.valor = v;
	}
	public String toString() {
		String str = valor;
			if (!this.esHoja) {
				str += "(";
				for ( int i = 0; i < numHijos; i++) {
					str += hijos.get(i).toString();
				}
				str += ")";
			}
			return str += " ";
	}
	
	//Setters
	public void setEsHoja(boolean esHoja) {
		this.esHoja = esHoja;
	}
	public void setEsRaiz(boolean esRaiz) {
		this.esRaiz = esRaiz;
	}
	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}
	
	public void recalculaNumNodosYprof(int n) {
		this.profundidad = n;
		this.numNodos = 1;
		if (this.esRaiz) {
			this.numNodos = 1;
			for (Arbol hijo: hijos) {
				hijo.recalculaNumNodosYprof(n+1);
				this.numNodos += hijo.getNumNodos(); 
			}
		}
	}
	
	public void setProfundidad(int prof) {
		this.profundidad = prof;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public void setHijos(ArrayList<Arbol> hijos) {
		this.hijos = hijos;
	}
	
	//Getters
	
	public String getValor() {
		return this.valor;
	}
	
	public int getNumNodos() {
		return this.numNodos;
	}
	
	public boolean isEsHoja() {
		return this.esHoja;
	}
	
	public boolean isEsRaiz() {
		return this.esRaiz;
	}
	
	public int getProf() {
		return this.profundidad;
	}
	
	public int getNumHijos() {
		return this.numHijos;
	}
	public ArrayList<Arbol> getHijos() {
		return this.hijos;
	}
	
	// Devuelve el arbol en forma de array
	public ArrayList<String> toArray(){
		ArrayList<String> array = new ArrayList<String>();
		toArrayAux(array, this);
		return array;
	}
	
	// Insertar un valor en el arbol (nodo simple)
	public Arbol insert(String v, int index){
		Arbol a = new Arbol(v);
		if(index == -1){
			hijos.add(a);
			numHijos = hijos.size();
			}  
		else 
			hijos.set(index, a);
		return a;
	}    
	
	// Insertar un arbol en otro arbol.
	public void insert(Arbol a, int index){
		if(index == -1){
			hijos.add(a);
			numHijos = hijos.size();
		}   
		else
			hijos.set(index, a);
		}  
	
	public Arbol at(int index){
		return at(this, 0, index);
	}    
	
	private Arbol at(Arbol a, int pos, int index){
		Arbol s = null;
		if(pos >= index) s = a;
		else if(a.getNumHijos() > 0){
			for(int i = 0; i < a.getNumHijos(); i++)
				if(s == null)
					s = at(a.getHijos().get(i), pos+i+1, index);
			}
		return s;
	} 
	 
	private void toArrayAux(ArrayList<String> array, Arbol a){
		array.add(a.valor);
		for(int i = 0; i < a.hijos.size(); i++){
			toArrayAux(array, a.hijos.get(i));
		}
	} 
	
	public int inicializacionCreciente(int p, int nodos) {
		int n = nodos;
		int nHijos = 2;
		if(p < max_prof){
			setProfundidad(p);
			Random rnd = new Random();
			int func = 0;
			int nfunc = 0;
			int nterm = 0;
			
			nfunc = Problema.funciones.length;
			nterm = Problema.terminales.length;
			
			if ( p == 0) func = rnd.nextInt(nfunc); //Si es la raiz del arbol evito que sea un nodo terminal
			else func = rnd.nextInt(nfunc + nterm);
		
			if (func < nfunc) {
				this.valor = Problema.funciones[func];
				this.setEsRaiz(true);
				this.setEsHoja(false);
				if(valor.equals("IF")) nHijos = 3;
				if(valor.equals("NOT")) nHijos = 1;
			}
			else {
				this.valor = Problema.terminales[func - nfunc];
				this.setEsRaiz(false);
				this.setEsHoja(true);
				nHijos = 0;
			}
		
			for(int i = 0; i < nHijos; i++){
				Arbol hijo = new Arbol(max_prof);
				//hijo.setPadre(this);
				//esRaiz = true;
				n++;
				n = hijo.inicializacionCreciente(p+1, n);
				hijos.add(hijo);
				numHijos++;   
			}  
		}  
		else{   
			setProfundidad(p);
			Random rnd = new Random();
			int terminal;
			this.setEsHoja(true);
			terminal = rnd.nextInt(Problema.terminales.length);
			valor = Problema.terminales[terminal];
			esHoja = true;
			numHijos = 0;
		}  
		
		setNumNodos(n);
		return n;
	}
	
	public int inicializacionCompleta(int p, int nodos){
		int n = nodos;
		int nHijos = 2;
		if(p < max_prof){
			setProfundidad(p);
			Random rnd = new Random();
			int func = 0;
			
			func = rnd.nextInt(Problema.funciones.length);

			
			this.valor = Problema.funciones[func];
			this.setEsRaiz(true);
			
			if(valor.equals("IF")) nHijos = 3;
			if(valor.equals("NOT")) nHijos = 1;
			
			for(int i = 0; i < nHijos; i++){
				Arbol hijo = new Arbol(max_prof);
				//hijo.setPadre(this);
				esRaiz = true;
				n++;
				n = hijo.inicializacionCompleta(p+1, n);
				hijos.add(hijo);
				numHijos++;   
			}  
		}  
		else{   
			setProfundidad(p);
			Random rnd = new Random();
			int terminal;
			this.setEsHoja(true);
			terminal = rnd.nextInt(Problema.terminales.length);
			valor = Problema.terminales[terminal];
			esHoja = true;
			numHijos = 0;
		}  
		
		setNumNodos(n);
		return n;
	}     
			 
	/*private int creaHijos(int p, int nodos) {
		int n = nodos;
		int nHijos = 2;
		
		if(valor.equals("IF")) nHijos = 3;
		if(valor.equals("NOT")) nHijos = 1;
		
		for(int i = 0; i < nHijos; i++){
			Arbol hijo = new Arbol(max_prof, useIF);
			//hijo.setPadre(this);
			n++;
			n = hijo.inicializacionCreciente(p+1, n);
			hijos.add(hijo); 
			numHijos++;
		} 
		return n;
	} */
	
	
	/**
	 *     Devuelve los nodos hoja del árbol
	 *     @param hijos Hijos del árbol a analizar
	 *     @param nodos Array donde se guardan los terminales
	 */
	
	public void getTerminales(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {
		for(int i = 0; i < hijos.size(); i++){
			if(hijos.get(i).isEsHoja()){
				nodos.add(hijos.get(i));
				}
			else{
				getTerminales(hijos.get(i).getHijos(), nodos);
				} 
		}
	}
	
	public int insertTerminal(ArrayList<Arbol> list_hijos, Arbol terminal, int index, int pos){
		int p = pos;
		for(int i = 0; i < list_hijos.size() && p != -1; i++){
			if(list_hijos.get(i).isEsHoja() && (p == index)){
				//terminal.padre = list_hijos.get(i).padre;
				list_hijos.set(i, terminal.copia());
				p = -1;
				}
			else if(list_hijos.get(i).esHoja && (p != index)){
				p++;
			}
			else{
				p = insertTerminal(list_hijos.get(i).hijos,terminal, index, p);
			}
		}
		return p;
	}  
	
	public int insertFuncion(ArrayList<Arbol> list_hijos, Arbol terminal, int index, int pos){
		int p = pos;
		for(int i = 0; i < list_hijos.size() && p != -1; i++){
			if(list_hijos.get(i).esRaiz && (p == index)){
				//terminal.padre = list_hijos.get(i).padre;
				list_hijos.set(i, terminal.copia());
				p = -1;
				}
			else if(list_hijos.get(i).esRaiz && (p != index)){
				p++;
				p = insertFuncion(list_hijos.get(i).hijos, terminal, index, p);
			}
		}
		return p;
	}
	
	public void getFunciones(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {
		for(int i = 0; i < hijos.size(); i++){
			if(hijos.get(i).isEsRaiz()){
				nodos.add(hijos.get(i));
				getFunciones(hijos.get(i).hijos, nodos);
			}
		}
	}

	public Arbol copia(){
		Arbol copia = new Arbol(this.max_prof);
		copia.setEsHoja(this.esHoja);
		copia.setEsRaiz(this.esRaiz);
		copia.setNumHijos(this.numHijos);
		copia.setNumNodos(this.numNodos);
		copia.setProfundidad(this.profundidad);
		copia.setValor(this.valor);
		ArrayList<Arbol> aux = new ArrayList<Arbol>();
		aux = copiaHijos();
		copia.setHijos(aux);
		return copia;
	}
	
	private ArrayList<Arbol> copiaHijos() {
		ArrayList<Arbol> array = new ArrayList<Arbol>();
		for(int i = 0; i < this.hijos.size(); i++){
			array.add(this.hijos.get(i).copia());
		}
		return array;
	}

	public int obtieneNodos(Arbol nodo, int n){
		if(nodo.esHoja) return n;
		if(nodo.valor.equals("IF")){
			n = obtieneNodos(nodo.hijos.get(0), n+1);
			n = obtieneNodos(nodo.hijos.get(1), n+1);
			n = obtieneNodos(nodo.hijos.get(2), n+1);
		}
		else if(nodo.valor.equals("AND") || nodo.valor.equals("OR")){
			n = obtieneNodos(nodo.hijos.get(0), n+1);
			n = obtieneNodos(nodo.hijos.get(1), n+1);
		}
		else{
			n = obtieneNodos(nodo.hijos.get(0), n+1);
		}
		return n;
	}
	/*
	public boolean evalua(char[] multCase) {
		boolean evaluacion = false;
		if (this.esHoja) {
			for (int i = 0; i < Problema.terminales.length; i++) {
				if (Problema.terminales[i].equals(valor)) {
					if (multCase[i] == '1') {
						evaluacion = true;
						i = Problema.terminales.length;
					}
				}
			}
		}
		if (valor.equals("NOT")) {
			evaluacion = !hijos.get(0).evalua(multCase);
		}
		if (valor.equals("OR")) {
			evaluacion = hijos.get(0).evalua(multCase) || hijos.get(1).evalua(multCase);
		}
		if (valor.equals("AND")) {
			evaluacion = hijos.get(0).evalua(multCase) && hijos.get(1).evalua(multCase);
		}
		if (valor.equals("IF")) {
			if (hijos.get(0).evalua(multCase)) {
				evaluacion = hijos.get(1).evalua(multCase);
			}
			else {
				evaluacion = hijos.get(2).evalua(multCase);
			}
		}
		return evaluacion;
	}*/
	
}
