package excepciones;

public class SinTicketsDisponiblesException extends Exception {
	public SinTicketsDisponiblesException() {
		super("El paciente no tiene tickets disponibles");
	}

}
