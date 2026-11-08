package excepciones;


public class YaExisteDetalleConsultaException extends Exception{
	public YaExisteDetalleConsultaException() {
		super("Ya existe detalle de la consulta");
	}
}
