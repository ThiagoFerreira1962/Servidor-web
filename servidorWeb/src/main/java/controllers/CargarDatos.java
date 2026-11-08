package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import logica.CargaDatos;

/**
 * Servlet implementation class CargaDatos
 */
@WebServlet("/cargarDatos")
public class CargarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/inico");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			CargaDatos.cargar();
			HttpSession sesion = request.getSession(false);
			if (sesion != null) {
				sesion.setAttribute("mensaje", "Carga de datos realizada con éxito");
			}
		}
		
		catch (RuntimeException e) {
			HttpSession sesion = request.getSession(false);
			if (sesion != null) {
				sesion.setAttribute("errorGlobal", e.getMessage());
			}
		}
		response.sendRedirect(request.getContextPath() + "/inicio");
	}

}
