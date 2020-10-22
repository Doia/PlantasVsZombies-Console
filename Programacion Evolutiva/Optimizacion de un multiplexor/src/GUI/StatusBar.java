package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.Clock;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import Logic.Controller;
import Logic.Observer;
import Logic.Algoritmos.Individuos.Individuo;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements Observer {
	
	
	private JLabel _currCruce;
	private JLabel _currMut;
	private JLabel _curSel;
	private JLabel _LastResult;
	
	private String _Cruce;
	private String _mut;
	private String _sel;
	private Individuo mejor;

	StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		
		_sel = ctrl.getSeleccionesName()[0];
		_Cruce = ctrl.getCrucesName()[0];
		_mut= ctrl.getMutacionesName()[0];
		mejor = null;
		
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout( new BorderLayout());
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		JToolBar tB = new JToolBar();
		this.add(Box.createHorizontalGlue());
		tB.setAlignmentX(JToolBar.RIGHT_ALIGNMENT);
		this.add(tB);
		tB.addSeparator(new Dimension(10, 10));
		
		_curSel = new JLabel();
		tB.add(_curSel);
		tB.addSeparator(new Dimension(50, 10));
		
		_currCruce = new JLabel();
		tB.add(_currCruce);
		tB.addSeparator(new Dimension(50, 10));
		
		_currMut = new JLabel();
		tB.add(_currMut);
		tB.addSeparator(new Dimension(50, 10));
		
		_LastResult = new JLabel();
		tB.add(_LastResult);
		
		
		updateLabelText();
	}

	//Update the value of the label 
	private void updateLabelText() {
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
		mejor.evalua();
		String str = mejor.toString() + " Resultado: " + mejor.getPunt();
		return str;
	}
	
	
	
	@Override
	public void onException(String exMsg) {}

	@Override
	public void onRegister() {}

	@Override
	public void onFinished(ArrayList<double[]> evaluation, int gen, Individuo _mejor) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				mejor = _mejor;
				updateLabelText();
			}
		});
	}

	@Override
	public void onAlgorithmChanged(String fileName, String sel, String mut, String cruce, String tCB, String tIni) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_Cruce = cruce;
				_mut = mut;
				_sel = sel;
				updateLabelText();
				
			}
		});
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onGeneration(ArrayList<double[]> evaluation, int gen, Individuo mejor) {}


}
