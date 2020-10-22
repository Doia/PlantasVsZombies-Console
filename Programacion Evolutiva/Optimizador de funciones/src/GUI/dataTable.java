package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Extras.Controller;


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
		this.setMaximumSize(new Dimension(2000,300));
		this.setPreferredSize(new Dimension(2000,300));	
	}
}
