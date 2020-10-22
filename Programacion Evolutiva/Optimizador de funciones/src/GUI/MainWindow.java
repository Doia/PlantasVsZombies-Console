package GUI;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


import Extras.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Algortimo Evolutivo");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
	
		//Control Panel
		ControlPanel cp = new ControlPanel(_ctrl); 
		mainPanel.add(cp, BorderLayout.PAGE_START);
		
		//data Table
		dataTable dTable = new dataTable(_ctrl);
		
		//plot
		Myplot plot = new Myplot(_ctrl);
		
		StatusBar bar = new StatusBar(_ctrl);
		
		JPanel centerPanel = new JPanel();
		
		centerPanel.setLayout(new BoxLayout (centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(dTable);
		centerPanel.add(plot);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(bar, BorderLayout.PAGE_END);
		
		mainPanel.setVisible(true);
		this.add(mainPanel);
		this.setVisible(true);
		this.pack(); 
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setExtendedState(MAXIMIZED_BOTH);
		setContentPane(mainPanel);
	}
}
