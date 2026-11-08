package excepciones;

@SuppressWarnings("serial")
public class BeneficioAnualAnioPosteriorException extends Exception {
	public BeneficioAnualAnioPosteriorException(int anio) {
		super("El año no puede ser mayor a " + anio + ".");
	}

}
