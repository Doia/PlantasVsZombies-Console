package Logic.Problemas;

import java.util.ArrayList;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;

public abstract class Problema {

	String id;
	
	//Gramatica del problema
	public static String terminales[];
	public static  String funciones[];
	
	public Problema(String id) {
		this.id = id;
	}
	public Problema(String id, String terminales[], String funciones[]) {
		this.id = id;
		this.terminales = terminales;
		this.funciones = funciones;
	}
	
	public abstract Problema newInstance(boolean useIf, int nInput);
	
	public abstract Arbol simplificaArbol(Arbol arbol);
	
	public abstract int evalua(Arbol arbol);
	
	public abstract int getHijosFunc(String func);
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
}
