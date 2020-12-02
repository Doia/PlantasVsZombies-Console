package Logic.Mensajes;

import Exceptions.NoOperationAvaiable;

public class M_PedirFichero extends Mensaje {

	String userDest;
	String filePath;
	int idDescarga; //permite distinguir dos descargas del mismo archivo (util si se usa la interfaz grafica) 
	
	public M_PedirFichero(String userDest, String filePath, int idDescarga) {
		super("M_PedirFichero");
		this.userDest = userDest;
		this.filePath = filePath;
		this.idDescarga = idDescarga;
	}
	
	public String getUsuarioDestino() throws NoOperationAvaiable {
		return this.userDest;
	}
	
	public String getFichero() throws NoOperationAvaiable {
		return this.filePath;
	}
	
	public int getIdDescarga() throws NoOperationAvaiable{
		return this.idDescarga;
	}

}
