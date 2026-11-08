package logica;

public class BeneficioAnual {
	private final int anio;
	private int cantidadOrdenes;
	private int cantidadTickets;
	
	public BeneficioAnual(int anio, int cantOrdenes, int cantTickets) {
		this.anio = anio;
		this.cantidadOrdenes = cantOrdenes;
		this.cantidadTickets = cantTickets;
	}
	
	public int getAnio() { return anio; }
	
	public int getCantidadOrdenes() { return cantidadOrdenes; }
	public void setCantidadOrdenes(int cantOrdenes) { this.cantidadOrdenes = cantOrdenes; }
	
	//SOLO LAS RESERVAS PUEDEN MODIFICAR CANTIDAD(POR AHORA)
	public void usarReserva() {
		cantidadOrdenes--;
	}
	public int getCantidadTickets() { return cantidadTickets; }
	public void setCantidadTickets(int cantTickets) { this.cantidadTickets = cantTickets; }
	
	//public Paciente getPaciente() { return this.paciente;}
	
}