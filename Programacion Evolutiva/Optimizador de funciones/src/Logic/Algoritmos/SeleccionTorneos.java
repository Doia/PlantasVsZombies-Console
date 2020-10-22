package Logic.Algoritmos;

import java.util.ArrayList;
import java.util.Random;

import Logic.Cruce;
import Logic.Individuo;
import Logic.IndividuoBinario;
import Logic.Seleccion;

public class SeleccionTorneos extends Seleccion {

	public final static String ID = "Seleccion por Torneos";
	public final static double INF = 10E9;
	
	public SeleccionTorneos() {
		super(ID);
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		
	double pEleg;
	Individuo i1, i2;
	ArrayList<Individuo> nuevos = new ArrayList<Individuo>();
	int punto;
		for (int i = 0; i < poblacion.size(); i++) {
			
			pEleg = 1.0 / poblacion.size();
			
			punto = (int) Math.floor(Math.random()/pEleg);
			i1= poblacion.get(punto);
			
			punto = (int) Math.floor(Math.random()/pEleg);
			i2= poblacion.get(punto);
			
			if(i1.getFitnessNormalizado()>i2.getFitnessNormalizado()) nuevos.add(i1.duplicate());
			else nuevos.add(i2.duplicate());
		}
		return nuevos;
	}

	@Override
	public Seleccion newInstance() {
		// TODO Auto-generated method stub
		return new SeleccionTorneos();
	}

}
