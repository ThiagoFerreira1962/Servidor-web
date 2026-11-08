package logica;

import excepciones.PacienteYaTieneReservaException;
import excepciones.UsuarioRepetidoException;
import excepciones.SinTicketsDisponiblesException;
import excepciones.SinBeneficioAnualException;
import excepciones.ReservaNoDetalladaException;
import excepciones.YaExisteDetalleConsultaException;
import excepciones.NoExisteDetalleConsultaException;
import excepciones.MedicamentoVencidoException;
import excepciones.ElPacienteNoAsistioException;
import java.time.LocalDate;
import datatypes.Mes;
import datatypes.DTBeneficioAnual;
import datatypes.DTMedico;
import datatypes.DTPaciente;
import datatypes.DTReserva;
import datatypes.DTUsuario;
import datatypes.DTConsulta;
import datatypes.DTMedicamento;
import datatypes.DTBeneficioAnual;
import datatypes.DTDetalleConsulta;

import java.util.List;
import excepciones.SinCuposDisponiblesException;
import excepciones.SinTicketsDisponiblesException;
import excepciones.UsuarioContrasenaInvalidaException;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.ContrasenaConfirmarException;
import excepciones.EspecialidadEnUsoException;
import excepciones.BeneficioAnualAnioPosteriorException;


public interface IControladorUsuario {
	
	void altaPaciente(String nickname, String nombre, String apellido, String correo,
					 LocalDate fechaNacimiento, String cedula, LocalDate fechaIngreso, String antecedentes,
					 String contrasena, String foto) throws UsuarioRepetidoException;
	
	
	void altaMedico(String nickname, String nombre, String apellido, String correo,
				   String registro, Mes licencia, float costoAdicional, String web, List<String> especialidades,
				   String contrasena, String foto) throws UsuarioRepetidoException;
	
	List<DTPaciente> listarPacientes(); 
	
	List<DTMedico> listarMedicos();
	
	boolean altaReserva(String nicknamePaciente, String nombreConsulta, LocalDate fecha, float costoReserva, int anioBeneficio) throws PacienteYaTieneReservaException, SinCuposDisponiblesException;
	
	List<DTReserva> listarReservas(String nickname);
	
	List<DTUsuario> listarUsuarios();
	
	DTUsuario seleccionarUsuario(String nickname);
	
	void editarPaciente(String nickname, String nombre, String apellido, LocalDate fechaNacimiento, 
            LocalDate fechaIngreso, String cedula, String antecedentes, String imagen);	
	
	void editarMedico(String nickname, String nombre, String apellido, String registro,
				      Mes licencia, float costoAdicional, String web, List<String> especialidades, String imagen) throws EspecialidadEnUsoException;
	
	void altaBeneficioAnual(String nicknamePaciente, int anio, int cantOrdenes, int cantTickets) throws BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException;

	void editarBeneficioAnual(String nicknamePaciente, int anio, int cantOrdenes, int cantTickets);
	
	
	public DTBeneficioAnual getDTBeneficioAnualPacienteAnio(String nickPaciente, int anio);

	List<DTBeneficioAnual> listarBeneficioAnuales(String nickname);
	
	List<DTBeneficioAnual> listarBeneficioAnualesConOrdenesDisponibles(String nickname);
	
	String verificarCredenciales(String nickname, String contrasena) throws UsuarioContrasenaInvalidaException;
	
	void modificarContrasena(String nick, String nueva, String confirmar) throws ContrasenaConfirmarException;

	List<DTConsulta> listarConsultasAsistidas(String nickname);
	
	DTConsulta seleccionarConsultaAsistida(String nickname, String nombreConsulta);
	
	void valorarConsulta(String nickname, String nombreConsulta, int calificacion);
	
	List<DTConsulta> listarConsultasMedico(String nicknameMedico);
	
	DTReserva seleccionarReserva(String nicknamePaciente, String nombreConsulta);
	
	DTDetalleConsulta consultarDetalleConsulta(DTReserva reserva) throws NoExisteDetalleConsultaException;
	
	void recetarMedicamento(DTReserva reserva, String nombre, String posologia)throws NoExisteDetalleConsultaException, ElPacienteNoAsistioException;
	
	List<DTMedicamento> listarMedicamentosPaciente(String nicknamePaciente);
	
	List<DTBeneficioAnual> listarBeneficiosConTickets(String nicknamePaciente);
	
	void solicitarMedicamentoADomicilio(String nicknamePaciente, DTMedicamento medicamento, LocalDate fechaEntrega, String direccion, int anioBeneficio) throws ReservaNoDetalladaException, SinBeneficioAnualException, SinTicketsDisponiblesException, MedicamentoVencidoException;

	void altaDetallesConsulta(String nickMedico, String nicknamePaciente, String nombreConsulta, boolean asistio,
			String motivoConsulta, String diagnostico) throws YaExisteDetalleConsultaException;


	void recetarMedicamento(String nickMedico, String nickPaciente, String nombreConsulta, String nombreMedicamento,
			String posologia) throws NoExisteDetalleConsultaException, ElPacienteNoAsistioException;
}
