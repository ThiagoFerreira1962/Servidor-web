package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import logica.Fabrica;
import logica.IControladorDiaConsulta;
import logica.IControladorEspecialidad;
import logica.IControladorUsuario;
import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DTMedico;
import datatypes.DTUsuario;

/**
 * Servlet implementation class ConsultarDiaConsulta
 */
@WebServlet("/consultarDiaConsulta")
public class ConsultarDiaConsulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorDiaConsulta icdc;
	private IControladorEspecialidad ice;
	private IControladorUsuario icu;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarDiaConsulta() {
        icdc = Fabrica.getInstance().getIControladorDiaConsulta();
        ice = Fabrica.getInstance().getIControladorEspecialidad();
        icu = Fabrica.getInstance().getIControladorUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreDia = request.getParameter("nombreDia"); //Obtengo el nombre del día de consulta
		String[] especialidades = ice.getNombreEspecialidades().clone();
		Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
		request.setAttribute("especialidades", especialidades);
		if (nombreDia == null || nombreDia.isBlank()) { //Si es null significa que tengo que listar todos los días de consulta
			
			String especialidad = request.getParameter("especialidad");
			List<DTDiaConsulta> diasConsulta;			
			List<DTConsulta> todasConsultas;
			if (especialidad != null && !especialidad.isBlank()) {
				//Esto es cuando se quiere filtrar los días de consulta por especialidad
				diasConsulta = new ArrayList<>(ice.listarDiasPorEspecialidad(especialidad));
				todasConsultas = new ArrayList<>(ice.listarConsultasPorEspecialidad(especialidad));
				request.setAttribute("especialidadFiltro", especialidad);
			} else {
				//Esto es cuando se quiere todos los días de consulta del sistema
				diasConsulta = new ArrayList<>(icdc.listarDiasConsultas());
				todasConsultas = new ArrayList<>(icdc.listarTodasConsultasAceptada());
			}
			
			diasConsulta.sort(Comparator.comparing(DTDiaConsulta::getDia)); //Los ordeno en orden semanal
			request.setAttribute("diasConsulta", diasConsulta); //El jsp va a buscar la lista de los dias consulta con la clave diasConsulta
			request.setAttribute("destinoEspecialidad", "/consultarDiaConsulta");
			todasConsultas.sort(Comparator.comparing(DTConsulta::getNombreClinica).thenComparing(DTConsulta::getFecha)); //Ordena por clínicas y luego por orden de fecha
			request.setAttribute("todasConsultas", todasConsultas);
			request.getRequestDispatcher("/WEB-INF/listarDiasConsulta.jsp").forward(request,response); //Manda al jsp la info necesaria para mostrar

		} else { //Si no es null es que quiero el detalle de un día de consulta en concreto
			
			DTDiaConsultaDetallado dtdc = icdc.getDTDiaConsultaDetalladoAceptadas(nombreDia);
			if (dtdc == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			boolean puedoCrearConsulta = false;
			HttpSession sesion = request.getSession(false);
			String nickname = (sesion == null) ? null : (String) sesion.getAttribute("nickname");
			if (nickname != null) {
				DTUsuario usuario = icu.seleccionarUsuario(nickname);
				if (usuario instanceof DTMedico medico) {
					puedoCrearConsulta = medico.getEspecialidades().contains(dtdc.getEspecialidad());
				}
			}
			request.setAttribute("puedoCrearConsulta", puedoCrearConsulta);
			
			request.setAttribute("diaConsulta", dtdc);
			request.getRequestDispatcher("/WEB-INF/consultarDiaConsulta.jsp").forward(request, response);
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
