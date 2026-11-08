package logica;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import datatypes.DiaSemana;
import datatypes.Mes;
import excepciones.BeneficioAnualAnioPosteriorException;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.ClinicaRepetidaException;
import excepciones.ConsultaRepetidaException;
import excepciones.DiaConsultaRepetidoException;
import excepciones.EspecialidadRepetidaException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;
import excepciones.PacienteYaTieneReservaException;
import excepciones.SinCuposDisponiblesException;
import excepciones.UsuarioRepetidoException;

public class CargaDatos {
	private static boolean cargados = false;
	private CargaDatos() {
	}
	public static void cargar() {
	    if (cargados) {
	        return;
	    }
	    Fabrica fab = Fabrica.getInstance();
	    IControladorEspecialidad ICE = fab.getIControladorEspecialidad();
	    IControladorClinica ICC = fab.getIControladorClinica();
	    IControladorUsuario ICU = fab.getIControladorUsuario();
	    IControladorDiaConsulta ICD = fab.getIControladorDiaConsulta();

	    try {
	        //ESPECIALIDADES
	        ICE.crearEspecialidad("Cardiología", 20);
	        ICE.crearEspecialidad("Pediatría", 10);
	        ICE.crearEspecialidad("Medicina General", 0);
	        ICE.crearEspecialidad("Fisioterapia", 8);
	        ICE.crearEspecialidad("Nutrición", 12);
	        ICE.crearEspecialidad("Ginecología", 15);
	        ICE.crearEspecialidad("Oncología", 25);
	        ICE.crearEspecialidad("Oftalmología", 10);
	        ICE.crearEspecialidad("Dermatología", 12);
	        ICE.crearEspecialidad("Traumatología", 18);

	        //CLINICAS
	        ICC.altaClinica("Hospital Británico", "Hospital general privado", "Av. Italia 2420", 350, 1200, "https://www.hospitalbritanico.org.uy/", "CL01.jpg");
	        ICC.altaClinica("CASMU", "Institución de asistencia médica", "Av. 8 de Octubre 3310", 200, 950, "https://www.casmu.com.uy/", "CL02.jpeg");
	        ICC.altaClinica("Médica Uruguaya", "Institución de asistencia médica", "Av. 8 de Octubre 2492", 280, 1050, "https://www.medicauruguaya.com.uy/", "CL03.jpeg");
	        ICC.altaClinica("Asociación Española", "Institución de salud privada", "Bulevar Artigas 1525", 310, 1100, "https://www.asesp.com.uy/", "defaultClinica.jpg");
	        ICC.altaClinica("Hospital Evangélico", "Centro de salud", "Bv. Artigas 1985", 250, 980, "https://www.hospitalevangelico.com/", "CL05.png");
	        ICC.altaClinica("Hospital de Clínicas", "Hospital universitario", "Av. Italia s/n", 180, 800, "https://www.hc.edu.uy/", "CL06.jpg");

	        //USUARIOS
	        //MEDICOS
	        ICU.altaMedico("ana.perez", "Ana", "Pérez", "ana.perez@tuclinica.com", "REG1001", Mes.Enero, 15f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "aperez123", "US01.jpeg");
	        ICU.altaMedico("bsilva", "Bruno", "Silva", "bruno.silva@hospital.com", "REG1002", Mes.Julio, 5f, "", Arrays.asList("Pediatría"), "bsilva123", "US02.jpeg");
	        ICU.altaMedico("carla.rodriguez", "Carla", "Rodríguez", "carla.rodriguez@hospital.com", "REG1003", Mes.Febrero, 10f, "https://www.hospitalbritanico.org.uy/", Arrays.asList("Cardiología", "Medicina General"), "crodriguez123", "US03.jpeg");
	        ICU.altaMedico("diego.acosta7", "Diego", "Acosta", "diego.acosta7@hospital.com", "REG1007", Mes.Enero, 5f, "", Arrays.asList("Cardiología", "Fisioterapia"), "dacosta123", "US07.jpeg");
	        ICU.altaMedico("elena.benítez8", "Elena", "Benítez", "elena.benítez8@clinicas.com", "REG1008", Mes.Marzo, 10f, "", Arrays.asList("Pediatría", "Nutrición"), "ebenitez123", "US08.jpeg");
	        ICU.altaMedico("federico.cabrera9", "Federico", "Cabrera", "federico.cabrera9@hospital.com", "REG1009", Mes.Junio, 12f, "", Arrays.asList("Medicina General", "Ginecología"), "fcabrero123", "US09.jpeg");
	        ICU.altaMedico("gabriela.díaz10", "Gabriela", "Díaz", "gabriela.díaz10@hospital.com", "REG1010", Mes.Agosto, 15f, "", Arrays.asList("Fisioterapia", "Oncología"), "gdiaz123", "defaultMedico.jpg");
	        ICU.altaMedico("hernán.estevez11", "Hernán", "Estevez", "hernán.estevez11@clinica.com", "REG1011", Mes.Noviembre, 20f, "", Arrays.asList("Nutrición", "Oftalmología"), "hestevez123", "defaultMedico.jpg");

	        //PACIENTES
	        ICU.altaPaciente("lgomez", "Laura", "Gómez", "laura.gomez@gmail.com", LocalDate.of(1990, 4, 12), "45678901", LocalDate.of(2024, 3, 1), "Sin antecedentes relevantes.", "lgomez123", "US04.jpeg");
	        ICU.altaPaciente("mar.fernandez", "Mario", "Fernández", "mario.fernandez@hotmail.com", LocalDate.of(1955, 11, 8), "32145678", LocalDate.of(2022, 6, 15), "Hipertensión arterial diagnosticada en 2018, "
	                + "actualmente controlada con tratamiento farmacológico. Antecedentes de colesterol elevado y episodios ocasionales de migraña. En 2022 fue intervenido quirúrgicamente por una hernia inguinal, sin complicaciones posteriores.", "mfernandez123", "US05.jpeg");
	        ICU.altaPaciente("s.lopez", "Sofía", "López", "sofia.lopez@gmail.com", LocalDate.of(2014, 9, 20), "58963214", LocalDate.of(2025, 2, 10), "Alergia a penicilina.", "slopez123", "defaultPaciente.jpg");
	        ICU.altaPaciente("andrés.núñez19", "Andrés", "Núñez", "andrés.núñez19@gmail.com", LocalDate.of(1947, 1, 1), "30248729", LocalDate.of(2022, 1, 1), "Sin antecedentes relevantes.", "añunez123", "US12.jpeg");
	        ICU.altaPaciente("beatriz.ortiz20", "Beatriz", "Ortiz", "beatriz.ortiz20@hotmail.com", LocalDate.of(1952, 2, 3), "30261820", LocalDate.of(2023, 2, 2), "Alergia estacional al polen.", "bortiz123", "US13.jpeg");
	        ICU.altaPaciente("camila.pereira21", "Camila", "Pereira", "camila.pereira21@hotmail.com", LocalDate.of(1960, 3, 5), "30274911", LocalDate.of(2024, 3, 3), "Hipertensión arterial controlada.", "cpereira123", "US14.png");
	        ICU.altaPaciente("daniel.quintana22", "Daniel", "Quintana", "daniel.quintana22@gmail.com", LocalDate.of(1975, 4, 7), "30288002", LocalDate.of(2025, 4, 4), "Antecedentes de asma leve.", "dquintana123", "US15.jpeg");
	        ICU.altaPaciente("emilia.ramos23", "Emilia", "Ramos", "emilia.ramos23@gmail.com", LocalDate.of(1985, 5, 9), "30301093", LocalDate.of(2026, 5, 5), "Cirugía de apéndice sin complicaciones.", "eramos123", "defaultPaciente.jpg");

	        //BENEFICIOSANUALES
	        ICU.altaBeneficioAnual("lgomez", 2025, 2, 3);
	        ICU.altaBeneficioAnual("lgomez", 2026, 3, 4);
	        ICU.altaBeneficioAnual("mar.fernandez", 2026, 4, 5);
	        ICU.altaBeneficioAnual("s.lopez", 2026, 2, 2);
	        //ICU.altaBeneficioAnual("lgomez", 2027, 5, 2);
	        //ICU.altaBeneficioAnual("mar.fernandez", 2027, 1, 3);
	        //ICU.altaBeneficioAnual("s.lopez", 2027, 2, 4);
	        ICU.altaBeneficioAnual("andrés.núñez19", 2026, 5, 5);
	        //ICU.altaBeneficioAnual("andrés.núñez19", 2027, 5, 5);
	        ICU.altaBeneficioAnual("beatriz.ortiz20", 2026, 1, 2);
	        //ICU.altaBeneficioAnual("beatriz.ortiz20", 2027, 1, 2);
	        ICU.altaBeneficioAnual("camila.pereira21", 2026, 2, 3);

	        //DIASCONSULTA
	        ICD.altaDiaConsulta("Cardiología - Lunes", "Atención cardiológica semanal", DiaSemana.Lunes, LocalTime.parse("09:00"), "Hospital Británico", "Cardiología", "DC01.jpeg");
	        ICD.altaDiaConsulta("Pediatría - Martes", "Atención pediátrica semanal", DiaSemana.Martes, LocalTime.parse("14:00"), "CASMU", "Pediatría", "DC02.png");
	        ICD.altaDiaConsulta("Medicina General Central - Miércoles", "Consulta general semanal", DiaSemana.Miercoles, LocalTime.parse("10:30"), "Hospital Británico", "Medicina General", "DC03.jpeg");
	        ICD.altaDiaConsulta("Cardiología - Jueves", "Atención cardiológica semanal", DiaSemana.Jueves, LocalTime.parse("16:00"), "CASMU", "Cardiología", "DC04.jpeg");
	        ICD.altaDiaConsulta("Cardiología - Lunes - Turno 1", "Atención de cardiología en Hospital Británico", DiaSemana.Lunes, LocalTime.parse("08:00"), "Hospital Británico", "Cardiología", "defaultDiaConsulta.jpg");
	        ICD.altaDiaConsulta("Pediatría - Martes - Turno 1", "Atención de pediatría en Asociación Española", DiaSemana.Martes, LocalTime.parse("09:30"), "Asociación Española", "Pediatría", "DC06.jpeg");
	        ICD.altaDiaConsulta("Medicina General - Miércoles - Turno 1", "Atención de medicina general en Hospital Británico", DiaSemana.Miercoles, LocalTime.parse("10:00"), "Hospital Británico", "Medicina General", "DC07.jpeg");
	        ICD.altaDiaConsulta("Fisioterapia - Jueves", "Atención de fisioterapia en Asociación Española", DiaSemana.Jueves, LocalTime.parse("11:30"), "Asociación Española", "Fisioterapia", "DC08.png");
	        ICD.altaDiaConsulta("Nutrición - Viernes", "Atención de nutrición en Hospital Británico", DiaSemana.Viernes, LocalTime.parse("12:00"), "Hospital Británico", "Nutrición", "DC09.jpeg");
	        ICD.altaDiaConsulta("Ginecología - Sábado", "Atención de ginecología en Asociación Española", DiaSemana.Sabado, LocalTime.parse("13:30"), "Asociación Española", "Ginecología", "DC10.jpeg");

	        //CONSULTAS
	        ICD.altaConsulta("Cardiología 240826", LocalDate.of(2026, 8, 24), 101, 3, "ana.perez", "Cardiología - Lunes", "CO01.jpeg");
	        ICD.altaConsulta("Pediatría 080926", LocalDate.of(2026, 9, 8), 202, 2, "bsilva", "Pediatría - Martes", "CO02.jpeg");
	        ICD.altaConsulta("Medicina General 090926", LocalDate.of(2026, 9, 9), 103, 4, "carla.rodriguez", "Medicina General Central - Miércoles", "CO03.jpeg");
	        ICD.altaConsulta("Cardiología 130826", LocalDate.of(2026, 8, 13), 204, 2, "carla.rodriguez", "Cardiología - Jueves", "CO04.jpeg");
	        ICD.altaConsulta("Cardiología 140926", LocalDate.of(2026, 9, 14), 101, 3, "ana.perez", "Cardiología - Lunes", "CO05.jpeg");
	        ICD.altaConsulta("Cardiología 121026 6", LocalDate.of(2026, 10, 12), 100, 3, "ana.perez", "Cardiología - Lunes", "CO06.jpeg");
	        ICD.altaConsulta("Pediatría 131026 7", LocalDate.of(2026, 10, 13), 101, 4, "bsilva", "Pediatría - Martes", "CO07.jpeg");
	        ICD.altaConsulta("Medicina General 141026 8", LocalDate.of(2026, 10, 28), 102, 5, "ana.perez", "Medicina General Central - Miércoles", "CO08.png");
	        ICD.altaConsulta("Cardiología 151026 9", LocalDate.of(2026, 10, 15), 103, 6, "ana.perez", "Cardiología - Jueves", "defaultConsulta.jpg");
	        ICD.altaConsulta("Cardiología 121026 10", LocalDate.of(2026, 10, 12), 104, 7, "ana.perez", "Cardiología - Lunes - Turno 1", "CO10.jpeg");
	        ICD.altaConsulta("Pediatría 131026 11", LocalDate.of(2026, 10, 27), 105, 8, "bsilva", "Pediatría - Martes - Turno 1", "CO11.jpeg");
	        ICD.altaConsulta("Medicina General 141026 12", LocalDate.of(2026, 10, 28), 106, 9, "ana.perez", "Medicina General - Miércoles - Turno 1", "CO12.jpeg");
	        ICD.altaConsulta("Fisioterapia 151026 13", LocalDate.of(2026, 10, 15), 107, 10, "diego.acosta7", "Fisioterapia - Jueves", "CO13.jpeg");
	        ICD.altaConsulta("Nutrición 161026 14", LocalDate.of(2026, 10, 16), 108, 3, "elena.benítez8", "Nutrición - Viernes", "CO14.jpeg");
	        ICD.altaConsulta("Ginecología 171026 15", LocalDate.of(2026, 10, 17), 109, 4, "federico.cabrera9", "Ginecología - Sábado", "CO15.jpeg");

	        //ESTADOS DE LAS CONSULTAS
	        ICD.aceptarConsulta("Cardiología 240826", "Cardiología - Lunes");
	        ICD.aceptarConsulta("Pediatría 080926", "Pediatría - Martes");
	        ICD.aceptarConsulta("Medicina General 090926", "Medicina General Central - Miércoles");
	        //CO04 queda Rechazada
	        ICD.rechazarConsulta("Cardiología 130826", "Cardiología - Jueves");
	        //CO05 queda Ingresada
	        ICD.aceptarConsulta("Cardiología 121026 6", "Cardiología - Lunes");
	        ICD.aceptarConsulta("Pediatría 131026 7", "Pediatría - Martes");
	        ICD.aceptarConsulta("Medicina General 141026 8", "Medicina General Central - Miércoles");
	        //CO09 queda Ingresada
	        //CO09 queda Rechazada
	        ICD.rechazarConsulta("Cardiología 121026 10", "Cardiología - Lunes - Turno 1");
	        ICD.aceptarConsulta("Pediatría 131026 11", "Pediatría - Martes - Turno 1");
	        ICD.aceptarConsulta("Medicina General 141026 12", "Medicina General - Miércoles - Turno 1");
	        ICD.aceptarConsulta("Fisioterapia 151026 13", "Fisioterapia - Jueves");
	        //CO14 queda Ingresada
	        //CO15 queda Rechazada
	        ICD.rechazarConsulta("Ginecología 171026 15", "Ginecología - Sábado");

	        //RESERVAS
	        ICU.altaReserva("lgomez", "Cardiología 240826", LocalDate.of(2026, 8, 1), 1656f, 0);
	        ICU.altaReserva("mar.fernandez", "Cardiología 240826", LocalDate.of(2026, 7, 27), 1324.8f, 0);
	        ICU.altaReserva("s.lopez", "Pediatría 080926", LocalDate.of(2026, 8, 27), 1097.25f, 0);
	        ICU.altaReserva("lgomez", "Pediatría 080926", LocalDate.of(2026, 8, 28), 1097.25f, 0);
	        ICU.altaReserva("mar.fernandez", "Medicina General 090926", LocalDate.of(2026, 8, 29), 1056f, 0);
	        ICU.altaReserva("lgomez", "Cardiología 121026 6", LocalDate.of(2026, 10, 9), 0f, 2025);   //usa el beneficio anual BA01
	        ICU.altaReserva("mar.fernandez", "Cardiología 121026 6", LocalDate.of(2026, 10, 8), 1324.8f, 0);
	        ICU.altaReserva("s.lopez", "Cardiología 121026 6", LocalDate.of(2026, 10, 7), 1656f, 0);
	        ICU.altaReserva("andrés.núñez19", "Pediatría 131026 7", LocalDate.of(2026, 10, 10), 877.8f, 0);
	        ICU.altaReserva("beatriz.ortiz20", "Pediatría 131026 7", LocalDate.of(2026, 10, 9), 877.8f, 0);
	        ICU.altaReserva("camila.pereira21", "Pediatría 131026 7", LocalDate.of(2026, 10, 8), 877.8f, 0);
	        ICU.altaReserva("daniel.quintana22", "Pediatría 131026 7", LocalDate.of(2026, 10, 7), 1097.25f, 0);
	        ICU.altaReserva("daniel.quintana22", "Medicina General 141026 8", LocalDate.of(2026, 10, 11), 1380f, 0);
	        ICU.altaReserva("emilia.ramos23", "Medicina General 141026 8", LocalDate.of(2026, 10, 10), 1380f, 0);
	    } catch (BeneficioAnualAnioPosteriorException | BeneficioAnualRepetidoException | ClinicaRepetidaException
	    		| ConsultaRepetidaException | DiaConsultaRepetidoException | EspecialidadRepetidaException 
	    		| FechaNoCoincideException | MedicoNoTieneEspecialidadException | PacienteYaTieneReservaException 
	    		| SinCuposDisponiblesException | UsuarioRepetidoException  e) {
	        throw new RuntimeException("No se pudieron cargar los datos de prueba: " + e.getMessage(), e);
	    }
	    cargados = true;
	}
	
	//Para poder esconder el botón en la página, cuando se cargan los datos
	public static boolean estanCargados() {
		return cargados;
	}
	
	//Para hacer test
	public static void reiniciar() {
		cargados = false;
	}
}