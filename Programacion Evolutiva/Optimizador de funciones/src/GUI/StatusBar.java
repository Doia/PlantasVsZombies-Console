package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Extras.Controller;
import Extras.Observer;
import Logic.Individuo;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements Observer {

	private JLabel _currFunction;
	private JLabel _currCruce;
	private JLabel _currMut;
	private JLabel _curSel;
	private JLabel _LastResult;
	
	private String _function;
	private String _Cruce;
	private String _mut;
	private String _sel;
	private Individuo mejor;

	StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		
		_function = ctrl.getFuntionsName()[0];
		_sel = ctrl.getSeleccionesName()[0];
		_Cruce = ctrl.getCrucesName()[0];
		_mut= ctrl.getMutacionesName()[0];
		mejor = null;
		
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		JToolBar tB = new JToolBar();
		this.add(tB);
		tB.addSeparator(new Dimension(10, 10));
		
		_currFunction = new JLabel();
		tB.add(_currFunction);
		tB.addSeparator(new Dimension(80, 10));
		
		_curSel = new JLabel();
		tB.add(_curSel);
		tB.addSeparator(new Dimension(80, 10));
		
		_currCruce = new JLabel();
		tB.add(_currCruce);
		tB.addSeparator(new Dimension(80, 10));
		
		_currMut = new JLabel();
		tB.add(_currMut);
		tB.addSeparator(new Dimension(80, 10));
		
		_LastResult = new JLabel();
		tB.add(_LastResult);
		
		updateLabelText();
	}

	//Update the value of the label 
	private void updateLabelText() {
		_currFunction.setText("Funcion: " + _function);
		_currCruce.setText("Cruce: " + _Cruce);
		_currMut.setText("Mutacion: " + _mut);
		_curSel.setText("Seleccion: " + _sel);
		
		if (mejor != null) {
			_LastResult.setText("Solucion: " + strMejor());
		}
		else {
			_LastResult.setText("Solucion: No Existe");
		}
	}
	
	private String strMejor() {
		String str = "";
		double[] fen = mejor.getFenotipo();
		
		for (int i = 0; i < fen.length; i++) {
			str += "X"+(i+1)+": "+ fen[i] + "; ";
		}
		
		str += "Valor: " + mejor.getFitness();
		
		return str;
	}
	
	
	
	@Override
	public void onException(String exMsg) {}

	@Override
	public void onRegister() {}

	@Override
	public void onFinished(ArrayList<double[]> evaluation, int gen, Individuo mejor) {
		this.mejor = mejor;
		updateLabelText();
	}

	@Override
	public void onAlgorithmChanged(String function, String sel, String mut, String cruce) {
		_function = function;
		_Cruce = cruce;
		_mut = mut;
		_sel = sel;
		updateLabelText();
		
	}


}
