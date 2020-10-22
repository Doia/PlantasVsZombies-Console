package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;
import java.util.Random;

import Logic.Algoritmos.Cruces.Cruce;
import Logic.Algoritmos.Individuos.Individuo;

public class SeleccionTorneos extends Seleccion {

	public final static String ID = "Seleccion por Torneos";
	
	int participantes;
	
	public SeleccionTorneos() {
		super(ID);
	}
	
	public SeleccionTorneos(int participantes, boolean maximizacion) {
		super(ID, maximizacion);
		this.participantes = participantes;
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		
		double pEleg;
		int punto;
		Individuo max;
		ArrayList<Individuo> nuevos = new ArrayList<Individuo>();
		ArrayList<Individuo> torneo = new ArrayList<Individuo>();
		

		for (int i = 0; i < poblacion.size(); i++) {
			
			pEleg = 1.0 / poblacion.size();
			
			for (int j = 0; j < participantes; j++) {
				punto = (int) Math.floor(Math.random()/pEleg);
				if (torneo.contains(poblacion.get(punto))) j--;
				else torneo.add(poblacion.get(punto));
			}
			max = torneo.get(0);
			for (int j = 0; j < participantes; j++) {
				if (this.maximizacion) {
					if(torneo.get(j).getFitness()> max.getFitness()) max = torneo.get(j);
				}
				else {
					if(torneo.get(j).getFitness() < max.getFitness()) max = torneo.get(j);
				}
			}
			nuevos.add(max.duplicate());
			torneo.clear();
		}
		return nuevos;
	}

	@Override
	public Seleccion newInstance(int participantes, double pGMejor, boolean maximizacion) {
		// TODO Auto-generated method stub
		return new SeleccionTorneos(participantes ,maximizacion);
	}

}
