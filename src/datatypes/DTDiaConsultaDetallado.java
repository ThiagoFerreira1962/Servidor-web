package datatypes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class DTDiaConsultaDetallado extends DTDiaConsulta{
	private final List<DTConsulta> consultas;
	private final String especialidad;
	
	
	public DTDiaConsultaDetallado(String nombre, String descripcion, DiaSemana dia, LocalTime horaInicio, float costo, List<DTConsulta> consultas, String clinica, String especialidad, String imagen) {
		super(nombre, descripcion, dia, horaInicio, costo, clinica, imagen);
		this.consultas = (consultas == null) ? new ArrayList<>() : new ArrayList<>(consultas);
		this.especialidad = especialidad;
	}
	
	public List<DTConsulta> getConsultas() { return this.consultas; }
	
	public String getEspecialidad() { return this.especialidad; }
}