package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import Extras.Controller;
import Extras.Observer;
import Logic.Individuo;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements Observer {

	private JToolBar toolBar;
	
	private JButton startButton;
	private JButton funtionButton;
	private JButton seleccionButton;
	private JButton cruceButton;
	private JButton mutacionButton;
	
	private JTextField _time;
	
	private JTextField T_tam;
	private JTextField T_gens;
	private JTextField T_pCruce;
	private JTextField T_pMut;
	private JTextField T_pElite;
	private JTextField T_precision;
	private JTextField T_numVar;

	private String indv;
	private String sel;
	private String comb;
	private String mut;
	private String aux;
	
	private int tam;
	private int gens;
	private double pCruce;
	private double pMut;
	private double pElite;
	private double precision;
	private int numVar;

	private Controller _ctrl;
//	private boolean _stopped;

	ControlPanel(Controller ctrl) {
		super(new BorderLayout());
	
		_ctrl = ctrl;
		
		
		
		indv = ctrl.getFuntionsName()[0];
		sel = ctrl.getSeleccionesName()[0];
		comb = ctrl.getCrucesName()[0];
		mut= ctrl.getMutacionesName()[0];
		
		initGUI();
		_ctrl.addObserver(this);
		
		_ctrl.setAlgorithm(indv, sel, mut, comb);

	}

	private void initGUI() {
		


		toolBar = new JToolBar();
		this.add(toolBar);

		toolBar.addSeparator(new Dimension(10, 10));
		
		// Start Button
		startButton = new JButton();
		startButton.setToolTipText("Start simulation");
		startButton.setIcon(new ImageIcon("resources/icons/run.png"));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run () {
						try {
							precision =Double.parseDouble(T_precision.getText()) ;
							pCruce =Double.parseDouble(T_pCruce.getText());
							pMut =Double.parseDouble(T_pMut.getText()) ;
							tam =Integer.parseInt(T_tam.getText()) ;
							gens =Integer.parseInt(T_gens.getText()) ; 
							pElite =Double.parseDouble(T_pElite.getText()) ;
							numVar =Integer.parseInt(T_numVar.getText()) ;
							
							
						} catch (Exception ex) {
							onException("Load Values Fail");
						}	
						
						try {
							SwingUtilities.invokeLater(new Runnable() {
								public void run () {
									_ctrl.run(precision, tam, gens, pCruce, pMut, pElite, comb, indv, mut, sel, numVar);
								}
							});
							
						}
						catch(Exception ex){
							onException("Run Error!");	
						}
				}
				});
				//setEnable(false);
				
				
			}
		});
		toolBar.add(startButton);
//		
		toolBar.addSeparator(new Dimension(10, 10));
		
//Tipo de funcion
		
		funtionButton = new JButton();
		funtionButton.setToolTipText("Set funcion");
		funtionButton.setIcon(new ImageIcon("resources/icons/funcion.png"));
		funtionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String possibilities[];
				possibilities = _ctrl.getFuntionsName();


				aux = (String) JOptionPane.showInputDialog(null, "Select function to be used:",
						"Funtions Selector", JOptionPane.QUESTION_MESSAGE,
						new ImageIcon("resources/icons/mundo.png"), possibilities, "Funcion Basica");
				if (aux != null) {
					indv = aux;
				}
				_ctrl.setAlgorithm(indv, sel, mut, comb);
				
			}
		});
		toolBar.add(funtionButton);		
		toolBar.addSeparator(new Dimension(10, 10));
		
//Tipo de seleccion
		
		seleccionButton = new JButton();
		seleccionButton.setToolTipText("seleccion");
		seleccionButton.setIcon(new ImageIcon("resources/icons/seleccion.png"));
		seleccionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String possibilities[];
				possibilities = _ctrl.getSeleccionesName();


				aux = (String) JOptionPane.showInputDialog(null, "Select selection method to be used:",
						"selection method", JOptionPane.QUESTION_MESSAGE,
						new ImageIcon("resources/icons/mundo.png"), possibilities, "Seleccion por Torneos");
				if (aux != null) {
					sel = aux;
				}
				_ctrl.setAlgorithm(indv, sel, mut, comb);
			}
		});
		toolBar.add(seleccionButton);
//		
		toolBar.addSeparator(new Dimension(10, 10));
		
//Tipo de cruce

		cruceButton = new JButton();
		cruceButton.setToolTipText("cruce");
		cruceButton.setIcon(new ImageIcon("resources/icons/cruce.png"));
		cruceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String possibilities[];
				possibilities = _ctrl.getCrucesName();


				aux = (String) JOptionPane.showInputDialog(null, "Combination method to be used:",
						"Combination method Selector", JOptionPane.QUESTION_MESSAGE,
						new ImageIcon("resources/icons/mundo.png"), possibilities, "Cruce Monopunto");
				if (aux != null) {
					comb = aux;
				}
				_ctrl.setAlgorithm(indv, sel, mut, comb);
			}
		});
		toolBar.add(cruceButton);
//		
		toolBar.addSeparator(new Dimension(10, 10));
		
//Tipo de mutacion
		
		
		mutacionButton = new JButton();
		mutacionButton.setToolTipText("mutacion");
		mutacionButton.setIcon(new ImageIcon("resources/icons/mutacion.png"));
		mutacionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String possibilities[];
				possibilities = _ctrl.getMutacionesName();


				aux = (String) JOptionPane.showInputDialog(null, "Mutation method to be used:",
						"Mutation method Selector", JOptionPane.QUESTION_MESSAGE,
						new ImageIcon("resources/icons/mundo.png"), possibilities, "Mutacion Basica");
				if (aux != null) {
					mut = aux;
				}
				_ctrl.setAlgorithm(indv, sel, mut, comb);
			}
		});
		toolBar.add(mutacionButton);
		
