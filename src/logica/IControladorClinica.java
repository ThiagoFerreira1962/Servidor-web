package logica;
import java.util.List;
import datatypes.DTClinica;
import excepciones.ClinicaRepetidaException;


public interface IControladorClinica{
 public	void altaClinica(String nombreClinica, String descripcion, String direccion, 
			float costoTicketMedicamento, float costoOrdenConsulta, String sitioWeb,  String foto) throws ClinicaRepetidaException;
 public List<String> listarNombreClinicas();
 public DTClinica seleccionarClinica(String nombreClinica);
 public List<DTClinica> listarClinicas();
};