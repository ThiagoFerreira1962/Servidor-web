<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.net.URLEncoder" %>

<!doctype html>
<html lang="es-UY">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<% String especialidadFiltro = (String) request.getAttribute("especialidadFiltro"); %>
		<% List<DTConsulta> consultas = (List<DTConsulta>) request.getAttribute("consultas"); %>
		<% DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
		
		<title>Consultas de <%= especialidadFiltro %> | SaludUy</title>
		<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
		<!-- HEADER -->
  		<jsp:include page="header.jsp" />
	</head>
	<body>
	
	
  		
  		<div class="container-fluid my-5">
  			<div class="row g-4">
  				<!-- Columna izquierda para las especialidades y operaciones del Usuario-->
				<nav class="col-12 col-lg-2">
				
					<% String rol = (String) session.getAttribute("rol"); %>
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
				
				<!-- Columna central con el listado de Consultas-->
				<main class="col-12 col-lg-7">
					<article class="card mb-3">
						
						<h2 class="card-header text-center">Consultas de <%= especialidadFiltro %></h2>
						<% if (consultas != null && !consultas.isEmpty()) { %>
							<div class="list-group list-group-flush border-bottom-0">
								<% for (DTConsulta c : consultas){ %>
									<a href ="<%= request.getContextPath() %>/consultarConsulta?nombreConsulta=<%= URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="list-group-item list-group-item-action d-flex gap-3 align-items-center">
										<div class="d-flex align-items-center justify-content-center flex-shrink-0 text-secondary" style="width:100px; height:100px">
											<% if (c.getImagen() == null || c.getImagen().isBlank()) { %>
													<i class="bi bi-clipboard2-pulse text-secondary flex-shrink-0" style="font-size:5rem;"></i>
											<% } else { %>
												<img src="<%= request.getContextPath() %>/img/<%= c.getImagen() %>"  class="rounded flex-shrink-0" style="width:100%; height:100%; object-fit:cover" alt="<%= c.getNombre() %>">
											<% } %>
										</div>
										<div>
											<h3 class="h6 mb-1 fw-semibold"><%= c.getNombre() %></h3>
											<p class="mb-1 text-secondary"><%= c.getNombreMedico() %> <%= c.getApellidoMedico() %></p>
											<p class="mb-0 text-secondary small"><%= c.getFecha().format(formato) %></p>
										</div>
									</a>
								<% } %>
							</div>
						<% } else { %>
							<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay consultas de <%= especialidadFiltro %></div>
						<% } %>

					</article>
					
				</main>
			</div>
		</div>
		<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>