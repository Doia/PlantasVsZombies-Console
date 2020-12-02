package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class HistorialRenderer extends JComponent implements TableCellRenderer {
	  public HistorialRenderer() { }

	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
		 if (value instanceof Integer) {
			 JProgressBar c = new JProgressBar();
			   c.setStringPainted(true);
			 c.setValue((Integer) value);
			 return c;
		 }
		 if (value instanceof String) {
			return new JLabel((String)value);
			 //this.setValue((Integer) value);
		 }
		 else {
			return null;
		 }
	  }
	}
