package excepciones;

//CU::Alta de Cosulta
@SuppressWarnings("serial")
public class SinCuposDisponiblesException extends Exception{
	
	public SinCuposDisponiblesException() {
		super("La consulta seleccionada no tiene cupos disponibles");
	}
}
