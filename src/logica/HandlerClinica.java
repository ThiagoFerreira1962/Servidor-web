package logica;
import java.util.Map;

import datatypes.DTClinica;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class HandlerClinica{

private	static HandlerClinica instance;
private	Map<String, Clinica> clinicas;


private HandlerClinica() {
    clinicas = new HashMap<>();
}
public static HandlerClinica getInstance() {
	if (instance == null) {
		instance = new HandlerClinica();
	}
 return instance;
};

public List<String> listarNombreClinicas(){
	
	Iterator<Map.Entry<String, Clinica>> itr = clinicas.entrySet().iterator();
	List<String> conjunto = new ArrayList<String>();
	while (itr.hasNext()) {	
		Map.Entry<String, Clinica> entry = itr.next();
		conjunto.add(entry.getKey());
	};
return conjunto;
}

public List<DTClinica> listarDTClincas() {
	List<DTClinica> resultado = new ArrayList<DTClinica>();
	for (Clinica cli : clinicas.values()) {
		resultado.add(cli.getDTClinica());
	}
	return resultado;
}

public boolean exists(String nombreClinica) {
	return clinicas.containsKey(nombreClinica);
}

public Clinica getClinica(String nombreClinica) {
		return clinicas.get(nombreClinica);
}

public void add(Clinica clinica) {
	clinicas.put(clinica.getNombre(), clinica);
}

public void limpiarInstancia() {
	instance = null;
}

}