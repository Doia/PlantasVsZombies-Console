package Launchers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import GUI.MainWindow;
import Logic.Controller;
import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Cruces.CruceClasico;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Individuos.Arbol.Arbol;
import Logic.Problemas.Problema;
import Logic.Problemas.ProblemaMultiplexor;


public class Main {

	public static void main(String[] args) {
		
		Controller ctrl = new Controller();
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run () {
					new MainWindow(ctrl); 
				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

		