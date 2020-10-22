package simulator.view;

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

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {

	private JToolBar toolBar;

	private JButton loadButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton lawsButton;
	private JButton offButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JButton clearButton;

	private JSpinner _steps;
	private JSpinner _delay;
	private JTextField _time;
	private JFileChooser fileChooser;

	private volatile Thread _thread;

	private Controller _ctrl;
//	private boolean _stopped;

	ControlPanel(Controller ctrl) {
		super(new BorderLayout());
		_ctrl = ctrl;
		//_stopped = true;
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\ddemi\\OneDrive\\Escritorio\\Java\\workspace java\\PhysicSimulator\\resources\\examples"));
		initGUI();
		_ctrl.addObserver(this);
	}

	private void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("resources/icons/salir.png"),
				null, null);
		if (n == 0) {
			System.exit(0);
		}
	}

	private void initGUI() {

		toolBar = new JToolBar();
		this.add(toolBar);

		// Load Button
		loadButton = new JButton();
		loadButton.setToolTipText("Load file");
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loadButton) {
					int selection = fileChooser.showOpenDialog(loadButton);
					if (selection == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						_ctrl.reset();
						try {
							_ctrl.loadBodies(new FileInputStream(file));
						} catch (FileNotFoundException e1) {
							onException("Load fail");
						}
					} else {
						//onException("Load fail");
					}
				}
			}
		});
		toolBar.add(loadButton);

		toolBar.addSeparator(new Dimension(10, 10));

		// Save Button
		saveButton = new JButton();
		saveButton.setToolTipText("Save file");
		saveButton.setIcon(new ImageIcon("resources/icons/save.png"));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == saveButton) {
					int selection = fileChooser.showSaveDialog(saveButton);
					if (selection == JFileChooser.APPROVE_OPTION) {
		
						File file = fileChooser.getSelectedFile();
						_ctrl.save(file);
					} else {
						//onException("Save fail");
					}
				}
				
			}
		});
		toolBar.add(saveButton);

		toolBar.addSeparator(new Dimension(10, 10));		
		
		// Laws Button
		lawsButton = new JButton();
		lawsButton.setToolTipText("Set law");
		lawsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		lawsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String law = null;

				Object possibilities[] = new String[_ctrl.getGravityLawsFactory().getInfo().size()];
				for (int i = 0; i < possibilities.length; i++) {
					possibilities[i] = _ctrl.getGravityLawsFactory().getInfo().get(i).getString("type");
				}

				String election = (String) JOptionPane.showInputDialog(null, "Select gravity laws to be used:",
						"Gravity Laws Selector", JOptionPane.QUESTION_MESSAGE,
						new ImageIcon("resources/icons/mundo.png"), possibilities, "nglu");

				law = setGravitylaw(election);
				if (law == null) {
					onException("Wrong law");
				}
			}
		});
		toolBar.add(lawsButton);

		toolBar.addSeparator(new Dimension(10, 10));

		// Start Button
		startButton = new JButton();
		startButton.setToolTipText("Start simulation");
		startButton.setIcon(new ImageIcon("resources/icons/run.png"));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setEnable(false);
				//_stopped = false;
				try {
					_thread = new Thread(new Runnable() {
						@Override
						public void run() {
							_ctrl.setDeltaTime(Double.parseDouble(_time.getText()));
							run_sim((int) _steps.getValue(), (int) _delay.getValue());
							setEnable(true);
							_thread = null;
						}
					});
					_thread.start();
				} catch (NumberFormatException ex) {
					//_stopped = true;
					setEnable(true);
					onException("Wrong number format");
				}
			}
		});
		toolBar.add(startButton);

		toolBar.addSeparator(new Dimension(10, 10));

		// Stop Button
		
		stopButton = new JButton();
		stopButton.setToolTipText("Stop simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_thread != null) {
					
					//Hay que interrumpir el hilo
					_thread.interrupt();
				}
			}
		});
		toolBar.add(stopButton);
		

		toolBar.addSeparator(new Dimension(10, 10));
		
		// add Button
		addButton = new JButton();
		addButton.setToolTipText("add new body");
		addButton.setIcon(new ImageIcon("resources/icons/add.png"));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddWindow(_ctrl); 
			}
		});
		toolBar.add(addButton);

		toolBar.addSeparator(new Dimension(10, 10));
		
		
		//remove Button
		
		removeButton = new JButton();
		removeButton.setToolTipText("remove body");
		removeButton.setIcon(new ImageIcon("resources/icons/delete.png"));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean removed = false;
				ArrayList<String> listId = _ctrl.getIds_BodyList();
				
				if (listId.isEmpty()) {
					onException("There is no bodies to remove");
				}
				else {
					
					Object possibilities[] = new String[listId.size()];
					for (int i = 0; i < possibilities.length; i++) {
						possibilities[i] = listId.get(i);
					}
	
					String election = (String) JOptionPane.showInputDialog(null, "Select body to be deleted:",
							"List of bodies", JOptionPane.QUESTION_MESSAGE,
							new ImageIcon("resources/icons/mundo.png"), possibilities, "nglu");
	
	
					removed = _ctrl.removeBody(election);
					if (removed == false) {
						onException("Wrong body");
					}
				}

			}
		});
		toolBar.add(removeButton);
		
		toolBar.addSeparator(new Dimension(10, 10));
		
		// Clear Button
		clearButton = new JButton();
		clearButton.setToolTipText("clear simulation");
		clearButton.setIcon(new ImageIcon("resources/icons/clear.png"));
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to reset?", "Reset",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("resources/icons/clear.png"),
						null, null);
				if (n == 0) {
					_ctrl.reset();;
				}

			}
		});
		toolBar.add(clearButton);




		toolBar.addSeparator(new Dimension(30, 10));

		// JSpinner DELAY
		toolBar.add(new JLabel("Delay: "));
		_delay = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
		_delay.setToolTipText("delay between steps: 0-1000");
		_delay.setMaximumSize(new Dimension(70, 30));
		_delay.setMinimumSize(new Dimension(70, 30));
		_delay.setValue(1);
		toolBar.add(_delay);

		toolBar.addSeparator(new Dimension(30, 10));

		// JSpinner STEPS
		toolBar.add(new JLabel("Steps: "));
		_steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000000, 1));
		_steps.setToolTipText("pasos a ejecutar: 1-1.000.000");
		_steps.setMaximumSize(new Dimension(70, 30));
		_steps.setMinimumSize(new Dimension(70, 30));
		_steps.setValue(10000);
		toolBar.add(_steps);

		toolBar.addSeparator(new Dimension(30, 10));

		// JTextField DT
		toolBar.add(new JLabel("Delta-Time:   "));
		_time = new JTextField("0", 5);
		_time.setToolTipText("Delta time");
		_time.setMaximumSize(new Dimension(70, 30));
		_time.setMinimumSize(new Dimension(70, 30));
		_time.setEditable(true);
		toolBar.add(_time);

		toolBar.addSeparator(new Dimension(30, 10));

		toolBar.add(Box.createHorizontalGlue());

		// Off Button
		offButton = new JButton();
		offButton.setToolTipText("Exit");
		offButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		offButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		offButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		toolBar.add(offButton);
	}

	@SuppressWarnings("static-access")
	private void run_sim(int n, int d) {
		
		while ( n>0 && !_thread.isInterrupted() ) {
			// 1. execute the simulator one step, i.e., call method
			// _ctrl.run(1) and handle exceptions if any
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				onException("Run fail");
			}
			// 2. sleep the current thread for ’delay’ milliseconds
			try {
				_thread.sleep((int)_delay.getValue());
			} catch (InterruptedException e) {
				_thread.interrupt();
			}
			n--;
			_steps.setValue(n);
		}

	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_time.setText("" + dt);
			}
		});
		
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_time.setText("" + dt);
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
	}
	@Override
	public void onBodyRemoved(List<Body> bodies) {
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_time.setText("" + dt);
			}
		});
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
	}

	@Override
	public void onException(String exMsg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				JOptionPane.showMessageDialog(null, exMsg, "Exception", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	// Change the gravity law
	private String setGravitylaw(String election) {
		String ret = null;
		boolean changed = false;
		int i = 0;
		while (i < _ctrl.getGravityLawsFactory().getInfo().size() && !changed) {

			if (_ctrl.getGravityLawsFactory().getInfo().get(i).getString("type").equals(election)) {

				_ctrl.setGravityLaws(_ctrl.getGravityLawsFactory().getInfo().get(i));
				ret = _ctrl.getGravityLawsFactory().getInfo().get(i).getString("type");
				changed = true;
			}
			i++;
		}
		return ret;
	}

	// Enable or disable the buttons
	private void setEnable(boolean enable) {
		loadButton.setEnabled(enable);
		saveButton.setEnabled(enable);
		startButton.setEnabled(enable);
		lawsButton.setEnabled(enable);
		addButton.setEnabled(enable);
		removeButton.setEnabled(enable);
		clearButton.setEnabled(enable);
		
		_steps.setEnabled(enable);
		_time.setEnabled(enable);
		_delay.setEnabled(enable);
	}


}
