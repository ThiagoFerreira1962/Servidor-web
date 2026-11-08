package excepciones;

//CU::Reserva de consulta
@SuppressWarnings("serial")
public class PacienteYaTieneReservaException extends Exception{
	
	public PacienteYaTieneReservaException() {
		super("El paciente seleccionado ya tiene una reserva para esa consulta.");
	}

}
