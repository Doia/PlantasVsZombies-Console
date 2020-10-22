package simulator.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Optimizador de funciones");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
	
		//Control Panel
		ControlPanel cp = new ControlPanel(_ctrl); 
		mainPanel.add(cp, BorderLayout.PAGE_START);
		
		//Bodies Table
		BodiesTable bTable = new BodiesTable(_ctrl);
		
		//Viewer
		Viewer viewer = new Viewer(_ctrl);
		
		JPanel centerPanel = new JPanel();
		
		centerPanel.setLayout(new BoxLayout (centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(bTable);
		centerPanel.add(viewer);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		//Status bar
		StatusBar statusb = new StatusBar(_ctrl);
		mainPanel.add(statusb, BorderLayout.PAGE_END);
		
		mainPanel.setVisible(true);
		this.add(mainPanel);
		this.setVisible(true);
		this.pack(); 
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setContentPane(mainPanel);
	}
}
