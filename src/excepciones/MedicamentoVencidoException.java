package excepciones;

public class MedicamentoVencidoException extends Exception{
	public MedicamentoVencidoException() {
		super("Medicamento vencido");
	}
}
