package excepciones;

public class ReservaNoDetalladaException extends Exception {

    public ReservaNoDetalladaException() {
        super("La reserva no tiene un detalle de consulta asociado.");
    }
}
