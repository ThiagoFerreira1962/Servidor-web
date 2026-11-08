package logica;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTReserva;

public class HandlerDiaConsulta {
	private static HandlerDiaConsulta instance;
	private Map<String, DiaConsulta> diasConsulta;
	private HandlerDiaConsulta() {
		diasConsulta=new HashMap<>();
	}
	public static HandlerDiaConsulta getInstance() {
		if (instance==null) {
			instance= new HandlerDiaConsulta();
		}
		return instance;
	}
	
	public boolean hayCuposConsulta(String nomConsulta) {

	    for (DiaConsulta d : diasConsulta.values()) {

	        if (d.existeConsultaNombre(nomConsulta)) {
	            return d.hayCuposConsulta(nomConsulta);
	        }
	    }

	    return false;
	}
	
	public boolean agregarReservaConsulta(Reserva res, String nomConsulta){ 
		for (DiaConsulta dcon : diasConsulta.values()) 
			if (dcon.existeConsulta(res, nomConsulta)) {
				return true;
			}
		return false;
		}
	
	public List<DTDiaConsulta> getDTDiasConsulta(){
		List<DTDiaConsulta> dias= new ArrayList<>();
		for (DiaConsulta dcon : diasConsulta.values()) {
			DTDiaConsulta dtc= dcon.getDTDiaConsulta();
			dias.add(dtc);
			
		}
		return dias;
	}
	
	public List<DTConsulta> getDTTodasConsultasAceptadas() {
		List<DTConsulta> resultado = new ArrayList<>();
		for (DiaConsulta dcon : diasConsulta.values()) {
			resultado.addAll(dcon.getDTConsultasAceptadas());
		}
		return resultado;
	}
	
	public List<DTReserva> listarReservasConsulta(String nombreConsulta) {
		for (DiaConsulta d : diasConsulta.values()) {
			Consulta consulta = d.getConsulta(nombreConsulta);
			if (consulta != null) {
				return consulta.getDTReservas();
			}
		}
		return new ArrayList<>();
	}
	
	public boolean existeNombreConsulta(String nom) {
		for (DiaConsulta d : diasConsulta.values()) {
			boolean encontrado = d.existeConsultaNombre(nom);
			if (encontrado) {
				return true;
			}
			
		}
		return false;
	}
	
	public DTConsulta seleccionarConsulta(String nombreConsulta) {
		for (DiaConsulta d : diasConsulta.values()) {
			DTConsulta consulta = d.seleccionarConsulta(nombreConsulta);
			if (consulta != null) {
				return consulta;
			}
		}
		return null;
	}

	public boolean existe(String nombreDC) {
		return diasConsulta.containsKey(nombreDC);
	}
	
	public DiaConsulta getDiaConsulta(String nombreDia) {
		return diasConsulta.get(nombreDia); //a traves de la clave el mapa devuelve el diaConsutla correspondiente
	}
	
	public void add(DiaConsulta dcons) {
		diasConsulta.put(dcons.getNombre(), dcons);
	}
	public int getNroActualConsulta(String nombreConsulta) {
		for (DiaConsulta d : diasConsulta.values()){
			Consulta encontrado = d.getConsulta(nombreConsulta);
			if ( encontrado != null ) {
				return encontrado.getNroActual();
				
			}
		}
		return 0;
	}
	public void limpiarInstancia() {
		instance = null;
	}
}
