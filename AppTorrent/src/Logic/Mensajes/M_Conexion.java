package Logic.Mensajes;

public class M_Conexion extends Mensaje {

	String usuarioOrigen;
	
	public M_Conexion(String userID) {
		super("M_Conexion");
		this.usuarioOrigen = userID;
	}
	
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

}
