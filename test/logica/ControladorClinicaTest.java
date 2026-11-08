package logica;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTClinica;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DiaSemana;
import datatypes.Mes;
import excepciones.ClinicaRepetidaException;

class ControladorClinicaTest {
	private IControladorClinica controlador;
	@BeforeEach
	public void iniciarTest() {
		Fabrica.getInstance().limpiarInstancia();
		HandlerClinica.getInstance().limpiarInstancia();
		controlador = Fabrica.getInstance().getIControladorClinica();
		
		
	}
	@Test
	void testExitoAlta() throws ClinicaRepetidaException {
	    controlador.altaClinica("ClinicaTest", "DescripcionTest", "Avenida Julio Herrera y Reissig 565", 100, 10, "clinicatest@test.com", "CL01.jpg");

	    DTClinica dtclinica= controlador.seleccionarClinica("ClinicaTest");
	    assertNotNull(dtclinica);
	    assertEquals("ClinicaTest", dtclinica.getNombre());
	    assertEquals("DescripcionTest", dtclinica.getDescripcion());
	    assertEquals("Avenida Julio Herrera y Reissig 565", dtclinica.getDireccion());
	    assertEquals(100, dtclinica.getCostoTicket());
	    assertEquals(10, dtclinica.getCostoConsulta());
	    assertEquals("clinicatest@test.com", dtclinica.getSitioWeb());
	    assertEquals("CL01.jpg", dtclinica.getFoto());
	}
	
	@Test
	void testAltaSinSitioWebNiFoto() throws ClinicaRepetidaException {
	    controlador.altaClinica("SinExtras", "DescripcionTest", "Dirección", 50, 20, null, null);

	    DTClinica dtclinica = controlador.seleccionarClinica("SinExtras");
	    assertNotNull(dtclinica);
	    assertNull(dtclinica.getSitioWeb());
	    assertNull(dtclinica.getFoto());
	}
	
	@Test
	void testAltaSoloConSitioWeb() throws ClinicaRepetidaException {
	    controlador.altaClinica("SoloWeb", "DescripcionTest", "Dirección", 0, 0, "https://www.ejemplo.com/", null);

	    DTClinica dtclinica = controlador.seleccionarClinica("SoloWeb");
	    assertNotNull(dtclinica);
	    assertEquals("https://www.ejemplo.com/", dtclinica.getSitioWeb());
	    assertNull(dtclinica.getFoto());
	}
	
	@Test
	void testFallidoAlta() throws ClinicaRepetidaException {
	    controlador.altaClinica("TestRepetido", "DescripcionTest", "Avenida Julio Herrera y Reissig 565", 100, 10, "clinicatest@test.com", "CL01.jpg");

	    assertThrows(ClinicaRepetidaException.class, () -> {
	        controlador.altaClinica("TestRepetido", "DescripcionTest diff", "Dirección diff", 90, 20, "clinicatestdiff@test.com", "CL02.jpeg");
	    });
	    
	    assertEquals(1, controlador.listarNombreClinicas().size());
	    assertEquals("DescripcionTest", controlador.seleccionarClinica("TestRepetido").getDescripcion());
	}
	
	@Test
	void testListaAlta() throws ClinicaRepetidaException {
	    assertTrue(controlador.listarNombreClinicas().isEmpty());

	    controlador.altaClinica("a", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("b", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("c", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("d", "a", "a", 10, 10, null, null);

	    List<String> nombres = controlador.listarNombreClinicas();
	    assertEquals(4, nombres.size());
	    assertTrue(nombres.contains("a"));
	    assertTrue(nombres.contains("d"));
	}
	
	@Test
	void testSeleccionarClinicaInexistente() {
	    assertNull(controlador.seleccionarClinica("No existe"));
	}
	
	@Test
	void altaClinicaCorrecta() throws Exception {
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();

	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");

	    DTClinica dtclinica = ICC.seleccionarClinica("Hospital Británico");
	    assertNotNull(dtclinica);
	    assertEquals("Hospital Británico", dtclinica.getNombre());
	    assertEquals("Av. Italia 2420", dtclinica.getDireccion());
	    assertEquals(350, dtclinica.getCostoTicket());
	    assertEquals(1200, dtclinica.getCostoConsulta());
	    assertEquals("https://www.hospitalbritanico.org.uy/", dtclinica.getSitioWeb());
	    assertEquals("CL01.jpg", dtclinica.getFoto());
	}
	
	@Test
	void altaClinicaSinSitioWebNiFoto() throws Exception {
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();

	    ICC.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, null, null);

	    DTClinica dtclinica = ICC.seleccionarClinica("CASMU");
	    assertNotNull(dtclinica);
	    assertNull(dtclinica.getSitioWeb());
	    assertNull(dtclinica.getFoto());
	}
	
	@Test
	void altaClinicaRepetida() throws Exception {
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();

	    ICC.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, null, null);

	    assertThrows(ClinicaRepetidaException.class, () -> {
	        ICC.altaClinica("CASMU", "Otra descripción", "Otra dirección", 100, 100, null, null);
	    });

	    assertEquals(1, ICC.listarNombreClinicas().size());
	}

	@Test
	void listarClinicasTest() throws Exception {

		controlador.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, null, null);
		controlador.altaClinica("a", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("b", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("c", "a", "a", 10, 10, null, null);
	    controlador.altaClinica("d", "a", "a", 10, 10, null, null);
		List<DTClinica> lista = controlador.listarClinicas();
		assertEquals(5, lista.size());
		assertNotNull(lista.getFirst());
		assertNotNull(lista.getLast());
	}
	
	@Test
	void seleccionarClinicaConDiasConsultaAsociados() throws Exception {
		IControladorDiaConsulta ICDC = Fabrica.getInstance().getIControladorDiaConsulta();
		controlador.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, null, null);
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		ICE.crearEspecialidad("Cardiología", 20);

		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología"), "1234", "US01.jpeg");
		ICDC.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "CASMU", "Cardiología", "DC01.jpeg");
		ICDC.altaDiaConsulta("Cardiología - Martes", "Atención cardiológica semanal de martes", DiaSemana.Martes, LocalTime.parse("15:00"), "CASMU", "Cardiología", "DC04.jpeg");
		DTClinica clinica = controlador.seleccionarClinica("CASMU");
		List<DTDiaConsultaDetallado> dias = clinica.getDiasConsulta();
		assertEquals("CASMU", clinica.getNombre());
		assertEquals(2, dias.size());
		assertNotNull(dias.getFirst());
	}
	
}	
 	

