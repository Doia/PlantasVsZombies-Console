package Logic.Mensajes;

import java.util.ArrayList;

import Logic.Servidor.Usuario;

public class M_Confirmacion_Lista_Usuarios extends Mensaje {
	
	ArrayList<String> listaUsuarios;

	public M_Confirmacion_Lista_Usuarios(ArrayList<String> listaUsuarios) {
		super("M_Confirmacion_Lista_Usuarios");
		this.listaUsuarios = listaUsuarios;
	}
	
	public ArrayList<String> getUsuarios(){
		return this.listaUsuarios;
	}

}
