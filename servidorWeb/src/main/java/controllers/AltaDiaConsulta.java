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

import excepciones.DiaConsultaRepetidoException;
import logica.IControladorDiaConsulta;
import logica.Fabrica;
import datatypes.DiaSemana;
import java.time.LocalTime;
import logica.IControladorClinica;
import java.util.List;
import logica.IControladorEspecialidad;
/**
 * Servlet implementation class altaDiaConsulta
 */
@WebServlet("/altaDiaConsulta")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)

public class AltaDiaConsulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaDiaConsulta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Fabrica fab = Fabrica.getInstance();
		IControladorClinica icc = fab.getIControladorClinica();
		List<String> listaClinicas = icc.listarNombreClinicas();
		IControladorEspecialidad ice = fab.getIControladorEspecialidad();
		String[] listaEspecialidad = ice.getNombreEspecialidades();
		request.setAttribute("clinicas", listaClinicas);
		request.setAttribute("especialidades", listaEspecialidad);
		request.getRequestDispatcher("WEB-INF/altaDiaConsulta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nombre = request.getParameter("nombre").trim();
		String desc = request.getParameter("descripcion");
		String hora = request.getParameter("horaInicio");
		LocalTime horaIni = LocalTime.parse(hora);
		DiaSemana dia = DiaSemana.valueOf(request.getParameter("diaSemana"));
		String c = request.getParameter("clinica");
		String esp = request.getParameter("especialidad");
		
		//Obtengo la imagen
		Part foto = request.getPart("foto");
		String nombreOriginal = (foto != null) ? foto.getSubmittedFileName() : null ;
		String nombreArchivo = "defaultDiaConsulta.jpg";
		//Si subió foto, la guardo en img/
		if (nombreOriginal != null && !nombreOriginal.isBlank() ) {
			String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
			
		    if (!List.of(".jpg", ".jpeg", ".png").contains(extension)) {
	                request.setAttribute("errorFormato", "El formato debe ser jpg, jpeg o png." );
	                request.getRequestDispatcher("/WEB-INF/altaDiaConsulta.jsp").forward(request, response);
	           return;     
		    }
		    
		    nombreArchivo = nombre + extension;
			String rutaCarpeta = getServletContext().getRealPath("/img");
		    foto.write(new File(rutaCarpeta, nombreArchivo).getAbsolutePath());
		    
		}
		
		Fabrica fab = Fabrica.getInstance();
		IControladorDiaConsulta icdc = fab.getIControladorDiaConsulta();
		IControladorClinica icc = fab.getIControladorClinica();
		IControladorEspecialidad ice = fab.getIControladorEspecialidad();
		try {
			HttpSession sesion = request.getSession(false);
			icdc.altaDiaConsulta(nombre, desc, dia, horaIni, c, esp, nombreArchivo);
			sesion.setAttribute("mensaje", "Alta de día de consulta realizada con éxito");
			response.sendRedirect(request.getContextPath() + "/inicio");
		}
		catch (DiaConsultaRepetidoException e) {
			request.setAttribute("error", e.getMessage());
			request.setAttribute("clinicas", icc.listarNombreClinicas());
			request.setAttribute("especialidades", ice.getNombreEspecialidades());
			request.getRequestDispatcher("/WEB-INF/altaDiaConsulta.jsp").forward(request, response);
		}
	}
}
