package Logic.Mensajes;

import Exceptions.NoOperationAvaiable;

public class M_Dejar_Compartir_Archivo extends Mensaje {

	String filePath;
	
	public M_Dejar_Compartir_Archivo(String filePath) {
		super("M_Dejar_Compartir_Archivo");
		this.filePath = filePath;
	}
	
	public String getFichero() throws NoOperationAvaiable {
		return this.filePath;
	}
	
}
