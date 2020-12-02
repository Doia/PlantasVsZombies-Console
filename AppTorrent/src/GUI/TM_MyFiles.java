package GUI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import Logic.Cliente.Cliente;
import Logic.Cliente.Observer;

public class TM_MyFiles extends AbstractTableModel implements Observer {

	private String clienteID;
	private String[] _listColumn = {"Tus Archivos"} ;
	private ArrayList<String> myFiles; 
	private int generaciones;
	
	TM_MyFiles(Cliente cliente) {
		myFiles = new ArrayList<String>();
		this.generaciones = 0;
		clienteID = cliente.getIdCliente();
		cliente.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return myFiles.size();
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
			case "Tus Archivos": ret = new File(myFiles.get(rowIndex)).getName(); break;
		}
		return ret;
	}
	
	public String getFullPath(int row) {
		return myFiles.get(row);
	}

	@Override
	public void onRegister() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdateUsuarios(ArrayList<String> listusuarios) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onUpdateArchivos(ArrayList<ArrayList<String>> listaArchivos) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				boolean encontrado = false;
				myFiles.clear();
				for (ArrayList<String> user: listaArchivos) {
					if (user.get(0).equals(clienteID)) {
						for (int i = 1; i < user.size(); i++) {
							myFiles.add(user.get(i));
						}
						encontrado = true;
					}
					if (encontrado) break;
				}
				fireTableStructureChanged();
			}
		});
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
