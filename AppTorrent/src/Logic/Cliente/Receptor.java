package Logic.Cliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import Exceptions.DisconnectException;
import Logic.Mensajes.M_Conexion;
import Logic.Mensajes.Mensaje;

public class Receptor extends Thread {
	
	Cliente cliente;
	Socket socket;
	
	InetAddress host;
	String fileName;
	int port;
	int idDescarga;
	boolean activo;
	double tamFile;
	double progreso;
	
	public Receptor(InetAddress host, int port, String file, int idDescarga, long tamFile, Cliente cliente) {
		super();
		this.cliente = cliente;
		this.host = host;
		this.port = port;
		this.idDescarga = idDescarga;
		File f = new File(file);	
		this.fileName = f.getName();
		this.tamFile = tamFile / 1024;  // tamanio del archivo en KB
		
		activo = true;
	}
	
	public void run() {
		try {
			socket = new Socket(host, port);
			
			downloadFile();		

			socket.close();
			
			cliente.finalizaDescarga(this.idDescarga);
		} catch (Exception e) {
			cliente.ErrorDuranteLaDescarga(idDescarga);
		}
	}

	private void downloadFile() throws Exception {
		try {
			File dir = new File("Descargas");
			if (!dir.exists() && !dir.mkdir()) {
				throw new DisconnectException();
			}
			
			File file = new File("Descargas\\" + fileName);
			
			BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(file));
			InputStream fin = socket.getInputStream();
			
			byte[] b = new byte[1024 * 16];
			int count = fin.read(b);
			progreso += (count / 1024);
			while (count > 0) {
				cliente.paqueteRecibido(idDescarga, (progreso / tamFile) * 100);
				fout.write(b, 0, count);
				count = fin.read(b);
				progreso += (count / 1024);
			}
			fout.close();
			fin.close();
		}
		catch (Exception e) {
			throw new DisconnectException();
		}
		
	}
}
