package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import Exceptions.ParseException;
import Logic.Controller;
import Logic.Observer;
import Logic.Algoritmos.Individuos.Individuo;
import Logic.Algoritmos.Selecciones.SeleccionTorneos;
import Logic.Algoritmos.Selecciones.SeleccionTorneosProbabilistica;
import Logic.Bloating.Tarpeian;

public class AlgorithmPanel extends JPanel implements Observer {

	Controller _ctrl;
	
	JPanel principal;
	JPanel problem;
	JPanel pSelection;
	JPanel pCross;
	JPanel pMute;
	
	private JTextField T_participantes;
	private JTextField T_penalizacion;
	private JLabel L_participantes;
	private JLabel L_penalizacion;
	
	private JTextField T_probMejor;
	private JLabel L_probMejor;
	
	JComboBox<String> SelectionBox;
	JComboBox<String> CrossBox;
	JComboBox<String> MutationBox;
	JComboBox<String> ProblemsBox;
	JComboBox<String> tipoBloatingBox;
	JComboBox<String> tipoCreacionBox; 
	
	JSpinner NinputBox; 
	JSpinner maxProf; 
	
	JCheckBox UseIFCheck; 
	
	int participantes;
	double pMejor, penalizacion;
	String tCB, sel, mut, comb, tProblema, tipoIni;

	
	AlgorithmPanel(Controller _ctrl){
		this._ctrl = _ctrl;
		
		principal = new JPanel();
		principal.setLayout(new BorderLayout ());
		
		principal.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 145));
		principal.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 145));
		
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
		pSelection.setPreferredSize(new Dimension(width,145));	
		pSelection.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Seleccion",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		pCross = new JPanel();
		pCross.setBackground(Color.WHITE);
		pCross.setPreferredSize(new Dimension(width,145));
		pCross.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Cruce",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		pMute = new JPanel();
		pMute.setBackground(Color.WHITE);
		pMute.setPreferredSize(new Dimension(width,145));
		pMute.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Mutacion",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		problem = new JPanel();
		//problem.setLayout(new BorderLayout ());
		problem.setBackground(Color.WHITE);
		problem.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 145));
		problem.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Problema",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		
		String[] possibilities = _ctrl.getSeleccionesName();
		sel = possibilities[0];
		SelectionBox = new JComboBox<String>(possibilities);	

		SelectionBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sel = (String)SelectionBox.getSelectedItem();
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

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
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

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
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

			}
		});
		pMute.add(MutationBox);
		
		
		
		//Argumentos del problema
		
		possibilities = _ctrl.getProblemsName();
		
		ProblemsBox = new JComboBox<String>(possibilities);	

		ProblemsBox.setBounds(100, 100, 200, 20);
		ProblemsBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tProblema = (String)ProblemsBox.getSelectedItem();
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

			}
		});
		problem.add(ProblemsBox);
		
		problem.add(new JLabel("			           	                                                          "));
		
		
		//tipo de creacion
		possibilities = this._ctrl.getInicializacionName();
		
		tipoCreacionBox = new JComboBox<String>(possibilities);	
		tipoCreacionBox.setBounds(100, 100, 200, 20);
		tipoCreacionBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tipoIni = (String)tipoCreacionBox.getSelectedItem();
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

			}
		});
		problem.add(tipoCreacionBox);
		
		problem.add(new JLabel("                                         "));
		
		
		//control de bloating
		possibilities = this._ctrl.getBloatingName();
		tCB = possibilities[0];
		tipoBloatingBox = new JComboBox<String>(possibilities);	

		tipoBloatingBox.setBounds(100, 100, 200, 20);
		tipoBloatingBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tCB = (String)tipoBloatingBox.getSelectedItem();
				_ctrl.setAlgorithm(tProblema, sel, mut, comb, tCB, tipoIni);

			}
		});
		problem.add(tipoBloatingBox);
		
		problem.add(new JLabel("                                                                                                                "));
		problem.add(new JLabel("                                                                                                                "));
		L_penalizacion = new JLabel("Penalizacion: ");
		
		problem.add(L_penalizacion);
		T_penalizacion = new JTextField("0.05", 5);
		T_penalizacion.setToolTipText("penalizacion");
		T_penalizacion.setMaximumSize(new Dimension(90, 30));
		T_penalizacion.setMinimumSize(new Dimension(90, 30));
		T_penalizacion.setEditable(true);
		T_penalizacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					penalizacion = Integer.parseInt(T_penalizacion.getText()) ;
					if ( penalizacion > 1 || penalizacion < 0) {
						throw new Exception();
					}
				}
				catch(Exception ex) {
					_ctrl.onException("Parse % penalizacion fail");
				}

			}
		});
		problem.add(T_penalizacion);
		
		problem.add(new JLabel("                                                                                          "));
		problem.add(new JLabel("                                                                                          "));
		problem.add(new JLabel("                                                                                          "));
		
		JLabel ProfMaxLabel = new JLabel("Profundidad Maxima:");
		problem.add(ProfMaxLabel);
		
		maxProf = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
		maxProf.setToolTipText("Max prof from trees");
		maxProf.setMaximumSize(new Dimension(70, 30));
		maxProf.setMinimumSize(new Dimension(70, 30));
		//maxProf.setValue(3);
		problem.add(maxProf);
		
		problem.add(new JLabel("                                                              "));
		
		
		JLabel NinputLabel = new JLabel("Numero de entradas:");
		problem.add(NinputLabel);
		
			
		NinputBox = new JSpinner(new SpinnerNumberModel(3, 1, 3, 1));
		NinputBox.setToolTipText("Number of imputs between 1 - 3");
		NinputBox.setMaximumSize(new Dimension(70, 30));
		NinputBox.setMinimumSize(new Dimension(70, 30));
		//NinputBox.setValue(3);
		problem.add(NinputBox);
		
		problem.add(new JLabel("                                                                   "));
		
		UseIFCheck = new JCheckBox("Use IF ");
		UseIFCheck.setSelected(true);
		problem.add(UseIFCheck);
		
		problem.add(new JLabel("                                       "));
		
		
		principal.add(pSelection, BorderLayout.WEST);
		principal.add(pCross, BorderLayout.CENTER);
		principal.add(pMute, BorderLayout.EAST);
		
		this.add(principal, BorderLayout.NORTH);
		this.add(problem, BorderLayout.SOUTH);
		
		setVisible(sel, tCB);
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
	
	public Double getPenalizacion() throws ParseException {
		return Double.parseDouble(T_penalizacion.getText());
	}
	
	public String getBloating()  throws ParseException {
		return (String) tipoBloatingBox.getSelectedItem();
	}
	
	public String getInicializacion()  throws ParseException {
		return (String) tipoCreacionBox.getSelectedItem();
	}
	
	public String getProblem()  throws ParseException {
		return (String) ProblemsBox.getSelectedItem();
	}
	
	public int getProfMaxima()  throws ParseException {
		return (int) maxProf.getValue();
	}
	
	public boolean getUseIF()  throws ParseException {
		return UseIFCheck.isSelected();
	}
	
	public int getInput()  throws ParseException {
		return (int) NinputBox.getValue();
	}
	
	
	
	public void setVisible(String sel, String tCB) {
		switch (tCB) {
		case Tarpeian.ID:
			T_penalizacion.setVisible(true);
			L_penalizacion.setVisible(true);
			break;
		default:
			T_penalizacion.setVisible(false);
			L_penalizacion.setVisible(false);
		}
		
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
			T_probMejor.setText("0.6");
			L_probMejor.setText("Prob. Gana el mejor: ");
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
	public void onAlgorithmChanged(String _problem, String sel, String mut, String cruce, String tCB, String tIni) {
		this.tProblema = _problem;
		this.tCB = tCB;
		this.tipoIni = tIni;
		this.sel = sel;
		this.mut = mut;
		this.comb = cruce;
		setVisible(sel, tCB);
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
