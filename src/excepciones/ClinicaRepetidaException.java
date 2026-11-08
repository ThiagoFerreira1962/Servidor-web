package excepciones;

//CU::Alta de clinica
@SuppressWarnings("serial")
public class ClinicaRepetidaException extends Exception {
	
	public ClinicaRepetidaException() {
		super("Ya existe una clinica con este nombre.");
	}
}
