package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

import logica.Fabrica;
import logica.IControladorDiaConsulta;
import logica.IControladorEspecialidad;
import datatypes.DTConsulta;
import datatypes.DTReserva;

/**
 * Servlet implementation class ConsultarReserva
 */
@WebServlet("/consultarReserva")
public class ConsultarReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorDiaConsulta icdc;
	private IControladorEspecialidad ice;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarReserva() {
    	icdc = Fabrica.getInstance().getIControladorDiaConsulta();
        ice = Fabrica.getInstance().getIControladorEspecialidad();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String rol = (session == null) ? null : (String) session.getAttribute("rol");
		
		String nombreConsulta = (String) request.getParameter("nombreConsulta");
		if (nombreConsulta == null || nombreConsulta.isBlank()) {
			response.sendRedirect(request.getContextPath() + "/consultarConsulta");
			return;
		}
		
		DTConsulta consulta = (DTConsulta) icdc.seleccionarConsulta(nombreConsulta);
		
		if (consulta == null) {
			response.sendRedirect(request.getContextPath() + "/consultarConsulta");
			return;
		}
		
		String nickname = (session == null) ? null : (String) session.getAttribute("nickname");
		
		if (rol == null) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		DTReserva reserva = null;
		
		if ("paciente".equals(rol)) {
			for (DTReserva r : consulta.getReservas()) {
				if (r.getNicknamePaciente().equals(nickname)) {
					reserva = r;
					break;
				}
			}
		}
		
		if ("medico".equals(rol)) {
			String nicknamePaciente = (String) request.getParameter("nicknamePaciente");
			for (DTReserva r : consulta.getReservas()) {
				if (r.getNicknamePaciente().equals(nicknamePaciente)) {
					reserva = r;
					break;
				}
			}
		}
		
		if (reserva == null) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		String[] especialidades = (String[]) ice.getNombreEspecialidades();
		Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
		request.setAttribute("especialidades", especialidades);
		request.setAttribute("reserva", reserva);
		request.setAttribute("consulta", consulta);
		request.getRequestDispatcher("/WEB-INF/consultarReserva.jsp").forward(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
