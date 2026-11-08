package logica;

import datatypes.DTReserva;
import java.time.LocalDate;
import datatypes.DTConsulta;
import datatypes.DTMedicamento;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
	private LocalDate fecha;
	private int nroPaciente;
	private float costo;
	private boolean usaBeneficio;
	private Paciente paciente;
	private Consulta consulta;
	private DetalleConsulta detalle;
	private float calificacion;

/*	public LocalDate getFecha() {
		return this.fecha;
	};*/

	public int getNroPaciente() {
		return this.nroPaciente;
	}

/*	public float getCosto() {
		return this.costo;
	}

	public boolean isUsaBeneficio() {
		return usaBeneficio;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}
	
	public Consulta getConsulta() {
		return consulta;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	};

	public void setNroPaciente(int nroPaciente) {
		this.nroPaciente = nroPaciente;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	public void setUsaBeneficio(boolean usaBeneficio) {
		this.usaBeneficio = usaBeneficio;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}*/
	
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public void setDetalle(DetalleConsulta detalle) {
		this.detalle= detalle;
	}

	/*public Reserva() {
	};*/

	public Reserva(LocalDate fecha, int nroPaciente, float costo, boolean usaBeneficio, Paciente paciente) {
		this.fecha = fecha;
		this.nroPaciente = nroPaciente;
		this.costo = costo;
		this.usaBeneficio = usaBeneficio; // agrego
		this.paciente = paciente;
		calificacion = 0;
		detalle = null;
	};

/*	Reserva(Reserva obj) {
		this.fecha = obj.fecha;
		this.nroPaciente = obj.nroPaciente;
		this.costo = obj.costo;
	};*/

	public DTReserva getDTReserva() {
		DTReserva DTR = new DTReserva(this.fecha, this.nroPaciente, this.costo, this.usaBeneficio, this.paciente.getNickname(), this.consulta.getNombre());
		return DTR;
	};

	// public String getNickPaciente() {
		//return (paciente.getNickname());
	//} 
	
	public String getNombreConsulta() {
		return consulta.getNombre();
	}
	
	public boolean buscarConsulta(String nombreConsulta) {
		return nombreConsulta == consulta.getNombre();
	}
	
	public DTConsulta seleccionarConsulta() {
		return consulta.getDTConsulta();
	}
	
	public DTConsulta getConsultaAsistida() {
		if (detalle != null && detalle.getAsistencia()) {
			return seleccionarConsulta();
		}
		return null;
	}
	
	public boolean yaCalificado() {
		if (calificacion != 0) {
			return true;
		}
		return false;
	}
	
	public void valorarConsulta(float calificacion) {
		this.calificacion = calificacion;
		consulta.valorarConsulta(calificacion);	
	}

	public DetalleConsulta getDetalleConsulta() {
		return this.detalle;
	}
	
	public String getNicknamePaciente() {
		return paciente.getNickname();
	}

	public void ingresarDetalles(boolean asistio, String motivoConsulta, String diagnostico) {
		if (detalle == null) {
			detalle = new DetalleConsulta(asistio, motivoConsulta, diagnostico, this);
		}
	}

	public float getCostoTicket() {
		return consulta.getCostoTicket();
	}
	
};