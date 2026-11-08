package excepciones;

//CU::Alta de Usuario
@SuppressWarnings("serial")
public class UsuarioRepetidoException extends Exception{
	
	
	public UsuarioRepetidoException(String caso) {
		super("Ya existe un usuario con ese " + caso + "." );
	}	
}
