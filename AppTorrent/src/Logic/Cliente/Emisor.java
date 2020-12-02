package Logic.Cliente;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import Logic.Mensajes.M_Cerrar_Conexion;
import Logic.Mensajes.M_Confirmacion_Conexion;
import Logic.Mensajes.M_Error_Establecer_Conexion;
import Logic.Mensajes.M_Preparado_ClienteServidor;
import Logic.Mensajes.Mensaje;

public class Emisor extends Thread {

	Cliente cliente;
	Socket socket;
	ObjectOutputStream writerToServer;
	
	String idDest;
	String fichero;
	boolean activo;
	ServerSocket server;
	int portIndex;
	int idDescarga;
	
	public Emisor(ObjectOutputStream writerToServer, String idDest ,String fichero, int portIndex, int idDescarga, Cliente cliente) {
		super();
		this.cliente = cliente;
		this.idDest = idDest;
		this.fichero = fichero;
		this.portIndex = portIndex;
		this.writerToServer = writerToServer;
		this.idDescarga = idDescarga;
		activo = true;
		
	}
	
	public void run() {

		try {
			
			this.cliente.acquireEmision(portIndex);
			writerToServer.writeObject(new M_Preparado_ClienteServidor(idDest, cliente.getAddress(), cliente.getMyPort(portIndex), fichero, idDescarga, new File(fichero).length()));

			server = new ServerSocket(cliente.getMyPort(portIndex));

			socket = server.accept();
			
			sendFile();
			
			socket.close();
			server.close();
			Thread.sleep(250); //Tiempo de espera
			this.cliente.releaseEmision(portIndex);
			
		} catch (Exception e) {	
			if (!socket.isClosed()) {
				try {
					socket.close();
				} catch (Exception e1) {}
			}
			if (!server.isClosed()) {
				try {
					server.close();
				} catch (IOException e1) {}
			}

			this.cliente.releaseEmision(portIndex);
		}
	}

	private void sendFile() throws Exception {
		
		File file = new File(fichero);
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
		OutputStream fout = socket.getOutputStream();
		
		byte[] b = new byte[1024 * 16];
		int count = fin.read(b);
		while (count > 0) {
			fout.write(b, 0, count);
			count = fin.read(b);
		}
		fout.close();
		fin.close();
	}
	
}
