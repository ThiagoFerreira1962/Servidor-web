package datatypes;

import datatypes.DTMedicamento;
import java.util.ArrayList;
import java.util.List;
import logica.Reserva;
import logica.SolicitudMedicamento;

public class DTDetalleConsulta {
	private final boolean asistencia;
	private final String motivo;
	private final String diagnostico;
	private final List<DTMedicamento> medicamentos;
	
    public DTDetalleConsulta(boolean asistencia, String motivo,
            String diagnostico, List<DTMedicamento> medicamentos) {
    	this.asistencia = asistencia;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.medicamentos = medicamentos;
    }
    
    public boolean getAsistencia() {
    	return this.asistencia;
    }
    public String getMotivo() {
    	return this.motivo;
    }
    public String diagnostico() {
    	return this.diagnostico;
    }
    public List<DTMedicamento> getMedicamentos(){
    	return this.medicamentos;
    }

}
