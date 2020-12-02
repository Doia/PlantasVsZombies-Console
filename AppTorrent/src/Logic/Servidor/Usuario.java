package Logic.Servidor;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Usuario {

	String id;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	
	public Usuario(String id, ObjectOutputStream writer, ObjectInputStream reader) {
		this.id = id;
		this.writer = writer;
		this.reader = reader;
	}
	
	public boolean equals(Object user) {
        boolean sameSame = false;

        if (user != null && user instanceof Usuario)
        {
            sameSame = this.id.equals( ((Usuario) user).getId() );
        }

        return sameSame;
	}
	
	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
	public String getId() {
		return this.id;
	}
	
	public ObjectOutputStream getWriter() {
		return this.writer;
	}
	
	public ObjectInputStream getReader() {
		return this.reader;
	}
}
