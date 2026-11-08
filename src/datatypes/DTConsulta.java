package datatypes;


import java.time.LocalDate;
import java.util.List;

public class DTConsulta {
	private final String nombre;
	private final LocalDate fecha;
	private final int numSala;
	private final int nroDisp;
	private final float costo;
	private final int nroActual;
	private final List<DTReserva> reservas;
	private final String nombreMedico;
	private final String apellidoMedico;
	private final String nicknameMedico;
	private final EstadoConsulta estadoActual;
	private final String nombreDia;
	private final String nombreClinica;
	private final String imagen;
	private final float calificacion;
	
	public DTConsulta(String nombre, LocalDate fecha, int numSala, int nroDisp, float costo, int contador, List<DTReserva> reservas, String nombreMedico, String apellidoMedico, String nicknameMedico, EstadoConsulta estadoActual, String nombreDia, String nombreClinica, String imagen, float calificacion) {
		this.nombre=nombre;
		this.fecha=fecha;
		this.numSala=numSala;
		this.nroDisp=nroDisp;
		this.costo=costo;
		this.nroActual = contador;
		this.reservas = reservas;
		this.nombreMedico = nombreMedico;
		this.apellidoMedico = apellidoMedico;
		this.nicknameMedico = nicknameMedico;
		this.estadoActual= estadoActual;
		this.nombreDia = nombreDia;
		this.nombreClinica = nombreClinica;
		this.imagen = imagen;
		this.calificacion = calificacion;
	}
	
	/*public DTConsulta(String nombre, LocalDate fecha, int numSala, int nroDisp, float costo) {
		this.nombre=nombre;
		this.fecha=fecha;
		this.numSala=numSala;
		this.nroDisp=nroDisp;
		this.costo=costo;
		this.nroActual = 0;
	}*/
	public int getNroActual() {
		return nroActual;
	}
	
	public String getNombre() {
		return nombre;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public int getNumSala() {
		return numSala;
	}
	public int getNroDisp() {
		return nroDisp;
	}
	public float getCosto() {
		return costo;
	}
	public EstadoConsulta getEstadoActual() {
	    return estadoActual;
	}
	public List<DTReserva> getReservas() {
		return reservas;
	}
	public String getNombreMedico() {
		return nombreMedico;
	}
	public String getApellidoMedico() {
		return apellidoMedico;
	}
	public String getNicknameMedico() {
		return nicknameMedico;
	}
	public String getNombreDia() {
		return nombreDia;
	}
	public String getImagen() {
		return this.imagen;
	}
	public String getNombreClinica() {
		return this.nombreClinica;
	}
	public float getCalificacion() {
		return calificacion;
	}
	@Override
	public String toString() {
		return this.nombre;
	}
}
