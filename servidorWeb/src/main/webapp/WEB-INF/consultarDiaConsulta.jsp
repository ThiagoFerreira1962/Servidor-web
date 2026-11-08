<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTDiaConsultaDetallado" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="java.net.URLEncoder" %>

<!doctype html>
<html lang="es-UY">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<% DTDiaConsultaDetallado dia = (DTDiaConsultaDetallado) request.getAttribute("diaConsulta"); %>
		<title><%= dia.getNombre() %> | SaludUy</title>
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
				
				<!-- Columna central con el detalle del día de consulta -->
				<main class="col-12 col-lg-7">
					<article class="card mb-3">
						<div class="card-header d-grid align-items-center" style="grid-template-columns: 1fr auto 1fr;">
   							<div>
        						<a href="<%= request.getAttribute("volverA") %>" class="btn btn-secondary p-2">
            					<i class="bi bi-arrow-left"></i> Volver
        						</a>
    						</div>

							<h1><%= dia.getNombre() %></h1>
							<!-- Botón de reservar -->
								<div class="text-end">
									<% if ("paciente".equals(rol)) { %>
									<a href="<%= request.getContextPath() %>/altaReserva?nombreDia=<%= URLEncoder.encode(dia.getNombre(), "UTF-8") %>" class="btn btn-default">Reservar</a>
									<% } %>
								</div>
							</div>
						<div class="d-flex align-items-center justify-content-center mx-auto my-3 rounded overflow-hidden" style="width:200px; height:200px;">
							<% if (dia.getImagen() == null || dia.getImagen().isBlank()) { %>
									<i class="bi bi-calendar-week text-secondary " style="font-size:10rem;"></i>
							<% } else { %>
								<img src="<%= request.getContextPath() %>/img/<%= dia.getImagen() %>" style="width:100%; height:100%; object-fit: cover" alt="<%= dia.getNombre() %>">
							<% } %>
						</div>
						
						<div class="card-body">
							<dl class="row mb-0">
								<dt class="col-sm-4">Descripción:</dt>
								<dd class="col-sm-8"><%= dia.getDescripcion() %></dd>
								
								<dt class="col-sm-4">Día:</dt>
								<dd class="col-sm-8"><%= dia.getDia() %></dd>
								
								<dt class="col-sm-4">Hora de inicio:</dt>
								<dd class="col-sm-8"><%= dia.getHoraInicio() %></dd>
								
								<dt class="col-sm-4">Clínica:</dt>
								<dd class="col-sm-8"><%= dia.getClinica() %><a href="<%= request.getContextPath() %>/consultarClinica?nombreClinica=<%= URLEncoder.encode(dia.getClinica(), "UTF-8") %>"
									class="ms-1 text-decoration-none" title="Ver detalle de la clínica">
									<i class="bi bi-eye"></i>
								</a></dd>
								
								<dt class="col-sm-4">Especialidad:</dt>
								<dd class="col-sm-8"><%= dia.getEspecialidad() %></dd>
								
								<dt class="col-sm-4">Costo:</dt>
								<dd class="col-sm-8">$<%= String.format("%.2f", dia.getCosto()) %></dd>
								
							</dl>
							
							<!-- Botón de volver -->

						</div>
					</article>
				</main>
				
				<!-- Columna derecha que tiene las consultas asociadas -->
				<aside class="col-12 col-lg-3">
					<div class="card mb-3" >
						<h2 class="card-header text-center">Consultas</h2>
						
						<div class="list-group list-group-flush border-bottom-0">
							<% if (dia.getConsultas() != null && !dia.getConsultas().isEmpty()) { %>
								<% for (DTConsulta c : dia.getConsultas()) { %>
									<a href="<%= request.getContextPath() %>/consultarConsulta?nombreDia=<%= URLEncoder.encode(dia.getNombre(), "UTF-8") %>&nombreConsulta=<%= URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="list-group-item list-group-item-action text-center">
										<% if (c.getImagen() == null || c.getImagen().isBlank()) { %>
											<i class="bi bi-clipboard2-pulse text-secondary d-block mb-2" style="font-size:6rem;"></i>
										<% } else { %>
											<img src="<%= request.getContextPath() %>/img/<%= c.getImagen() %>" class="img-fluid rounded mx-auto d-block mb-2" style="max-height:150px" alt="<%= c.getNombre() %>">
										<% } %>
										<span><%= c.getNombre() %></span>
									</a>
								<% } %>
							<% } else { %>
								<div class="list-group-item text-secondary text-center mt-3">No hay consultas</div>
							<% } %>
						</div>
						
						<!-- Botón para crear una consulta -->
						<% if (Boolean.TRUE.equals(request.getAttribute("puedoCrearConsulta"))) { %>
							<div class="card-body">
								<div class="d-grid">
									<a href="<%= request.getContextPath() %>/altaConsulta?nombreDia=<%= URLEncoder.encode(dia.getNombre(), "UTF-8") %>" class="btn btn-default"> Crear Consulta</a>
								</div>
							</div>
						<% } %>
					</div>
				</aside>
			</div>
		</div>
		<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="../js/mainjs.js"></script>
	</body>
</html>