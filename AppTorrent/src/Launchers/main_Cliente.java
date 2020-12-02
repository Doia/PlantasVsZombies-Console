package Launchers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Logic.Cliente.Cliente;

public class main_Cliente {

	
	/*
	 * 	Argumentos:
	 * 		1. Direccion ip del server al que se desea conectar
	 * 		2. n = numero de puertos a usar por el cliente > 0
	 * 		3 to 2 + n. Puerto usado por el cliente 
	 * 		3 + n. puerto que esta usando el server
	 * 		4 + n. Nada el modo GUI estara activo si se escribe "Batch" se activara el modo batch 
	 */

		
	public static void main(String[] args) {
		
		boolean modeGUI = true;
		ArrayList<Integer> puertosCliente;
		InetAddress serverAddres = null;
		InetAddress myAddres = null;
		int n;
		
		if (args.length < 2) return; 
		n = Integer.parseInt(args[1]);
		if (n <= 0) return;
		puertosCliente = new ArrayList<Integer>();
		try {
			serverAddres = InetAddress.getByName(args[0]);
			myAddres = InetAddress.getLocalHost();
			
			for( int i = 0; i < n; i++){
				puertosCliente.add(Integer.parseInt(args[2+i]));
			}
			
			int myPort = Integer.parseInt(args[1]);
			int serverPort = Integer.parseInt(args[3+n]);
			if (args.length > 3+n) {
				if (args[4 + n].equalsIgnoreCase("Batch")) modeGUI = false;
			}
			
			
			Cliente cliente = new Cliente(serverAddres, myAddres, puertosCliente, serverPort, modeGUI);
		
			cliente.run();
			
		} catch (UnknownHostException e1) {
			System.out.println("Error. Los argumentos no pudieron parsearse correctamente.");
		}
		


	}

}
