package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import javax.swing.SwingUtilities;

import Exceptions.ParseException;
import Logic.Controller;
import Logic.Algoritmos.Individuos.Individuo;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements Logic.Observer {

	public final String InitialFileDir = "resources\\datos\\ajuste.txt";
	
	Thread _thread;
	
	private JToolBar toolBar;
	
	private JButton startButton;
	private JButton loadButton;
	
	private JCheckBox maximizacionCheck;
	private JCheckBox nocturnoCheck;
	
	private JTextField T_tam;
	private JTextField T_gens;
	private JTextField T_pCruce;
	private JTextField T_pMut;
	private JTextField T_pElite;
	private JTextField T_precision;
	private JTextField T_participantes;
	
	private JLabel L_tam;
	private JLabel L_gens;
	private JLabel L_pCruce;
	private JLabel L_pMut;
	private JLabel L_pElite;
	private JLabel L_precision;
	private JLabel L_participantes;
	
	
	
	JFileChooser fileChooser;

	private boolean maximizacion;
	private boolean nocturno;
	
	private String fileName;
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
	private double pGMejor;
	private int participantes;

	private AlgorithmPanel APanel;
	private Controller _ctrl;

	ControlPanel(Controller ctrl, AlgorithmPanel APanel) {
		super(new BorderLayout());
	
		_ctrl = ctrl;
		this.APanel = APanel;
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources\\datos"));
		
		fileName = _ctrl.getFileName();
		sel = ctrl.getSeleccionesName()[0];
		comb = ctrl.getCrucesName()[0];
		mut= ctrl.getMutacionesName()[0];
		
		initGUI();
		
		initAjusteFile();
		
		_ctrl.addObserver(this);
		_ctrl.setAlgorithm(fileName, sel, mut, comb);

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
				startButton.setEnabled(false);
				
				try {
					parseInput();
					_thread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								_ctrl.run(precision, tam, gens, pCruce, pMut, pElite, APanel.getCross(), APanel.getMutation(),
										  APanel.getSelection(), participantes, pGMejor, maximizacion);
							} catch (Exception e) {
								onException("Run Error!\n" + e.getMessage());	
							}
							startButton.setEnabled(true);
							_thread = null;
						}
					});
					_thread.start();
				} catch (Exception ex) {
					startButton.setEnabled(true);
					String msg;
					
					if (ex.getMessage() == null) {
						msg = "Error desconocido";
					}
					else {
						msg = ex.getMessage();
					}
					
					onException("Parse Error!\n" + msg);	
				}
			}
		});
			
			/*@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run () {

						try {
							
							parseInput();
							_ctrl.run(precision, tam, gens, pCruce, pMut, pElite, APanel.getCross(), APanel.getMutation(),
									  APanel.getSelection(), participantes, pGMejor, maximizacion);
							
						}
						catch(Exception ex){
							
							String msg;
							
							if (ex.getMessage() == null) {
								msg = "Error desconocido";
							}
							else {
								msg = ex.getMessage();
							}
							
							onException("Run Error!\n" + msg);	
						}
				}
				});
				//setEnable(false);
				
				
			}
		});*/
		toolBar.add(startButton);
//		
		toolBar.addSeparator(new Dimension(10, 10));
		
//file Loader
		
		loadButton = new JButton();
		loadButton.setToolTipText("load problem");
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == loadButton) {
					int selection = fileChooser.showOpenDialog(loadButton);
					if (selection == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						try {
							_ctrl.loadData(file);

						} catch (Exception ex) {
							onException("Load fail");
						}
						fileName = _ctrl.getFileName();
						_ctrl.setAlgorithm(fileName, sel, mut, comb);
					} else {
						//onException("Load fail");
					}
				}
				
			}
		});
		toolBar.add(loadButton);	
		
		//toolBar.addSeparator(new Dimension(10, 10));
		

