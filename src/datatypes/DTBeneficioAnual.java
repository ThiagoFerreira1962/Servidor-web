package datatypes;

public class DTBeneficioAnual {
	private final int anio;
	private final int cantidadOrdenes;
	private final int cantidadTickets;
	private final String nicknamePaciente;
	
	public DTBeneficioAnual(int anio, int cantOrdenes, int cantTickets, String nickPaciente) {
		this.anio = anio;
		this.cantidadOrdenes = cantOrdenes;
		this.cantidadTickets = cantTickets;
		this.nicknamePaciente = nickPaciente;
	}

	public int getAnio() {
		return anio;
	}

	public int getCantidadOrdenes() {
		return cantidadOrdenes;
	}

	public int getCantidadTickets() {
		return cantidadTickets;
	}

	public String getNicknamePaciente() {
		return nicknamePaciente;
	}
}