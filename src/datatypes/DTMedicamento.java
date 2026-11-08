package datatypes;
import java.time.LocalDate;


public class DTMedicamento {
	private final String nombre; 
	private final String posologia;
	private final LocalDate fecha;
	private String nombreConsulta;
	
	public DTMedicamento(String nombre, String posologia, String nombreConsulta) {
		this.nombre=nombre;
		this.posologia=posologia;
		this.fecha=LocalDate.now();
		this.nombreConsulta=nombreConsulta;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getPosologia() {
		return this.posologia;
	}
	public LocalDate getFecha() {
		return this.fecha;
	}
	public String getNombreConsulta() {
		return nombreConsulta;
	}
}
