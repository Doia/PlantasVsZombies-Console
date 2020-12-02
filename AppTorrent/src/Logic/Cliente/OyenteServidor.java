package Logic.Cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Logic.Mensajes.M_Preparado_ClienteServidor;
import Logic.Mensajes.Mensaje;

public class OyenteServidor extends Thread {
	
	Cliente cliente;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	String id;
	boolean activo;
	
	public OyenteServidor(ObjectOutputStream writer, ObjectInputStream reader, Cliente cliente) {
		super();
		this.cliente = cliente;
		this.writer = writer;
		this.reader = reader;
		activo = true;
	}
	
	public void run() {
		Mensaje msg;
		while (activo) {
				try {
					msg = (Mensaje) reader.readObject();
					String tipoMensaje = msg.getTipo();
					
					switch (tipoMensaje) {
						case "M_Confirmacion_Conexion":					
							cliente.DesactivaEspera();
							cliente.setConectado(true);
							break;
						case "M_Confirmacion_Cerrar_Conexion":
							cliente.cierraConexiones();
							activo = false;
							break;
						case "M_Error_Establecer_Conexion":
							cliente.setConectado(false);	
							cliente.DesactivaEspera();
							break;
						case "M_Error_Pedir_Fichero":
							cliente.errorPedirFichero(msg.getIdDescarga());
							break;
						case "M_Dejar_Compartir_Archivo":
							cliente.ErrorEliminarFichero(msg.getError());
							break;	
						case "M_Confirmacion_Lista_Usuarios":
							cliente.recibeListaDeUsuarios(msg.getUsuarios());
							break;
							
						case "M_Confirmacion_Lista_Archivos":
							cliente.recibeListaDeArchivos(msg.getListaArchivos());
							break;
						
						case "M_Emitir_Fichero":
							Emisor emisor = new Emisor(writer ,msg.getUsuarioOrigen(), msg.getFichero(), cliente.getAndIncrPortAct(), msg.getIdDescarga() ,this.cliente);
							emisor.start();	
							break;
							
						case "M_Preparado_ServidorCliente":
							Receptor receptor = new Receptor(msg.getOrigen(), msg.getPortOrigen(), msg.getFichero(), msg.getIdDescarga(), msg.getTamFile(), this.cliente);
							receptor.start();
							break;
						
						default:
							break;
					}
				
				
				} catch (Exception e) {
					e.printStackTrace();
					activo = false;

				}
		}
		
	}
}
