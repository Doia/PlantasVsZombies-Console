package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private String[] _listColumn = {"Id", "Mass", "Position", "Velocity", "Acceleration"} ;
	private List<Body> _bodies;
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _bodies == null ? 0 : _bodies.size();
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
			case "Id": ret = _bodies.get(rowIndex).getId(); break;
			case "Mass": ret = _bodies.get(rowIndex).getMass(); break;
			case "Position": ret = _bodies.get(rowIndex).getPosition(); break;			
			case "Velocity": ret = _bodies.get(rowIndex).getVelocity(); break;
			case "Acceleration": ret = _bodies.get(rowIndex).getAceleration(); break;
		}
		return ret;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}
	
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}
	
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}
	
	@Override
	public void onBodyRemoved(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
		
	}
	
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {}
	
	@Override
	public void onGravityLawChanged(String gLawsDesc) {}

	@Override
	public void onException(String exMsg) {}

	
}
