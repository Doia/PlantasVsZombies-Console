package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class BodiesTable extends JPanel {
	
	BodiesTableModel bodyTModel;
	
	BodiesTable(Controller ctrl) {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black, 2),
			"Bodies",
			TitledBorder.LEFT, TitledBorder.TOP));
		
		bodyTModel = new BodiesTableModel(ctrl);
		JTable table = new JTable(bodyTModel);
		JScrollPane jS = new JScrollPane(table);
		
		table.setFillsViewportHeight(true);
		this.add(jS);
		this.setMaximumSize(new Dimension(2000,200));
		this.setPreferredSize(new Dimension(2000,200));	
	}
}
