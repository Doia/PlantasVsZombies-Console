package Logic.Mensajes;

import java.util.ArrayList;

import Exceptions.NoOperationAvaiable;

public class M_Confirmacion_Lista_Archivos extends Mensaje {

	ArrayList<ArrayList<String>> listaArchivos;
	
	public M_Confirmacion_Lista_Archivos(ArrayList<ArrayList<String>> listaArchivos) {
		super("M_Confirmacion_Lista_Archivos");
		this.listaArchivos = listaArchivos;
	}

	public ArrayList<ArrayList<String>> getListaArchivos() throws NoOperationAvaiable{
		return this.listaArchivos;
	}
	
	
}
