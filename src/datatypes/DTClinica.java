package datatypes;

import java.util.List;
import java.util.ArrayList;

public class DTClinica{
	private final String nombre; 
	private final String descripcion;
	private final String direccion;
	private final String sitioWeb;
	private final float costoTicket;
	private final float costoConsulta;
	private final List<DTDiaConsultaDetallado> diasConsulta;
	private final String foto;
	
	
	public DTClinica(String nombre, String descripcion, String direccion, String sitioWeb, float costoTicket, float costoConsulta, List<DTDiaConsultaDetallado> diasConsulta, String foto) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.sitioWeb = sitioWeb;
		this.costoTicket = costoTicket;
		this.costoConsulta = costoConsulta;
		this.diasConsulta = (diasConsulta == null) ? new ArrayList<>() : new ArrayList<>(diasConsulta);
		this.foto=foto;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getSitioWeb() {
		return sitioWeb;
	}
	public float getCostoTicket() {
		return costoTicket;
	}
	public float getCostoConsulta() {
		return costoConsulta;
	}
	public List<DTDiaConsultaDetallado> getDiasConsulta() {
		return diasConsulta;
	}
	public String getFoto() {
		return foto;
	}
	
};