package Logic.Servidor;

import java.util.ArrayList;

public class ArchivosDeUsuario {

	String id;
	ArrayList<String> fileList;
	
	public ArchivosDeUsuario(String id) {
		this.id = id;
		this.fileList = new ArrayList<String>();
	}
	
	
	public boolean add(String file) {
		if (!fileList.contains(file)) {
			fileList.add(file);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean delete(String file) {
		return fileList.remove(file);
	}
	
	
	
	public boolean equals(Object archivoDeUsuario) {
        boolean sameSame = false;

        if (archivoDeUsuario != null && archivoDeUsuario instanceof ArchivosDeUsuario)
        {
            sameSame = this.id.equals( ((ArchivosDeUsuario) archivoDeUsuario).getId() );
        }

        return sameSame;
	}
	
	public String getId() {
		return this.id;
	}
	
	public ArrayList<String> getFileList() {
		return this.fileList;
	}

	public boolean equals(String id) {
		return this.id.equals(id);
	}
	
}
