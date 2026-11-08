package controllers;

import java.io.IOException;

import excepciones.UsuarioContrasenaInvalidaException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorUsuario;
import datatypes.DTUsuario;

@WebServlet("/iniciarSesion")
public class IniciarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorUsuario ICU;
	
	
	public IniciarSesion() {
		ICU = Fabrica.getInstance().getIControladorUsuario();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	 request.getRequestDispatcher("WEB-INF/iniciarSesion.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
		String contrasena = request.getParameter("contrasena");
		DTUsuario dtu = ICU.seleccionarUsuario(nickname);
		String foto = null;
		if (dtu != null) {
			foto = dtu.getFoto();
		}
		
		try {
			
		String rol = ICU.verificarCredenciales(nickname, contrasena);
		HttpSession sesion = request.getSession();
		sesion.setAttribute("nickname", nickname);
		sesion.setAttribute("rol", rol);
		sesion.setAttribute("foto", foto);
		response.sendRedirect(request.getContextPath() + "/inicio");
		
		} catch (UsuarioContrasenaInvalidaException e) {
			
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("WEB-INF/iniciarSesion.jsp").forward(request, response);
		}
		
		
		
	}
}
