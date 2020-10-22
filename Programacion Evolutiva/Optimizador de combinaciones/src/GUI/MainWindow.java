package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import Logic.Controller;

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
		
		mainPanel.setAutoscrolls(true);
		//mid-top Panel
		dataTable dTable = new dataTable(_ctrl);
		AlgorithmPanel aPanel = new AlgorithmPanel(_ctrl);
		//Control Panel
		ControlPanel cp = new ControlPanel(_ctrl, aPanel); 
		mainPanel.add(cp, BorderLayout.PAGE_START);
		
		//top Panel
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout (topPanel, BoxLayout.X_AXIS));
		
		//top Table

		topPanel.add(aPanel);
		topPanel.add(dTable);
		topPanel.setMaximumSize(new Dimension(2000,300));
		topPanel.setPreferredSize(new Dimension(2000,300));	
		
		//plot
		Myplot plot = new Myplot(_ctrl);
		
		StatusBar bar = new StatusBar(_ctrl);
		
		JPanel centerPanel = new JPanel();
		
		centerPanel.setLayout(new BoxLayout (centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(topPanel);
		centerPanel.add(plot);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(bar, BorderLayout.PAGE_END);
		
		mainPanel.setVisible(true);
		this.add(mainPanel);

		this.setVisible(true);
		this.pack(); 
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setExtendedState(MAXIMIZED_BOTH);
		//this.setResizable(false);
		setContentPane(mainPanel);
	}
}
