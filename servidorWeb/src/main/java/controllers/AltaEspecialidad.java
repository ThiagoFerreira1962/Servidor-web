package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorEspecialidad;

import java.io.IOException;

import excepciones.EspecialidadRepetidaException;

/**
 * Servlet implementation class AltaEspecialidad
 */
@WebServlet("/altaEspecialidad")
public class AltaEspecialidad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaEspecialidad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		
		if (!"medico".equals(rol) ) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/altaEspecialidad.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		
		if (!"medico".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		String nombreEspecialidad = request.getParameter("nombreEspecialidad").trim();
		float costo = Float.parseFloat(request.getParameter("porcentajeAdicional"));
		IControladorEspecialidad ice = Fabrica.getInstance().getIControladorEspecialidad();
		
		try {
			ice.crearEspecialidad(nombreEspecialidad, costo);
			sesion.setAttribute("mensaje", "Alta de especialidad realizada con éxito");
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		catch (EspecialidadRepetidaException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/altaEspecialidad.jsp").forward(request, response);
		}
	}

}
