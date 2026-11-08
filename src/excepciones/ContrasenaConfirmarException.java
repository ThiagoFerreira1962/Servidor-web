package excepciones;

@SuppressWarnings("serial")
public class ContrasenaConfirmarException extends Exception {
	
	public ContrasenaConfirmarException() {
		super("Las contraseñas deben ser iguales.");
	}

}
