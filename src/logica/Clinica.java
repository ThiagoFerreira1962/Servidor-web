package logica;
import datatypes.DTClinica;
import datatypes.DTDiaConsultaDetallado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clinica {
	private String nombre;
	private String descripcion;
	private String direccion;
	private String sitioWeb;
	private float costoTicket;
	private float costoConsulta;
	private Map<String, DiaConsulta> diasconsulta;
	private String foto;

	public Clinica(String nombre, String descripcion,
			String direccion, float costoTicket, float costoConsulta, String sitioWeb,  String foto){
		    this.nombre = nombre;
		    this.descripcion = descripcion;
		    this.direccion = direccion;
		    this.sitioWeb = sitioWeb;
		    this.costoTicket = costoTicket;
		    this.costoConsulta = costoConsulta;
		    this.diasconsulta = new HashMap<>();
		    this.foto=foto;

	};
	public Clinica(String nombre, String descripcion,
			String direccion, float costoTicket, float costoConsulta){
		    this.nombre = nombre;
		    this.descripcion = descripcion;
		    this.direccion = direccion;
		    this.costoTicket = costoTicket;
		    this.costoConsulta = costoConsulta;
		    this.diasconsulta = new HashMap<>();
	};
	/* public Clinica(Clinica obj1){
		this.nombre = obj1.nombre;
		this.descripcion = obj1.descripcion;
		this.direccion = obj1.direccion;
		this.sitioWeb = obj1.sitioWeb;
		this.costoTicket = obj1.costoTicket;
		this.costoConsulta = obj1.costoConsulta;
		this.diasconsulta = new HashMap<>();
	}; */
	public String getNombre() {
		return nombre;
	};
	/* public String getDescripcion() {
		return descripcion;
	};
	public String getDireccion() {
		return direccion;
	};
	public String getsitioWeb() {
		return sitioWeb;
	};
	public float getcostoConsulta() {
		return costoConsulta;
	};
/*	public void setNombre(String nombre) {
		this.nombre = nombre;
	};
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	};
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	};
	public void setcostoTicket(float costoTicket) {
		this.costoTicket = costoTicket;
	};
	public void setcostoConsulta(float costoConsulta) {
		this.costoConsulta = costoConsulta;
	};
	public void setsitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}*/
	public void add(DiaConsulta dia) {
		this.diasconsulta.put(dia.getNombre(), dia);
	}
	public float getcostoTicket() {
		return costoTicket;
	}; 
	public float getcostoConsulta() {
		return costoConsulta;
	};
	
	public DTClinica getDTClinica() {
		List<DTDiaConsultaDetallado> dias = new ArrayList<>();
		for (DiaConsulta d : diasconsulta.values()) {
			dias.add(d.getDTDiaConsultaDetallado());
		}
		DTClinica DTC = new DTClinica(this.nombre, this.descripcion, this.direccion, this.sitioWeb, this.costoTicket, this.costoConsulta, dias, this.foto);
		return DTC;
		
	}
	/* public boolean altaDiaConsulta(String nombreDC, String nombreClinica, 
			String descripcion, DiaSemana dia, LocalTime horaInicio, String especialidad) {
		return false;
	};
	public boolean exists(String nombreDiaConsulta) {
		return diasconsulta.containsKey(nombreDiaConsulta);
	}*/
}
