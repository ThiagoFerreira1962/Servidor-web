package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import jakarta.servlet.http.HttpSession;

import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTBeneficioAnual;
import excepciones.PacienteYaTieneReservaException;
import excepciones.SinCuposDisponiblesException;
import logica.Fabrica;
import logica.IControladorDiaConsulta;
import logica.IControladorUsuario;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;

/**
 * Servlet implementation class AltaReserva
 */
@WebServlet("/altaReserva")
public class AltaReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaReserva() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		String nickPaciente = (sesion == null) ? null : (String) sesion.getAttribute("nickname");
		
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		if (nickPaciente == null || !"paciente".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		
		IControladorDiaConsulta icdc = Fabrica.getInstance().getIControladorDiaConsulta();
		List<DTDiaConsulta> lista = icdc.listarDiasConsultas();
		request.setAttribute("diasDeConsulta", lista);
		lista.sort(Comparator.comparing(DTDiaConsulta::getDia)); //Los ordeno en orden semanal
		
		IControladorUsuario icu = Fabrica.getInstance().getIControladorUsuario();
		List<DTBeneficioAnual> beneficios = icu.listarBeneficioAnualesConOrdenesDisponibles(nickPaciente);
		request.setAttribute("beneficios", beneficios);
		
		//ME FIJO SI SE SELECCIONA EL BENEFICIO
		String beneficioElegido = request.getParameter("beneficio");
		if (beneficioElegido != null && !beneficioElegido.isEmpty()) {
			request.setAttribute("beneficioElegido", beneficioElegido);
		}
		
		//ME FIJO SI SE SELECCIONA EL DIA
		String dia = request.getParameter("nombreDia");
		if (dia != null && !dia.isEmpty()) {
			request.setAttribute("diaElegido", dia);
			request.setAttribute("consultas", icdc.listarConsultasDiaConsultaAceptadasConCupo(dia));
		}
		
		//ME FIJO SI SE SELECCIONA CONSULTA
		String consulta = request.getParameter("nombreConsulta");
		if (consulta != null && !consulta.isEmpty()) {
			request.setAttribute("consultaElegida", consulta);
		}
		request.getRequestDispatcher("WEB-INF/altaReserva.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		IControladorUsuario icu = Fabrica.getInstance().getIControladorUsuario();
		IControladorDiaConsulta icdc = Fabrica.getInstance().getIControladorDiaConsulta();
		HttpSession sesion = request.getSession(false);
		String nomConsulta = request.getParameter("consulta");
		DTConsulta consulta = icdc.seleccionarConsulta(nomConsulta);
		LocalDate fecha = consulta.getFecha();
		float costo = consulta.getCosto();
		String nickname = (String) sesion.getAttribute("nickname");
		String beneficioParam = request.getParameter("beneficio");
		int anio = (beneficioParam == null || beneficioParam.isBlank())
		        ? 0
		        : Integer.parseInt(beneficioParam);
		try {
			icu.altaReserva(nickname, nomConsulta, fecha, costo,anio);
			sesion.setAttribute("mensaje", "Reserva realizada con éxito");
			response.sendRedirect(request.getContextPath() + "/inicio");
		} 
		catch (PacienteYaTieneReservaException | SinCuposDisponiblesException e){
			//CARGO LOS CAMPOS DE NUEVO
			request.setAttribute("error", e.getMessage());
			request.setAttribute("diasDeConsulta", icdc.listarDiasConsultas());
			String dia = request.getParameter("dia");
			request.setAttribute("diaElegido", dia);
			request.setAttribute("consultaElegida", nomConsulta);
			request.setAttribute("consultas", icdc.listarConsultasDiaConsultaAceptadasConCupo(dia));
			request.setAttribute("beneficioElegido", beneficioParam);
			request.setAttribute("beneficios", icu.listarBeneficioAnualesConOrdenesDisponibles(nickname));
			request.getRequestDispatcher("WEB-INF/altaReserva.jsp").forward(request, response);
		}
		
	}

}
