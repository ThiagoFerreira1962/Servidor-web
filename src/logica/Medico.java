package logica;

import datatypes.Mes;
import java.util.ArrayList;
import datatypes.DTUsuario;
import datatypes.DTMedico;
import datatypes.DTConsulta;
import java.util.List;

public class Medico extends Usuario {
	
	private String nroRegistro;
	private Mes licencia;
	private float costoAdicional;
	private String sitioWeb;

	private List<Especialidad> especialidades;
	private List<Consulta> consultas;
	
	//constructor
	
	Medico(String nickname, String nombre, String apellido, String correo, String nroRegistro, Mes licencia, float costoAdicional, String sitioWeb, List<Especialidad> especialidades, String contrasena, String foto){
		super(nickname, nombre, apellido, correo, contrasena, foto);
		this.nroRegistro = nroRegistro;
		this.licencia = licencia;
		this.costoAdicional = costoAdicional;
		this.sitioWeb = sitioWeb;
		this.especialidades = especialidades;
		this.consultas= new ArrayList<>(); //inicializar lista vacia
	}
	

	//getters
	
	/*public String getNroRegistro() {return this.nroRegistro;}*/
	
	/*public Mes getLicencia() {return this.licencia;}*/
	
	public float getCostoAdicional() {return this.costoAdicional;
	}
	
	/*public String getSitioWeb() {return this.sitioWeb;}*/
	
	public List<String> getEspecialidades() {
		List<String> listaEs = new ArrayList<>();
		for (Especialidad es : especialidades) {
			listaEs.add(es.getNombre());
		}
		return listaEs;
	}
	

	
	//setters
	
	 public void setNroRegistro(String nroRegistro) {this.nroRegistro = nroRegistro;
	 }
	
	public void setLicencia(Mes licencia) {this.licencia = licencia; 
	}
	
	public void setCostoAdicional(float costoAdicional) {this.costoAdicional = costoAdicional;
	}
	
	public void setSitioWeb(String sitioWeb) {this.sitioWeb = sitioWeb;
	} 
	
	
	@Override
	public DTUsuario obtenerDTUsuario() {
		DTUsuario dtu = new DTMedico(this.getNickname(), this.getNombre(), this.getApellido(), this.getCorreo(), this.nroRegistro, this.licencia, this.costoAdicional, this.sitioWeb, 
									this.obtenerConsultas(), this.getEspecialidades(), this.getFoto());
		return dtu;
		}
	
	
	public List<DTConsulta> obtenerConsultas() {
		List<DTConsulta> res= new ArrayList<>(); //inicializar lista
		for (Consulta consulta : this.consultas) {
			res.add(consulta.getDTConsulta());
		}
		return res;
	}
	
	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	} 
	
	public boolean tieneEspecialidad(Especialidad especialidad) {
		for (Especialidad esp : especialidades) { //recorro la lista 
			if (esp==especialidad) {
				return true;
			}
		}
		return false;
	}
	
	public void agregarConsulta(Consulta cons) {
		consultas.add(cons);
	}
	
	public boolean tieneConsultaConEspecialidad(Especialidad esp) {
	    for (Consulta c : consultas) {
	        if (c.getDiaConsulta().getEspecialidad() == esp) {
	            return true;
	        }
	    }
	    return false;
	}
	public Consulta obtenerConsulta(String nombre) {
		for (Consulta c : consultas) {
			if (nombre == c.getNombre()) {
				return c;
			}
		}
		return null;
	}
}
