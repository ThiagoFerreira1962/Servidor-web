package logica;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTBeneficioAnual;
import datatypes.DTMedico;
import datatypes.DTPaciente;
import datatypes.DTReserva;
import datatypes.DTUsuario;
import datatypes.DiaSemana;
import datatypes.Mes;
import excepciones.BeneficioAnualAnioPosteriorException;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.ClinicaRepetidaException;
import excepciones.ConsultaRepetidaException;
import excepciones.ContrasenaConfirmarException;
import excepciones.DiaConsultaRepetidoException;
import excepciones.EspecialidadEnUsoException;
import excepciones.EspecialidadRepetidaException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;
import excepciones.PacienteYaTieneReservaException;
import excepciones.SinCuposDisponiblesException;
import excepciones.UsuarioContrasenaInvalidaException;
import excepciones.UsuarioRepetidoException;

class ControladorUsuarioTest {
	
	@BeforeEach
	void setUp() {
		Fabrica.getInstance().limpiarInstancia();
		HandlerUsuario.getInstance().limpiarInstancia();
		HandlerDiaConsulta.getInstance().limpiarInstancia();
		HandlerClinica.getInstance().limpiarInstancia();
		HandlerEspecialidades.getInstance().limpiarInstancia();
	}
	
	//ALTA PACIENTE
	@Test
		void testAltaPaciente() throws UsuarioRepetidoException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			DTPaciente dtu = (DTPaciente) ICU.seleccionarUsuario("lgomez");
			assertNotNull(dtu);
			assertEquals("lgomez", dtu.getNickname());
			assertEquals("Laura", dtu.getNombre());
			assertEquals("Gómez", dtu.getApellido());
			assertEquals("laura.gomez@gmail.com", dtu.getCorreo());
			assertEquals(LocalDate.of(1990, 4, 12), dtu.getFechaNacimiento());
			assertEquals("45678901", dtu.getCi());
			assertEquals(LocalDate.of(2024,  3, 1), dtu.getFechaIngreso());
			assertEquals("Sin antecedentes relevantes.", dtu.getAntecedentes());
			assertEquals("US04.jpeg", dtu.getFoto());
		}
	
	@Test
		void testAltaPacienteNicknameRepetido() throws UsuarioRepetidoException  {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		assertThrows(UsuarioRepetidoException.class, () -> {
			ICU.altaPaciente("lgomez", "Lucia", "Gómez", "lucia.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45688901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		});
	}
	
	@Test
	void testAltaPacienteCorreoRepetido() throws UsuarioRepetidoException {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgonzalez", "Laura", "Gonzalez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		assertThrows(UsuarioRepetidoException.class, () -> {
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		});
	}
	
	@Test
	void testAltaPacienteNicknameYCorreoRepetido() throws UsuarioRepetidoException {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		assertThrows(UsuarioRepetidoException.class, () -> {
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		});
	}
	
	@Test
	void testAltaPacienteNicknameYCorreoRepetidos() throws Exception {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes.", "1234", "US04.jpeg");
		UsuarioRepetidoException ex = assertThrows(UsuarioRepetidoException.class, () -> {
			ICU.altaPaciente("lgomez", "Otra", "Persona", "laura.gomez@gmail.com", LocalDate.of(1985, 1, 1), "11111111", LocalDate.of(2024, 1, 1), "", "1234", "US04.jpeg");
		});
		assertTrue(ex.getMessage().contains("nickname") && ex.getMessage().contains("correo"));
	}
	
	//ALTA MEDICO
	@Test
		void testAltaMedico() throws UsuarioRepetidoException, EspecialidadRepetidaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"),"1234", "US01.jpeg");
			DTMedico dtu = (DTMedico) ICU.seleccionarUsuario("ana.perez");
			assertNotNull(dtu);
			assertEquals("ana.perez", dtu.getNickname());
			assertEquals("Ana", dtu.getNombre());
			assertEquals("Pérez", dtu.getApellido());
			assertEquals("ana.perez@tuclinica.com", dtu.getCorreo());
			assertEquals("REG1001", dtu.getNroRegistro());
			assertEquals(Mes.Enero, dtu.getLicencia());
			assertEquals(15f, dtu.getCostoAdicional());
			assertEquals("https://www.hospitalbritanico.org.uy/", dtu.getSitioWeb());
			assertEquals(Arrays.asList("Cardiología", "Medicina General"), dtu.getEspecialidades());
			assertEquals("US01.jpeg", dtu.getFoto());
		}
	
	@Test
		void testAltaMedicoNicknameRepetido() throws UsuarioRepetidoException, EspecialidadRepetidaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
		
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			assertThrows(UsuarioRepetidoException.class, () -> {
				ICU.altaMedico("ana.perez", "Ana", "Peralta", "ana.perez@gmail.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			});
		}
	
	@Test
		void testAltaMedicoCorreoRepetido() throws UsuarioRepetidoException, EspecialidadRepetidaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			assertThrows(UsuarioRepetidoException.class, () -> {
				ICU.altaMedico("juancito.gomes", "Ana", "Peralta", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			});
		}
	
	@Test
		void testAltaMedicoNicknameYCorreoRepetido() throws UsuarioRepetidoException, EspecialidadRepetidaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			assertThrows(UsuarioRepetidoException.class, () -> {
				ICU.altaMedico("ana.perez", "Ana", "Peralta", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			});
		}
	
	@Test
	void testAltaMedicoEspecialidadInexistente() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);
 
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "",
				Arrays.asList("Cardiología", "EspecialidadQueNoExiste"), "1234", "US01.jpeg");
		DTMedico dtm = (DTMedico) ICU.seleccionarUsuario("ana.perez");
		assertEquals(1, dtm.getEspecialidades().size());
		assertEquals("Cardiología", dtm.getEspecialidades().get(0));
	}
	
	//LISTAR USUARIOS
	@Test 
		void testListarUsuarios() throws UsuarioRepetidoException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			ICU.altaPaciente("mar.fernandez", "Mario", "Fernández", "mario.fernandez@hotmail.com", LocalDate.of(1955, 11, 8), "32145678", LocalDate.of(2022, 6, 15), "Hipertensión arterial diagnosticada en 2018, "
					+ "actualmente controlada con tratamiento farmacológico. Antecedentes de colesterol elevado y episodios ocasionales de migraña. En 2022 fue intervenido quirúrgicamente por una hernia inguinal, sin complicaciones posteriores.", "1234", "US05.jpeg");
			ICU.altaPaciente("s.lopez", "Sofía", "López", "sofia.lopez@gmail.com", LocalDate.of(2014, 9, 20), "58963214", LocalDate.of(2025, 2, 10), "Alergia a penicilina.", "1234", "defaultMedico.jpg");
			List<DTUsuario> lista = ICU.listarUsuarios();
			assertNotNull(lista);
			assertEquals(3, lista.size());
		}
	
	@Test
		void testListarUsuariosVacio() {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			List<DTUsuario> lista = ICU.listarUsuarios();
			assertNotNull(lista);
			assertTrue(lista.isEmpty());
		}
	
	
	
	//LISTAR PACIENTES
	@Test 
		void testListarPacientes() throws UsuarioRepetidoException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "1234");
			ICU.altaPaciente("mar.fernandez", "Mario", "Fernández", "mario.fernandez@hotmail.com", LocalDate.of(1955, 11, 8), "32145678", LocalDate.of(2022, 6, 15), "Hipertensión arterial diagnosticada en 2018, "
					+ "actualmente controlada con tratamiento farmacológico. Antecedentes de colesterol elevado y episodios ocasionales de migraña. En 2022 fue intervenido quirúrgicamente por una hernia inguinal, sin complicaciones posteriores.", "1234", "1234");
			ICU.altaPaciente("s.lopez", "Sofía", "López", "sofia.lopez@gmail.com", LocalDate.of(2014, 9, 20), "58963214", LocalDate.of(2025, 2, 10), "Alergia a penicilina.", "1234", "1234");
			List<DTPaciente> lista = ICU.listarPacientes();
			assertNotNull(lista);
			assertEquals(3, lista.size());
	}
	
	@Test
		void testListarPacientesVacio() {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			List<DTPaciente> lista = ICU.listarPacientes();
			assertNotNull(lista);
			assertTrue(lista.isEmpty());
	}
	
	//LISTAR MEDICOS
	@Test 
		void testListarMedicos() throws UsuarioRepetidoException, EspecialidadRepetidaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			ICU.altaMedico("bsilva", "Bruno", "Silva", "bruno.silva@hospital.com", "REG1002", Mes.Julio, 5f, "", Arrays.asList("Pediatría"), "1234", "US02.jpeg");
			ICU.altaMedico("carla.rodriguez", "Carla", "Rodríguez", "carla.rodriguez@hospital.com", "REG1003", Mes.Febrero, 10f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US03.jpeg");
			List<DTMedico> lista = ICU.listarMedicos();
			assertNotNull(lista);
			assertEquals(3, lista.size());
		}
	
	@Test
		void testListarMedicosVacio() {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			List<DTMedico> lista = ICU.listarMedicos();
			assertNotNull(lista);
			assertTrue(lista.isEmpty());
		}
	
	//EDITAR PACIENTE
	@Test
		void testEditarPaciente() throws UsuarioRepetidoException {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		HandlerUsuario hu = HandlerUsuario.getInstance();
		hu.obtenerPaciente("lgomez");
		ICU.editarPaciente("lgomez", "Lucia", "Rodriguez", LocalDate.of(2000, 2, 7), LocalDate.of(2020, 2, 12), "12345678",  "Obeso", "US04.jpeg");
	}
	
	@Test
	void testEditarPacienteCheck() throws Exception {
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes.", "1234", "US04.jpeg");
		ICU.editarPaciente("lgomez", "Laura Nueva", "Gómez Nueva", LocalDate.of(1991, 5, 20), LocalDate.of(2024, 4, 1), "99999999", "Antecedentes actualizados.", "US04.jpeg");
		DTPaciente dtp = (DTPaciente) ICU.seleccionarUsuario("lgomez");
		assertEquals("Laura Nueva", dtp.getNombre());
		assertEquals("Gómez Nueva", dtp.getApellido());
		assertEquals(LocalDate.of(1991, 5, 20), dtp.getFechaNacimiento());
		assertEquals(LocalDate.of(2024, 4, 1), dtp.getFechaIngreso());
		assertEquals("99999999", dtp.getCi());
		assertEquals("Antecedentes actualizados.", dtp.getAntecedentes());
		assertEquals("lgomez", dtp.getNickname());
		assertEquals("laura.gomez@gmail.com", dtp.getCorreo());
	}
	
	//EDITAR MEDICO
		@Test
		void testEditarMedico() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
	 
			ICU.editarMedico("ana.perez", "Ana Nueva", "Pérez Nueva", "REG2002", Mes.Julio, 25f, "https://nuevaweb.com", Arrays.asList("Cardiología"), "US01.jpeg");
	 
			DTMedico dtm = (DTMedico) ICU.seleccionarUsuario("ana.perez");
			assertEquals("Ana Nueva", dtm.getNombre());
			assertEquals("Pérez Nueva", dtm.getApellido());
			assertEquals("REG2002", dtm.getNroRegistro());
			assertEquals(Mes.Julio, dtm.getLicencia());
			assertEquals(25f, dtm.getCostoAdicional());
			assertEquals("https://nuevaweb.com", dtm.getSitioWeb());
			assertEquals("ana.perez", dtm.getNickname());
			assertEquals("ana.perez@tuclinica.com", dtm.getCorreo());
		}
	 
		@Test
		void testEditarMedicoAgregarEspecialidadNueva() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Pediatría", 10);
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
	 
			ICU.editarMedico("ana.perez", "Ana", "Pérez", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología", "Pediatría"), "US01.jpeg");
	 
			DTMedico dtm = (DTMedico) ICU.seleccionarUsuario("ana.perez");
			assertEquals(2, dtm.getEspecialidades().size());
			assertTrue(dtm.getEspecialidades().contains("Pediatría"));
		}
	 
		@Test
		void testEditarMedicoQuitarEspecialidadSinUso() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Pediatría", 10);
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología", "Pediatría"), "1234","US01.jpeg");
	 
			ICU.editarMedico("ana.perez", "Ana", "Pérez", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "US01.jpeg");
	 
			DTMedico dtm = (DTMedico) ICU.seleccionarUsuario("ana.perez");
			assertEquals(1, dtm.getEspecialidades().size());
			assertFalse(dtm.getEspecialidades().contains("Pediatría"));
		}
	 
		@Test
		void testEditarMedicoQuitarEspecialidadEnUso() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
	 
			IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
			ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
	 
			IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
			ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
	 
			assertThrows(EspecialidadEnUsoException.class, () -> {
				ICU.editarMedico("ana.perez", "Ana", "Pérez", "REG1001", Mes.Enero, 15f, "", Arrays.asList(), "DC01.jpg");
			});
			DTMedico dtm = (DTMedico) ICU.seleccionarUsuario("ana.perez");
			assertEquals(1, dtm.getEspecialidades().size());
			assertTrue(dtm.getEspecialidades().contains("Cardiología"));
		}
		
		@Test
		void editarContrasenaMedicoTest() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			HandlerUsuario hu = HandlerUsuario.getInstance();
			ICE.crearEspecialidad("Cardiología", 20);
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
	 
			ICU.modificarContrasena("ana.perez", "1234", "1234");
			
			assertEquals("1234", hu.obtenerMedico("ana.perez").getContrasena());
			assertEquals("1234", hu.obtenerUsuario("ana.perez").getContrasena());
		}
		
		@Test
		void editarContraseniaMedicoMalTest() throws Exception {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
	 
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
	 
			assertThrows(ContrasenaConfirmarException.class, () -> {
		    	ICU.modificarContrasena("ana.perez", "1234", "123456");
			});
		}
	 
	
	//ALTA RESERVA
	@Test
		void testAltaReserva() throws SinCuposDisponiblesException, EspecialidadRepetidaException, DiaConsultaRepetidoException, ConsultaRepetidaException, UsuarioRepetidoException, ClinicaRepetidaException, MedicoNoTieneEspecialidadException, FechaNoCoincideException, PacienteYaTieneReservaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
			ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"),"1234","US01.jpeg");
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			
			IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
			ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
			
			boolean b = ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1),  1656f, 0);
			assertTrue(b);
	}
	
	@Test
		void testAltaReservaSinCuposDisponibles() throws SinCuposDisponiblesException, EspecialidadRepetidaException, DiaConsultaRepetidoException, ConsultaRepetidaException, UsuarioRepetidoException, ClinicaRepetidaException, MedicoNoTieneEspecialidadException, FechaNoCoincideException, PacienteYaTieneReservaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
			ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			
			IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
			ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 0, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
			
			assertThrows(SinCuposDisponiblesException.class, () -> {
				ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1), 1656f, 0);
			});
		}
	
	@Test
	void testHayCuposConsultaInexistente() {
	    HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();

	    assertFalse(hdc.hayCuposConsulta("noExiste"));
	}
	
	@Test
	void testGetNroActualConsultaInexistente() {
	    HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();

	    assertEquals(0, hdc.getNroActualConsulta("consultaNoExiste"));
	}
	
	@Test
	void testAgregarReservaConsultaInexistente() throws UsuarioRepetidoException{
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
	    HandlerUsuario hu = HandlerUsuario.getInstance();
	    Paciente paci = (Paciente) hu.obtenerPaciente("lgomez");
	    Reserva reser = new Reserva(LocalDate.of(2026, 8, 1), 1, 1000f, false, paci);
	    HandlerDiaConsulta hdc = HandlerDiaConsulta.getInstance();
	    assertFalse(hdc.agregarReservaConsulta(reser, "consultaQueNoExiste"));	    
	}
	
	@Test
		void testAltaReservaConsultaRepetida() throws SinCuposDisponiblesException, EspecialidadRepetidaException, DiaConsultaRepetidoException, ConsultaRepetidaException, UsuarioRepetidoException, ClinicaRepetidaException, MedicoNoTieneEspecialidadException, FechaNoCoincideException, PacienteYaTieneReservaException {
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			ICE.crearEspecialidad("Cardiología", 20);
			ICE.crearEspecialidad("Medicina General", 0);
			
			IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
			ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
			
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			
			IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
			ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
			

			ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1),  1656f, 0);
			assertThrows(PacienteYaTieneReservaException.class, () -> {
				ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1),  1656f, 0); 
			});
		}
	
	//SELECCIONAR USUARIO
	@Test
		void testSeleccionarUsuario() throws UsuarioRepetidoException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			DTUsuario dtu = ICU.seleccionarUsuario("lgomez");
			assertNotNull(dtu);
	}
	
	@Test
		void testSeleccionarUsuarioVacio() throws UsuarioRepetidoException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			DTUsuario dtu = ICU.seleccionarUsuario("hdahf");
			assertNull(dtu);
		}
	
	//LISTAR RESERVAS
	@Test
		void listarReservas() throws SinCuposDisponiblesException, EspecialidadRepetidaException, DiaConsultaRepetidoException, ConsultaRepetidaException, UsuarioRepetidoException, ClinicaRepetidaException, MedicoNoTieneEspecialidadException, FechaNoCoincideException, PacienteYaTieneReservaException {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);
		ICE.crearEspecialidad("Medicina General", 0);
		
		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
		
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		
		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		
		ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1),  1656f, 0);
		List<DTReserva> list = ICU.listarReservas("lgomez");
		assertNotNull(list);
		
	}
	@Test
	     void testSinConsultas() throws SinCuposDisponiblesException, DiaConsultaRepetidoException, FechaNoCoincideException, MedicoNoTieneEspecialidadException, ConsultaRepetidaException, ClinicaRepetidaException, UsuarioRepetidoException, EspecialidadRepetidaException {
		 IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		 IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		 
		 ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
		 ICE.crearEspecialidad("Cardiología", 20);
		 
		 
		 IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		 ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
		 ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			
		 IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		 ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		 ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 0, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
			

		 assertThrows(SinCuposDisponiblesException.class, () -> {
			 ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 31),  1656f, 0);
		 });
	}
	
	//ALTABENEFICIOANUAL
		@Test
			void altaBeneficioAnualTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			    
			    ICU.altaBeneficioAnual("lgomez", 2026, 3, 4);
			    
			    List<DTBeneficioAnual> lista = ICU.listarBeneficioAnuales("lgomez");
			    assertEquals(1, lista.size());
			    assertEquals(2026, lista.get(0).getAnio());
			    assertEquals(3, lista.get(0).getCantidadOrdenes());
			    assertEquals(4, lista.get(0).getCantidadTickets());
			    assertEquals("lgomez", lista.get(0).getNicknamePaciente());
			}

		@Test
			void altaBeneficioAnualRepetidoTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			    ICU.altaBeneficioAnual("lgomez", 2026, 3, 4);
			    
			    assertThrows(BeneficioAnualRepetidoException.class, () -> {
			        ICU.altaBeneficioAnual("lgomez", 2026, 5, 6);
			    });
			}

		@Test
			void altaBeneficioAnualDosAniosTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			    
			    ICU.altaBeneficioAnual("lgomez", 2025, 2, 3);
			    ICU.altaBeneficioAnual("lgomez", 2026, 3, 4);
			    
			    assertEquals(2, ICU.listarBeneficioAnuales("lgomez").size());
			}

		//EDITAR BENEFICIO ANUAL
		@Test
			void editarBeneficioAnualTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException{
			    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			    ICU.altaBeneficioAnual("lgomez", 2026, 3, 4);
			    
			    ICU.editarBeneficioAnual("lgomez", 2026, 7, 8);
			    
			    List<DTBeneficioAnual> lista = ICU.listarBeneficioAnuales("lgomez");
			    assertEquals(1, lista.size());
			    assertEquals(7, lista.get(0).getCantidadOrdenes());
			    assertEquals(8, lista.get(0).getCantidadTickets());
			}

		//LISTARBENEFICIOSANUALES
		@Test
			void listarBeneficiosAnualesVacioTest() throws UsuarioRepetidoException {
			    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			    
			    List<DTBeneficioAnual> lista = ICU.listarBeneficioAnuales("lgomez");
			    assertNotNull(lista);
			    assertTrue(lista.isEmpty());
			}
				
		@Test
		void listarBeneficiosAnualesConOrdenesDisponiblesTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
		            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		    
		    ICU.altaBeneficioAnual("lgomez", 2026, 10, 20);
		    ICU.altaBeneficioAnual("lgomez", 2025, 0, 0);
		    List<DTBeneficioAnual> lista = ICU.listarBeneficioAnualesConOrdenesDisponibles("lgomez");
		    assertEquals(1, lista.size());
		}
		
		@Test
		void beneficioAnualAnioPosteriorTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
		            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
		    assertThrows(BeneficioAnualAnioPosteriorException.class, () -> {
		    	ICU.altaBeneficioAnual("lgomez", 2027, 1, 0);
			});
		}
	//CONSULTAR BENEFICIO ANUAL:
		@Test
			void consultarBeneficioAnualTest() throws UsuarioRepetidoException, BeneficioAnualRepetidoException, BeneficioAnualAnioPosteriorException {
			 	IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			 	ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com",
			            LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			 	ICU.altaBeneficioAnual("lgomez", 2026, 7, 8);
			    DTBeneficioAnual beneficio =ICU.getDTBeneficioAnualPacienteAnio("lgomez", 2026);

			    assertNotNull(beneficio);
			    assertEquals(2026, beneficio.getAnio());
			    assertEquals(7, beneficio.getCantidadOrdenes());
			    assertEquals(8, beneficio.getCantidadTickets());
			    assertEquals("lgomez", beneficio.getNicknamePaciente()); 
		}
		
		//Verificación de contraseña
		@Test
			void testVerificarCredencialesPaciente() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			assertEquals(ICU.verificarCredenciales("lgomez", "1234"), "paciente");
		}
		
		@Test
			void testVerificarCredencialesNoExistePaciente() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			assertThrows(UsuarioContrasenaInvalidaException.class, () -> {
				ICU.verificarCredenciales("lgomez", "1234");
			});
		}
		
		@Test
			void testVerificarCredencialesContrasenaPaciente() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "1234", "US04.jpeg");
			assertThrows(UsuarioContrasenaInvalidaException.class, () -> {
				ICU.verificarCredenciales("lgomez", "5678");
			});
		}
		
		@Test
			void testVerificarCredencialesMedico() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			assertEquals(ICU.verificarCredenciales("ana.perez", "1234"), "medico");
		}
		
		@Test
			void testVerificarCredencialesNoExisteMedico() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			assertThrows(UsuarioContrasenaInvalidaException.class, () -> {
				ICU.verificarCredenciales("ana.perez", "1234");
			});
		}
		
		@Test
			void testVerificarCredencialesContrasenaIncorrecta() throws UsuarioRepetidoException, UsuarioContrasenaInvalidaException {
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "1234", "US01.jpeg");
			assertThrows(UsuarioContrasenaInvalidaException.class, () -> {
				ICU.verificarCredenciales("ana.perez", "5678");
			});
		}
		
}


