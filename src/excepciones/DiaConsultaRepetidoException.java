package excepciones;

//CU::Alta de Dia de Consulta
@SuppressWarnings("serial")
public class DiaConsultaRepetidoException extends Exception {
	
	public DiaConsultaRepetidoException() {
		super("Ya existe un día de consulta con ese nombre.");
	}
}
