package logica;

import java.time.LocalDate;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import datatypes.DTReserva;
import datatypes.DTConsulta;
import datatypes.EstadoConsulta;

public class Consulta {
	private String nombre;
	private LocalDate fecha;
	private int numSala;
	private int nroDisp;
	private int nroActual;
	private float costo;
	private Medico medico;
	private Map<Integer, Reserva> reservas; //usar Integer en el map
	private DiaConsulta diaConsulta;
	private EstadoConsulta estadoActual;
	private String imagen;
	private float calificacion;
	private int votos;
	
	public Consulta(String nombre, LocalDate fecha, int numSala, int nroDisp, float costo, Medico medico, DiaConsulta diaConsulta, String imagen) {
		this.nroActual = 1;
		this.nombre=nombre;
		this.fecha=fecha;
		this.numSala = numSala;
		this.nroDisp = nroDisp;
		this.costo=costo;
		this.medico=medico;
		this.reservas= new HashMap<>();
		this.diaConsulta = diaConsulta;
		estadoActual = EstadoConsulta.Ingresada;
		this.imagen = imagen;
		calificacion = 0;
		votos = 0;
		
	}
	
	/* public String getNickMedico() {
		return medico.getNickname();
	}
	

	public int getnroActual() {
		return this.nroActual;
	}
	
	public static int getContador() {
		return contador;
	}
	
	public String getNombreDiaConsulta() {
		return diaConsulta.getNombre();
	} */
	
public EstadoConsulta getEstadoActual() {
	return estadoActual;
}

	
	public List<DTReserva> getDTReservas(){
		List<DTReserva> reservs= new ArrayList<>();
		for (Reserva r : reservas.values()) {
			DTReserva dtr= r.getDTReserva();
			reservs.add(dtr);
			
		}
		return reservs;
	}
	
	public DTConsulta getDTConsulta() {
		List<DTReserva> reservas = this.getDTReservas();
		String nombreMedico = medico.getNombre();
		String nicknameMedico = medico.getNickname();
		String apellidoMedico = medico.getApellido();
		String nombreClinica = diaConsulta.getNombreClinica();
		DTConsulta res= new DTConsulta(nombre, fecha, numSala, nroDisp, costo,  nroActual, reservas, nombreMedico, apellidoMedico, nicknameMedico, estadoActual, diaConsulta.getNombre(), nombreClinica , imagen, calificacion);
		return res;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public DiaConsulta getDiaConsulta() {
		return diaConsulta;
	}
/*	public LocalDate getFecha() {
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
	public Medico getMedico() {
		return medico;
	} 
	public List<Reserva> getReservas(){
		List<Reserva> reservs= new ArrayList<>();
		for(Reserva r : reservas.values()) {
			reservs.add(r);
		}
		return reservs;
	}*/
	/*public String getImagen() {
		return this.imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}*/
	
	public void agregarReserva(Reserva res) {
		reservas.put(res.getNroPaciente(), res);
		nroActual++;
		nroDisp--;
		res.setConsulta(this);
	}
	
	public void agregarConjuntoConsultas() {
		medico.agregarConsulta(this);
	}
	
	public boolean hayCupos() {
		return nroDisp > 0;
	}
	
	public int getNroActual() {
		return nroActual;
	}
	
	public void setEstadoActual(EstadoConsulta estado) {
		this.estadoActual = estado;
	}
	
	public void valorarConsulta(float nuevaCal) {
		if (votos == 0) {
			calificacion = nuevaCal;
			votos++;
		} else {
		calificacion = ((calificacion * votos) + nuevaCal)/(votos + 1);
		votos++;
		}
		
	}

	public Reserva getReservaConsultaPaciente(String nicknamePaciente) {
		for (Reserva r: reservas.values()){
			if (r.getNicknamePaciente() == nicknamePaciente) 
				return r;
		}
		return null;
	}
/* public boolean yaReservado(String nicknamePaciente) {
		for (Reserva r : reservas.values()) {
			if (nicknamePaciente.equals(r.getNickPaciente())) {
				return true;
			}
		}
		return false;
	} */

	public float getCostoTicket() {
		return diaConsulta.getCostoTicket();
	}
}
