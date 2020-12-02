package Launchers;

import Logic.Servidor.Server;

public class main_Servidor {

	//Solo necesita un argumento, el puerto usado por el server
	
	public static void main(String[] args) {

		if (args.length < 1) return;
		
		int port = Integer.parseInt(args[0]);
		
		Server server = new Server(port);
		
		server.start();

	}

}