//Tamaño de la poblacion
		
		toolBar.addSeparator(new Dimension(10, 10));
		
		L_tam = new JLabel("Poblacion:  ");
		
		toolBar.add(L_tam);
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
		
		L_gens = new JLabel("Generaciones:  ");
		
		toolBar.add(L_gens);
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
		
		L_pCruce = new JLabel("Prob. cruce:  ");
		
		toolBar.add(L_pCruce);
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
		
		L_pMut = new JLabel("Prob. mutacion:  ");
		
		toolBar.add(L_pMut);
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
		
		L_pElite = new JLabel("Prob. elitismo:  ");
		
		toolBar.add(L_pElite);
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
		
		L_precision = new JLabel("Precision:  ");
		
		toolBar.add(L_precision);
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
		
		maximizacionCheck = new JCheckBox("Maximizar/Minimizar");
		
		toolBar.add(maximizacionCheck);
			
		toolBar.addSeparator(new Dimension(10, 10));
		
		//MODO NOCTURNO EN DESARROLLO
		
		nocturnoCheck = new JCheckBox("Modo nocturno");
		nocturnoCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cambiaModo(nocturnoCheck.isSelected());

			}
		});
		
		nocturnoCheck.setVisible(false);
		
		toolBar.add(nocturnoCheck);
		
		toolBar.addSeparator(new Dimension(30, 10));
	}
	
	public void parseInput() throws ParseException {
		try {
			precision =Double.parseDouble(T_precision.getText());
			pCruce =Double.parseDouble(T_pCruce.getText());
			pMut =Double.parseDouble(T_pMut.getText());
			tam =Integer.parseInt(T_tam.getText());
			gens =Integer.parseInt(T_gens.getText()); 
			pElite =Double.parseDouble(T_pElite.getText());
			participantes = APanel.getParticipantes();
			pGMejor  = APanel.getPMejor();
			maximizacion = maximizacionCheck.isSelected();
		}
		catch(Exception ex) {throw new ParseException("Format error input data");}
		
		if (!_ctrl.isDataOk()) throw new ParseException("File data is corrupt");
		
		if (tam < 0) throw new ParseException("Negative Poblation");
		if (gens < 0) throw new ParseException("Negative Generations");
		
		if (pCruce < 0) throw new ParseException("Negative Cross probability");
		if (pMut < 0) throw new ParseException("Negative Mutation probability");
		if (pElite < 0) throw new ParseException("Negative Elite probability");
		if (pGMejor < 0) throw new ParseException("Negative winrate of the best probability");
		
		if (pCruce > 1) throw new ParseException("Cross probability can not be more than 1");
		if (pMut > 1) throw new ParseException("Mutation probability  can not be more than 1");
		if (pElite > 1) throw new ParseException("Elite probability  can not be more than 1");
		if (pGMejor > 1) throw new ParseException("Winrate of the best can not be more than 1");
		
		if (participantes < 0) throw new ParseException("Negative Participants");
		if (precision < 0) throw new ParseException("Negative Accuracy");
		
		if (participantes > 0.2 * tam) throw new ParseException("participants can not be more than 20% of poblation ( " + (int) (0.2 * tam) + " )" );
		if (precision > 1) throw new ParseException("Accuracy can not be more than 1");
		
	}
	
	public void cambiaModo(boolean nocturno) {
		Color c, l;
		if (nocturno) {c = Color.BLACK; l = Color.WHITE;}
		else {l = Color.BLACK; c = Color.WHITE;}
		
		toolBar.setBackground(c);
		
		startButton.setBackground(c);
		loadButton.setBackground(c);
		
		maximizacionCheck.setBackground(c);
		nocturnoCheck.setBackground(c);
		
		//Cambia el color de fondo
		T_tam.setBackground(c);
		T_gens.setBackground(c);
		T_pCruce.setBackground(c);
		T_pMut.setBackground(c);
		T_pElite.setBackground(c);
		T_precision.setBackground(c);
		T_participantes.setBackground(c);
		
		//Cambia color de las letras
		T_tam.setForeground(l);
		T_gens.setForeground(l);
		T_pCruce.setForeground(l);
		T_pMut.setForeground(l);
		T_pElite.setForeground(l);
		T_precision.setForeground(l);
		T_participantes.setForeground(l);
		
		L_tam.setForeground(l);
		L_gens.setForeground(l);
		L_pCruce.setForeground(l);
		L_pMut.setForeground(l);
		L_pElite.setForeground(l);
		L_precision.setForeground(l);
		L_participantes.setForeground(l);
		
		maximizacionCheck.setBackground(c);
		nocturnoCheck.setBackground(c);
		
		maximizacionCheck.setForeground(l);
		nocturnoCheck.setForeground(l);
		
	}
	
	public void initAjusteFile() {
		File f = new File(InitialFileDir);
		if (f.exists()) {
			try {
				_ctrl.loadData(f);
				fileName = f.getName();
				_ctrl.setAlgorithm(this.fileName, sel, mut, this.comb);
			}
			catch(Exception ex) {
				onException("file " + f.getName() + " is corrupt.");
			}
		}
		else {
			onException("Warning!!\nThere is no file loaded");
		}
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
				JOptionPane.showMessageDialog(null, exMsg, "Excepcion", JOptionPane.ERROR_MESSAGE);
			}
		});
		
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
