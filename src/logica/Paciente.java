package logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import datatypes.DTUsuario;
import datatypes.DTPaciente;
import datatypes.DTReserva;
import datatypes.DTBeneficioAnual;
import datatypes.DTConsulta;
import datatypes.DTMedicamento;
import java.util.ArrayList;
import java.util.List;
import excepciones.PacienteYaTieneReservaException;

public class Paciente extends Usuario{
	private LocalDate fechaNacimiento;
	private LocalDate fechaIngreso;
	private String cedulaUsr;
	private String antecedentes;
	private Map<Integer, BeneficioAnual> beneficiosPorAnio;
	private List<Reserva> reservas;
	
	public Paciente(String nickname, String nombre, String apellido, String correo, LocalDate fechaNacimiento, LocalDate fechaIngreso, String cedula, String antecedentes, String contrasena, String foto) {
		super(nickname, nombre, apellido, correo, contrasena, foto);
		this.fechaNacimiento = fechaNacimiento;
		this.fechaIngreso = fechaIngreso;
		this.cedulaUsr = cedula;
		this.antecedentes = antecedentes;
		this.reservas = new ArrayList<>();
		this.beneficiosPorAnio = new HashMap<>();
	}
	

	public LocalDate getFechaNacimiento() { return fechaNacimiento; }
	
	/* public LocalDate getFechaIngreso() { return fechaIngreso; }
	
	public String getCi() { return ci; }

	public String getAntecedentes() { return antecedentes; } */
	
	//public Map<Integer, BeneficioAnual> getBeneficiosPorAnio() { return beneficiosPorAnio; }
	
	public DTBeneficioAnual getDTBeneficioAnual(int anio) {
		BeneficioAnual benef = beneficiosPorAnio.get(anio);
		DTBeneficioAnual dtba = new DTBeneficioAnual(anio, benef.getCantidadOrdenes(), benef.getCantidadTickets(), getNickname());
		return dtba;
	}
	
	@Override 
	public DTUsuario obtenerDTUsuario() {
		DTUsuario dtu = new DTPaciente(this.getNickname(), this.getNombre(), this.getApellido(), this.getCorreo(), this.fechaNacimiento, this.fechaIngreso, this.cedulaUsr, this.antecedentes, this.obtenerReservas(), this.getFoto());
		return dtu;
	}
	
	public List<DTReserva> obtenerReservas() {
		List<DTReserva> resultado = new ArrayList<>();
		for (Reserva r: reservas) {
			DTReserva dtr = r.getDTReserva();
			resultado.add(dtr);
		}
		return resultado;
	}
	
	
	public Reserva crearReserva(String nombreConsulta, LocalDate fecha, int nroPaciente, float costo, int anioBeneficio) throws PacienteYaTieneReservaException{
		Reserva res;
		for (Reserva r: reservas)
		{
			if (r.getNombreConsulta().equals(nombreConsulta)) {
				throw new PacienteYaTieneReservaException();
			}
		}
		boolean usaBeneficio = anioBeneficio != 0;
		if (usaBeneficio) {
			beneficiosPorAnio.get(anioBeneficio).usarReserva();
		}
		res = new Reserva(fecha, nroPaciente, costo, usaBeneficio, this);
		reservas.add(res);
		return res;
	}
	
	public boolean tieneBeneficio(int anio) {
		return beneficiosPorAnio.containsKey(anio);
	}
	
	public void crearBeneficioAnual(int anio, int cantOrdenes, int cantTickets) {
		BeneficioAnual benef = new BeneficioAnual(anio, cantOrdenes, cantTickets);
		beneficiosPorAnio.put(anio, benef);
	}
	
	public void editarBeneficioAnual(int anio, int cantOrdenes, int cantTickets) {
		BeneficioAnual benef = beneficiosPorAnio.get(anio);
		if (benef != null) {
			benef.setCantidadOrdenes(cantOrdenes);
			benef.setCantidadTickets(cantTickets);
		}
	}
	
	public List<DTBeneficioAnual> listarBeneficiosAnual() {
		List<DTBeneficioAnual> resultado = new ArrayList<>();
		for (BeneficioAnual b : beneficiosPorAnio.values()) {
			DTBeneficioAnual dtba = new DTBeneficioAnual(b.getAnio(), b.getCantidadOrdenes(), b.getCantidadTickets(), this.getNickname());
			resultado.add(dtba);
		}
		return resultado;
	}
	
	public List<DTBeneficioAnual> listarBeneficiosAnualConOrdenesDisponibles() {
		List<DTBeneficioAnual> resultado = new ArrayList<>();
		for (BeneficioAnual b : beneficiosPorAnio.values()) {
			if (b.getCantidadOrdenes() > 0) {
			DTBeneficioAnual dtba = new DTBeneficioAnual(b.getAnio(), b.getCantidadOrdenes(), b.getCantidadTickets(), this.getNickname());
			resultado.add(dtba);
			}
		}
		return resultado;
	}

	/*public Map<Integer, BeneficioAnual> getBeneficiosPorAnio() {
		return beneficiosPorAnio;
	}


	public void setBeneficiosPorAnio(Map<Integer, BeneficioAnual> beneficiosPorAnio) {
		this.beneficiosPorAnio = beneficiosPorAnio;
	}


	public List<Reserva> getReservas() {
		return reservas;
	}


	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}*/


	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}


	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	public void setCi(String cedula) {
		this.cedulaUsr = cedula;
	}
	
	//public void setBeneficiosPorAnio(Map<Integer,BeneficioAnual> beneficios) {
		//this.beneficiosPorAnio = beneficios;
	//}
	
	public List<DTConsulta> listarConsultasAsistidas(){
		List<DTConsulta> lista = new ArrayList<>();
		DTConsulta consulta = null;
		for (Reserva r: reservas) {
			consulta = r.getConsultaAsistida();
			if (consulta != null) {
				lista.add(consulta);
			}
			
		}
		return lista;
	}
	
	public DTConsulta seleccionarConsultaAsistida(String nombreConsulta) {
		Boolean encontre = false;
		DTConsulta consulta = null;
		for (Reserva r: reservas) {
			encontre = r.buscarConsulta(nombreConsulta);
			if (encontre) {
				consulta = r.seleccionarConsulta();
				break;
			}
		}
		return consulta;
	}
	
	public void valorarConsulta(String nombreConsulta, float calificacion) {
		Boolean encontre = false;
		for (Reserva r: reservas) {
			encontre = r.buscarConsulta(nombreConsulta);
			if (encontre) {
				r.valorarConsulta(calificacion);
				break;
			}
		}
	}
	
	public List<DTMedicamento> listarMedicamentos(){
		List<DTMedicamento> medicamentos= new ArrayList<>();
		for (Reserva r : reservas) {
			List<DTMedicamento> medicamentoReserva=r.obtenerMedicamentosReserva();
			for (DTMedicamento medicamento : medicamentoReserva) {
				medicamentos.add(medicamento);
			}	
		}
		return medicamentos;
	}
	public List<Reserva> getReservas() {
		return reservas;
	}
	

	public List<DTBeneficioAnual> listarBeneficiosAnualConTicketsDisponibles() { //para medicamentos
	    List<DTBeneficioAnual> resultado = new ArrayList<>();

	    for (BeneficioAnual b : beneficiosPorAnio.values()) {
	        if (b.getCantidadTickets() > 0) {
	            resultado.add(getDTBeneficioAnual(b.getAnio()));
	        }
	    }

	    return resultado;
	}
	public BeneficioAnual obtenerBeneficioAnual(int anio) {
	    return beneficiosPorAnio.get(anio);
	}
	
}