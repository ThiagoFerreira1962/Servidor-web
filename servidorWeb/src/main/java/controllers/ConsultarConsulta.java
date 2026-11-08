package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorDiaConsulta;
import logica.IControladorEspecialidad;
import logica.IControladorUsuario;
import datatypes.DTConsulta;
import datatypes.DTDiaConsultaDetallado;
import datatypes.DTReserva;
import datatypes.DTMedico;

import java.util.Arrays;
import java.util.List;

import java.io.IOException;

/**
 * Servlet implementation class consultarConsulta
 */
@WebServlet("/consultarConsulta")
public class ConsultarConsulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorDiaConsulta icdc;
	private IControladorUsuario icu;
	private IControladorEspecialidad ice;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarConsulta() {
        icdc = Fabrica.getInstance().getIControladorDiaConsulta();
        icu = Fabrica.getInstance().getIControladorUsuario();
        ice = Fabrica.getInstance().getIControladorEspecialidad();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String rol = (session == null) ? null : (String) session.getAttribute("rol");
		
		String nombreConsulta = request.getParameter("nombreConsulta");
		String especialidad = request.getParameter("especialidad");
		
		//Esto es cuando se selecciona una especialidad en la lista de especialidades y se muestran las consultas con dicha especialidad
		if (especialidad != null && !especialidad.isBlank()) {
			List<DTConsulta> consultas = ice.listarConsultasPorEspecialidad(especialidad);
			request.setAttribute("consultas", consultas);
			request.setAttribute("especialidadFiltro", especialidad);
			String[] especialidades = (String[]) ice.getNombreEspecialidades();
			Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
			request.setAttribute("especialidades", especialidades);
			request.getRequestDispatcher("/WEB-INF/listarConsultas.jsp").forward(request, response);
			return;
		}
		
		if (nombreConsulta == null || nombreConsulta.isBlank()) {
			response.sendRedirect(request.getContextPath() + "/consultarDiaConsulta");
			return;
		}
		
		DTConsulta dc = icdc.seleccionarConsulta(nombreConsulta);
		if (dc == null) {
			response.sendRedirect(request.getContextPath() + "/consultarDiaConsulta"); //Acá estaría bueno que muestre mensaje de error y no que redireccione
			return;
		}
		
		String nombreDia = dc.getNombreDia();
		request.setAttribute("consulta", dc);
		request.setAttribute("nombreDia", nombreDia);
		
		DTMedico medico = (DTMedico) icu.seleccionarUsuario(dc.getNicknameMedico());
		request.setAttribute("medicoConsulta", medico);
		
		DTDiaConsultaDetallado dia = icdc.getDTDiaConsultaDetalladoNombre(nombreDia);
		if (dia != null) {
			request.setAttribute("imagenDia", dia.getImagen());
		}
		
		String[] especialidades = (String[]) ice.getNombreEspecialidades();
		Arrays.sort(especialidades,  String.CASE_INSENSITIVE_ORDER);
		request.setAttribute("especialidades", especialidades);
		
		//Se fija si el usuario es paciente, agrego los beneficios y si tiene una reserva en la consulta, se cargan en atributos
		if ("paciente".equals(rol)) {
			String nickname = (String) session.getAttribute("nickname");
			request.setAttribute("beneficios", icu.listarBeneficioAnualesConOrdenesDisponibles(nickname));
			for (DTReserva r : dc.getReservas()) {
				if (r.getNicknamePaciente().equals(nickname)) {
					request.setAttribute("miReserva", r);
					break;
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/consultarConsulta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
