package logica;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


import datatypes.DTBeneficioAnual;
import datatypes.DTMedico;
import datatypes.DTPaciente;
import datatypes.DTConsulta;
import datatypes.DTDetalleConsulta;
import datatypes.DTMedicamento;
import datatypes.DTDiaConsulta;
import datatypes.DTDetalleConsulta;

import excepciones.EspecialidadEnUsoException;
import excepciones.SinBeneficioAnualException;
import excepciones.SinTicketsDisponiblesException;
import excepciones.ReservaNoDetalladaException;
import excepciones.YaExisteDetalleConsultaException;
import excepciones.NoExisteDetalleConsultaException;
import excepciones.MedicamentoVencidoException;
import excepciones.ElPacienteNoAsistioException;

import datatypes.DTReserva;
import datatypes.DTUsuario;
import datatypes.DTBeneficioAnual;
import datatypes.Mes;
import excepciones.PacienteYaTieneReservaException;
import excepciones.SinCuposDisponiblesException;
import excepciones.UsuarioContrasenaInvalidaException;
import excepciones.UsuarioRepetidoException;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.ContrasenaConfirmarException;
import excepciones.BeneficioAnualAnioPosteriorException;

@SuppressWarnings("unused")
public class ControladorUsuario implements IControladorUsuario{
	
	@Override
	public void altaPaciente(String nickname, String nombre, String apellido, String correo,
			  				 LocalDate fechaNacimiento, String cedula, LocalDate fechaIngreso, String antecedentes, 
			  				 String contrasena, String foto) throws UsuarioRepetidoException  {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		
		String excepcion = "";
		int caso = husr.existeNicknameCorreo(nickname, correo);
		if (caso == 1) {excepcion = "nickname";
		}
		if (caso == 2) {excepcion = "correo";
		}
		if (caso == 3) {excepcion = "nickname y correo";
		}
		if (caso != 0) {throw new UsuarioRepetidoException(excepcion);
		}
		
		Paciente pac = new Paciente(nickname, nombre , apellido, correo, fechaNacimiento, fechaIngreso, cedula, antecedentes, contrasena, foto);
		husr.agregarPaciente(pac);
		}
	
	
	@Override
	public void altaMedico(String nickname, String nombre, String apellido, String correo, String registro,
						  Mes licencia, float costoAdicional, String web, List<String> especialidades,
						  String contrasena, String foto) throws UsuarioRepetidoException {
		
		HandlerUsuario husr = HandlerUsuario.getInstance();
		String excepcion = "";
		int caso = husr.existeNicknameCorreo(nickname, correo);
		if (caso == 1) {excepcion = "nickname";
		}
		if (caso == 2) {excepcion = "correo";
		}
		if (caso == 3) {excepcion = "nickname y correo";
		}
		if (caso != 0) {throw new UsuarioRepetidoException(excepcion);
		}
		
		HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
		List<Especialidad> listaEspecialidades = new ArrayList<>() ;
		for (String especialidad : especialidades) {
			Especialidad objEspecialidad = hesp.find(especialidad);
				if (objEspecialidad != null) {
					listaEspecialidades.add(hesp.find(especialidad));
				}
			}
			Medico med = new Medico(nickname, nombre, apellido, correo, registro, licencia, costoAdicional, web, listaEspecialidades, contrasena, foto);
			husr.agregarMedico(med);
		}

	@Override
	public List<DTPaciente> listarPacientes() {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		List<DTPaciente> pacientes = husr.listarPacientes();
		return pacientes;
	}
	
	@Override
	public List<DTMedico> listarMedicos(){
		HandlerUsuario husr= HandlerUsuario.getInstance();
		List<DTMedico> medicos= husr.listarMedicos();
		return medicos;
	}

	@Override
	public boolean altaReserva(String nicknamePaciente, String nombreConsulta, LocalDate fecha,
			float costoReserva, int anioBeneficio) throws PacienteYaTieneReservaException, SinCuposDisponiblesException {
		
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nicknamePaciente);
		int edadPaciente = Period.between(pac.getFechaNacimiento(), LocalDate.now()).getYears();	//edad del paciente
		
		if (anioBeneficio != 0) {
			costoReserva = 0;
		} else {
			if (edadPaciente >= 60) {
				costoReserva = (float) (costoReserva*0.8);
			}//bonificacion del 20% si es mayor de 60
		}
		
		HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
		
