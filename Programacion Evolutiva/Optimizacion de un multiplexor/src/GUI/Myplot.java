package GUI;


import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import Logic.Controller;
import Logic.Observer;
import Logic.Algoritmos.Individuos.Individuo;

public class Myplot extends Plot2DPanel implements Observer {

	Controller _ctrl;
	int gen;
	ArrayList<double[]> _evaluation;

	int[] plot;
	int numPlot;
	double[] y;
	
	public Myplot(Controller ctrl) {
		
		this._ctrl = ctrl;

		this._evaluation = new ArrayList<double[]>();
		this.gen = 0;
		
		this.setName("Grafica de Evolucion");
		
		this.addLegend("EAST");
		
		this._ctrl.addObserver(this);
		
	}
	
	public void initPlotsLines() {
		
		String[] plotNames = {"Maximo generacion","Minimo generacion", "Mejor Absoluto","Media"};
		Color[] colors = {Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};
		
		numPlot = plotNames.length;
		
		plot = new int[numPlot];
		for (int i = 0; i < numPlot; i++) {
			this.addLinePlot(plotNames[i], y, _evaluation.get(i));
			this.changePlotColor(i, colors[i]);
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
	public void onAlgorithmChanged(String fileName, String sel, String mut, String cruce, String tCB, String tIni)  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGeneration(ArrayList<double[]> evaluation, int gen, Individuo mejor) {
		// TODO Auto-generated method stub
		
	}

	
}
