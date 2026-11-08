package logica;

import java.util.HashMap;
import java.util.Map;

public class Especialidad {
	private String nombre;
	private float costo;
	@SuppressWarnings("unused")
	private Map<String, Medico> medicos;
	private Map<String, DiaConsulta> diasConsulta;

	public Especialidad(String nombre, float costo) {
		this.costo = costo;
		this.nombre = nombre;
		medicos = new HashMap<>();
		diasConsulta = new HashMap<>();
	}

	/* public Especialidad() {}*/

	public String getNombre() {
		return nombre;
	}

/*	public void setNombre(String nombre) {
		this.nombre = nombre;
	}*/

	public float getCosto() {
		return costo;
	}

/*	public void setCosto(int costo) {
		this.costo = costo;
	}

	public Map<String, Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(Map<String, Medico> medicos) {
		this.medicos = medicos;
	}

	public void setDiasConsulta(Map<String, DiaConsulta> diasConsulta) {
		this.diasConsulta = diasConsulta;
	} 
*/
	public Map<String, DiaConsulta> getDiasConsulta() {
		return diasConsulta;
	}
	
	public void add(DiaConsulta dcon) {
		diasConsulta.put(dcon.getNombre(), dcon);
	}

/*	public boolean existsMedico(String nick) {
		return medicos.containsKey(nick);
	}

	public Medico findMedico(String nick) {
		return medicos.get(nick);
	}*/
}






