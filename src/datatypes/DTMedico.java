package datatypes;

import java.util.List;


public class DTMedico extends DTUsuario {

	private final String nroRegistro;
	private final Mes licencia;
	private final float costoAdicional;
	private final String sitioWeb;
	private final List<DTConsulta> consultas;
	private final List<String> especialidades;
	
	public DTMedico(String nickname, String nombre, String apellido, String correo, String nroRegistro, Mes licencia, float costoAdicional, String sitioWeb, String foto) {
		super(nickname, nombre, apellido, correo, foto);
		this.nroRegistro = nroRegistro;
		this.licencia = licencia;
		this.costoAdicional = costoAdicional;
		this.sitioWeb = sitioWeb;
		this.especialidades = null;
		this.consultas = null;
	}
	
	public DTMedico(String nickname, String nombre, String apellido, String correo, String nroRegistro, Mes licencia, float costoAdicional, String foto) {
		super(nickname, nombre, apellido, correo, foto);
		this.nroRegistro = nroRegistro;
		this.licencia = licencia;
		this.costoAdicional = costoAdicional;
		this.sitioWeb = "";
		this.especialidades = null;
		this.consultas = null;
	}
	
	public DTMedico(String nickname, String nombre, String apellido, String correo, String nroRegistro, Mes licencia, float costoAdicional, String sitioWeb, List<DTConsulta> consultas, List<String> especialidades, String foto) {
		super(nickname, nombre, apellido, correo, foto);
		this.nroRegistro = nroRegistro;
		this.licencia = licencia;
		this.costoAdicional = costoAdicional;
		this.sitioWeb = sitioWeb;
		this.consultas = consultas;
		this.especialidades = especialidades;
	}

	public String getNroRegistro() {return this.nroRegistro; }
	
	public Mes getLicencia() {return this.licencia; }
	
	public float getCostoAdicional() {return this.costoAdicional; }
	
	public String getSitioWeb() {return this.sitioWeb; }

	public List<DTConsulta> getConsultas() {
		return consultas;
	}
	
	public List<String> getEspecialidades() {
		return especialidades;
	}
	
	
}
