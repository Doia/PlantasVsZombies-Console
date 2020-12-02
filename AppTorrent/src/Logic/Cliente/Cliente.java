package Logic.Cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import GUI.ClientWindow;
import GUI.TM_Usuarios;
import Logic.Mensajes.M_Cerrar_Conexion;
import Logic.Mensajes.M_Compartir_Archivo;
import Logic.Mensajes.M_Conexion;
import Logic.Mensajes.M_Dejar_Compartir_Archivo;
import Logic.Mensajes.M_ListaDeUsuarios;
import Logic.Mensajes.M_Lista_De_Archivos;
import Logic.Mensajes.M_PedirFichero;
import Logic.Mensajes.Mensaje;

public class Cliente{
	
	private ArrayList<Observer> obs;
	private boolean modeGUI;
	
	private ArrayList<Integer> myPorts;
	private int serverPort;
	private InetAddress myAddress;
	private InetAddress serverAddress;
	private Socket socket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private String idCliente;
	private boolean conectado;
	Lock lock;
	Condition waiting;
	
	ArrayList<Semaphore> listaSemaforoEmisores;
	int portAct;
	
	public Cliente(InetAddress serverAddress, InetAddress myAddress ,ArrayList<Integer> myPorts, int serverPort, boolean modeGUI) {
		
		this.modeGUI = modeGUI;
		obs = new ArrayList<Observer>();
		
		//Para que la escritura del menu en el modo batch espere a que le llegue una respuesta del servidor
		this.lock = new ReentrantLock();
		waiting = lock.newCondition();
		
		this.myPorts = myPorts;
		
		listaSemaforoEmisores = new ArrayList<Semaphore>();
		for (int port: myPorts) {
			listaSemaforoEmisores.add(new Semaphore(1));
		}
		this.portAct = 0;

		this.serverPort = serverPort;
		this.myAddress = myAddress;
		this.serverAddress = serverAddress;
		this.conectado = false;
	}
	
