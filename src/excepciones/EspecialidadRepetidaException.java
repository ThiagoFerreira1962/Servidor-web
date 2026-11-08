package excepciones;

//CU::Alta de Especialidad
@SuppressWarnings("serial")
public class EspecialidadRepetidaException extends Exception{
	
	public EspecialidadRepetidaException() {
		super("Ya existe una especialidad con ese nombre");
	}
}