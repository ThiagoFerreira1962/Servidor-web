package logica;

import java.util.Map;
import java.util.HashMap;

public class HandlerEspecialidades{
	private static HandlerEspecialidades instance;
	private static Map<String, Especialidad> especialidades;
	
	private HandlerEspecialidades() {
		especialidades = new HashMap<>();
	}
	
	
	public static HandlerEspecialidades getInstance() {
		if (instance == null) {
			instance = new HandlerEspecialidades();
		}
		return instance;
	}
	
	public void add(Especialidad esp) {
		especialidades.put(esp.getNombre(), esp);
	}
	
	public Especialidad find(String nombre) {
		return especialidades.get(nombre);
	}
	
	public boolean exists(String nombre) {
		return especialidades.containsKey(nombre);
	}
	
	public String[] listarNombreEspecialidades() {
		 String[] lista = new String[especialidades.size()]; 
		int indice = 0;
		for (String nomEspecialidad  : especialidades.keySet()){
			lista[indice] = nomEspecialidad;
			indice++;
		}
		return lista;
	}
	public void limpiarInstancia() {
		especialidades.clear();
		instance = null;
	}
}