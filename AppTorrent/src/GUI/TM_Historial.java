package GUI;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import Logic.Cliente.Cliente;
import Logic.Cliente.Observer;

public class TM_Historial extends AbstractTableModel implements Observer {


	
	private String[] _listColumn = {"Archivo", "Propietario" ,"Estado", "Porcentaje"} ;
	
	ArrayList<String> archivos;
	ArrayList<String> propietarios;
	ArrayList<String> estados;
	ArrayList<Integer> progress;
	ArrayList<Integer> ids;
	int idDescargaNext;

	
	TM_Historial(Cliente cliente) {
		archivos = new ArrayList<String>();
		propietarios = new ArrayList<String>();
		estados = new ArrayList<String>();
		progress = new ArrayList<Integer>();
		idDescargaNext = 0;
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
			case "Archivo": ret = archivos.get(rowIndex); break;
			case "Propietario": ret = propietarios.get(rowIndex); break;
			case "Estado": ret = estados.get(rowIndex); break;		
			case "Porcentaje": ret = progress.get(rowIndex); break;
		}
		return ret;
	}
	
	public int getNextIdDescarga() {
		int re = idDescargaNext;
		idDescargaNext++;
		return re;
	}

	
	@Override
	public void onStartDownload(int id, String propietario, String file) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				archivos.add(new File(file).getName());
				propietarios.add(propietario);
				estados.add("Conectando...");
				progress.add(new Integer(0));
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onPackageRecieve(int idDescarga, double progreso) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				double porcentaje = 0.0;
				estados.set(idDescarga, "Descargando...");
				porcentaje = round(progreso); // redondea a dos decimales
				progress.set(idDescarga, new Integer((int)porcentaje));
				fireTableStructureChanged();
			}
		});	

	}
	
	private double round(double n) {
		double ratio = 1.0 / 0.01;
		return (int)(n*ratio) / ratio;
	}

	@Override
	public void onFinishDownload(int id) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				estados.set(id, "Finalizado");
				progress.set(id, new Integer(100));
				fireTableStructureChanged();
			}
		});	
	}
	
	@Override
	public void onTransmisionError(int idDescarga) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				estados.set(idDescarga, "Error");
				fireTableStructureChanged();
			}
		});	
	}
	
	@Override
	public void onRegister() {}

	@Override
	public void onUpdateUsuarios(ArrayList<String> listusuarios) {}
	
	@Override
	public void onUpdateArchivos(ArrayList<ArrayList<String>> listaArchivos) {}

	@Override
	public void onUpload() {}

	@Override
	public void onDelete() {}

	@Override
	public void onException(String exMsg) {}



}
