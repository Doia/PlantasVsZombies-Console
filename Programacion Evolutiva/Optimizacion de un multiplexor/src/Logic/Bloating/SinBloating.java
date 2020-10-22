package Logic.Bloating;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.Seleccion;

public class SinBloating extends Bloating {

	public static final String ID = "Sin control de bloating";
	
	public SinBloating() {
		super(ID);
	}
	public SinBloating(boolean maximizacion) {
		super(ID, maximizacion);
	}

	@Override
	public void setFitness(ArrayList<Individuo> poblacion) {
		for (int i = 0; i < poblacion.size(); i++) {
			poblacion.get(i).setFitness((double)poblacion.get(i).getPunt());
		}
	}
	@Override
	public Bloating newInstance(double penalizacion, boolean maximizacion) {
		return new SinBloating(maximizacion);
	}

}
