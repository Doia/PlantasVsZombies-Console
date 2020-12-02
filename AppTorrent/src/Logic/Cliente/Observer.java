package Logic.Cliente;

import java.util.ArrayList;

public interface Observer {
	public void onRegister();
	public void onUpdateUsuarios(ArrayList<String> lista);
	public void onUpdateArchivos(ArrayList<ArrayList<String>> listaArchivos);
	public void onUpload();
	public void onDelete();
	public void onStartDownload(int id, String propietario, String file);
	public void onPackageRecieve(int idDescarga, double progreso);
	public void onFinishDownload(int id);
	public void onTransmisionError(int idDescarga);
	public void onException(String exMsg);
}
