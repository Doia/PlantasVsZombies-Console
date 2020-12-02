package Logic.Mensajes;

import Exceptions.NoOperationAvaiable;

public class M_Error_Pedir_Fichero extends Mensaje {

	private int idDescarga;
	
	public M_Error_Pedir_Fichero(int idDescarga) {
		super("M_Error_Pedir_Fichero");	
		this.idDescarga = idDescarga;
	}
	
	public int getIdDescarga() throws NoOperationAvaiable{
		return this.idDescarga;
	}

}