//Tamaño de la poblacion
		
		toolBar.addSeparator(new Dimension(10, 10));
		
		toolBar.add(new JLabel("Poblacion:  "));
		T_tam = new JTextField("100", 5);
		T_tam.setToolTipText("Tamaño poblacion");
		T_tam.setMaximumSize(new Dimension(70, 30));
		T_tam.setMinimumSize(new Dimension(25, 30));
		T_tam.setEditable(true);
		T_tam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				    tam =Integer.parseInt(T_tam.getText()) ;
				}
				catch(Exception ex) {
					onException("Parse Poblation fail");
				}
			}
		});
		toolBar.add(T_tam);
		
		toolBar.addSeparator(new Dimension(10, 10));

//Numero de generaciones
		
		toolBar.add(new JLabel("Generaciones:  "));
		T_gens = new JTextField("100", 5);
		T_gens.setToolTipText("Nº generaciones");
		T_gens.setMaximumSize(new Dimension(70, 30));
		T_gens.setMinimumSize(new Dimension(25, 30));
		T_gens.setEditable(true);
		T_gens.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
					    gens =Integer.parseInt(T_gens.getText()) ;
					}
					catch(Exception ex) {
						onException("Nº generations fail");
					}
			}
		});
		toolBar.add(T_gens);
		
		toolBar.addSeparator(new Dimension(10, 10));

//Probabilidad de cruce
		
		toolBar.add(new JLabel("Prob. cruce:  "));
		T_pCruce = new JTextField("0.6", 5);
		T_pCruce.setToolTipText("Prob. cruce");
		T_pCruce.setMaximumSize(new Dimension(70, 30));
		T_pCruce.setMinimumSize(new Dimension(25, 30));
		T_pCruce.setEditable(true);
		T_pCruce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pCruce =Double.parseDouble(T_pCruce.getText());
				}
				catch(Exception ex) {
					onException("Parse Prob. cruce fail");
				}
			}
		});
		toolBar.add(T_pCruce);
		
		toolBar.addSeparator(new Dimension(10, 10));
	
//Probabilidad de mutacion
		toolBar.add(new JLabel("Prob. mutacion:  "));
		T_pMut = new JTextField("0.05", 5);
		T_pMut.setToolTipText("Prob. mutacion");
		T_pMut.setMaximumSize(new Dimension(70, 30));
		T_pMut.setMinimumSize(new Dimension(25, 30));
		T_pMut.setEditable(true);
		T_pMut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pMut =Double.parseDouble(T_pMut.getText()) ;
				}
				catch(Exception ex) {
					onException("Parse Prob. mutacion fail");
				}
			}
		});
		toolBar.add(T_pMut);
		toolBar.addSeparator(new Dimension(10, 10));
		
//Probabilidad de elitismo
		
		toolBar.add(new JLabel("Prob. elitismo:  "));
		T_pElite = new JTextField("0.0", 5);
		T_pElite.setToolTipText("Prob. elitismo");
		T_pElite.setMaximumSize(new Dimension(70, 30));
		T_pElite.setMinimumSize(new Dimension(25, 30));
		T_pElite.setEditable(true);
		T_pElite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pElite =Double.parseDouble(T_pElite.getText()) ;
				}
				catch(Exception ex) {
					onException("Parse Prob. elitismo fail");
				}

			}
		});
		toolBar.add(T_pElite);
		toolBar.addSeparator(new Dimension(10, 10));
		
//Precision
		
		toolBar.add(new JLabel("Precision:  "));
		T_precision = new JTextField("0.001", 5);
		T_precision.setToolTipText("Precision");
		T_precision.setMaximumSize(new Dimension(70, 30));
		T_precision.setMinimumSize(new Dimension(25, 30));
		T_precision.setEditable(true);
		T_precision.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					precision =Double.parseDouble(T_precision.getText()) ;
				}
				catch(Exception ex) {
					onException("Parse Precision fail");
				}
				
			}
		});
		toolBar.add(T_precision);
		
		toolBar.addSeparator(new Dimension(10, 10));
		
		toolBar.add(new JLabel("Num Var:  "));
		T_numVar = new JTextField("2", 5);
		T_numVar.setToolTipText("Num Var");
		T_numVar.setMaximumSize(new Dimension(70, 30));
		T_numVar.setMinimumSize(new Dimension(25, 30));
		T_numVar.setEditable(true);
		T_numVar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					numVar =Integer.parseInt(T_numVar.getText()) ;
				}
				catch(Exception ex) {
					onException("Parse nº variables fail");
				}

			}
		});
		toolBar.add(T_numVar);

		toolBar.addSeparator(new Dimension(30, 10));
	}

	@Override
	public void onRegister() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				
			}
		});
		
		
	}


	public void onException(String exMsg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				JOptionPane.showMessageDialog(null, exMsg, "Exception", JOptionPane.ERROR_MESSAGE);
			}
		});
		
	}

	@Override
	public void onFinished(ArrayList<double[]> evaluation, int gen, Individuo mejor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAlgorithmChanged(String function, String sel, String mut, String cruce) {
		// TODO Auto-generated method stub
		
	}



}
