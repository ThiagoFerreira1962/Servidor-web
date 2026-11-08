package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jakarta.servlet.http.Part;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


import excepciones.ConsultaRepetidaException;
import excepciones.FechaNoCoincideException;
import excepciones.MedicoNoTieneEspecialidadException;
import logica.IControladorDiaConsulta;
import logica.Fabrica;
import datatypes.DTDiaConsulta;

import java.util.Comparator;
import java.util.List;
/**
 * Servlet implementation class AltaConsulta
 */
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
@WebServlet("/altaConsulta")
public class AltaConsulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorDiaConsulta icdc;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaConsulta() {
        super();
        icdc = Fabrica.getInstance().getIControladorDiaConsulta();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub

		HttpSession sesion = request.getSession(false);
		String nickMedico = (sesion == null) ? null : (String) sesion.getAttribute("nickname");
		
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		if (nickMedico == null || !"medico".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		List<DTDiaConsulta> lista = icdc.listarDiasConsultas();
		lista.sort(Comparator.comparing(DTDiaConsulta::getDia)); //Los ordeno en orden semanal
		String nombreDiaURL = request.getParameter("nombreDia");
		request.setAttribute("diasConsulta", lista);
		request.setAttribute("nomDiaConsulta", nombreDiaURL);
		request.getRequestDispatcher("WEB-INF/altaConsulta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    HttpSession sesion = request.getSession(false);
	    String nickMedico = (sesion == null) ? null : (String) sesion.getAttribute("nickname");
	    String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");

	    if (nickMedico == null || !"medico".equals(rol)) {
	        response.sendRedirect(request.getContextPath() + "/inicio");
	        return;
	    }

	    String nombre = request.getParameter("nombreConsulta").trim();
	    LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
	    LocalDate fechaHoy =LocalDate.now();
	    if (fecha.isBefore(fechaHoy)) {
	    	request.setAttribute("error", "La fecha no puede ser anterior a la actual");
	        List<DTDiaConsulta> lista = icdc.listarDiasConsultas();
	        lista.sort(Comparator.comparing(DTDiaConsulta::getDia));
	        request.setAttribute("diasConsulta", lista);
	        request.getRequestDispatcher("/WEB-INF/altaConsulta.jsp").forward(request, response);
	    	
	    };
	    int nroSala = Integer.parseInt(request.getParameter("numSala"));
	    int nroDisp = Integer.parseInt(request.getParameter("nroDisp"));
	    String nomDiaConsulta = request.getParameter("nomDiaConsulta");
	    
	    //Obtengo la imagen
		Part foto = request.getPart("foto");
		String nombreOriginal = (foto != null) ? foto.getSubmittedFileName() : null ;
		String nombreArchivo = "defaultConsulta.jpg";
		//Si subió foto, la guardo en img/
		if (nombreOriginal != null && !nombreOriginal.isBlank() ) {
			String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
			
		    if (!List.of(".jpg", ".jpeg", ".png").contains(extension)) {
	                request.setAttribute("errorFormato", "El formato debe ser jpg, jpeg o png." );
	                request.getRequestDispatcher("/WEB-INF/altaConsulta.jsp").forward(request, response);
	           return;     
		    }
		    
		    nombreArchivo = nombre + extension;
			String rutaCarpeta = getServletContext().getRealPath("/img");
		    foto.write(new File(rutaCarpeta, nombreArchivo).getAbsolutePath());
		    
		}

	    try {
	        icdc.altaConsulta(nombre, fecha, nroSala, nroDisp, nickMedico, nomDiaConsulta, nombreArchivo);
	        sesion.setAttribute("mensaje", "Alta de consulta realizada con éxito");
	        response.sendRedirect(request.getContextPath() + "/inicio");
	    }
	    catch (FechaNoCoincideException | MedicoNoTieneEspecialidadException | ConsultaRepetidaException e) {
	        request.setAttribute("error", e.getMessage());
	        List<DTDiaConsulta> lista = icdc.listarDiasConsultas();
	        lista.sort(Comparator.comparing(DTDiaConsulta::getDia));
	        request.setAttribute("diasConsulta", lista);
	        request.getRequestDispatcher("/WEB-INF/altaConsulta.jsp").forward(request, response);
	    }
	}

}
