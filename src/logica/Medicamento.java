package logica;

import java.time.LocalDate;

public class Medicamento {

	private static int proximaId = 0; //valor incremental para la id
	private String nombre;
	private String posologia;
	private float costo;
	private LocalDate fecha;
	private int id;
	private DetalleConsulta detalleConsulta;
		
	Medicamento(String nombre, String posologia, float costo, DetalleConsulta detalleConsulta){
		this.nombre = nombre;
		this.posologia = posologia;
		this.costo = costo;
		this.fecha = LocalDate.now();
		this.id = proximaId;
		proximaId++;
		this.detalleConsulta = detalleConsulta;
	}
	
}