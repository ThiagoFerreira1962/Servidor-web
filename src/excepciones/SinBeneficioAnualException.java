package excepciones;

public class SinBeneficioAnualException extends Exception {

    public SinBeneficioAnualException() {
        super("El paciente no tiene beneficio anual para este año");
    }
}
