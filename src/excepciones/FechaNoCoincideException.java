package excepciones;

import datatypes.DiaSemana;

//CU::Alta de Consulta
@SuppressWarnings("serial")
public class FechaNoCoincideException extends Exception{

	public FechaNoCoincideException(DiaSemana dia) {
		super("La fecha seleccionada debe ser de un " + dia + ".");
	}
}