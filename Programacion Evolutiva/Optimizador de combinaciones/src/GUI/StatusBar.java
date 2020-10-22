package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Logic.Controller;
import Logic.Observer;
import Logic.Algoritmos.Individuos.Individuo;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements Observer {

	private JLabel _currFile;
	private JLabel _currCruce;
	private JLabel _currMut;
	private JLabel _curSel;
	private JLabel _LastResult;
	
	private String _fileName;
	private String _Cruce;
	private String _mut;
	private String _sel;
	private Individuo mejor;

	StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		
		_fileName = ctrl.getFileName();
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
		
		_currFile = new JLabel();
		tB.add(_currFile);
		tB.addSeparator(new Dimension(50, 10));
		
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
		_currFile.setText("Archivo: " + _fileName);
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

		Integer[] fen = (Integer[]) mejor.getGenoma();
		
		for (int i = 0; i < fen.length; i++) {
			str += fen[i].intValue() + ";   ";
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
	public void onAlgorithmChanged(String fileName, String sel, String mut, String cruce) {
		_fileName = fileName;
		_Cruce = cruce;
		_mut = mut;
		_sel = sel;
		updateLabelText();
		
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
