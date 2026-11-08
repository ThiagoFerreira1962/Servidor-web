 package logica;

import datatypes.DTUsuario;

abstract class Usuario {
	
	private String nickname;
	private String nombre;
	private String apellido;
	private String correo;
	private String contrasena;
	private String foto;
	
	protected Usuario(String nickname, String nombre, String apellido, String correo, String contrasena, String foto){
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.contrasena=contrasena;
		this.foto = foto;
	}
	
	//getters
	
	 public String getNickname() {return this.nickname;
	 }
	 
	 public String getNombre() {return this.nombre;
	 }
	 
	 public String getApellido() {return this.apellido;
	 }
	  
	 public String getCorreo() {return this.correo;
	 }
	 
	 public String getContrasena() {return this.contrasena;
	 }
	 
	 public String getFoto() {return this.foto;
	 }
	
	//setters
	 
	 public void setNombre(String nombre) {this.nombre = nombre;
	 }

	 public void setApellido(String apellido) {this.apellido = apellido;
	 }
	 
	 public void setFoto(String foto) {this.foto = foto;
	 }
	 
	 public void setContrasena(String contrasena) {this.contrasena = contrasena;
	 }
	 
	 //public void setNickname(String nickname) {
		 	//this.nickname = nickname;
	 //}

	 //public void setCorreo(String correo) {
		 	//this.correo = correo;
	 //}
	
	//metodos
	 
	 public abstract DTUsuario  obtenerDTUsuario();

	  
	
	 

}
