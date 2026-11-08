<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="datatypes.DTMedico" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTBeneficioAnual" %>

<!doctype html>
<html lang="es-UY">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<% String nombreDia = (String) request.getAttribute("nombreDia"); %>
		<% String imagenDia = (String) request.getAttribute("imagenDia"); %>
		<% DTConsulta consulta = (DTConsulta) request.getAttribute("consulta"); %>
		<% String rol = (String) session.getAttribute("rol"); %>
		<% String nickname = (String) session.getAttribute("nickname"); %>
		<% boolean esElMedico = nickname != null && "medico".equals(rol) &&  nickname.equals(consulta.getNicknameMedico()); %>
		<% DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
		<title><%= consulta.getNombre() %> | SaludUy</title>
		<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
		
	</head>
	<body>
		<!-- HEADER -->
  		<jsp:include page="header.jsp" />
	
  		
		<div class="container-fluid my-5">
  			<div class="row g-4">
  				<!-- Columna izquierda para las especialidades y operaciones del Usuario-->
				<nav class="col-12 col-lg-2">
				
					<% if (rol == null) { %>
						<!--  No se le muestra la parte de Acciones al visitante porque no tiene operaciones de dar de alta -->
					<% } else { %>
						<div class="card mb-3">
							<h2 class="card-header text-center">Acciones</h2>
							<div class="list-group list-group-flush text-center">
								<% if ("medico".equals(rol)) { %>
									<a href="<%= request.getContextPath() %>/altaDiaConsulta" class="list-group-item list-group-item-action">Alta de Día de Consulta</a>
									<a href="<%= request.getContextPath() %>/altaConsulta" class="list-group-item list-group-item-action">Alta de Consulta</a>
									<a href="<%= request.getContextPath() %>/altaClinica" class="list-group-item list-group-item-action">Alta de Clínica</a>
									<a href="<%= request.getContextPath() %>/altaEspecialidad" class="list-group-item list-group-item-action">Alta de Especialidad</a>
									<a href="<%= request.getContextPath() %>/altaBeneficioAnual" class="list-group-item list-group-item-action">Alta de Beneficio Anual</a>
								<% } else { %>
									<a href="<%= request.getContextPath() %>/altaReserva" class="list-group-item list-group-item-action">Reserva de Consulta</a>
								<% } %>
							</div>
						</div>
					<% } %>
					
					<!-- Lista de Especialidades -->			
  					<jsp:include page="listarEspecialidades.jsp" />

				</nav>
				
				<!-- Columna central con el detalle de la consulta -->
				<main class="col-12 col-lg-7">
					<article class="card mb-3">
						<div class="card-header d-grid align-items-center" style="grid-template-columns: 1fr auto 1fr;">
							<div>
        						<a href="<%= request.getAttribute("volverA") %>" class="btn btn-secondary p-2">
            					<i class="bi bi-arrow-left"></i> Volver
        						</a>
    						</div>
							<h1><%= consulta.getNombre() %></h1>
							<div class="text-end">
							<!-- Botón de reservar -->
							<% if ("paciente".equals(rol) && (consulta.getNroDisp() > 0)) { %>
								<!-- ESTO ES PARA LA VENTANA QUE APARECE ARRIBA -->
    							<button type="button"
            					class="btn btn-default"
            					data-bs-toggle="modal" data-bs-target="#modalBeneficios">
       							 Reservar
						    	</button>
							<% } %>
							</div>
						</div>
						<div class="d-flex align-items-center justify-content-center mx-auto my-3 rounded overflow-hidden" style="width:200px; height:200px;">
							<% if (consulta.getImagen() == null || consulta.getImagen().isBlank()) { %>
								<i class="bi bi-clipboard2-pulse text-secondary" style="font-size:10rem;"></i>
							<% } else { %>
							<img src="<%= request.getContextPath() %>/img/<%= consulta.getImagen() %>" style="width:100%; height:100%; object-fit: cover" alt="<%= consulta.getNombre() %>">
							<% } %>
						</div>
						
						<div class="card-body">
							<dl class="row mb-0">
								<dt class="col-sm-4">Fecha:</dt>
								<dd class="col-sm-8"><%= consulta.getFecha().format(formato) %></dd>
								
								<dt class="col-sm-4">Número de Sala:</dt>
								<dd class="col-sm-8"><%= consulta.getNumSala() %></dd>
								
								<dt class="col-sm-4">Cantidad de Números Disponibles:</dt>
								<dd class="col-sm-8"><%= consulta.getNroDisp() %></dd>
								
								<dt class="col-sm-4">Costo:</dt>
								<dd class="col-sm-8">$<%= String.format("%.2f", consulta.getCosto()) %></dd>
							</dl>
							
							<!-- Botón de volver -->

						</div>
					</article>
					
					<!-- Sección de las reservas -->
					<% if (rol == null) { %>
						<!-- El usuario es visitante, por lo que no se le muestran reservas -->
					<% } else if (esElMedico) { %>
							<section class="card">
								<h2 class="card-header text-center">Reservas</h2>
								<% if (consulta.getReservas() != null && !consulta.getReservas().isEmpty()) { %>
								<table class="table table-striped align-middle mb-0">
									<thead class="table-light">
										<tr>
											<th>Paciente</th>
											<th>Fecha</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
									<% for (DTReserva r : consulta.getReservas()) { %>
										<tr>
											<td><%= r.getNicknamePaciente() %></td>
											<td><%= r.getFecha().format(formato) %></td>
											<td><a href="<%= request.getContextPath() %>/consultarReserva?nombreConsulta=<%= URLEncoder.encode(consulta.getNombre(), "UTF-8") %>&nicknamePaciente=<%= URLEncoder.encode(r.getNicknamePaciente(), "UTF-8") %>" class="ms-1 text-decoration-none" title="Ver detalle de la reserva"><i class="bi bi-eye"></i></a></td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</section>
							<% } else { %>
								<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay reservas</div>
							<% } %>
							
					<% } else if (rol.equals("paciente")) { %>
							<% DTReserva miReserva = (DTReserva) request.getAttribute("miReserva"); %>
							<% if (miReserva == null) { %>
								<!-- No se muestra nada porque el paciente no tiene reserva en esa consulta -->
							<% } else { %>
								<section class="card">
									<h2 class="card-header text-center">Mi Reserva</h2>
									<table class="table table-striped align-middle mb-0">
										<thead class="table-light">
											<tr>
												<th>Fecha de Reserva</th>
												<th>Número de Paciente</th>
												<th>Costo</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><%= miReserva.getFecha().format(formato) %></td>
												<td><%= miReserva.getNroPaciente() %></td>
												<td>$<%= String.format("%.2f", miReserva.getCosto()) %></td>
												<td><a href="<%= request.getContextPath() %>/consultarReserva?nombreConsulta=<%= URLEncoder.encode(consulta.getNombre(), "UTF-8") %>" class="ms-1 text-decoration-none" title="Ver detalle de la reserva"><i class="bi bi-eye"></i></a></td>
											</tr>
										</tbody>
									</table>
								</section>
							<% } %>
					<% } %>
						
				</main>
					
					<!-- Columna derecha con el día de consulta y el médico asociado -->
					<div class="col-12 col-lg-3">
					
					<!-- Día consulta asociado -->
					<div class="card mb-3">
						<h2 class="card-header text-center">Día de Consulta</h2>
						<div class="list-group list-group-flush">
							<a href="<%= request.getContextPath() %>/consultarDiaConsulta?nombreDia=<%= URLEncoder.encode(nombreDia, "UTF-8") %>" class="list-group-item list-group-item-action text-center p-3">
							<div class="d-flex align-items-center justify-content-center mx-auto my-3 rounded overflow-hidden" style="width:200px; height:200px;">
								<% if (imagenDia == null || imagenDia.isBlank()) { %>
										<i class="bi bi-calendar-week text-secondary " style="font-size:6rem;"></i>
								<% } else { %>
									<img src="<%= request.getContextPath() %>/img/<%= imagenDia %>" style="width:100%; height:100%; object-fit: cover;" alt="<%= nombreDia %>">
								<% } %>
							</div>
							<span class="semi-bold"><%= nombreDia %></span>
							</a>
						</div>
					</div>
					
					<!-- Médico asociado -->
					<div class="card">
				   		<h2 class="card-header text-center">Médico</h2>
				   		<% DTMedico medico = (DTMedico) request.getAttribute("medicoConsulta"); %>
				   		<div class="list-group list-group-flush">
					   		<a href="<%= request.getContextPath() %>/perfil?nick=<%= URLEncoder.encode(medico.getNickname(), "UTF-8") %>" class="list-group-item list-group-item-action text-center p-3">
					   		<div class="d-flex align-items-center justify-content-center mx-auto my-3 rounded-circle overflow-hidden" style="width:200px; height:200px;">
						   		<% if (medico.getFoto() == null || medico.getFoto().isBlank()) { %>
						   			<i class="bi bi-person-circle text-secondary d-block mb-2" style="font-size:6rem;"></i>
						   		<% } else { %>
						   			<img src="<%= request.getContextPath() %>/img/<%= medico.getFoto() %>" style="width:100%; height:100%; object-fit:cover;" alt="<%= medico.getNombre() %> <%= medico.getApellido() %>">
						   		<% } %>
						   	</div>
					       	<span class="semi-bold"><%= medico.getNombre() %> <%= medico.getApellido() %></span>
				    		</a>
				    	</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- ESTA MAGIA DE ACA ES PARA LA LEGENDARIA VENTANA DE RESERVAR CON EL BOTON -->
		<% if ("paciente".equals(rol) && (consulta.getNroDisp() > 0)) { %>
		<% List<DTBeneficioAnual> beneficios = (List<DTBeneficioAnual>) request.getAttribute("beneficios"); %>
		<div class="modal fade" id="modalBeneficios" tabindex="-1" aria-labelledby="modalBeneficiosLabel" aria-hidden="true">
 			<div class="modal-dialog modal-dialog-centered">
    			<div class="modal-content">
      				<form action="<%= request.getContextPath() %>/altaReserva" method="post">
        				<input type="hidden" name="dia" value="<%= consulta.getNombreDia() %>">
        				<input type="hidden" name="consulta" value="<%= consulta.getNombre() %>">

        				<div class="modal-header">
          					<h5 class="modal-title" id="modalBeneficiosLabel">Elija un beneficio</h5>
          					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        				</div>

        				<div class="modal-body">
          					<div class="list-group">
            					<button type="submit" name="beneficio" value="" class="list-group-item list-group-item-action">
              					No usar beneficio
            					</button>
            				<% if (beneficios != null) { for (DTBeneficioAnual ben : beneficios) { %>
              					<button type="submit" name="beneficio" value="<%= ben.getAnio() %>"
                      			class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                				Beneficio <%= ben.getAnio() %>
                					<span class="badge text-bg-secondary rounded-pill"><%= ben.getCantidadOrdenes() %> consultas</span>
              					</button>
           					<% } } %>
          					</div>
        				</div>
      				</form>
    			</div>
  			</div>
		</div>
		<% } %>
		<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
	</body>
</html>