	public void run() {
		
		try {
			socket = new Socket(serverAddress,serverPort);
			System.out.println("Conectando con el servidor...");

			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
			
			OyenteServidor OS = new OyenteServidor(writer, reader, this);
			
			OS.start();
			while(!conectado) {
				this.idCliente = JOptionPane.showInputDialog("ID Cliente: ");
				
	            if (this.idCliente == null) {
	            	this.idCliente = "";
	            	this.disconect();
	            	break;
	            }
	            else if (this.idCliente.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Mensaje de usuario Erroneo.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            else {
		    		Mensaje msg = new M_Conexion(idCliente);
		    		writer.writeObject(msg);
					ActivaEspera();
	            }
				
			}
			if (conectado) {
				if (modeGUI) {
					new ClientWindow(this);
				}
				else runBatchMode();
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//Setters

	public void setId(String id) {
		this.idCliente = id;
	}
	
	public void setConectado(boolean connect) {
		this.conectado = connect;
	}
	
	//Getters
	
	
	public String getIdCliente() {
		return this.idCliente;
	}
	
	public InetAddress getAddress() {
		return this.myAddress;
	}
	
	public int getMyPort(int i) {
		return this.myPorts.get(i);
	}
	
	public Lock getLock() {
		return this.lock;
	}
	
	public Condition getCondition() {
		return this.waiting;
	}
	
	public void acquireEmision(int i) throws Exception {
		this.listaSemaforoEmisores.get(i).acquire();
	}
	
	public void releaseEmision(int i) {
		this.listaSemaforoEmisores.get(i).release();
	}
	
	public synchronized int getAndIncrPortAct() {
		int re = this.portAct;
		this.portAct++;
		this.portAct %= this.myPorts.size();
		return re;
	}

	
	public void ActivaEspera() throws InterruptedException {
		lock.lock();
        waiting.await();
        lock.unlock();
	}
	public void DesactivaEspera() throws InterruptedException {
		lock.lock();
        waiting.signal();
        lock.unlock();
	}
	
	public void cierraConexiones() throws IOException {
		writer.close();
		reader.close();
		socket.close();
	}
	
	public void recibeListaDeUsuarios(ArrayList<String> lista) throws Exception{
		if (modeGUI) for (Observer o : obs) o.onUpdateUsuarios(lista);
		else {
			muestraListaUsuarios(lista);
			DesactivaEspera();
		}
	}
	
	public void recibeListaDeArchivos(ArrayList<ArrayList<String>> listaArchivos) throws Exception{
		if (modeGUI) for (Observer o : obs) o.onUpdateArchivos(listaArchivos);
		else {
			muestraListaArchivos(listaArchivos);
			DesactivaEspera();
		}
	}
	
	
	
	
	//Funciones Modo Batch
	
	public void runBatchMode() throws Exception {
		
        while (conectado) {
        	int option = menu();
        	switch (option) {
        	case 0:
        		writer.writeObject(new M_Cerrar_Conexion());
        		return;
        	case 1:
        		writer.writeObject(new M_ListaDeUsuarios());
				ActivaEspera();
        		break;        		
        	case 2:
        		writer.writeObject(new M_Lista_De_Archivos());
				ActivaEspera();
        		break;
        	case 3:
        		System.out.println("Introduce archivo a compartir.");
        		String archivoCompartido = getPath(); 
        		File f = new File(archivoCompartido);
        		
        		if (f.exists() && f.isFile()) {
        			writer.writeObject(new M_Compartir_Archivo(archivoCompartido));
        		}
        		else {
        			System.out.println("El archivo indicado no existe");        			
        		}
        		break;
        	case 4:
        		System.out.println("Introduce archivo a eliminar.");
        		String archivoaEliminar = getPath(); 
        		f = new File(archivoaEliminar);
        		
        		if (f.exists() && f.isFile()) {
        			writer.writeObject(new M_Dejar_Compartir_Archivo(archivoaEliminar));
        		}
        		else {
        			System.out.println("El archivo indicado no existe");        			
        		}
        		break;
        	case 5:
        		String usuarioDestino = getUsuarioDestino();
        		System.out.println("Introduce archivo a descargar.");
        		String archivo = getPath();  

        		writer.writeObject(new M_PedirFichero(usuarioDestino, archivo, 0)); //en modo batch nos da igual el id descarga

        		break;
        	default:
        			
        	}
        }
        
	}
	
	public void muestraListaUsuarios(ArrayList<String> lista) {
		System.out.println("Lista Usuarios:\n");
		int i = 1;
		for (String user: lista) {
			System.out.println(i + ". " + user);
			i++;
		}
		System.out.println("\n");
	}
	
	public void muestraListaArchivos(ArrayList<ArrayList<String>> listaArchivos) {
		String tab = "    ";
		System.out.println("Lista Archivos:\n");
		for (ArrayList<String> archivosUser : listaArchivos) {
			if (archivosUser.size() > 1)
				System.out.println("- " + archivosUser.get(0));
				for (int i = 1; i < archivosUser.size(); i++) {
					System.out.println(tab + i + ". " + archivosUser.get(i));
				}
				System.out.println();
		}
		System.out.println("\n");
	}
	
	public int menu() {
		int option = -1;
		Scanner in = new Scanner(System.in);
		System.out.println("0 - Salir\n1 - Listar usuarios\n2 - Listar archivos compartidos\n3 - Subir archivos\n4 - Eliminar Archivos \n5 - Descargar archivos");
		while (option < 0 || option > 5) {
			System.out.print("> ");
			option = in.nextInt();
		}
		return option;
	}
	
	public String getUsuarioDestino() {
		String idUser = null;
		Scanner in = new Scanner(System.in);
		System.out.print("Nombre del usuario: ");
		idUser = in.nextLine();
		return idUser;
	}
	
	public String getPath() {
		String path;
		Scanner in = new Scanner(System.in);
		
		System.out.print("Nombre del archivo: ");
		path = in.nextLine();
		return path;
		
	}
	
	
	
	//Funciones Modo GUI
	
	public void runGUIMode() {}
	
	public void getUsuarios() throws Exception {
		writer.writeObject(new M_ListaDeUsuarios());
	}
	
	public void compartirArchivo(String file) {
		try {
			writer.writeObject(new M_Compartir_Archivo(file));
		} catch (Exception e) {
			for (Observer o: obs) o.onException(e.getMessage());
		}
	}
	
	public void eliminarArchivo(String file) {
		try {
			writer.writeObject(new M_Dejar_Compartir_Archivo(file));
		} catch (Exception e) {
			for (Observer o: obs) o.onException(e.getMessage());
		}
	}
	
	public void descargarArchivo(String usuarioDestino, String archivo, int idDescarga) {
		try {
    		writer.writeObject(new M_PedirFichero(usuarioDestino, archivo, idDescarga));
    		for (Observer o: obs) o.onStartDownload(idDescarga, usuarioDestino, archivo);
		} catch (Exception e) {
			for (Observer o: obs) o.onException(e.getMessage());
		}
	}
	
	public void desconectaDelServidor() {
		try {
			writer.writeObject(new M_Cerrar_Conexion());
			this.disconect();
		} catch (Exception e) {
			for (Observer o: obs) o.onException(e.getMessage());
		}
	}
	
	public void actualizaGUI() {
		try {
			writer.writeObject(new M_ListaDeUsuarios());
    		writer.writeObject(new M_Lista_De_Archivos());
    		
		} catch (Exception e) {
			for (Observer o: obs) o.onException(e.getMessage());
		}
	}

	
	public void disconect() throws Exception {
		writer.writeObject(new M_Cerrar_Conexion());
	}
	
	public void addObserver(Observer observer) {
		observer.onRegister();
		obs.add(observer);
	}

	public void finalizaDescarga(int idDescarga) { //informa a la interfaz grafica que la descarga ha finalizado
		for (Observer o: obs) {
			o.onFinishDownload(idDescarga);
		}
	}
	public void paqueteRecibido(int idDescarga, double progreso) { //informa a la interfaz grafica que la descarga ha finalizado
		for (Observer o: obs) {
			o.onPackageRecieve(idDescarga, progreso);
		}
	}
	public void ErrorDuranteLaDescarga(int idDescarga) {
		if (modeGUI) for (Observer o: obs) o.onTransmisionError(idDescarga);
		else System.out.println("Descarga cancelada: perdida de conexion.");
	}

	public void errorPedirFichero(int idDescarga) {
		if (modeGUI) for (Observer o: obs) o.onTransmisionError(idDescarga);
		else System.out.println("Descarga cancelada: archivo inexistente.");
	}
	
	public void ErrorEliminarFichero(String file) {
		if (modeGUI) for (Observer o: obs) o.onException("Error. No se pudo dejar de compartir el fichero " + file);
		else System.out.println("Error. No se pudo dejar de compartir el fichero " + file);
	}


	
}
