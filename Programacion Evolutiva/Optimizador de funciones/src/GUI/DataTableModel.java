package GUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import Extras.Controller;
import Extras.Observer;
import Logic.Individuo;


@SuppressWarnings("serial")
public class DataTableModel extends AbstractTableModel implements Observer {

	private String[] _listColumn = {"Generación", "Mejor" ,"Máximo generacion", "Mínimo generacion", "Media"} ;
	private ArrayList<double[]> evaluation; 
	private int generaciones;
	
	DataTableModel(Controller ctrl) {
		evaluation = new ArrayList<double[]>();
		this.generaciones = 0;
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return this.generaciones;
	}
	
	@Override
	public int getColumnCount() {
		return _listColumn.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return _listColumn[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object ret = null;
		switch(getColumnName(columnIndex)) {
			case "Generación": ret = rowIndex + 1; break;
			case "Máximo generacion": ret = evaluation.get(0)[rowIndex]; break;
			case "Mínimo generacion": ret = evaluation.get(1)[rowIndex]; break;		
			case "Mejor": ret = evaluation.get(2)[rowIndex]; break;
			case "Media": ret = evaluation.get(3)[rowIndex]; break;
		}
		return ret;
	}
	

	@Override
	public void onException(String exMsg) {}

	@Override
	public void onRegister() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFinished(ArrayList<double[]> eval, int gen, Individuo mejor) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				
				generaciones = gen;
				evaluation = eval;
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onAlgorithmChanged(String function, String sel, String mut, String cruce) {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
