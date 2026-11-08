package datatypes;

	public abstract class DTUsuario {
	
	private final String nickname;
	private final String nombre;
	private final String apellido;
	private final String correo;
	private final String foto;
	
	protected DTUsuario(String nickname, String nombre, String apellido, String correo, String foto){
		this.nickname = nickname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.foto = foto;
	}
	
	//getters
	
	 public String getNickname() {return this.nickname; }
	 
	 public String getNombre() {return this.nombre; }
	 
	 public String getApellido() {return this.apellido; }
	 
	 public String getCorreo() {return this.correo; }
	 
	 public String getFoto() {return this.foto; }
	 
	 @Override
	 public String toString() {
		 return this.nickname;
	 }

	
}