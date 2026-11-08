package logica;

import java.util.List;

import datatypes.DTClinica;
import excepciones.ClinicaRepetidaException;

public class ControladorClinica implements IControladorClinica{

	@Override
	public List<String> listarNombreClinicas(){
		HandlerClinica hcli = HandlerClinica.getInstance();
		return hcli.listarNombreClinicas();
	};

	@Override
	public DTClinica seleccionarClinica(String nombreClinica) {
		HandlerClinica hcli = HandlerClinica.getInstance();
		Clinica clinica = hcli.getClinica(nombreClinica);
		if (clinica != null) {
			return clinica.getDTClinica();
		}
		return null;
	};
	
	@Override
	public void altaClinica(String nombreClinica, String descripcion, String direccion, float costoTicketMedicamento, float costoOrdenConsulta, String sitioWeb, String foto) throws ClinicaRepetidaException {
		HandlerClinica hcli = HandlerClinica.getInstance();
	    if (hcli.exists(nombreClinica)) {
	        throw new ClinicaRepetidaException();
	    }
	    Clinica clinica;
	    if (sitioWeb == null && foto == null) {
	        clinica = new Clinica(nombreClinica, descripcion, direccion, costoTicketMedicamento, costoOrdenConsulta);
	    } else {
	        clinica = new Clinica(nombreClinica, descripcion, direccion, costoTicketMedicamento, costoOrdenConsulta, sitioWeb, foto);
	    }
	    hcli.add(clinica);
	}
	    
	@Override
	public List<DTClinica> listarClinicas() {
		HandlerClinica hcli = HandlerClinica.getInstance();
		return hcli.listarDTClincas();
	}
}
