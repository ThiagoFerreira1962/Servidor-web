package excepciones;

//CU::Alta de Consulta
@SuppressWarnings("serial")
public class ConsultaRepetidaException extends Exception{
	
	public ConsultaRepetidaException() {
		super("Ya existe una consulta con ese nombre.");
	}
}
