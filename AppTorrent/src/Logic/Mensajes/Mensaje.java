package Logic.Mensajes;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import Exceptions.NoOperationAvaiable;
import Logic.Servidor.ArchivosDeUsuario;
import Logic.Servidor.Usuario;

public abstract class Mensaje implements Serializable {
	
	private String tipoMensaje;
	private InetAddress origen;
	
	
	
	
	private Object[] args;
	
	public Mensaje(String id) {
		this.tipoMensaje = id;
	}
	
	public boolean equals(String tipoMensaje) {
		return this.tipoMensaje.equals(tipoMensaje);
	}
	
	public String getTipo() {
		return this.tipoMensaje;
	}
	
	public String getUsuarioDestino() throws NoOperationAvaiable {
		throw new NoOperationAvaiable();
	}
	
	public String getFichero() throws NoOperationAvaiable {
		throw new NoOperationAvaiable();
	}
	
	public String getUsuarioOrigen() throws NoOperationAvaiable {
		throw new NoOperationAvaiable();
	}
	
	public InetAddress getOrigen() throws NoOperationAvaiable {
		throw new NoOperationAvaiable();
	}
	
	public int getPortOrigen() throws NoOperationAvaiable {
		throw new NoOperationAvaiable();
	}
	
	public ArrayList<String> getUsuarios() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
	
	public ArrayList<ArrayList<String>> getListaArchivos() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
	
	public ArrayList<ArchivosDeUsuario> getArchivosUsuarios() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
	
	public int getIdDescarga() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
	public long getTamFile() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
	
	public String getError() throws NoOperationAvaiable{
		throw new NoOperationAvaiable();
	}
}
