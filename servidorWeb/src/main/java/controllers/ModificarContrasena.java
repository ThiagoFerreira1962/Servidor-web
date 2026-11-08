package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

import excepciones.ContrasenaConfirmarException;
import excepciones.UsuarioContrasenaInvalidaException;
import logica.IControladorUsuario;
import logica.Fabrica;
/**
 * Servlet implementation class ModificarContrasena
 */
@WebServlet("/modificarContrasena")
public class ModificarContrasena extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IControladorUsuario ICU;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarContrasena() {
        super();
    	ICU = Fabrica.getInstance().getIControladorUsuario();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.getRequestDispatcher("/WEB-INF/modificarContrasena.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("nickname") == null) {
	        response.sendRedirect(request.getContextPath() + "/iniciarSesion");
	        return;
	    }
		String nickname = (String) session.getAttribute("nickname");
		String contrasenaActual = (String) request.getParameter("actual");
		String contrasenaNueva = (String) request.getParameter("nueva");
		String contrasenaNuevaConfirmar = (String) request.getParameter("confirmar");
		
		String destino = request.getContextPath() + "/modificarContrasena";
		
		if (contrasenaNueva == null || contrasenaNueva.isBlank()) {
			session.setAttribute("errorGlobal", "Ingrese la nueva contraseña.");
			response.sendRedirect(destino);
			return;
		}
		if (!contrasenaNueva.equals(contrasenaNuevaConfirmar)) {
			session.setAttribute("errorGlobal", "Las contraseñas no coinciden.");
			response.sendRedirect(destino);
			return;
		}
		
		try {
			if (ICU.verificarCredenciales(nickname, contrasenaActual) == null) {
				session.setAttribute("errorGlobal", "La contraseña actual no es la correcta.");
				response.sendRedirect(destino);
				return;
			}
			ICU.modificarContrasena(nickname, contrasenaNueva, contrasenaNuevaConfirmar);	
			session.setAttribute("mensaje", "Contraseña modificada con éxito");
			response.sendRedirect(request.getContextPath() + "/perfil?nick=" + URLEncoder.encode(nickname, "UTF-8"));
		}
		catch (UsuarioContrasenaInvalidaException e){
			session.setAttribute("errorGlobal", "La contraseña ingresada es inválida.");
			response.sendRedirect(destino);
		}
		catch (ContrasenaConfirmarException e) {
			session.setAttribute("errorGlobal", "Las contrasenas no coinciden.");
			response.sendRedirect(destino);
		}
	}
}
