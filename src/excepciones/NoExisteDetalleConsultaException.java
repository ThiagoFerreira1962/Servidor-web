package excepciones;

@SuppressWarnings("serial")
public class NoExisteDetalleConsultaException extends Exception {
	public NoExisteDetalleConsultaException() {
		super("No existe detalle de esta consulta");
	}
}

