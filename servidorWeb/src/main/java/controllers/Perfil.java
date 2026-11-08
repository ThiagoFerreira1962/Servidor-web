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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logica.Fabrica;
import logica.IControladorUsuario;
import datatypes.DTUsuario;
import datatypes.EstadoConsulta;
import datatypes.DTBeneficioAnual;
import datatypes.DTConsulta;
import datatypes.DTMedico;
import datatypes.DTPaciente;
import logica.IControladorEspecialidad;
import excepciones.EspecialidadEnUsoException;
import datatypes.Mes;
/**
 * Servlet implementation class ModificarUsuario
 */
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
@WebServlet("/perfil")
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private	IControladorUsuario icu;
	private IControladorEspecialidad ice;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Perfil() {
        super();
        // TODO Auto-generated constructor stub
        icu = Fabrica.getInstance().getIControladorUsuario();
        ice = Fabrica.getInstance().getIControladorEspecialidad();
    }

	/**.,
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false);
		String nickUsuarioSolicitado = request.getParameter("nick");
		String nickUsuarioSesion = (session != null) ? (String) session.getAttribute("nickname") : null;
		String rolUsuarioSolicitado = (String) request.getAttribute("rol");
		String rolUsuarioLogueado = (String) session.getAttribute("rol");
		DTUsuario DTUsuarioLogueado = (nickUsuarioSesion != null) ? icu.seleccionarUsuario(nickUsuarioSesion) : null;
		DTUsuario DTUsuarioSolicitado = icu.seleccionarUsuario(nickUsuarioSolicitado);
		boolean tienenMismoNick;
		if (nickUsuarioSolicitado != null)
			tienenMismoNick = nickUsuarioSolicitado.equals(nickUsuarioSesion);
		else 
			tienenMismoNick = false;
		if (DTUsuarioSolicitado == null) {
		    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
		    return;
		}

		List<DTConsulta> consultas = null;
		
		/* me estoy viendo mi propio perfil */
		if (nickUsuarioSolicitado != null && tienenMismoNick) {
			 request.setAttribute("usuario", DTUsuarioLogueado);
			 request.setAttribute("perfilDeSiMismo", true);
			 String imagenlog = (String) session.getAttribute("foto");
			 request.setAttribute("foto", imagenlog);

			 /* soy paciente */ 
			 if (DTUsuarioLogueado instanceof DTPaciente){
				List<DTBeneficioAnual> beneficiosAnuales = icu.listarBeneficioAnuales(nickUsuarioSolicitado);
				request.setAttribute("beneficiosAnuales", beneficiosAnuales);

			 }
				/* soy médico */ 
			 else if (DTUsuarioLogueado instanceof DTMedico) {
				 	consultas = ((DTMedico) DTUsuarioLogueado).getConsultas();
					request.setAttribute("consultas", consultas);
					List<String> listEsp = ((DTMedico) DTUsuarioLogueado).getEspecialidades();
					List<String> listaEspGlobal = new ArrayList<>(Arrays.asList(ice.getNombreEspecialidades()));
					request.setAttribute("especialidades", listEsp);
					listaEspGlobal.removeAll(listEsp);
					request.setAttribute("especialidadesQueNoTiene", listaEspGlobal);
					session.setAttribute("mesLicencia", ((DTMedico) DTUsuarioLogueado).getLicencia().name());
				}
				request.setAttribute("rol", rolUsuarioLogueado);
				
			}
		
		
	   /* controlo cuando no son la misma persona y seteo todos los atributos para el mismo */
		else if (!tienenMismoNick) {	
			
			if (DTUsuarioSolicitado instanceof DTMedico) {
				consultas = ((DTMedico) DTUsuarioSolicitado).getConsultas();
				List<String> listaEsp = ((DTMedico) DTUsuarioSolicitado).getEspecialidades();
				request.setAttribute("especialidades", listaEsp); 
				List<DTConsulta> consultasAceptadas = new ArrayList<>();
				
				 if(consultas!=null) {
			        	for(DTConsulta dc : consultas) {
			        		if(dc.getEstadoActual()==EstadoConsulta.Aceptada) 
								consultasAceptadas.add(dc);
			        	}
			}
				 request.setAttribute("consultas", consultasAceptadas);
				
			}
			request.setAttribute("usuario", DTUsuarioSolicitado);
			request.setAttribute("perfilDeSiMismo", false);
			String imagenreq = (String) request.getParameter("imagen");
			request.setAttribute("imagen", imagenreq);
			request.setAttribute("rol", rolUsuarioSolicitado);
			}
			
		 request.getRequestDispatcher("/WEB-INF/perfil.jsp").forward(request, response);
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("nickname") == null) {
		    response.sendRedirect(request.getContextPath() + "/iniciarSesion");
		    return;
		}
		
		//Esto es para separar el form completo de cuando se agrega o elimina una especialidad
		String nick = (String) session.getAttribute("nickname");
		String accion = request.getParameter("accion");
		if ("borrarEspecialidad".equals(accion) || "agregarEspecialidad".equals(accion)) {
		    DTUsuario u = icu.seleccionarUsuario(nick);
		    if (u instanceof DTMedico med) {
		        List<String> esps = med.getEspecialidades();
		        String aBorrar  = request.getParameter("espElegida");
		        String aAgregar = request.getParameter("espAgregar");
		        if (aBorrar  != null) esps.remove(aBorrar);
		        if (aAgregar != null && !esps.contains(aAgregar)) esps.add(aAgregar);
		        try {
		            icu.editarMedico(nick, med.getNombre(), med.getApellido(), med.getNroRegistro(),
		                    med.getLicencia(), med.getCostoAdicional(), med.getSitioWeb(),
		                    esps, med.getFoto());
		            session.setAttribute("mensaje", "Especialidades actualizadas con éxito.");
		        } catch (EspecialidadEnUsoException e) {
		            session.setAttribute("errorGlobal",
		                "La especialidad no se puede eliminar porque está actualmente en uso.");
		        }
		    }
		    response.sendRedirect(request.getContextPath() + "/perfil?nick="
		            + java.net.URLEncoder.encode(nick, "UTF-8"));
		    return;
		}
		String nombre = (String) request.getParameter("nombre");
		String apellido = (String) request.getParameter("apellido");
		DTUsuario usuario = icu.seleccionarUsuario(nick);
		
		if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
		    session.setAttribute("errorGlobal", "El nombre y el apellido no pueden estar vacíos.");
		    response.sendRedirect(request.getContextPath() + "/perfil?nick="
		            + java.net.URLEncoder.encode(nick, "UTF-8"));
		    return;
		}

		if (usuario instanceof DTPaciente) {
		    String error = validarPaciente(request);
		    if (error != null) {
		        session.setAttribute("errorGlobal", error);
		        response.sendRedirect(request.getContextPath() + "/perfil?nick="
		                + java.net.URLEncoder.encode(nick, "UTF-8"));
		        return;
		    }
		}
		
		Part part = request.getPart("foto");
		String nombreOriginal = (part != null) ? part.getSubmittedFileName() : null;
		String foto = usuario.getFoto();   //conserva la actual

		if (nombreOriginal != null && !nombreOriginal.isBlank()) {
		    int punto = nombreOriginal.lastIndexOf('.');
		    String extension = (punto >= 0) ? nombreOriginal.substring(punto).toLowerCase() : "";

		    if (List.of(".jpg", ".jpeg", ".png").contains(extension)) {
		        foto = nick + extension;
		        String rutaCarpeta = getServletContext().getRealPath("/img");
		        part.write(new File(rutaCarpeta, foto).getAbsolutePath());
		    } else {
		        session.setAttribute("mensaje", "El formato debe ser jpg, jpeg o png.");
		    }
		}
		
		if (usuario instanceof DTMedico ) {
					try {
						List<String> especialidades = ((DTMedico) usuario).getEspecialidades(); 
						String registro = (String) request.getParameter("nroRegistro");
						String stringLicencia = request.getParameter("mesLicencia");
						if (stringLicencia == null || stringLicencia.isBlank()) {
						    stringLicencia = (String) session.getAttribute("mesLicencia");
						}
						if (stringLicencia == null) {
						    session.setAttribute("errorGlobal", "Por favor, ingrese un mes.");
						    response.sendRedirect(request.getContextPath() + "/perfil?nick="
						            + java.net.URLEncoder.encode(nick, "UTF-8"));
						    return;
						}
						Mes mesLicencia = Mes.valueOf(stringLicencia);
						    
						String costoStr = request.getParameter("costoAdicional");
						if (costoStr == null || costoStr.isBlank()) {
						    session.setAttribute("errorGlobal", "Por favor, ingrese un porcentaje adicional.");
						    response.sendRedirect(request.getContextPath() + "/perfil?nick="+ java.net.URLEncoder.encode(nick, "UTF-8"));
						    return;
						}
						float costoAdicional = Float.parseFloat(costoStr.replace(',', '.'));
						String web = request.getParameter("sitioWeb");
							icu.editarMedico(nick, nombre, apellido, registro, mesLicencia, costoAdicional, web, especialidades, foto);
							session.setAttribute("foto", foto);
							session.setAttribute("mensaje", "Datos de usuario modificados con éxito.");
						    response.sendRedirect(request.getContextPath() + "/perfil?nick=" + java.net.URLEncoder.encode(nick, "UTF-8"));
						}
					catch (EspecialidadEnUsoException e) {
						request.setAttribute("error", e.getMessage());
						request.setAttribute("mensaje", e.getMessage());
						 session.setAttribute("errorGlobal", "La especialidad no se puede eliminar porque está actualmente en uso.");
						 response.sendRedirect(request.getContextPath() + "/perfil?nick="+ java.net.URLEncoder.encode(nick, "UTF-8"));
						 return;
					
					}
					catch (NumberFormatException e) {
						session.setAttribute("errorGlobal", "Por favor, ingrese un porcentaje válido.");
						response.sendRedirect(request.getContextPath() + "/perfil?nick=" + java.net.URLEncoder.encode(nick, "UTF-8"));
						return;
					}
					catch (IllegalArgumentException e) {
						session.setAttribute("errorGlobal", "Por favor, ingrese un mes válido.");
						 response.sendRedirect(request.getContextPath() + "/perfil?nick="+ java.net.URLEncoder.encode(nick, "UTF-8"));
						 return;
					}
				}
		else if (usuario instanceof DTPaciente) {
			LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));
			LocalDate fechaIngreso = LocalDate.parse(request.getParameter("fechaIngreso"));
			String ci = request.getParameter("ci");
			String antecedentes = request.getParameter("antecedentes");
			icu.editarPaciente(nick, nombre, apellido, fechaNacimiento, fechaIngreso, ci, antecedentes, foto);
			session.setAttribute("foto", foto);
		    session.setAttribute("mensaje", "Datos de usuario modificados con éxito.");
		    response.sendRedirect(request.getContextPath() + "/perfil?nick="+ java.net.URLEncoder.encode(nick, "UTF-8"));
		}
	}
	
	private static LocalDate parsearFecha(String s) {
	    try {
	        return (s == null || s.isBlank()) ? null : LocalDate.parse(s);
	    } catch (java.time.format.DateTimeParseException e) {
	        return null;
	    }
	}
	
	private static boolean cedulaValida(String ci) {
	    if (ci == null) return false;
	    String d = ci.trim();
	    if (!d.matches("\\d{7,8}")) return false;
	    if (d.charAt(0) == '0') return false;

	    d = String.format("%8s", d).replace(' ', '0');

	    int[] pesos = {2, 9, 8, 7, 6, 3, 4};
	    int suma = 0;
	    for (int i = 0; i < 7; i++) {
	        suma += pesos[i] * (d.charAt(i) - '0');
	    }
	    int verificador = (10 - suma % 10) % 10;
	    return verificador == (d.charAt(7) - '0');
	}
	
	/** Devuelve el mensaje de error, o null si todo está bien. */
	private String validarPaciente(HttpServletRequest request) {
	    String ci = request.getParameter("ci");
	    if (ci == null || !ci.trim().matches("\\d{7,8}")) {
	        return "La cédula debe tener 7 u 8 dígitos, sin puntos ni guiones.";
	    }
	    
	    if(!cedulaValida(ci)) {
	    	return "La cédula ingresada no es válida.";
	    }
	    
	    LocalDate nac = parsearFecha(request.getParameter("fechaNacimiento"));
	    if (nac == null) {
	        return "Ingrese una fecha de nacimiento válida.";
	    }
	    
	    LocalDate ing = parsearFecha(request.getParameter("fechaIngreso"));
	    if (ing == null) {
	        return "Ingrese una fecha de ingreso válida.";
	    }
	    
	    if (nac.isAfter(LocalDate.now()))
	        return "La fecha de nacimiento no puede ser futura.";
	    if (ing.isAfter(LocalDate.now()))
	        return "La fecha de ingreso no puede ser futura.";
	    if (ing.isBefore(nac))
	        return "La fecha de ingreso no puede ser anterior a la de nacimiento.";

	    return null;
	}
}