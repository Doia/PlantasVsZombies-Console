package GUI;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import Logic.Cliente.Cliente;
import Logic.Cliente.Observer;

public class TM_Usuarios extends AbstractTableModel implements Observer {

	private String clienteID;
	private String[] _listColumn = {"Conectados"} ;
	private ArrayList<String> users; 
	private int generaciones;
	
	TM_Usuarios(Cliente cliente) {
		users = new ArrayList<String>();
		
		clienteID = cliente.getIdCliente();
		cliente.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return users.size();
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
			case "Conectados": ret = users.get(rowIndex); break;
		}
		return ret;
	}

	@Override
	public void onRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdateUsuarios(ArrayList<String> listusuarios) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				users.clear();
				for (String userID: listusuarios) {
					if (userID.equals(clienteID)) users.add(userID + " (Tú)"); 
					else users.add(userID);
				}
				fireTableStructureChanged();
			}
		});
	}
	
	@Override
	public void onUpdateArchivos(ArrayList<ArrayList<String>> listaArchivos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartDownload(int id, String propietario, String file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPackageRecieve(int idDescarga, double progreso) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinishDownload(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(String exMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTransmisionError(int idDescarga) {
		// TODO Auto-generated method stub
		
	}

}
