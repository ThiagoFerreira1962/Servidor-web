package excepciones;

@SuppressWarnings("serial")
public class EspecialidadEnUsoException extends Exception {
    public EspecialidadEnUsoException(String especialidad) {
        super("No se puede quitar la especialidad '" + especialidad + "' porque el medico tiene consultas asociadas.");
    }
}