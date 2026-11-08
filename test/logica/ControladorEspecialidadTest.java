package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DiaSemana;
import datatypes.Mes;
import excepciones.EspecialidadRepetidaException;

class ControladorEspecialidadTest {
	private IControladorEspecialidad controlador;
    @BeforeEach 
    public void iniciarTest() {	
    	Fabrica.getInstance().limpiarInstancia();
    	HandlerUsuario.getInstance().limpiarInstancia();
		HandlerDiaConsulta.getInstance().limpiarInstancia();
		HandlerClinica.getInstance().limpiarInstancia();
		HandlerEspecialidades.getInstance().limpiarInstancia();
		controlador = Fabrica.getInstance().getIControladorEspecialidad();
    }
    
    //TESTS
	@Test
	void testCorrecto() throws EspecialidadRepetidaException{
		controlador.crearEspecialidad("EspecialidadTest", 0);
		String[] listaEsp = controlador.getNombreEspecialidades();
		assertTrue(Arrays.asList(listaEsp).contains("EspecialidadTest"));
	}
	
	@Test
	void testError() throws EspecialidadRepetidaException{
		controlador.crearEspecialidad("EspecialidadTest", 0);
		assertThrows(EspecialidadRepetidaException.class, ()-> {
			controlador.crearEspecialidad("EspecialidadTest", 0);
		});
	}
	
	@Test
	void listarDiasPorEspecialidadFiltra() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICE.crearEspecialidad("Pediatría", 10);
	    ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, null, null);

	    ICD.altaDiaConsulta("Cardiología - Lunes", "desc", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Cardiología - Jueves", "desc", DiaSemana.Jueves, LocalTime.parse("16:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Pediatría - Martes", "desc", DiaSemana.Martes, LocalTime.parse("14:00"), "Hospital Británico", "Pediatría", null);

	    List<DTDiaConsultaDetallado> dias = ICE.listarDiasPorEspecialidad("Cardiología");

	    assertEquals(2, dias.size());
	    List<String> nombres = dias.stream().map(DTDiaConsultaDetallado::getNombre).toList();
	    assertTrue(nombres.contains("Cardiología - Lunes"));
	    assertTrue(nombres.contains("Cardiología - Jueves"));
	    assertFalse(nombres.contains("Pediatría - Martes"));

	    assertEquals(1, ICE.listarDiasPorEspecialidad("Pediatría").size());
	}
	
	@Test
	void listarDiasPorEspecialidadSinDias() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();

	    ICE.crearEspecialidad("Dermatología", 12);

	    List<DTDiaConsultaDetallado> dias = ICE.listarDiasPorEspecialidad("Dermatología");
	    assertNotNull(dias);
	    assertTrue(dias.isEmpty());
	}
	
	@Test
	void listarDiasPorEspecialidadInexistente() {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();

	    List<DTDiaConsultaDetallado> dias = ICE.listarDiasPorEspecialidad("No existe");
	    assertNotNull(dias);
	    assertTrue(dias.isEmpty());
	}
	
	@Test
	void listarConsultasPorEspecialidadJuntaLosDiasYFiltraPorEstado() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICE.crearEspecialidad("Pediatría", 10);
	    ICC.altaClinica("Hospital Británico", "desc", "Av. Italia 2420", 350, 1200, null, null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@test.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología", "Pediatría"), "1234", null);

	    ICD.altaDiaConsulta("Cardiología - Lunes", "desc", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Cardiología - Jueves", "desc", DiaSemana.Jueves, LocalTime.parse("16:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Pediatría - Martes", "desc", DiaSemana.Martes, LocalTime.parse("14:00"), "Hospital Británico", "Pediatría", null);

	    ICD.altaConsulta("Cardio A", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Cardio B", LocalDate.of(2026, 8, 27), 102, 3, "ana.perez", "Cardiología - Jueves", null);
	    ICD.altaConsulta("Cardio C", LocalDate.of(2026, 9, 14), 103, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Pedia A", LocalDate.of(2026, 8, 25), 104, 3, "ana.perez", "Pediatría - Martes", null);

	    ICD.aceptarConsulta("Cardio A", "Cardiología - Lunes");
	    ICD.aceptarConsulta("Cardio B", "Cardiología - Jueves");
	    ICD.aceptarConsulta("Pedia A", "Pediatría - Martes");

	    List<DTConsulta> consultas = ICE.listarConsultasPorEspecialidad("Cardiología");

	    assertEquals(2, consultas.size());
	    List<String> nombres = consultas.stream().map(DTConsulta::getNombre).toList();
	    assertTrue(nombres.contains("Cardio A"));
	    assertTrue(nombres.contains("Cardio B"));
	    assertFalse(nombres.contains("Cardio C"));
	    assertFalse(nombres.contains("Pedia A"));
	}
	
	@Test
	void listarConsultasPorEspecialidadSinAceptadas() throws Exception {
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    IControladorClinica ICC = Fabrica.getInstance().getIControladorClinica();
	    IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
	    IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();

	    ICE.crearEspecialidad("Cardiología", 20);
	    ICE.crearEspecialidad("Pediatría", 10);
	    ICC.altaClinica("Hospital Británico", "desc", "Av. Italia 2420", 350, 1200, null, null);
	    ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@test.com", "REG1001", Mes.Enero, 15f, "", Arrays.asList("Cardiología", "Pediatría"), "1234", null);

	    ICD.altaDiaConsulta("Cardiología - Lunes", "desc", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Cardiología - Jueves", "desc", DiaSemana.Jueves, LocalTime.parse("16:00"), "Hospital Británico", "Cardiología", null);
	    ICD.altaDiaConsulta("Pediatría - Martes", "desc", DiaSemana.Martes, LocalTime.parse("14:00"), "Hospital Británico", "Pediatría", null);

	    ICD.altaConsulta("Cardio A", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Cardio B", LocalDate.of(2026, 8, 27), 102, 3, "ana.perez", "Cardiología - Jueves", null);
	    ICD.altaConsulta("Cardio C", LocalDate.of(2026, 9, 14), 103, 3, "ana.perez", "Cardiología - Lunes", null);
	    ICD.altaConsulta("Pedia A", LocalDate.of(2026, 8, 25), 104, 3, "ana.perez", "Pediatría - Martes", null);

	    assertTrue(ICE.listarConsultasPorEspecialidad("Cardiología").isEmpty());
	}
	
	@Test
	void listarConsultasPorEspecialidadInexistente() {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();

	    List<DTConsulta> consultas = ICE.listarConsultasPorEspecialidad("No existe");
	    assertNotNull(consultas);
	    assertTrue(consultas.isEmpty());
	}
	
}
