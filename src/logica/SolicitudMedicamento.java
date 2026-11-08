package logica;
import datatypes.DTMedicamento;
import java.time.LocalDate;

public class SolicitudMedicamento {
	private DetalleConsulta detalleConsulta;
	private DTMedicamento medicamento;
	private LocalDate fecha;
	private String direccion;
	private float costo;
	private int anioBeneficio;
	
	public SolicitudMedicamento(DetalleConsulta dc, DTMedicamento medicamento, LocalDate fechaEntrega, String direccion, float costo, int anioBeneficio) {
		this.detalleConsulta= dc;
		this.medicamento= medicamento;
		this.fecha=fechaEntrega;
		this.direccion=direccion;
		this.costo=costo;
		this.anioBeneficio=anioBeneficio;	
	}
}
