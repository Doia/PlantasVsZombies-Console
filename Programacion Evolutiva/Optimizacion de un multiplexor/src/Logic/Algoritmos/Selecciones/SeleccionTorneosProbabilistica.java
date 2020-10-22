package Logic.Algoritmos.Selecciones;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;

public class SeleccionTorneosProbabilistica extends Seleccion {

	public final static String ID = "Seleccion por Torneos probabilística";
	
	int participantes;
	double pGMejor;
	
	public SeleccionTorneosProbabilistica() {
		super(ID);
	}
	
	public SeleccionTorneosProbabilistica(int participantes, double pGMejor, boolean maximizacion) {
		super(ID, maximizacion);
		this.participantes = participantes;
		this.pGMejor = pGMejor;
	}
	
	@Override
	public ArrayList<Individuo> doSeleccion(ArrayList<Individuo> poblacion) {
		
		boolean tipoTorneo;
		double pEleg, prob;
		int punto;
		Individuo max;
		ArrayList<Individuo> nuevos = new ArrayList<Individuo>();
		ArrayList<Individuo> torneo = new ArrayList<Individuo>();

		for (int i = 0; i < poblacion.size(); i++) {

			prob = Math.random();
			if (prob <= pGMejor) tipoTorneo = maximizacion; // gana el mejor
			else  tipoTorneo = !maximizacion; //gana el peor
			
			pEleg = 1.0 / poblacion.size();
			
			for (int j = 0; j < participantes; j++) {
				punto = (int) Math.floor(Math.random()/pEleg);
				if (torneo.contains(poblacion.get(punto))) j--;
				else torneo.add(poblacion.get(punto));
			}
			
			max = torneo.get(0);
			for (int j = 0; j < participantes; j++) {
				if (tipoTorneo) {
					if(torneo.get(j).getFitness() > max.getFitness()) max = torneo.get(j);
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
		return new SeleccionTorneosProbabilistica(participantes ,pGMejor ,maximizacion);
	}

}
