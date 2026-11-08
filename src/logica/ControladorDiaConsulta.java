package logica;

import java.time.LocalTime;

import java.time.LocalDate;
import java.util.List;

import excepciones.ConsultaRepetidaException;
import excepciones.DiaConsultaRepetidoException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;
import datatypes.DiaSemana;
import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DTReserva;

public class ControladorDiaConsulta implements IControladorDiaConsulta {
	
	@Override
	public List<DTDiaConsulta> listarDiasConsultas() {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		return hdc.getDTDiasConsulta();
	}
	@Override
	public List<DTConsulta> listarConsultasDiaConsulta(String nombreDiaConsulta){
		HandlerDiaConsulta dtc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = dtc.getDiaConsulta(nombreDiaConsulta);
		if (dcons != null) {
		return dcons.getDTConsultas();
		}
		return null;
	}
	
	@Override
	public List<DTConsulta> listarTodasConsultasAceptada() {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		return hdc.getDTTodasConsultasAceptadas();
	}
	
	@Override
	public DTDiaConsultaDetallado getDTDiaConsultaDetalladoNombre(String nombreDia) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = hdc.getDiaConsulta(nombreDia);
		if (dcons != null) {
			DTDiaConsultaDetallado dtdc = dcons.getDTDiaConsultaDetallado();
			return dtdc;
		}
		return null;
	}
	
	@Override
	public DTDiaConsultaDetallado getDTDiaConsultaDetalladoAceptadas(String nombreDia) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = hdc.getDiaConsulta(nombreDia);
		if (dcons != null) {
			DTDiaConsultaDetallado dtdc = dcons.getDTDiaConsultaDetalladoConsultasAceptadas();
			return dtdc;
		}
		return null;
	}
	
	@Override
	public List<DTConsulta> listarConsultasDiaConsultaIngresadas(String nombreDiaConsulta){
		HandlerDiaConsulta dtc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = dtc.getDiaConsulta(nombreDiaConsulta);
		if (dcons != null) {
		return dcons.getDTConsultasIngresadas();
	}
	return null;
	}
	
	@Override
	public List<DTConsulta> listarConsultasDiaConsultaAceptadas(String nombreDiaConsulta){
		HandlerDiaConsulta dtc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = dtc.getDiaConsulta(nombreDiaConsulta);
		if (dcons != null) {
		return dcons.getDTConsultasAceptadas();
	}
	return null;
	}
	
	@Override
	public List<DTConsulta> listarConsultasDiaConsultaAceptadasConCupo(String nombreDiaConsulta){
		HandlerDiaConsulta dtc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = dtc.getDiaConsulta(nombreDiaConsulta);
		if (dcons != null) {
		return dcons.getDTConsultasAceptadasConCupo();
	}
	return null;
	}
	
	/*@Override
	public int seleccionarPacienteConsulta(String nicknameP,String nombreC,String nombreDC) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dc = hdc.getDiaConsulta(nombreDC);
		return (dc.disponibleReserva(nicknameP, nombreC));
	}*/
	
	
	@Override
	public void altaConsulta(String nombre, LocalDate fecha, int numSala, int nroDisp, String nickMedico, String nomDiaConsulta, String imagen) 
								throws FechaNoCoincideException, MedicoNoTieneEspecialidadException, ConsultaRepetidaException {
		
		HandlerDiaConsulta hdc= HandlerDiaConsulta.getInstance();
		if (hdc.existeNombreConsulta(nombre)) {throw new ConsultaRepetidaException();
		}
		
		DiaConsulta diaConsulta = hdc.getDiaConsulta(nomDiaConsulta);
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Medico medico = (Medico) husr.obtenerUsuario(nickMedico);
		Especialidad especialidadDC = diaConsulta.getEspecialidad();
		if (!medico.tieneEspecialidad(especialidadDC)){ throw new MedicoNoTieneEspecialidadException(especialidadDC.getNombre());
		}
		
		DiaSemana dia = diaConsulta.getDia();
		if (dia.ordinal() != fecha.getDayOfWeek().ordinal()){ throw new FechaNoCoincideException(dia);
		}

		
		float costoConsulta = diaConsulta.getCosto() * (1 + (medico.getCostoAdicional()/100)) ;
		Consulta cons = diaConsulta.crearConsulta(nombre, fecha, numSala, nroDisp, costoConsulta, medico, imagen);
		cons.agregarConjuntoConsultas();
	}

	@Override
	public void altaDiaConsulta(String nombreDC, String descripcion, DiaSemana dia, LocalTime horaInicio, String clinica, String especialidad, String imagen) 
									throws DiaConsultaRepetidoException {
		
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		
		if (hdc.existe(nombreDC)) {
			throw new DiaConsultaRepetidoException();
		}
		
		HandlerClinica hcli = HandlerClinica.getInstance();
		Clinica objClinica = hcli.getClinica(clinica);
		
		HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
		Especialidad objEspecialidad = hesp.find(especialidad);
		
		DiaConsulta diaConsulta = new DiaConsulta(nombreDC, descripcion, dia, horaInicio, objClinica, objEspecialidad, imagen);
		objClinica.add(diaConsulta);
		objEspecialidad.add(diaConsulta);
		hdc.add(diaConsulta);
		
	}

	@Override
	public DTConsulta seleccionarConsultaMedico(String nombreDiaConsulta, String nombreConsulta) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons = hdc.getDiaConsulta(nombreDiaConsulta);
		DTConsulta dtcm = dcons.seleccionarConsulta(nombreConsulta);
		return dtcm;
	}
	
	@Override
	public DTConsulta seleccionarConsulta(String nombreConsulta) {
		HandlerDiaConsulta hdcons = HandlerDiaConsulta.getInstance();
		return hdcons.seleccionarConsulta(nombreConsulta);
	}
	
	@Override
	public 	void aceptarConsulta(String nombreConsulta, String nombreDia) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons= hdc.getDiaConsulta(nombreDia);
		dcons.aceptarConsulta(nombreConsulta);
	}
	
	@Override
	public void rechazarConsulta(String nombreConsulta, String nombreDia) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		DiaConsulta dcons= hdc.getDiaConsulta(nombreDia);
		dcons.rechazarConsulta(nombreConsulta);
	}
	
	@Override
	public List<DTReserva> listarReservasConsulta(String nombreConsulta) {
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		return hdc.listarReservasConsulta(nombreConsulta);
		
	}

	}

