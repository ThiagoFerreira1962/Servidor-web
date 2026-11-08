package logica;

public class Fabrica {
	private static Fabrica instance;
	private Fabrica(){
	};
	
	public static Fabrica getInstance() {
		if (instance == null) {
			instance = new Fabrica();
		}
		return instance;
	};
	
	public IControladorUsuario getIControladorUsuario() {
		IControladorUsuario icu = new ControladorUsuario();
		return icu;
	}
	
	public IControladorDiaConsulta getIControladorDiaConsulta() {
		IControladorDiaConsulta icdc = new ControladorDiaConsulta();
		return icdc;
	}
	
	public IControladorClinica getIControladorClinica() {
		IControladorClinica icc = new ControladorClinica();
		return icc;
	}
	
	public IControladorEspecialidad getIControladorEspecialidad() {
		IControladorEspecialidad ice = new ControladorEspecialidad();
		return ice;
	}
	public void limpiarInstancia() {
		instance = null;
	}
}
