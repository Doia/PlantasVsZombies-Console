package GUI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import Logic.Cliente.Cliente;
import Logic.Cliente.Observer;

public class TM_DownloadFiles extends AbstractTableModel implements Observer {

	private String clienteID;
	private String[] _listColumn = {"Propietario", "Archivo"} ;
	private ArrayList<String> propietarios; 
	private ArrayList<String> archivos; 

	TM_DownloadFiles(Cliente cliente) {
		propietarios = new ArrayList<String>();
		archivos = new ArrayList<String>();
		clienteID = cliente.getIdCliente();
		cliente.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return archivos.size();
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
			case "Propietario": ret = propietarios.get(rowIndex); break;
			case "Archivo": ret = new File(archivos.get(rowIndex)).getName(); break;
		}
		return ret;
	}
	
	public String getFullPath(int row) {
		return archivos.get(row);
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
				propietarios.clear();
				archivos.clear();
				String userID;
				for (ArrayList<String> user: listaArchivos) {
					if (!user.get(0).equals(clienteID)) {
						userID = user.get(0);
						for (int i = 1; i < user.size(); i++) {
							propietarios.add(userID);
							archivos.add(user.get(i));
						}
					}
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
