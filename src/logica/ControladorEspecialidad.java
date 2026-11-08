package logica;

import java.util.ArrayList;
import java.util.List;

import datatypes.DTConsulta;
import datatypes.DTDiaConsultaDetallado;
import excepciones.EspecialidadRepetidaException;

public class ControladorEspecialidad implements IControladorEspecialidad {

	@Override
	public String [] getNombreEspecialidades(){
	HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
	return hesp.listarNombreEspecialidades();	
	}
	
	@Override
	public void crearEspecialidad(String nombre, float costo) throws EspecialidadRepetidaException{
	HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
	if ( hesp.exists(nombre) ){ throw new EspecialidadRepetidaException();
	}
	Especialidad especialidad = new Especialidad(nombre, costo);
	hesp.add(especialidad);
	}
	
	@Override
	public List<DTDiaConsultaDetallado> listarDiasPorEspecialidad(String nombreEspecialidad) {
		HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
		Especialidad esp = hesp.find(nombreEspecialidad);
		if (esp == null) {
			return new ArrayList<>();
		}
		List<DTDiaConsultaDetallado> resultado = new ArrayList<>();
		for (DiaConsulta d : esp.getDiasConsulta().values()) {
			resultado.add(d.getDTDiaConsultaDetallado());
		}
		return resultado;
	}
	
	@Override
	public List<DTConsulta> listarConsultasPorEspecialidad(String nombreEspecialidad) {
		HandlerEspecialidades hesp = HandlerEspecialidades.getInstance();
		Especialidad esp = hesp.find(nombreEspecialidad);
		if (esp == null) {
			return new ArrayList<>();
		}
		List<DTConsulta> resultado = new ArrayList<>();
		for (DiaConsulta d : esp.getDiasConsulta().values()) {
			resultado.addAll(d.getDTConsultasAceptadas());
		}
		return resultado;
	}
	
}