package datatypes;

import java.time.LocalTime;

public class DTDiaConsulta {
	private final String nombre;
	private final String descripcion;
	private final DiaSemana dia;
	private final LocalTime horaInicio;
	private final float costo;
	private final String imagen;
	private final String clinica;
	
	public DTDiaConsulta(String nombre, String descripcion, DiaSemana dia, LocalTime horaInicio, float costo, String clinica, String imagen) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.costo = costo;
		this.imagen = imagen;
		this.clinica = clinica;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public DiaSemana getDia() {
		return this.dia;
	}
	public LocalTime getHoraInicio() {
		return this.horaInicio;
	}
	public float getCosto() {
		return this.costo;
	}
	public String getImagen() {
		return this.imagen;
	}
	public String getClinica() {
		return this.clinica; 
	}
	@Override
	public String toString() {
		return this.nombre;
	}
}
