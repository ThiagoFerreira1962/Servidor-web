package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorUsuario;
import logica.IControladorEspecialidad;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datatypes.Mes;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;

import excepciones.UsuarioRepetidoException;

/**
 * Servlet implementation class RegistrarPaciente
 */
@WebServlet("/registrarUsuario")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class RegistrarUsuario extends HttpServlet {
	
	private static final String defaultImgMed = "defaultMedico.jpg";
	private static final String defaultImgPac = "defaultPaciente.jpg";
	private IControladorUsuario ICU;
	private IControladorEspecialidad ICE;
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarUsuario() {
    	Fabrica fabrica = Fabrica.getInstance();
        ICU = fabrica.getIControladorUsuario();
        ICE = fabrica.getIControladorEspecialidad();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String rol = request.getParameter("rol");
		if ("paciente".equals(rol)) {
		request.getRequestDispatcher("/WEB-INF/registrarPaciente.jsp").forward(request, response);}
		else if ("medico".equals(rol)){
		request.setAttribute("listaEsp", ICE.getNombreEspecialidades());
		request.getRequestDispatcher("/WEB-INF/registrarMedico.jsp").forward(request, response);}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = request.getParameter("rol");
		String nickname = request.getParameter("nickname");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String correo = request.getParameter("correo");
		String contrasena = request.getParameter("contrasena");
		String confContrasena = request.getParameter("confContrasena");
		
		if (!contrasena.equals(confContrasena)) {
			request.setAttribute("error", "La contraseña y la confirmación no coinciden.");
			if ("medico".equals(rol)) {
				request.setAttribute("listaEsp", ICE.getNombreEspecialidades());
				request.getRequestDispatcher("/WEB-INF/registrarMedico.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/registrarPaciente.jsp").forward(request, response);
			}
		}

		Part foto = request.getPart("foto");
		String nombreOriginal = (foto != null) ? foto.getSubmittedFileName() : null ;
		String nombreArchivo = null;
		//Si subió foto, la guardo en img/
		if (nombreOriginal != null && !nombreOriginal.isBlank() ) {
			String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
			
		    if (!List.of(".jpg", ".jpeg", ".png").contains(extension)) {
	                request.setAttribute("errorFormato", "El formato debe ser jpg, jpeg o png." );
	                if("paciente".equals(rol)) { 
	                request.getRequestDispatcher("/WEB-INF/registrarPaciente.jsp").forward(request, response);
	                }
	                else {
	                request.setAttribute("listaEsp", ICE.getNombreEspecialidades());
	                request.getRequestDispatcher("/WEB-INF/registrarMedico.jsp").forward(request, response);
	                }
	           return;     
		    }
		    
		    nombreArchivo = nickname + extension;
			String rutaCarpeta = getServletContext().getRealPath("/img");
		    foto.write(new File(rutaCarpeta, nombreArchivo).getAbsolutePath());
		    
		}
		
	
		if ("paciente".equals(rol)){
			
		LocalDate fechaNac = LocalDate.parse(request.getParameter("fechaNac"));
		String ci = request.getParameter("ci");
		String antecedentes = request.getParameter("antecedentes");
		if (nombreArchivo == null){
			nombreArchivo = defaultImgPac;
		}
		try {
		//Doy de alta y redirijo hacia inicio
		ICU.altaPaciente(nickname, nombre, apellido, correo, fechaNac, ci, LocalDate.now(), antecedentes, contrasena, nombreArchivo);
		HttpSession sesion = request.getSession();
		sesion.setAttribute("nickname", nickname);
		sesion.setAttribute("rol", rol);
		sesion.setAttribute("foto", nombreArchivo);
		response.sendRedirect(request.getContextPath() + "/inicio");
		} catch (UsuarioRepetidoException e){ 
		request.setAttribute("error", e.getMessage());
		request.getRequestDispatcher("/WEB-INF/registrarPaciente.jsp").forward(request, response);}
		}
		
		if ("medico".equals(rol)){
		
		String nroRegistro = request.getParameter("nroReg");
		Mes licencia = Mes.values()[Integer.parseInt(request.getParameter("meses"))];
		String pagWeb = request.getParameter("linkWeb");
		String[] marcadas = request.getParameterValues("especialidades");
		List<String> espSeleccionadas = (marcadas != null) ? Arrays.asList(marcadas) : new ArrayList<>();
		float porAdic = Float.parseFloat(request.getParameter("costo"));
		if (nombreArchivo == null){
			nombreArchivo = defaultImgMed;
		}
		try {
		ICU.altaMedico(nickname, nombre, apellido, correo, nroRegistro, licencia, porAdic, pagWeb, espSeleccionadas, contrasena, nombreArchivo);
		HttpSession sesion = request.getSession();
		sesion.setAttribute("nickname", nickname);
		sesion.setAttribute("rol", rol);
		sesion.setAttribute("foto", nombreArchivo);
		response.sendRedirect(request.getContextPath() + "/inicio");
		} catch (UsuarioRepetidoException e) {	
		request.setAttribute("error", e.getMessage());
		request.setAttribute("listaEsp", ICE.getNombreEspecialidades());
		request.getRequestDispatcher("/WEB-INF/registrarMedico.jsp").forward(request, response);}
		}
	}

}