		if (!hdc.hayCuposConsulta(nombreConsulta)) {
		throw new SinCuposDisponiblesException();
		}
		int nroPaciente = hdc.getNroActualConsulta(nombreConsulta);
		Reserva res = pac.crearReserva(nombreConsulta, fecha, nroPaciente, costoReserva, anioBeneficio);
		return hdc.agregarReservaConsulta(res, nombreConsulta);
		
	}

	@Override
	public List<DTReserva> listarReservas(String nickname) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerUsuario(nickname);
		List<DTReserva> reservas = pac.obtenerReservas();
		return reservas;
	}

	@Override
	public List<DTUsuario> listarUsuarios() {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		return husr.listarUsuarios();
	}

	@Override
	public DTUsuario seleccionarUsuario(String nickname) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Usuario usr = husr.obtenerUsuario(nickname);
		if (usr == null) {
			return null;
		}
		return usr.obtenerDTUsuario();
	}

	@Override
	public void editarPaciente(String nickname, String nombre, String apellido, LocalDate fechaNacimiento,
	                            LocalDate fechaIngreso, String cedula, String antecedentes, String imagen) {
	    HandlerUsuario husr = HandlerUsuario.getInstance();
	    Usuario usr = husr.obtenerPaciente(nickname);
	    Paciente pac = (Paciente) usr;
	    pac.setNombre(nombre);
	    pac.setApellido(apellido);
	    pac.setFechaNacimiento(fechaNacimiento);
	    pac.setCi(cedula);
	    pac.setFechaIngreso(fechaIngreso);
	    pac.setAntecedentes(antecedentes);
	    pac.setFoto(imagen);
	}	
	

	@Override
	public void altaBeneficioAnual(String nicknamePaciente, int anio, int cantOrdenes, int cantTickets) throws BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
		int anioActual = LocalDate.now().getYear();
		if (anio > anioActual) {
			throw new BeneficioAnualAnioPosteriorException(anioActual);
		}
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nicknamePaciente);
		if (pac.tieneBeneficio(anio)) {
			throw new BeneficioAnualRepetidoException(anio); 
		}
		pac.crearBeneficioAnual(anio, cantOrdenes, cantTickets);
	}
	
	@Override
	public void editarBeneficioAnual(String nicknamePaciente, int anio, int cantOrdenes, int cantTickets) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nicknamePaciente);
		pac.editarBeneficioAnual(anio, cantOrdenes, cantTickets);
	}
	

	
	@Override
	public DTBeneficioAnual getDTBeneficioAnualPacienteAnio(String nickPaciente, int anio) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nickPaciente);
		return pac.getDTBeneficioAnual(anio);
	}
		


	
	@Override
	public List<DTBeneficioAnual> listarBeneficioAnuales(String nickname) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nickname);
		return pac.listarBeneficiosAnual();
	}
	
	public List<DTBeneficioAnual> listarBeneficioAnualesConOrdenesDisponibles(String nickname) {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(nickname);
		return pac.listarBeneficiosAnualConOrdenesDisponibles();
	}
	
	public void editarMedico(String nickname, String nombre, String apellido, String registro, Mes licencia, 
	                          float costoAdicional, String web, List<String> especialidades, String imagen) throws EspecialidadEnUsoException {
	    HandlerUsuario husr = HandlerUsuario.getInstance();
	    Usuario usr = husr.obtenerMedico(nickname);
	    Medico med = (Medico) usr;
	    
	    HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
	    List<String> especialidadesActuales = med.getEspecialidades();
	    if (especialidadesActuales != null) {
	    for (String especialidadVieja : especialidadesActuales) {
	        if (especialidades!=null && !especialidades.contains(especialidadVieja)) {
	            Especialidad objEspecialidadVieja = hesp.find(especialidadVieja);
	            if (objEspecialidadVieja != null && med.tieneConsultaConEspecialidad(objEspecialidadVieja)) {
	                throw new EspecialidadEnUsoException(especialidadVieja);
	            }
	        } 
	    }
	    }
	    
	    List<Especialidad> listaEspecialidades = new ArrayList<>();
	    if (especialidades != null)
	    {
	    for (String especialidad : especialidades) {
	        Especialidad objEspecialidad = hesp.find(especialidad);
	        if (objEspecialidad != null) {
	            listaEspecialidades.add(objEspecialidad);
	        }
	    
	    }
	    }
	    med.setFoto(imagen);
	    med.setNombre(nombre);
	    med.setApellido(apellido);
	    med.setNroRegistro(registro);
	    med.setLicencia(licencia);
	    med.setCostoAdicional(costoAdicional);
	    med.setSitioWeb(web);
	    med.setEspecialidades(listaEspecialidades);
	}
	
 	@Override
	public String verificarCredenciales(String nickname, String contrasena) throws UsuarioContrasenaInvalidaException {
 	
 	HandlerUsuario husr = HandlerUsuario.getInstance();
 	Usuario usuario = husr.obtenerUsuario(nickname);
 	if (usuario == null || !usuario.getContrasena().equals(contrasena)) 
 		{throw new UsuarioContrasenaInvalidaException();
 		}
 	if (usuario instanceof Medico)
 		return "medico";
 	else {return "paciente";
 	}
 	}

 
 	public void modificarContrasena(String nick, String nueva, String confirmar) throws ContrasenaConfirmarException {
 		HandlerUsuario husr = HandlerUsuario.getInstance();
 		Usuario usuario = husr.obtenerUsuario(nick);
		if (usuario == null || !nueva.equals(confirmar)) 
			throw new ContrasenaConfirmarException();
		else {
			usuario.setContrasena(nueva);
		}
 	}
 	public List<DTConsulta> listarConsultasMedico(String nicknameMedico){
 		HandlerUsuario husr = HandlerUsuario.getInstance();
 		Medico medico = (Medico) husr.obtenerMedico(nicknameMedico);
 		return medico.obtenerConsultas();
 	}
 	public DTReserva seleccionarReserva(String nicknamePaciente, String nombreConsulta) {
 		HandlerUsuario husr = HandlerUsuario.getInstance();
 		Paciente paciente = (Paciente) husr.obtenerPaciente(nicknamePaciente);
 		for (DTReserva dr : paciente.obtenerReservas()) {
 			if (dr.getNombreConsulta().equals(nombreConsulta)) {
 				return dr;
 			}
 		}
 		return null;
 	}
	public DTDetalleConsulta consultarDetalleConsulta(DTReserva reserva) throws NoExisteDetalleConsultaException {
		HandlerUsuario husr= HandlerUsuario.getInstance();
		Paciente paciente= (Paciente) husr.obtenerUsuario(reserva.getNicknamePaciente());
		for (Reserva r: paciente.getReservas()) {
			if (r.getNombreConsulta().equals(reserva.getNombreConsulta())) {
				DetalleConsulta detalle = r.getDetalleConsulta();
				if (detalle != null) {
					return detalle.getDTDetalleConsulta();				
				} else {
					throw new NoExisteDetalleConsultaException();
				}
			}
		}
		return null;
	}
	
	public void recetarMedicamento(DTReserva reserva, String nombre, String posologia) throws NoExisteDetalleConsultaException, ElPacienteNoAsistioException {
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente pac = (Paciente) husr.obtenerPaciente(reserva.getNicknamePaciente());
		for (Reserva r : pac.getReservas()) {
			if (r.getNombreConsulta().equals(reserva.getNombreConsulta())) {
				if (r.getDetalleConsulta()!=null) {
					if (!r.getDetalleConsulta().getAsistencia()) {
						throw new ElPacienteNoAsistioException();
					}
					DTMedicamento medicamento = new DTMedicamento(nombre, posologia, reserva.getNombreConsulta());
		            r.getDetalleConsulta().agregarMedicamento(medicamento);
		            break;        
				}else {
					throw new NoExisteDetalleConsultaException();
				}
			}
		}
	}
	
	public List<DTConsulta> listarConsultasAsistidas(String nickname){
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente paciente = (Paciente) husr.obtenerPaciente(nickname);
		return paciente.listarConsultasAsistidas();
		}
	
	public DTConsulta seleccionarConsultaAsistida(String nickname, String nombreConsulta){
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente paciente = (Paciente) husr.obtenerPaciente(nickname);
		return paciente.seleccionarConsultaAsistida(nombreConsulta);
	}
	
	public void valorarConsulta(String nickname, String nombreConsulta, int calificacion){
		HandlerUsuario husr = HandlerUsuario.getInstance();
		Paciente paciente = (Paciente) husr.obtenerPaciente(nickname);
		paciente.valorarConsulta(nombreConsulta, calificacion);
	}
	public List<DTMedicamento> listarMedicamentosPaciente(String nicknamePaciente){
		HandlerUsuario husr= HandlerUsuario.getInstance();
		Paciente paciente= (Paciente) husr.obtenerPaciente(nicknamePaciente);
		return paciente.listarMedicamentos();
	}
	
	public List<DTBeneficioAnual> listarBeneficiosConTickets(String nicknamePaciente) { //para q elija si usar ben anual para el medicamento
	    Paciente paciente = (Paciente) HandlerUsuario.getInstance().obtenerPaciente(nicknamePaciente);
	    return paciente.listarBeneficiosAnualConTicketsDisponibles();
	}
	
	public void solicitarMedicamentoADomicilio(String nicknamePaciente, DTMedicamento medicamento, LocalDate fechaEntrega,
			String direccion, int anioBeneficio) throws ReservaNoDetalladaException, SinBeneficioAnualException , SinTicketsDisponiblesException, MedicamentoVencidoException {			
		HandlerUsuario husr= HandlerUsuario.getInstance();
		HandlerDiaConsulta hdc= HandlerDiaConsulta.getInstance();
		HandlerClinica hdci= HandlerClinica.getInstance();
		Paciente paciente= (Paciente) husr.obtenerPaciente(nicknamePaciente);
		String nombreConsulta= medicamento.getNombreConsulta();

		for (Reserva r: paciente.getReservas()) {
			if (r.getNombreConsulta().equals(nombreConsulta)) {
			    if (r.getDetalleConsulta() == null) {
			        throw new ReservaNoDetalladaException();
			    }
			    if (LocalDate.now().isAfter(medicamento.getFecha().plusDays(30))) { //si mas de 30 dias no se puede 
			    	throw new MedicamentoVencidoException();
			    }
			    float costo=0;
			    if (anioBeneficio != 0) {
			    	BeneficioAnual beneficio = paciente.obtenerBeneficioAnual(anioBeneficio);
			        if (beneficio == null) {
			            throw new SinBeneficioAnualException();
			        }
			        if (beneficio.getCantidadTickets() <= 0) {
			            throw new SinTicketsDisponiblesException();
			        }
			        int cantOrdenes= beneficio.getCantidadOrdenes();
			        int cantTickets= beneficio.getCantidadTickets();
			        cantTickets--;
			        paciente.editarBeneficioAnual(anioBeneficio, cantOrdenes, cantTickets);
			    
			    }else {
					for (DTDiaConsulta dc : hdc.getDTDiasConsulta()) {
						DiaConsulta dcc = hdc.getDiaConsulta(dc.getNombre());
						if (dcc.existeConsultaNombre(nombreConsulta)) {
							String nombreClinica= dcc.getNombreClinica();
							Clinica c = hdci.getClinica(nombreClinica);
							costo=c.getcostoTicket();
							break;
						}
						
					}
				}
			    DetalleConsulta dc = r.getDetalleConsulta();
				SolicitudMedicamento solicitud = new SolicitudMedicamento(dc, medicamento, fechaEntrega, direccion, costo, anioBeneficio);
				dc.agregarSolicitud(solicitud);
			    break;
				}
		}	
		return;
	}
	@Override
	public void altaDetallesConsulta(String nickMedico, String nicknamePaciente, String nombreConsulta, boolean asistio, String motivoConsulta, String diagnostico) throws YaExisteDetalleConsultaException {
		HandlerUsuario hu = HandlerUsuario.getInstance();
		Medico m = (Medico) hu.obtenerMedico(nickMedico);
		Consulta c = m.obtenerConsulta(nombreConsulta);
		Reserva r = c.getReservaConsultaPaciente(nicknamePaciente);
		if (r.getDetalleConsulta() != null) {
			throw new YaExisteDetalleConsultaException();
		}
		else {
			r.ingresarDetalles(asistio, motivoConsulta, diagnostico);
		}
	}
	
	
	@Override
	public void recetarMedicamento(String nickMedico, String nickPaciente, String nombreConsulta, String nombreMedicamento, String posologia)
									throws NoExisteDetalleConsultaException, ElPacienteNoAsistioException {
	
	HandlerUsuario hu = HandlerUsuario.getInstance();
	Medico medico = (Medico) hu.obtenerMedico(nickMedico);
	Consulta consulta = medico.obtenerConsulta(nombreConsulta);
	Reserva reserva= consulta.getReservaConsultaPaciente(nickPaciente);
	DetalleConsulta detalle = reserva.getDetalleConsulta();
	if (detalle == null) {
		throw new NoExisteDetalleConsultaException();
	}
	
	detalle.recetarMedicamento(nombreMedicamento, posologia);

	}
	
}
	
	
