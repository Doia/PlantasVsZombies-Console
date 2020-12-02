package Logic.Mensajes;

import Exceptions.NoOperationAvaiable;

public class M_Emitir_Fichero extends Mensaje {

	String fichero;
	String usuarioOrigen;
	int idDescarga;
	
	public M_Emitir_Fichero(String userId, String fichero, int idDescarga) {
		super("M_Emitir_Fichero");
		this.usuarioOrigen = userId;
		this.fichero = fichero;
		this.idDescarga = idDescarga;
	}
	
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}
	
	public String getFichero() throws NoOperationAvaiable {
		return this.fichero;
	}
	
	public int getIdDescarga() throws NoOperationAvaiable{
		return this.idDescarga;
	}

}
