package logica;

import datatypes.DiaSemana;
import datatypes.DTDiaConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DTConsulta;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import datatypes.EstadoConsulta;

public class DiaConsulta {

	
private String nombre;
private String descripcion;
private DiaSemana dia;
private LocalTime horaInicio;	
private float costo;
private String imagen;
private Clinica clinica;
private Especialidad especialidad;
private Map<String, Consulta> consultas;

DiaConsulta(String nombre, String descripcion, DiaSemana dia, LocalTime horaInicio, Clinica clinica, Especialidad especialidad, String imagen){
	this.nombre = nombre;
	this.descripcion = descripcion;
	this.dia = dia;
	this.horaInicio = horaInicio;	
	this.clinica = clinica;	
	this.especialidad = especialidad;
	float costoC = clinica.getcostoConsulta();
	this.costo = costoC + costoC * (especialidad.getCosto()/100);
	this.consultas = new HashMap<>();
	this.imagen = imagen;
	
}

//public void asociarDiaConsultaClinica() {
	//clinica.add(this);
//}
//getters
//public void asociarDiaConsultaEspecialidad() {
	//especialidad.add(this);
//}

public Consulta getConsulta(String nombreConsulta) {
	return consultas.get(nombreConsulta);
}

public String getNombre() {return nombre;
}

/* public String getDescripcion() {return descripcion;} */

public DiaSemana getDia() {return dia;
}

/* public LocalTime getHoraInicio() {return horaInicio;} */

public Especialidad getEspecialidad() {return especialidad;
}

public float getCosto() {return costo;
}

//public String getImagen() {return imagen;}

public DTDiaConsulta getDTDiaConsulta() {
	DTDiaConsulta dtc = new DTDiaConsulta(this.nombre, this.descripcion, this.dia, this.horaInicio, this.costo, this.clinica.getNombre(), this.imagen);
	return dtc;
}

//setters 

/* public void setNombre(String nombre) {this.nombre = nombre;}

public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

public void setDia(DiaSemana dia) {this.dia = dia;}

public void setHoraInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;}

public void setCosto(float costo) {this.costo = costo;} */

//public void setImagen(String imagen) {this.imagen = imagen;}

public List<DTConsulta> getDTConsultas(){
	List<DTConsulta> res = new ArrayList<>();
	for (Consulta consulta : consultas.values()) {
		res.add(consulta.getDTConsulta());
	}
	return res;
}

public List<DTConsulta> getDTConsultasAceptadas(){
	List<DTConsulta> res = new ArrayList<>();
	for (Consulta consulta : consultas.values()) {
		if (consulta.getDTConsulta().getEstadoActual() == EstadoConsulta.Aceptada)
		res.add(consulta.getDTConsulta());
	}
	return res;
}

public List<DTConsulta> getDTConsultasAceptadasConCupo(){
	List<DTConsulta> res = new ArrayList<>();
	for (Consulta consulta : consultas.values()) {
		DTConsulta cons = consulta.getDTConsulta();
		if (cons.getEstadoActual() == EstadoConsulta.Aceptada && cons.getNroDisp() > 0)
		res.add(consulta.getDTConsulta());
	}
	return res;
}

public List<DTConsulta> getDTConsultasIngresadas(){
	List<DTConsulta> res = new ArrayList<>();
	for (Consulta consulta : consultas.values()) {
		if (consulta.getDTConsulta().getEstadoActual()==EstadoConsulta.Ingresada)
		res.add(consulta.getDTConsulta());
	}
	return res;
}

public String getNombreClinica() {
	return clinica.getNombre();
}


public boolean existeConsulta(Reserva reserva, String nombreConsulta) {
	Consulta cons = consultas.get(nombreConsulta);
	if (cons != null) {
		cons.agregarReserva(reserva);
		return true;
	}
	return false;
}

public boolean existeConsultaNombre(String nombreConsulta) {
	return consultas.containsKey(nombreConsulta);
}

/*public void agregarConsulta(Consulta c) {
	consultas.put(c.getNombre(), c);
}*/

public Consulta crearConsulta(String nombre, LocalDate fecha, int numSala, int nroDisp, float costo, Medico med, String imagen) {
	Consulta cons = new Consulta(nombre, fecha, numSala, nroDisp, costo, med, this, imagen);
	consultas.put(nombre, cons);
	return cons;
}

/* public boolean existeMedicoEspecialidad(String nick) {
	return especialidad.existsMedico(nick);
}*/ /* es excepcion */

/* public int disponibleReserva(String nicknamePaciente, String nombreConsulta) {
	Consulta c = consultas.get(nombreConsulta);
	if (c == null) {
		return 0;
	}
	if (!c.hayCupos()) {
		return 1;
	}
	if (c.yaReservado(nicknamePaciente)) {
		return 2;
	}
	return 0;
} */ /* Es una excepcion */

public DTDiaConsultaDetallado getDTDiaConsultaDetallado() {
	List<DTConsulta> resultado = new ArrayList<>();
	for (Consulta cons : consultas.values()) {
		resultado.add(cons.getDTConsulta());
	}
	
	return new DTDiaConsultaDetallado(nombre, descripcion, dia, horaInicio, costo, resultado, clinica.getNombre(), especialidad.getNombre(), imagen);
}

public DTDiaConsultaDetallado getDTDiaConsultaDetalladoConsultasAceptadas() {
	List<DTConsulta> resultado = new ArrayList<>();
	for (Consulta cons : consultas.values()) {
		if (cons.getEstadoActual() == EstadoConsulta.Aceptada) {
			resultado.add(cons.getDTConsulta());
		}
	}
	return new DTDiaConsultaDetallado(nombre, descripcion, dia, horaInicio, costo, resultado, clinica.getNombre(), especialidad.getNombre(), imagen);
}

public DTConsulta seleccionarConsulta(String nombreConsulta) {
	Consulta cons = consultas.get(nombreConsulta);
	if (cons != null) {
		return cons.getDTConsulta();
	}
	return null;
}

public boolean hayCuposConsulta(String nombreConsulta) {
	return consultas.get(nombreConsulta).hayCupos();
	
}

void aceptarConsulta(String nombreConsulta) {
	Consulta cons = consultas.get(nombreConsulta);
	cons.setEstadoActual(EstadoConsulta.Aceptada);
		
}
void rechazarConsulta(String nombreConsulta) {
	Consulta cons = consultas.get(nombreConsulta);
	cons.setEstadoActual(EstadoConsulta.Rechazada);
}

public float getCostoTicket() {
	return clinica.getcostoTicket();
}
}
