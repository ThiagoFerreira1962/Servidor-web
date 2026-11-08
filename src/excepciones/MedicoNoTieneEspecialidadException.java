package excepciones;

//CU::Alta de Consulta
@SuppressWarnings("serial")
public class MedicoNoTieneEspecialidadException extends Exception {

	public MedicoNoTieneEspecialidadException(String especialidad) {
		super("El medico seleccionado no tiene la especialidad " + especialidad + ".");
	}
}
