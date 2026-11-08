package excepciones;

public class ElPacienteNoAsistioException extends Exception{
	public ElPacienteNoAsistioException() {
		super("El paciente no asistió");
	}
}
