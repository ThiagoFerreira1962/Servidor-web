package logica;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DiaSemana;
import datatypes.EstadoConsulta;
import datatypes.Mes;
import excepciones.ConsultaRepetidaException;
import excepciones.DiaConsultaRepetidoException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;

class ControladorDiaConsultaTest {

	@BeforeEach
	void setUp() {
		HandlerUsuario.getInstance().limpiarInstancia();
		HandlerDiaConsulta.getInstance().limpiarInstancia();
		HandlerClinica.getInstance().limpiarInstancia();
		HandlerEspecialidades.getInstance().limpiarInstancia();
	}

	//ALTA DIA CONSULTA
	@Test
	void testAltaDiaConsulta() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		List<DTDiaConsulta> lista = ICD.listarDiasConsultas();
		assertNotNull(lista);
		assertEquals(1, lista.size());
		assertEquals("Cardiología - Lunes", lista.get(0).getNombre());
	}

	@Test
	void testAltaDiaConsultaNombreRepetido() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		assertThrows(DiaConsultaRepetidoException.class, () -> {
			ICD.altaDiaConsulta("Cardiología - Lunes", "Otra descripción", DiaSemana.Martes, LocalTime.parse("10:00"), "Hospital Británico", "Cardiología", "DC02.jpeg");
		});
	}

	//ALTA CONSULTA
	@Test
	void testAltaConsulta() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");

		List<DTConsulta> lista = ICD.listarConsultasDiaConsulta("Cardiología - Lunes");
		assertNotNull(lista);
		assertEquals(1, lista.size());
		assertEquals("Cardiología 240826", lista.get(0).getNombre());
	}

	@Test
	void testAltaConsultaNombreRepetido() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");

		assertThrows(ConsultaRepetidaException.class, () -> {
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		});
	}

	@Test
	void testAltaConsultaMedicoNoTieneEspecialidad() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);
		ICE.crearEspecialidad("Pediatría", 10);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("bsilva", "Bruno", "Silva", "bruno.silva@hospital.com", "REG1002", Mes.Julio, 5f, "", Arrays.asList("Pediatría"), "1234", "US02.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		assertThrows(MedicoNoTieneEspecialidadException.class, () -> {
			ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "bsilva", "Cardiología - Lunes", "CO01.jpeg");
		});
	}

	@Test
	void testAltaConsultaFechaNoCoincide() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		//Día de consulta es Lunes
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		assertThrows(FechaNoCoincideException.class, () -> {
			//25/08/2026 es un martes, no coincide con el día de consulta (Lunes)
			ICD.altaConsulta("Cardiología 250826", LocalDate.of(2026, 8, 25), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		});
	}

	//LISTAR DIAS CONSULTA
	@Test
	void testListarDiasConsultas() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);
		ICE.crearEspecialidad("Pediatría", 10);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
		ICC.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, "https://www.casmu.com.uy/", "CL02.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaDiaConsulta("Pediatría - Martes", "Atención pediátrica semanal", DiaSemana.Martes, LocalTime.parse("14:00"), "CASMU", "Pediatría", "DC02.png");

		List<DTDiaConsulta> lista = ICD.listarDiasConsultas();
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}

	@Test
	void testListarDiasConsultasVacio() {
		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		List<DTDiaConsulta> lista = ICD.listarDiasConsultas();
		assertNotNull(lista);
		assertTrue(lista.isEmpty());
	}

	//LISTAR CONSULTAS DE UN DIA
	@Test
	void testListarConsultasDiaConsulta() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		ICD.altaConsulta("Cardiología 140926", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "CL05.jpeg");

		List<DTConsulta> lista = ICD.listarConsultasDiaConsulta("Cardiología - Lunes");
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}

	@Test
	void testListarConsultasDiaConsultaVacio() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

		List<DTConsulta> lista = ICD.listarConsultasDiaConsulta("Cardiología - Lunes");
		assertNull(lista);
	}
	
	@Test
	void listarTodasConsultasAceptadaSistemaVacio() {
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
	    List<DTConsulta> aceptadas = ICD.listarTodasConsultasAceptada();

	    assertNotNull(aceptadas);
	    assertTrue(aceptadas.isEmpty());
	}
	
	@Test
	void listarTodasConsultasAceptadaDevuelveSoloAceptadas() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    ICD.altaConsulta("Aceptada 1", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Rechazada 1", LocalDate.of(2026, 9, 14), 102, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Ingresada 1", LocalDate.of(2026, 10, 12), 103, 3, "ana.perez", "Cardiología - Lunes", null);

	    ICD.aceptarConsulta("Aceptada 1", "Cardiología - Lunes");
	    ICD.rechazarConsulta("Rechazada 1", "Cardiología - Lunes");
	    // "Ingresada 1" queda sin tocar

	    List<DTConsulta> aceptadas = ICD.listarTodasConsultasAceptada();

	    assertEquals(1, aceptadas.size());
	    assertEquals("Aceptada 1", aceptadas.get(0).getNombre());
	    assertEquals(EstadoConsulta.Aceptada, aceptadas.get(0).getEstadoActual());
	}
	
	@Test
	void listarTodasConsultasAceptadaCreceAlAceptar() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    ICD.altaConsulta("Aceptada 1", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Rechazada 1", LocalDate.of(2026, 9, 14), 102, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Ingresada 1", LocalDate.of(2026, 10, 12), 103, 3, "ana.perez", "Cardiología - Lunes", null);

	    ICD.aceptarConsulta("Aceptada 1", "Cardiología - Lunes");
	    assertEquals(1, ICD.listarTodasConsultasAceptada().size());

	    ICD.aceptarConsulta("Ingresada 1", "Cardiología - Lunes");
	    assertEquals(2, ICD.listarTodasConsultasAceptada().size());
	}
	
	@Test
	void getDTDiaConsultaDetalladoNombreExistente() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

	    DTDiaConsultaDetallado dtdc = ICD.getDTDiaConsultaDetalladoNombre("Cardiología - Lunes");

	    assertNotNull(dtdc);
	    assertEquals("Cardiología - Lunes", dtdc.getNombre());
	    assertEquals("Atención cardiológica semanal", dtdc.getDescripcion());
	    assertEquals(DiaSemana.Lunes, dtdc.getDia());
	    assertEquals(LocalTime.parse("09:00"), dtdc.getHoraInicio());
	    assertEquals("Hospital Británico", dtdc.getClinica());
	    assertEquals("Cardiología", dtdc.getEspecialidad());
	    assertEquals("DC01.jpeg", dtdc.getImagen());
	}
	
	@Test
	void getDTDiaConsultaDetalladoNombreInexistente() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    assertNull(ICD.getDTDiaConsultaDetalladoNombre("Dermatología - Viernes"));
	    assertNull(ICD.getDTDiaConsultaDetalladoNombre("cardiología - lunes")); // distinta capitalización
	    assertNull(ICD.getDTDiaConsultaDetalladoNombre(""));
	}
	
	@Test
	void getDTDiaConsultaDetalladoNombreSistemaVacio() {
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
	    assertNull(ICD.getDTDiaConsultaDetalladoNombre("Cardiología - Lunes"));
	}
	
	@Test
	void getDTDiaConsultaDetalladoAceptadasFiltra() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    ICD.altaConsulta("Aceptada 1", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Rechazada 1", LocalDate.of(2026, 9, 14), 102, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Ingresada 1", LocalDate.of(2026, 10, 12), 103, 3, "ana.perez", "Cardiología - Lunes", null);

	    ICD.aceptarConsulta("Aceptada 1", "Cardiología - Lunes");
	    ICD.rechazarConsulta("Rechazada 1", "Cardiología - Lunes");

	    DTDiaConsultaDetallado dtdc = ICD.getDTDiaConsultaDetalladoAceptadas("Cardiología - Lunes");

	    assertNotNull(dtdc);
	    assertEquals("Cardiología - Lunes", dtdc.getNombre());
	    assertEquals(1, dtdc.getConsultas().size());
	    assertEquals("Aceptada 1", dtdc.getConsultas().get(0).getNombre());

	    assertEquals(3, ICD.getDTDiaConsultaDetalladoNombre("Cardiología - Lunes").getConsultas().size());
	}
	
	@Test
	void getDTDiaConsultaDetalladoAceptadasSinAceptadas() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    ICD.altaConsulta("Aceptada 1", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Rechazada 1", LocalDate.of(2026, 9, 14), 102, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Ingresada 1", LocalDate.of(2026, 10, 12), 103, 3, "ana.perez", "Cardiología - Lunes", null);

	    ICD.rechazarConsulta("Rechazada 1", "Cardiología - Lunes");

	    DTDiaConsultaDetallado dtdc = ICD.getDTDiaConsultaDetalladoAceptadas("Cardiología - Lunes");

	    assertNotNull(dtdc);
	    assertEquals("Cardiología - Lunes", dtdc.getNombre());
	    assertNotNull(dtdc.getConsultas());
	    assertTrue(dtdc.getConsultas().isEmpty());
	}
	
	@Test
	void getDTDiaConsultaDetalladoAceptadasInexistente() {
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
	    assertNull(ICD.getDTDiaConsultaDetalladoAceptadas("Dermatología - Viernes"));
	}
	
	@Test
	void listarConsultasAceptadasConCupoFiltraPorEstadoYCupo() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "", "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);

	    ICD.altaConsulta("Con cupo", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Sin cupo", LocalDate.of(2026, 9, 14), 102, 1, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Ingresada", LocalDate.of(2026, 10, 12), 103, 3, "ana.perez", "Cardiología - Lunes", null);

	    ICD.aceptarConsulta("Con cupo", "Cardiología - Lunes");
	    ICD.aceptarConsulta("Sin cupo", "Cardiología - Lunes");

	    ICU.altaReserva("lgomez", "Sin cupo", LocalDate.of(2026, 9, 1), 1000f, 0);

	    List<DTConsulta> disponibles = ICD.listarConsultasDiaConsultaAceptadasConCupo("Cardiología - Lunes");

	    assertEquals(1, disponibles.size());
	    assertEquals("Con cupo", disponibles.get(0).getNombre());
	}
	
	@Test
	void listarConsultasAceptadasConCupoSeVaciaAlLlenarse() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

		ICE.crearEspecialidad("Cardiología", 20);
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
		ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "", "1234", null);
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
		 
		ICD.altaConsulta("Con cupo", LocalDate.of(2026, 8, 24), 101, 1, "ana.perez", "Cardiología - Lunes", null);
		
		ICD.aceptarConsulta("Con cupo", "Cardiología - Lunes");
		
	    assertEquals(1, ICD.listarConsultasDiaConsultaAceptadasConCupo("Cardiología - Lunes").size());

	    ICU.altaReserva("lgomez", "Con cupo", LocalDate.of(2026, 8, 1), 1000f, 0);

	    assertTrue(ICD.listarConsultasDiaConsultaAceptadasConCupo("Cardiología - Lunes").isEmpty());
	}
	
	@Test
	void listarConsultasAceptadasConCupoDiaInexistente() {
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
	    assertNull(ICD.listarConsultasDiaConsultaAceptadasConCupo("Dermatología - Viernes"));
	}
	
	@Test
	void seleccionarConsultaExistente() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");

	    DTConsulta dtconsulta = ICD.seleccionarConsulta("Cardiología 240826");

	    assertNotNull(dtconsulta);
	    assertEquals("Cardiología 240826", dtconsulta.getNombre());
	    assertEquals(LocalDate.of(2026, 8, 24), dtconsulta.getFecha());
	    assertEquals(101, dtconsulta.getNumSala());
	    assertEquals(3, dtconsulta.getNroDisp());
	    assertEquals(EstadoConsulta.Ingresada, dtconsulta.getEstadoActual());
	    assertEquals("CO01.jpeg", dtconsulta.getImagen());
	}
	
	@Test
	void seleccionarConsultaReflejaElEstadoActual() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología"), "1234", null);
	    ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");

	    assertEquals(EstadoConsulta.Ingresada, ICD.seleccionarConsulta("Cardiología 240826").getEstadoActual());

	    ICD.aceptarConsulta("Cardiología 240826", "Cardiología - Lunes");

	    assertEquals(EstadoConsulta.Aceptada, ICD.seleccionarConsulta("Cardiología 240826").getEstadoActual());
	}
	
	@Test
	void seleccionarConsultaInexistente() {
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
	    assertNull(ICD.seleccionarConsulta("No existe"));
	}

	//SELECCIONAR CONSULTA MEDICO
	@Test
	void testSeleccionarConsultaMedico() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");

		DTConsulta dtc = ICD.seleccionarConsultaMedico("Cardiología - Lunes", "Cardiología 240826");
		assertNotNull(dtc);
		assertEquals("Cardiología 240826", dtc.getNombre());
		assertEquals("Ana", dtc.getNombreMedico());
	}

	@Test
	void testSeleccionarConsultaMedicoInexistente() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");

		DTConsulta dtc = ICD.seleccionarConsultaMedico("Cardiología - Lunes", "No Existe");
		assertNull(dtc);
	}
	
	//Tests tarea2 estacion de trabajo:
	@Test
	void testConsultaIniciadaComoIngresada()throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		DTConsulta consulta= ICD.seleccionarConsultaMedico("Cardiología - Lunes", "Cardiología 240826");
		assertEquals(consulta.getEstadoActual(), EstadoConsulta.Ingresada);
		
	}
	@Test
	void testAceptarConsulta()throws Exception{
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpeg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		ICD.aceptarConsulta("Cardiología 240826", "Cardiología - Lunes");
		DTConsulta consulta = ICD.seleccionarConsultaMedico("Cardiología - Lunes", "Cardiología 240826");
		assertEquals(EstadoConsulta.Aceptada, consulta.getEstadoActual());
	}
	@Test
	void testRechazarConsulta()throws Exception{
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		ICD.rechazarConsulta("Cardiología 240826", "Cardiología - Lunes");
		DTConsulta consulta= ICD.seleccionarConsultaMedico("Cardiología - Lunes", "Cardiología 240826");
		assertEquals(EstadoConsulta.Rechazada, consulta.getEstadoActual());
	}
	@Test
	void consultasListadasSoloIngresadas()throws Exception{
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		ICD.altaConsulta("Cardiología 140926", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "CO05.jpeg");
		ICD.altaConsulta("Cardiología 140925", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "defaultClinica.jpg");
		ICD.altaConsulta("Cardiología 140924", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "defaultClinica.jpg");
		ICD.aceptarConsulta("Cardiología 240826", "Cardiología - Lunes");
		ICD.aceptarConsulta("Cardiología 140926", "Cardiología - Lunes");
		List<DTConsulta> consultasIngresadas = ICD.listarConsultasDiaConsultaIngresadas("Cardiología - Lunes");
		assertEquals(2, consultasIngresadas.size());
		for (DTConsulta dtconsulta : consultasIngresadas) {
			assertEquals(EstadoConsulta.Ingresada, dtconsulta.getEstadoActual());
		}
	}
	@Test
	void consultasListadasSoloAceptadas()throws Exception{
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
		ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");

		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
		ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
		ICD.altaConsulta("Cardiología 140926", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "CO05.jpeg");
		ICD.altaConsulta("Cardiología 140925", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "defaultConsulta.jpg");
		ICD.altaConsulta("Cardiología 140924", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "defaultConsulta.jpg");
		ICD.aceptarConsulta("Cardiología 240826", "Cardiología - Lunes");
		ICD.aceptarConsulta("Cardiología 140926", "Cardiología - Lunes");
		List<DTConsulta> consultasAceptadas = ICD.listarConsultasDiaConsultaAceptadas("Cardiología - Lunes");
		assertEquals(2, consultasAceptadas.size());
		for (DTConsulta c : consultasAceptadas) {
			assertEquals(EstadoConsulta.Aceptada, c.getEstadoActual());
		}
	}
	@Test
	void testListarConsultasIngresadasDiaInexistente() {
		IControladorDiaConsulta ICD =Fabrica.getInstance().getIControladorDiaConsulta();
		List<DTConsulta> lista =ICD.listarConsultasDiaConsultaIngresadas("DiaQueNoExiste");
		assertNull(lista);
	}
	@Test
	void testListarConsultasAceptadasDiaInexistente() {
		IControladorDiaConsulta ICD =Fabrica.getInstance().getIControladorDiaConsulta();
		List<DTConsulta> lista =ICD.listarConsultasDiaConsultaAceptadas("DiaQueNoExiste");
		assertNull(lista);
	}
}
