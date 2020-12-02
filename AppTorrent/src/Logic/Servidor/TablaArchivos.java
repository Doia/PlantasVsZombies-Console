package Logic.Servidor;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class TablaArchivos{

	ArrayList<ArchivosDeUsuario> listArchivos;
	
	public TablaArchivos() {
		listArchivos = new ArrayList<ArchivosDeUsuario>();
	}
	
	public synchronized boolean addArchivo(String user, String file) {
		boolean added = false;
		for (ArchivosDeUsuario archivo : listArchivos) {
			if (archivo.getId().equals(user)) {
				added = archivo.add(file);
				break;
			}
		}
		return added;
	}
	
	public synchronized boolean add(String user) {
		ArchivosDeUsuario archivos = new ArchivosDeUsuario(user);
		if (!listArchivos.contains(archivos)) {
			listArchivos.add(archivos);
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized boolean deleteArchivo(String user, String file) {	
		boolean del = false;
		for (ArchivosDeUsuario archivo : listArchivos) {
			if (archivo.getId().equals(user)) {
				del = archivo.delete(file);
				break;
			}
		}
		return del;
	}
	
	public synchronized void delete(String user) {
		listArchivos.remove(new ArchivosDeUsuario(user));
	}

	public synchronized ArrayList<ArchivosDeUsuario> getArchivos() {
		return this.listArchivos;
	}
	
	
}
