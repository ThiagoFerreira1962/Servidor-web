package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.CargaDatos;
import logica.Fabrica;
import logica.IControladorEspecialidad;
import logica.IControladorUsuario;
import datatypes.DTPaciente;
import datatypes.DTMedico;

import java.io.IOException;
import java.util.Arrays;


@WebServlet("/inicio")
public class Inicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorEspecialidad ice;
	private IControladorUsuario icu;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inicio() {
    	ice = Fabrica.getInstance().getIControladorEspecialidad();
    	icu = Fabrica.getInstance().getIControladorUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String rol = (session == null) ? null : (String) session.getAttribute("rol");
		request.setAttribute("datosCargados", CargaDatos.estanCargados());
		
		//Visitante
		if (rol == null) {
			String[] especialidades = (String[]) ice.getNombreEspecialidades();
			Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
			request.setAttribute("especialidades", especialidades);
			
		//Paciente
		} else { 
			String nickname = (String) session.getAttribute("nickname");
			if ("paciente".equals(rol)){

				DTPaciente paciente = (DTPaciente) icu.seleccionarUsuario(nickname);
				request.setAttribute("reservas", paciente.getReservas());
				String[] especialidades = (String[]) ice.getNombreEspecialidades();
				Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
				request.setAttribute("especialidades", especialidades);
			//Médico
			} else if ("medico".equals(rol)) {
				DTMedico medico = (DTMedico) icu.seleccionarUsuario(nickname);
				request.setAttribute("consultas", medico.getConsultas());
			}
		}
		
		
		
		//ESTO VA SI O SI
		request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response); //LE DESPACHAMOS AL MAIN JSP LA INFO Y Q EL MANEJE
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
