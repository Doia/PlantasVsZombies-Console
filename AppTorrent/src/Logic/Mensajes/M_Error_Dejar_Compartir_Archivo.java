package Logic.Mensajes;

import Exceptions.NoOperationAvaiable;

public class M_Error_Dejar_Compartir_Archivo extends Mensaje {

	String error;
	
	public M_Error_Dejar_Compartir_Archivo(String error) {
		super("M_Error_Dejar_Compartir_Archivo");
	}
	
	public String getError() throws NoOperationAvaiable {
		return error;
	}

}
