package Logic.Servidor;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TablaUsuarios {

	ArrayList<Usuario> listUsuarios;
	
	public TablaUsuarios() {
		listUsuarios = new ArrayList<Usuario>();
	}
	
	public synchronized boolean add(Usuario user) {
		if (!listUsuarios.contains(user)) {
			listUsuarios.add(user);
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized void delete(Usuario user) {
		listUsuarios.remove(user);
	}
	
	public synchronized ArrayList<Usuario> getUsuarios() {
		return listUsuarios;
	}
	
	public synchronized ObjectOutputStream getWriter(String usuario) {
		for (Usuario user: listUsuarios) {
			if (user.equals(usuario)) {
				return user.getWriter();
			}
		}
		return null;
	}
	
}
