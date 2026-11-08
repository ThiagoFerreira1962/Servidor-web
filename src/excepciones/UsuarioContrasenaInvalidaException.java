package excepciones;

@SuppressWarnings("serial")
public class UsuarioContrasenaInvalidaException extends Exception {
	
	public UsuarioContrasenaInvalidaException() {
		super("Usuario o contraseña incorrectos.");
	}

}
