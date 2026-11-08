package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorUsuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import datatypes.DTPaciente;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.BeneficioAnualAnioPosteriorException;

/**
 * Servlet implementation class AltaBeneficioAnual
 */
@WebServlet("/altaBeneficioAnual")
public class AltaBeneficioAnual extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static  IControladorUsuario icu;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaBeneficioAnual() {
        icu = Fabrica.getInstance().getIControladorUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		if (!"medico".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		List<DTPaciente> pacientes = (List<DTPaciente>) icu.listarPacientes();
		request.setAttribute("pacientes", pacientes);
		request.getRequestDispatcher("/WEB-INF/altaBeneficioAnual.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(false);
		String rol = (sesion == null) ? null : (String) sesion.getAttribute("rol");
		String confirmado = request.getParameter("confirmado");
		if (!"medico".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/inicio");
			return;
		}
		String nicknamePaciente = (String) request.getParameter("nicknamePaciente");
		int anio = Integer.parseInt(request.getParameter("anio"));
		int cantOrdenes = Integer.parseInt(request.getParameter("cantOrdenes"));
		int cantTickets = Integer.parseInt(request.getParameter("cantTickets"));
		
		if (anio != LocalDate.now().getYear()) {
		    request.setAttribute("error", "El año ingresado no puede ser distinto del año actual");
		    request.setAttribute("pacientes", icu.listarPacientes());
		    request.getRequestDispatcher("/WEB-INF/altaBeneficioAnual.jsp").forward(request, response);
		    return;
		}
		
		if ("true".equals(confirmado)) {
		    icu.editarBeneficioAnual(nicknamePaciente, anio, cantOrdenes, cantTickets);
		    sesion.setAttribute("mensaje", "Beneficio anual modificado con éxito");
		    response.sendRedirect(request.getContextPath() + "/inicio");
		    return;
		}
		
		try {
			icu.altaBeneficioAnual(nicknamePaciente, anio, cantOrdenes, cantTickets);
			sesion.setAttribute("mensaje", "Alta de beneficio anual realizada con éxito");
			response.sendRedirect(request.getContextPath() + "/inicio");
		}
		catch (BeneficioAnualRepetidoException e){
			request.setAttribute("confirmarEdicion", true);
			List<DTPaciente> pacientes = (List<DTPaciente>) icu.listarPacientes();
			request.setAttribute("pacientes", pacientes);
			request.getRequestDispatcher("/WEB-INF/altaBeneficioAnual.jsp").forward(request, response);
		}
		catch (BeneficioAnualAnioPosteriorException ex) {
			List<DTPaciente> pacientes = (List<DTPaciente>) icu.listarPacientes();
			request.setAttribute("pacientes", pacientes);
			request.getRequestDispatcher("/WEB-INF/altaBeneficioAnual.jsp").forward(request, response);
		}
	}

}
