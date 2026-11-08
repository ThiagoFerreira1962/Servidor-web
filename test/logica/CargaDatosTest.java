package logica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.EspecialidadRepetidaException;

class CargaDatosTest {

	@BeforeEach
		void setUp() {
			HandlerUsuario.getInstance().limpiarInstancia();
			HandlerDiaConsulta.getInstance().limpiarInstancia();
			HandlerClinica.getInstance().limpiarInstancia();
			HandlerEspecialidades.getInstance().limpiarInstancia();
			CargaDatos.reiniciar();
		}
	
	@Test
	void cargaDatosTest() {
		CargaDatos.cargar();
		IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
		IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
		IControladorDiaConsulta ICD = Fabrica.getInstance().getIControladorDiaConsulta();
		
		assertEquals(10, ICE.getNombreEspecialidades().length);
		
		assertEquals(8, ICU.listarMedicos().size());
		assertEquals(8, ICU.listarPacientes().size());
		
		assertEquals(10, ICD.listarDiasConsultas().size());
		assertEquals(3, ICD.listarConsultasDiaConsulta("Cardiología - Lunes").size());
		assertEquals(2, ICD.listarConsultasDiaConsulta("Pediatría - Martes").size());
		assertEquals(2, ICD.listarConsultasDiaConsulta("Medicina General Central - Miércoles").size());
		assertEquals(2, ICD.listarConsultasDiaConsulta("Cardiología - Jueves").size());
		
		assertEquals(3, ICU.listarReservas("lgomez").size());
		assertEquals(3, ICU.listarReservas("mar.fernandez").size());
		assertEquals(2, ICU.listarReservas("s.lopez").size());
		
	}
	
	@Test 
		void cargaDatosTestDosVeces() {
			CargaDatos.cargar();
			CargaDatos.cargar();
			
			IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
			IControladorUsuario ICU = Fabrica.getInstance().getIControladorUsuario();
			
			assertEquals(10, ICE.getNombreEspecialidades().length);
			
			assertEquals(8, ICU.listarMedicos().size());
	}
	
	@Test
	void estanCargadosReflejaElEstado() {
	    assertFalse(CargaDatos.estanCargados());

	    CargaDatos.cargar();
	    assertTrue(CargaDatos.estanCargados());

	    CargaDatos.reiniciar();
	    assertFalse(CargaDatos.estanCargados());
	}
	
	@Test
	void cargarFallaSiYaExisteUnDato() throws Exception {
	    IControladorEspecialidad ICE = Fabrica.getInstance().getIControladorEspecialidad();
	    ICE.crearEspecialidad("Cardiología", 20);

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> CargaDatos.cargar());

	    assertTrue(exception.getMessage().startsWith("No se pudieron cargar los datos de prueba"));
	    assertTrue(exception.getCause() instanceof EspecialidadRepetidaException);

	    assertFalse(CargaDatos.estanCargados());
	}
}
