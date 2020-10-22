package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Exceptions.ParseException;
import Logic.Controller;
import Logic.Observer;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.SeleccionTorneos;
import Logic.Algoritmos.Selecciones.SeleccionTorneosProbabilistica;

public class AlgorithmPanel extends JPanel implements Observer {

	Controller _ctrl;
	
	JPanel principal;
	JPanel pSelection;
	JPanel pCross;
	JPanel pMute;
	
	private JTextField T_participantes;
	private JLabel L_participantes;
	
	private JTextField T_probMejor;
	private JLabel L_probMejor;
	
	JComboBox<String> SelectionBox;
	JComboBox<String> CrossBox;
	JComboBox<String> MutationBox;
	
	int participantes;
	double pMejor;
	String fileName, sel, mut, comb;

	
	AlgorithmPanel(Controller _ctrl){
		this._ctrl = _ctrl;
		
		principal = new JPanel();
		principal.setLayout(new BorderLayout ());
		
		principal.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		principal.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		
		init();
		
		this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		this.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, 300);
		
		this.setBackground(Color.WHITE);
		this._ctrl.addObserver(this);
	}
	
	public void init() {
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width / 6;
		
		pSelection = new JPanel();
		pSelection.setBackground(Color.WHITE);
		pSelection.setPreferredSize(new Dimension(width,295));	
		pSelection.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Seleccion",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		pCross = new JPanel();
		pCross.setBackground(Color.WHITE);
		pCross.setPreferredSize(new Dimension(width,295));
		pCross.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Cruce",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		pMute = new JPanel();
		pMute.setBackground(Color.WHITE);
		pMute.setPreferredSize(new Dimension(width,295));
		pMute.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Mutacion",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		
		fileName = _ctrl.getFileName();
		
		String[] possibilities = _ctrl.getSeleccionesName();
		sel = possibilities[0];
		SelectionBox = new JComboBox<String>(possibilities);	

		SelectionBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sel = (String)SelectionBox.getSelectedItem();
				_ctrl.setAlgorithm(fileName, sel, mut, comb);

			}
		});
		pSelection.add(SelectionBox);
		
		L_participantes = new JLabel("Participantes:             ");
		
		pSelection.add(L_participantes);
		T_participantes = new JTextField("2", 5);
		T_participantes.setToolTipText("participantes");
		T_participantes.setMaximumSize(new Dimension(90, 30));
		T_participantes.setMinimumSize(new Dimension(90, 30));
		T_participantes.setEditable(true);
		T_participantes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					participantes = Integer.parseInt(T_participantes.getText()) ;
				}
				catch(Exception ex) {
					_ctrl.onException("Parse nº participantes fail");
				}

			}
		});
		pSelection.add(T_participantes);
		
		
		
		L_probMejor = new JLabel("Prob. Gana el mejor: ");
		
		pSelection.add(L_probMejor);
		T_probMejor = new JTextField("0.6", 5);
		T_probMejor.setToolTipText("Prob. Gana el mejor");
		T_probMejor.setMaximumSize(new Dimension(70, 30));
		T_probMejor.setMinimumSize(new Dimension(25, 30));
		T_probMejor.setEditable(true);
		T_probMejor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pMejor = Double.parseDouble(T_probMejor.getText()) ;
				}
				catch(Exception ex) {
					_ctrl.onException("Parse Prob. de que gane el mejor fail");
				}

			}
		});
		pSelection.add(T_probMejor);
		

		
		
		
		
		possibilities = _ctrl.getCrucesName();
		
		CrossBox = new JComboBox<String>(possibilities);	

		CrossBox.setBounds(100, 100, 200, 20);
		CrossBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comb = (String)CrossBox.getSelectedItem();
				_ctrl.setAlgorithm(fileName, sel, mut, comb);

			}
		});
		pCross.add(CrossBox);
		
		
		
		
		possibilities = _ctrl.getMutacionesName();
		
		MutationBox = new JComboBox<String>(possibilities);	

		MutationBox.setBounds(100, 100, 200, 20);
		MutationBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mut = (String)MutationBox.getSelectedItem();
				_ctrl.setAlgorithm(fileName, sel, mut, comb);

			}
		});
		pMute.add(MutationBox);
		
		
		
		
		principal.add(pSelection, BorderLayout.WEST);
		principal.add(pCross, BorderLayout.CENTER);
		principal.add(pMute, BorderLayout.EAST);
		
		this.add(principal);
		
		setVisible(sel);
	}
	
	public String getSelection() {
		return this.sel;
	}
	public String getCross() {
		return this.comb;
	}
	public String getMutation() {
		return this.mut;
	}
	public int getParticipantes() throws ParseException {
		return Integer.parseInt(T_participantes.getText());
	}
	public Double getPMejor() throws ParseException {
		return Double.parseDouble(T_probMejor.getText());
	}
	
	public void setVisible(String sel) {
		switch (sel) {
		case SeleccionTorneos.ID:
			T_participantes.setVisible(true);
			L_participantes.setVisible(true);
			T_probMejor.setVisible(false);
			L_probMejor.setVisible(false);
			break;
		case SeleccionTorneosProbabilistica.ID:
			T_participantes.setVisible(true);
			L_participantes.setVisible(true);
			
			T_probMejor.setVisible(true);
			L_probMejor.setVisible(true);
			break;
		default:
			T_participantes.setVisible(false);
			L_participantes.setVisible(false);
			
			T_probMejor.setVisible(false);
			L_probMejor.setVisible(false);
		}
	}
	
	
	@Override
	public void onRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(String exMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinished(ArrayList<double[]> evaluation, int gen, Individuo mejor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlgorithmChanged(String fileName, String sel, String mut, String cruce) {
		this.fileName = fileName;
		this.sel = sel;
		this.mut = mut;
		this.comb = cruce;
		setVisible(sel);
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
