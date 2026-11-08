<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<!doctype html>
<html lang="es-UY">
<head>
<meta charset="utf-8">
<!-- TITULO QUE APARECE EN LA PESTAÑA -->
<title>SaludUy</title>
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<!-- LINK PARA USAR BOOTSTRAP -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<!-- TAMBIEN DE BOOTSTRAP,ES PARA EL TAMAÑO -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- LINKEO A CSS -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/base.css">
	
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="mensajeExito.jsp"/>
	
	<% HttpSession sesion = request.getSession(false); %>
	<% String rol = (sesion == null) ? null : (String) session.getAttribute("rol");%>
	<% DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
	
	<!-- Botón para cargar datos de prueba -->
	<% Boolean datosCargados = (Boolean) Boolean.TRUE.equals(request.getAttribute("datosCargados")); %>
	<% if (!datosCargados) { %>
		<div class="m-3 mb-2">
			<form action="<%= request.getContextPath() %>/cargarDatos" method="post">
				<button type="submit" class="btn btn-warning btn-sm">Cargar datos de prueba</button>
			</form>
    	</div>
    <% } %>
	
	<div class="container-fluid my-5">
  		<div class="row g-4">
  		<!-- Perfil del visitante -->
  		<nav class="col-12 col-lg-2">
			<% if (rol == null) { %>
				<!-- Lista de Especialidades para visitantes -->			
		  		<jsp:include page="listarEspecialidades.jsp" />
			<% } else { %>
			<!-- Bloque de Acciones para médicos y pacientes -->
			<div class="card mb-3">
				<h2 class="card-header text-center">Acciones</h2>
				<div class="list-group list-group-flush text-center">
				<% if ("medico".equals(rol)) { %>
					<a href="<%= request.getContextPath() %>/altaDiaConsulta" class="list-group-item list-group-item-action">Alta de Día de Consulta</a>
					<a href="<%= request.getContextPath() %>/altaConsulta" class="list-group-item list-group-item-action">Alta de Consulta</a>
					<a href="<%= request.getContextPath() %>/altaClinica" class="list-group-item list-group-item-action">Alta de Clínica</a>
					<a href="<%= request.getContextPath() %>/altaEspecialidad" class="list-group-item list-group-item-action">Alta de Especialidad</a>
					<a href="<%= request.getContextPath() %>/altaBeneficioAnual" class="list-group-item list-group-item-action">Alta de Beneficio Anual</a>
				<% } else if ("paciente".equals(rol)) { %>
					<a href="<%= request.getContextPath() %>/altaReserva" class="list-group-item list-group-item-action">Reserva de Consulta</a>
				</div>
			</div>
			<jsp:include page="listarEspecialidades.jsp" />
				<% } %>
			<% } %>
		</nav>
		
  		<!-- BOTONES FLOTANTES para todos -->  		
  		<div class="col-12 col-lg-8">
			<div class="d-flex flex-wrap justify-content-center gap-3 <%= rol != null ? "mt-botonera-logeado" : "mt-botonera" %>">
			   	<a href="<%= request.getContextPath() %>/consultarUsuario" class="btn btn-tile botonera-btn-verde shadow-lg">
			       	<i class="bi-people-fill"></i>
			    	<span>Lista De Usuarios</span>
			   	</a>
		       
		       	<a href="<%= request.getContextPath() %>/consultarDiaConsulta" class="btn btn-tile botonera-btn-verde shadow-lg">
		       		<i class="bi bi-calendar-fill"></i>
		       		<span>Días de Consulta</span>
		       	</a>
			   	
		       	<a href="<%= request.getContextPath() %>/consultarClinica" class="btn btn-tile botonera-btn-verde shadow-lg">
		       		<i class="bi bi-hospital-fill"></i>
		       		<span>Clínicas</span>
		       	</a>
			</div>
					   	
			<!-- Perfil del médico -->
			<% if ("medico".equals(rol)) { %>
		   		
		   		<!-- Consultas del médico -->
			   	<% List<DTConsulta> consultas = (List<DTConsulta>) request.getAttribute("consultas"); %>
			   		<div class="card mt-5">
		   				<h2 class="card-header text-center">Mis Consultas</h2>
		   				<% if (consultas != null && !consultas.isEmpty()) { %>
			   				<div class="table-responsive">
			   					<table class="table table-striped table-hover align-middle mb-0">
									<thead class="table-light">
										<tr>
											<th>Foto</th>
											<th>Consulta</th>
											<th>Fecha de Consulta</th>
											<th>Número de Sala</th>
											<th>Estado</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
									<% for (DTConsulta c : consultas) { %>
										<tr>
											<td><img src="<%= request.getContextPath() %>/img/<%= c.getImagen() %>" class="avatar"></td>
											<td><%= c.getNombre() %></td>
											<td><%= c.getFecha().format(formato) %></td>
											<td><%= c.getNumSala() %></td>
											<td><%= c.getEstadoActual() %></td>
											<td><a href="<%= request.getContextPath() %>/consultarConsulta?nombreConsulta=<%= URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="ms-1 text-decoration-none" title="Ver detalle de la consulta"><i class="bi bi-eye"></i></a></td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</div>
						<% } else { %>
							<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay consultas</div>
						<% } %>
					</div>
			   		
		   	<!-- Perfil del paciente -->
			<% } else if ("paciente".equals(rol)) { %>
			
		   		<!-- Reservas del paciente -->
		   		<% List<DTReserva> reservas = (List<DTReserva>) request.getAttribute("reservas"); %>
		   			<div class="card mt-5">
		   				<h2 class="card-header text-center">Mis Reservas</h2>
		   				<% if (reservas != null && !reservas.isEmpty()) {%>
			   				<div class="table-responsive">
			   					<table class="table table-striped table-hover align-middle mb-0">
									<thead class="table-light">
										<tr>
											<th>Consulta</th>
											<th>Fecha de Consulta</th>
											<th>Número de Paciente</th>
											<th>Costo</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
									<% for (DTReserva r : reservas) { %>
										<tr>
											<td><%= r.getNombreConsulta() %></td>
											<td><%= r.getFecha().format(formato) %></td>
											<td><%= r.getNroPaciente() %></td>
											<td>$<%= String.format("%.2f", r.getCosto()) %></td>
											<td><a href="<%= request.getContextPath() %>/consultarReserva?nombreConsulta=<%= r.getNombreConsulta() %>" class="ms-1 text-decoration-none" title="Ver detalle de la reserva"><i class="bi bi-eye"></i></a></td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</div>
						<% } else { %>
							<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay reservas</div>
						<% } %>
					</div>
				<% } %>
			</div>
		</div>
	</div>
	

	<!-- JAVASCRIPT -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>