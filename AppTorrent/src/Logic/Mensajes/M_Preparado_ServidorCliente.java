package Logic.Mensajes;

import java.net.InetAddress;

import Exceptions.NoOperationAvaiable;

public class M_Preparado_ServidorCliente extends Mensaje {

	InetAddress origen;
	String fichero;
	int portOrigen;
	int idDescarga;
	long tamFile;
	
	public M_Preparado_ServidorCliente(InetAddress origen, int portOrigen, String fichero, int idDescarga, long tamFile) {
		super("M_Preparado_ServidorCliente");
		this.origen = origen;
		this.fichero = fichero;
		this.portOrigen = portOrigen;
		this.idDescarga = idDescarga;
		this.tamFile = tamFile;
	}
	
	public InetAddress getOrigen() throws NoOperationAvaiable {
		return this.origen;
	}
	
	public int getPortOrigen() throws NoOperationAvaiable {
		return this.portOrigen;
	}
	
	public String getFichero() throws NoOperationAvaiable {
		return this.fichero;
	} 
	
	public int getIdDescarga() throws NoOperationAvaiable{
		return this.idDescarga;
	}
	public long getTamFile() throws NoOperationAvaiable{
		return this.tamFile;
	}

}
