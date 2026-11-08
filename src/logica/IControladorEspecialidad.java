package logica;

import java.util.List;

import datatypes.DTConsulta;
import datatypes.DTDiaConsultaDetallado;
import excepciones.EspecialidadRepetidaException;

public interface IControladorEspecialidad {

public void crearEspecialidad(String nombre, float costo) throws EspecialidadRepetidaException;

public String [] getNombreEspecialidades();

public List<DTDiaConsultaDetallado> listarDiasPorEspecialidad(String nombreEspecialidad);

public List<DTConsulta> listarConsultasPorEspecialidad(String nombreEspecialidad);

}
