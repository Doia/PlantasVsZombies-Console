package Logic.Bloating;

import java.util.ArrayList;

import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.Seleccion;

public class Tarpeian extends Bloating {

	public static final String ID = "Tarpeian";
	double penalizacion;
	
	public Tarpeian() {
		super(ID);
	}
	public Tarpeian(double penalizacion, boolean maximizacion) {
		super(ID, maximizacion);
		this.penalizacion = penalizacion;
		if (!maximizacion) this.penalizacion *= -1;
	}

	@Override
	public void setFitness(ArrayList<Individuo> poblacion) {
		
		double nodosExtra;
		double mediaNodos = 0;
		double maxNodos = 0;
		
		for (Individuo indv: poblacion) {
			mediaNodos += indv.getArbol().getNumNodos();
			maxNodos = Math.max(maxNodos,indv.getArbol().getNumNodos() );
		}
		mediaNodos /= poblacion.size();
		
		for (Individuo indv: poblacion) {
	        if(indv.getArbol().getNumNodos() > mediaNodos) {
	            double limitacion = (penalizacion * indv.getPunt());
	            
	            
	           nodosExtra = indv.getArbol().getNumNodos() - mediaNodos;
	            
	           indv.setFitness(indv.getPunt() - (limitacion * (Math.log(Math.max(1, nodosExtra))) / 5 ));
	        }
	        else {
	        	indv.setFitness(indv.getPunt());
	        }

		}
	}
	
	
	@Override
	public Bloating newInstance(double penalizacion, boolean maximizacion) {
		return new Tarpeian(penalizacion, maximizacion);
	}

}
