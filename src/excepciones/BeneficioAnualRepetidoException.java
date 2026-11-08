package excepciones;

//CU: Alta Beneficio Anual
@SuppressWarnings("serial")
public class BeneficioAnualRepetidoException extends Exception{
	
	public BeneficioAnualRepetidoException(int anio) {
		super("El paciente ya tiene beneficio anual para el año: " + anio + ".");
	}
}