package Logic.Servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import Exceptions.DisconnectException;
import Logic.Mensajes.M_Confirmacion_Cerrar_Conexion;
import Logic.Mensajes.M_Confirmacion_Conexion;
import Logic.Mensajes.M_Confirmacion_Lista_Archivos;
import Logic.Mensajes.M_Confirmacion_Lista_Usuarios;
import Logic.Mensajes.M_Dejar_Compartir_Archivo;
import Logic.Mensajes.M_Emitir_Fichero;
import Logic.Mensajes.M_Error_Establecer_Conexion;
import Logic.Mensajes.M_Error_Pedir_Fichero;
import Logic.Mensajes.M_Preparado_ServidorCliente;
import Logic.Mensajes.Mensaje;

public class OyenteCliente extends Thread {

	Server servidor;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	Usuario user;
	boolean activo;
	
	public OyenteCliente(ObjectOutputStream writer, ObjectInputStream reader, Server server) {
		super();
		this.servidor = server;
		this.writer = writer;
		this.reader = reader;
		activo = true;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		ObjectOutputStream writerDestino;
		Mensaje msg;
		while (activo) {
			try {
				msg = (Mensaje) reader.readObject();
				String tipoMensaje = msg.getTipo();
				
				switch (tipoMensaje) {
					case "M_Conexion":
						String id = msg.getUsuarioOrigen();
						user = new Usuario(id,writer,reader);
						if (servidor.addUser(user)) {
							writer.writeObject(new M_Confirmacion_Conexion());
							System.out.println("Conexion establecida");
						}
						else {
							writer.writeObject(new M_Error_Establecer_Conexion());
							System.out.println("Error al establecer conexion");
						}
						
						break;
					case "M_Cerrar_Conexion":
						if (user != null) {
							System.out.println("Conexion con " + user.getId() + " cerrada.");
							this.servidor.eliminaUsuario(user);
						}
						writer.writeObject(new M_Confirmacion_Cerrar_Conexion());
						activo = false;
						writer.close();
						reader.close();
						break;
					case "M_ListaDeUsuarios":
						System.out.println("Lista de usuarios");					
						writer.writeObject(new M_Confirmacion_Lista_Usuarios(this.servidor.getListaUsuarios()));
						break;
						
					case "M_Lista_De_Archivos":
						System.out.println("Lista de Archivos");			
						writer.writeObject(new M_Confirmacion_Lista_Archivos(this.servidor.getListaArchivosCompartidos()));
						break;
						
					case "M_Compartir_Archivo":
						System.out.println("Archivo añadido");
						servidor.addArchivo(user.getId(), msg.getFichero());
						break;
					case "M_Dejar_Compartir_Archivo":
						System.out.println("Archivo añadido");
						if (servidor.eliminarArchivo(user.getId(), msg.getFichero())) {
							System.out.println("Archivo eliminado");
						}
						else {
							writer.writeObject(new M_Dejar_Compartir_Archivo("Error. No se pudo eliminar el archivo: " + new File(msg.getFichero()).getName()));
							System.out.println("Error al eliminar el archivo " + new File(msg.getFichero()).getName());
						}
						break;
					case "M_PedirFichero":
						writerDestino = this.servidor.getWriterDestino(msg.getUsuarioDestino());
						if (writerDestino != null) {
							writerDestino.writeObject(new M_Emitir_Fichero(user.getId(), msg.getFichero(), msg.getIdDescarga()));
						}
						else {
							writer.writeObject(new M_Error_Pedir_Fichero(msg.getIdDescarga()));
						}
						break;
					case "M_Preparado_ClienteServidor":
						writerDestino = this.servidor.getWriterDestino(msg.getUsuarioDestino());
						if (writerDestino != null) {
							writerDestino.writeObject(new M_Preparado_ServidorCliente(msg.getOrigen(), msg.getPortOrigen(), msg.getFichero(), msg.getIdDescarga(), msg.getTamFile()));
						}
						break;
					default:
						break;
				}
			
			
			} catch (Exception e) {
				if (user != null) {
					this.servidor.eliminaUsuario(user);
					System.out.println("Conexion con " + user.getId() + " cerrada repentinamente.");
				}
				activo = false;
				try {
					writer.close();
					reader.close();	
				} catch (IOException e1) {}
			}
		}
		
	}
	
	
}
