package datatypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DTReserva{
		
	private final LocalDate fecha;
	private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final int nroPaciente;
	private final float costo;
	private final boolean usaBeneficio;
	private final String nicknamePaciente;
	private final String nombreConsulta;
		
	public DTReserva(LocalDate fecha, int nroPaciente, float costo, boolean usaBeneficio, String nicknamePaciente, String nombreConsulta) {
	 	  this.fecha = fecha;
	 	  this.nroPaciente = nroPaciente;
	 	  this.costo = costo;
	 	  this.usaBeneficio = usaBeneficio;
	 	  this.nicknamePaciente = nicknamePaciente;
	 	  this.nombreConsulta = nombreConsulta;
	 }
	 	  
	
	public LocalDate getFecha() { return fecha; }
		
	public int getNroPaciente() { return nroPaciente; }
		
	public float getCosto() { return costo; }
		
	public boolean getUsaBeneficio() { return usaBeneficio; }
	
	public String getNicknamePaciente() { return nicknamePaciente; }
	
	public String getNombreConsulta() { return nombreConsulta; }
	
	@Override
	public String toString() {
		return nicknamePaciente + " " + nombreConsulta + " " +  fecha.format(formato);
	}
}