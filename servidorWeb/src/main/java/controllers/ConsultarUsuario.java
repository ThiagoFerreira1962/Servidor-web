package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import logica.Fabrica;
import logica.IControladorUsuario;

import java.util.List;
import java.util.ArrayList;
import datatypes.DTUsuario;
import datatypes.DTMedico;
import datatypes.DTPaciente;
import datatypes.DTConsulta;
import datatypes.EstadoConsulta;
import datatypes.DTReserva;


/**
 * Servlet implementation class ConsultarUsuario
 */
@WebServlet("/consultarUsuario")
public class ConsultarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IControladorUsuario icu;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarUsuario() {
    	icu=Fabrica.getInstance().getIControladorUsuario();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickUsuario= request.getParameter("nick");
		if(nickUsuario==null) {
			List<DTUsuario> usuarios = icu.listarUsuarios();
			request.setAttribute("usuarios", usuarios);
			request.getRequestDispatcher("/WEB-INF/listarUsuarios.jsp").forward(request, response);
		}
		else{ 
			HttpSession sesion = request.getSession(false); //veo si es alguien q inicio sesion
			String usuarioLogueado = null;
			if (sesion != null)
				usuarioLogueado = (String) sesion.getAttribute("usuarioLogueado"); //si inicio sesion obtengo su nick
			boolean perfilDeSiMismo = nickUsuario.equals(usuarioLogueado); //veo si quiere ver su propia info
			DTUsuario dtu= icu.seleccionarUsuario(nickUsuario);
			if (dtu == null) {
			    response.sendError(HttpServletResponse.SC_NOT_FOUND);
			    return;
			}
			request.setAttribute("usuario", dtu);
			request.setAttribute("perfilDeSiMismo", perfilDeSiMismo);
			if (dtu instanceof DTMedico) {
				DTMedico med= (DTMedico)dtu;
				List<DTConsulta> consultas = med.getConsultas();
				if(perfilDeSiMismo) {	
			        List<String> consultasIngresadas = new ArrayList<>();
			        List<String> consultasRechazadas = new ArrayList<>();
			        if(consultas!=null) {
			        	for(DTConsulta dc : consultas) {
			        		EstadoConsulta estado=dc.getEstadoActual();
			        		if(estado== EstadoConsulta.Ingresada) 
			        			consultasIngresadas.add(dc.getNombre());
			        		if(estado == EstadoConsulta.Rechazada) 
			        			consultasRechazadas.add(dc.getNombre());
			        	}
			        }
				    request.setAttribute("consultasRechazadas", consultasRechazadas);
				    request.setAttribute("consultasIngresadas", consultasIngresadas);
				}
				    request.setAttribute("especialidades", med.getEspecialidades());
				    List<String> consultasAceptadas= new ArrayList<>();
				if(consultas!=null) {
					for(DTConsulta dcc : consultas) {
						if(dcc.getEstadoActual()==EstadoConsulta.Aceptada) {
							consultasAceptadas.add(dcc.getNombre());
						}
					}
				}
				request.setAttribute("consultasAceptadas", consultasAceptadas);
				response.sendRedirect("perfil");
			}
			else {
				if(perfilDeSiMismo) {
					DTPaciente pac= (DTPaciente)dtu;
					List<DTReserva> reservas= pac.getReservas();
					List<String> idsReserva=new ArrayList<>();
					if(reservas!=null) {
						for(DTReserva dr : reservas) {
							idsReserva.add(dr.getNombreConsulta());
						}
					}
					request.setAttribute("reservas", idsReserva);
					}
				response.sendRedirect("perfil");
			}
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
