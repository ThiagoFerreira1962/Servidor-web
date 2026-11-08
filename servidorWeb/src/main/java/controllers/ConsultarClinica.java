package controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import logica.Fabrica;
import logica.IControladorClinica;
import logica.IControladorEspecialidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import datatypes.DTClinica;
import datatypes.DTDiaConsultaDetallado;
/**
 * Servlet implementation class ConsultarClinica
 */
@WebServlet("/consultarClinica")
public class ConsultarClinica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorClinica icc;
	private IControladorEspecialidad ice;

    /**
     * Default constructor. 
     */
    public ConsultarClinica() {
        icc= Fabrica.getInstance().getIControladorClinica();
        ice = Fabrica.getInstance().getIControladorEspecialidad();
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreClinica=request.getParameter("nombreClinica"); //obtengo el nombre de la clinica q selecciono el user
		String[] especialidades = (String[]) ice.getNombreEspecialidades();
		Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
		request.setAttribute("especialidades", especialidades);
		if(nombreClinica == null) { //el servlet le llega la info de q debe listar ninguna fue elegida
			List<DTClinica> clinicas = icc.listarClinicas();
			request.setAttribute("clinicas", clinicas); //luego el jsp tendra nombre como clave para poder buscar cada clinica
			request.getRequestDispatcher("/WEB-INF/listarClinicas.jsp").forward(request,response);
			
		} else { //al servlet le llega el nombre de la clinica a la q debera mostrar
			DTClinica dt = icc.seleccionarClinica(nombreClinica);
			if (dt == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			List<DTDiaConsultaDetallado> dias = new ArrayList<>(dt.getDiasConsulta());
			String especialidad = request.getParameter("especialidad");
			if (especialidad != null && !especialidad.isBlank()) {
				//Esto es cuando se quiere filtrar los días de consulta por especialidad
				dias.removeIf(d -> !especialidad.equals(d.getEspecialidad()));
				request.setAttribute("especialidadFiltro", especialidad);
			}
			dias.sort(Comparator.comparing(DTDiaConsultaDetallado::getDia));
			request.setAttribute("diasConsulta", dias);
			request.setAttribute("infoClinica", dt);
			request.setAttribute("destinoEspecialidad", "/consultarClinica?nombreClinica=" + URLEncoder.encode(nombreClinica, "UTF-8"));
			request.getRequestDispatcher("/WEB-INF/consultarClinica.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
