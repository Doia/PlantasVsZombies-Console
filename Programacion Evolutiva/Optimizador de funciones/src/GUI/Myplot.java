package GUI;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import Extras.Controller;
import Extras.Observer;
import Logic.Individuo;

public class Myplot extends Plot2DPanel implements Observer {

	Controller _ctrl;
	int gen;
	ArrayList<double[]> _evaluation;

	int[] plot;
	int numPlot;
	double[] y;
	
	public Myplot(Controller ctrl) {
		
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
		this._evaluation = new ArrayList<double[]>();
		this.gen = 0;
		
		this.setName("Grafica de Evolucion");
		
		this.addLegend("EAST");
		/*
		numPlot = 0;
		plot = new int[numPlot];*/
	}
	
	public void initPlotsLines() {
		
		String[] plotNames = {"Maximo generacion","Minimo generacion", "Mejor Absoluto","Media"};
		numPlot = _evaluation.size();
		
		
		plot = new int[numPlot];
		for (int i = 0; i < numPlot; i++) {
			
			this.addLinePlot(plotNames[i], y, _evaluation.get(i));
		}
	}
	
	
	
	public void setMyPlots() {
		this.removeAllPlots();
		initPlotsLines();
	}

	@Override
	public void onRegister() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(String exMsg) {
		// TODO Auto-generated method stub
		
	}

	public void onFinished(ArrayList<double[]> evaluation, int gen,  Individuo mejor) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				y = new double[gen];
				for (int i = 0; i < gen; i++) {
					y[i] = i+1;
				}
				_evaluation = evaluation;
				setMyPlots();
			}
		});
	}

	@Override
	public void onAlgorithmChanged(String function, String sel, String mut, String cruce) {
		// TODO Auto-generated method stub
		
	}

	
}
