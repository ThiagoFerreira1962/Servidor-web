package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;


import java.io.IOException;
import java.util.List;
import java.io.File;

import logica.IControladorClinica;
import logica.IControladorUsuario;

import logica.Fabrica;
import datatypes.DTMedico;


import excepciones.ClinicaRepetidaException;

/**
 * Servlet implementation class AltaClinica
 */
@WebServlet("/altaClinica") 
@MultipartConfig
public class AltaClinica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorClinica icc;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaClinica() {
        icc = Fabrica.getInstance().getIControladorClinica();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//usamos el doGet para obtener la sesion iniciada y ver si es de un medico
		HttpSession sesion = request.getSession(false);
		if (sesion == null) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		String nickname = (String) sesion.getAttribute("nickname");
		IControladorUsuario icu= Fabrica.getInstance().getIControladorUsuario();
		boolean esMedico= icu.seleccionarUsuario(nickname) instanceof DTMedico;
		if(!esMedico) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		} 
		request.getRequestDispatcher("/WEB-INF/altaClinica.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//volvemos a chequear sesion
		HttpSession sesion = request.getSession(false);
		if (sesion == null) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		String nickname = (String) sesion.getAttribute("nickname");
		IControladorUsuario icu= Fabrica.getInstance().getIControladorUsuario();
		boolean esMedico= icu.seleccionarUsuario(nickname) instanceof DTMedico;
		if(!esMedico) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		} 

		String nombreClinica= request.getParameter("nombreClinica").trim();
		String descripcion= request.getParameter("descripcion");
		String direccion= request.getParameter("direccion");
		float costoTicketMedicamento= Float.parseFloat(request.getParameter("costoTicketMedicamento"));
		float costoOrdenConsulta= Float.parseFloat(request.getParameter("costoOrdenConsulta"));
		String sitioWeb= request.getParameter("sitioWeb");
		if (sitioWeb != null && sitioWeb.isBlank()) { //por si no tiene sitio web para evitar probelmas con ""
		    sitioWeb = null;
		}
		Part foto = request.getPart("foto");
		String nombreOriginal = (foto != null) ? foto.getSubmittedFileName() : null ;
		String nombreArchivo = "defaultClinica.jpg";
		//Si subió foto, la guardo en img/
		if (nombreOriginal != null && !nombreOriginal.isBlank() ) {
			String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
			
		    if (!List.of(".jpg", ".jpeg", ".png").contains(extension)) {
	                request.setAttribute("errorFormato", "El formato debe ser jpg, jpeg o png." );
	                request.getRequestDispatcher("/WEB-INF/altaClinica.jsp").forward(request, response);
	           return;     
		    }
		    
		    nombreArchivo = nombreClinica + extension;
			String rutaCarpeta = getServletContext().getRealPath("/img");
		    foto.write(new File(rutaCarpeta, nombreArchivo).getAbsolutePath());
		    
		}
		try {
			icc.altaClinica(nombreClinica, descripcion, direccion, costoTicketMedicamento, costoOrdenConsulta, sitioWeb,nombreArchivo);
			sesion.setAttribute("mensaje", "Alta de clínica realizada con éxito");
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		catch (ClinicaRepetidaException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/altaClinica.jsp").forward(request, response);
		}
		
	}

}
