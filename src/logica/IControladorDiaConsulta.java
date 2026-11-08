package logica;
import datatypes.DiaSemana;
import excepciones.ConsultaRepetidaException;
import excepciones.DiaConsultaRepetidoException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;

import java.time.LocalTime;

import java.time.LocalDate;
import java.util.List;
import datatypes.DTDiaConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DTReserva;
import datatypes.DTConsulta;
//import datatypes.DTConsultaMedico;

public interface IControladorDiaConsulta {
	
	//int seleccionarPacienteConsulta(String nicknameP,String nombreC,String nombreDC);
	
	void altaDiaConsulta(String nombreDC, String descripcion, DiaSemana dia, LocalTime horaInicio, String clinica,
			String especialidad, String imagen) throws DiaConsultaRepetidoException;
	
	List<DTDiaConsulta> listarDiasConsultas();
	List<DTConsulta> listarTodasConsultasAceptada();
	
	List<DTConsulta> listarConsultasDiaConsultaAceptadas(String nombreDiaConsulta);
	List<DTConsulta> listarConsultasDiaConsultaAceptadasConCupo(String nombreDiaConsulta);
	List<DTConsulta> listarConsultasDiaConsultaIngresadas(String nombreDiaConsulta);
	List<DTConsulta> listarConsultasDiaConsulta(String nombreDiaConsulta);
	
	DTDiaConsultaDetallado getDTDiaConsultaDetalladoNombre(String nombreDia);
	DTDiaConsultaDetallado getDTDiaConsultaDetalladoAceptadas(String nombreDia);
	
	void altaConsulta(String nombre, LocalDate fecha, int numSala, int nroDisp, String nickMedico, String nomDiaConsulta, String imagen) 
			throws FechaNoCoincideException, MedicoNoTieneEspecialidadException, ConsultaRepetidaException;
	
	DTConsulta seleccionarConsultaMedico(String nombreDiaConsulta, String nombreConsulta);
	DTConsulta seleccionarConsulta(String nombreConsulta);
	void aceptarConsulta(String nombreConsulta, String nombreDia);
	void rechazarConsulta(String nombreConsulta, String nombreDia);
	
	List<DTReserva> listarReservasConsulta(String nombreConsulta);
}
