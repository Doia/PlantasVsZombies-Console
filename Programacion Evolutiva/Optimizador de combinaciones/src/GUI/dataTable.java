package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import Logic.Controller;


@SuppressWarnings("serial")
public class dataTable extends JPanel {
	
	DataTableModel bodyTModel;
	
	dataTable(Controller ctrl) {

		setLayout(new BorderLayout());
		
		bodyTModel = new DataTableModel(ctrl);
		JTable table = new JTable(bodyTModel);
		JScrollPane jS = new JScrollPane(table);
		
		table.setFillsViewportHeight(true);
		this.add(jS);
		
		this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		this.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 300));
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, 300);

	}
}
