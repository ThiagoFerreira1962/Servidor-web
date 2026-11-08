package logica;

import datatypes.DTMedicamento;
import java.util.ArrayList;
import java.util.List;
import datatypes.DTDetalleConsulta;


public class DetalleConsulta {
	private boolean asistencia;
	private String motivo;
	private String diagnostico;
	private List<Medicamento> medicamentos;
	private Reserva reserva;
	private List<SolicitudMedicamento> solicitudes;
	
	public DetalleConsulta(boolean asistencia, String motivo, String diagnostico, Reserva reserva) {
		this.asistencia = asistencia;
		this.motivo = motivo;
		this.diagnostico = diagnostico;
		medicamentos = null;
		this.reserva = reserva;
		this.solicitudes= new ArrayList<>();
	}
	
	public DTDetalleConsulta getDTDetalleConsulta() {
	    return new DTDetalleConsulta(this.asistencia, this.motivo, this.diagnostico, this.medicamentos);
	}
	
	public boolean getAsistencia() {
		return asistencia;
	}
	public void agregarSolicitud(SolicitudMedicamento solicitud) {
		this.solicitudes.add(solicitud);
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public String getDiagnostico() {
		return diagnostico;
	}
	
	public Reserva getReserva() {
		return reserva;
	}
	public List<SolicitudMedicamento> getSolicitudes(){
		return this.solicitudes;
	}
	
	public void recetarMedicamento(String nomMedicamento, String posologia) {
		float costo = reserva.getCostoTicket();
		Medicamento medicamento = new Medicamento(nomMedicamento, posologia, costo, this);
		this.medicamentos.add(medicamento);
		
	}
	
	
}
