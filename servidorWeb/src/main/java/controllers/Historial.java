package controllers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet Filter implementation class Historial
 */
@WebFilter(urlPatterns = {"/consultarClinica", "/consultarConsulta", "/consultarDiaConsulta", "/consultarReserva", "/consultarUsuario",
							"/listarClinicas", "/listarConsultas", "/listarDiasConsulta", "/listarEspecialidades", "/listarUsuarios",
							"/perfil", "/inicio" })
public class Historial extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public Historial() {
    	super();

        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession sesion = req.getSession();
		ArrayList<String> historial = (ArrayList<String>)sesion.getAttribute("historial");
		if ("GET".equals(req.getMethod())) {
		String direccion = (req.getQueryString() == null) ?  
				req.getRequestURI() : req.getRequestURI() + "?" + req.getQueryString();
			
				if (historial == null ) { historial = new ArrayList<String>();}
				
				if(historial.contains(direccion)){
					int indice = historial.indexOf(direccion);
					if (indice < historial.size() - 1) {historial.subList(indice + 1, historial.size()).clear();}
				}else{
					historial.add(direccion);
					sesion.setAttribute("historial", historial);}
		}
		
		String pagAnterior = "/servidorWeb/inicio";
		if (historial != null) {
			if (historial.size() >= 2) { 
				pagAnterior = historial.get(historial.size() - 2);
			}
		}
		request.setAttribute("volverA", pagAnterior);
		chain.doFilter(request, response);
		}
	
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
