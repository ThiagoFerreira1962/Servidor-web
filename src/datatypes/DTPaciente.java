package datatypes;

import java.time.LocalDate;
import java.util.List;

public class DTPaciente extends DTUsuario{
	
	private final LocalDate fechaNacimiento;
	private final LocalDate fechaIngreso;
	private final String cedula;
	private final String antecedentes;
	private final List<DTReserva> reservas;
	


	public DTPaciente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, LocalDate fechaIngreso, String ci, String antecedentes, List<DTReserva> reservas, String foto) {
		super(nickname, nombre, apellido, correo, foto);
		this.fechaNacimiento = fechaNacimiento;
		this.fechaIngreso = fechaIngreso;
		this.cedula = ci;
		this.antecedentes = antecedentes;
		this.reservas = reservas;
	}
	
	public DTPaciente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, LocalDate fechaIngreso, String ci, String antecedentes, String foto) {
		super(nickname, nombre, apellido, correo, foto);
		this.fechaNacimiento = fechaNacimiento;
		this.fechaIngreso = fechaIngreso;
		this.cedula = ci;
		this.antecedentes = antecedentes;
		this.reservas = null;
	}
	
	
	//AÑADIR CONSTRUCTOR CON RESERVAS
	
	public LocalDate getFechaNacimiento() { return fechaNacimiento; }
	
	public LocalDate getFechaIngreso() { return fechaIngreso; }
	
	public String getCi() { return cedula; }
	
	public String getAntecedentes() { return antecedentes; }
	
	public List<DTReserva> getReservas() { return reservas; }
	
}