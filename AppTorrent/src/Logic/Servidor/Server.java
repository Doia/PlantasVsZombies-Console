package Logic.Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Server extends Thread{

	ServerSocket server;
	TablaUsuarios tablaUsuarios;
	TablaArchivos tablaArchivos;
	ArrayList<OyenteCliente> clientes;
	
	int port;
	
	
					
	public Server(int port) {
		super();
		this.port = port;
		tablaUsuarios = new TablaUsuarios();
		tablaArchivos = new TablaArchivos();
	}
	
	public void run() {
		
		Socket socket;
		OutputStream outputS;
		InputStream inputS;

		try { server = new ServerSocket(port);
		System.out.println("Server Abierto");
		System.out.println("Ip: " + InetAddress.getLocalHost());
		System.out.println("Port: " + port);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return;
		}
		while (true) {
			try {
				socket = server.accept();
				System.out.println("Intentan Conectarse...");
				
				ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
				
				OyenteCliente OC = new OyenteCliente(writer, reader, this);
				
				OC.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getListaUsuarios() {
		ArrayList<String> idUsers = new ArrayList<String>();
		
		for (Usuario user : tablaUsuarios.getUsuarios()) {
			idUsers.add(user.getId());
		}			
		return idUsers;
	}
	
	
	//Dentro del array list el elemento 0 indicara que ususario lo comparte y del elemento 1 al n-1 seran las direcciones de los archivos
	public ArrayList<ArrayList<String>> getListaArchivosCompartidos() { 
		
		ArrayList<ArrayList<String>> archivosCompartidos = new ArrayList<ArrayList<String>>();
		ArrayList<String> compartidosUsuario, aux;
		for (ArchivosDeUsuario archivos : tablaArchivos.getArchivos()) {
			compartidosUsuario = new ArrayList<String>();
			compartidosUsuario.add(archivos.getId());
			
			aux = archivos.getFileList();
			for (String file: aux) {
				compartidosUsuario.add(file);
			}
			
			archivosCompartidos.add(compartidosUsuario);
		}			
		return archivosCompartidos;
		
	}
	
	public boolean addUser(Usuario user) {
		return tablaUsuarios.add(user) && tablaArchivos.add(user.getId());
	}
	
	public void eliminaUsuario(Usuario user) {
		tablaUsuarios.delete(user);
		tablaArchivos.delete(user.getId());
	}
	
	public boolean addArchivo(String id, String path) {
		return tablaArchivos.addArchivo(id, path);
	}
	
	public boolean eliminarArchivo(String id, String path) {
		return tablaArchivos.deleteArchivo(id, path);
	}

	public ObjectOutputStream getWriterDestino(String usuarioDestino) {
		return tablaUsuarios.getWriter(usuarioDestino);
	}
	
}
