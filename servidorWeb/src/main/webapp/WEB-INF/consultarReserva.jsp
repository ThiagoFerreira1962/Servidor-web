<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="java.net.URLEncoder" %>

<!doctype html>
<html lang="es-UY">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<% DTReserva reserva = (DTReserva) request.getAttribute("reserva");%>
	<% DTConsulta consulta = (DTConsulta) request.getAttribute("consulta"); %>
	<% String nombreDia = consulta.getNombreDia(); %>
	<% DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>

	<title><%= reserva.getFecha().format(formato) %> | SaludUy</title>
	<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
		  rel="stylesheet">

	<link rel="stylesheet"
		  href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
	
</head>
<body>
	<!-- HEADER -->
  	<jsp:include page="header.jsp" />

  		<div class="container-fluid my-5">
  			<div class="row g-4">
  			<!-- Columna izquierda para las especialidades y operaciones del mÃ©dico -->
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
			
			<!-- Columna central con el detalle de la reserva de consulta -->
				<main class="col-12 col-lg-7">
					<article class="card mb-3">
						<h1 class="card-header text-center">Reserva de <%= reserva.getNombreConsulta() %></h1>
						<div class="card-body">
							<dl class="row mb-0">
								<dt class="col-sm-4">Paciente:</dt>
								<dd class="col-sm-8"><%= reserva.getNicknamePaciente() %></dd>
								
								<dt class="col-sm-4">Consulta:</dt>
								<dd class="col-sm-8"><%= reserva.getNombreConsulta() %></dd>
								
								<dt class="col-sm-4">Fecha:</dt>
								<dd class="col-sm-8"><%= reserva.getFecha().format(formato) %></dd>
								
								<dt class="col-sm-4">Número de Paciente:</dt>
								<dd class="col-sm-8"><%= reserva.getNroPaciente() %></dd>
								
								<dt class="col-sm-4">Costo:</dt>
								<dd class="col-sm-8">$<%= String.format("%.2f", reserva.getCosto()) %></dd>
								
								<dt class="col-sm-4">Usa Beneficio:</dt>
								<% String usaBeneficio = (reserva.getUsaBeneficio()) ? "Si" : "No"; %>
								<dd class="col-sm-8"><%= usaBeneficio %></dd>
							</dl>
							
							<!-- Botón de volver -->
							<div class="d-grid mt-3">
						   <!-- <button type="button" class="btn btn-default" onclick="history.back()">Volver</button> -->
								<a  class="btn btn-default" href="<%=request.getAttribute("volverA")%>">Volver</a>
							</div>
							
						</div>
					</article>
				</main>
		
		<!-- Columna derecha que tiene la consulta asociada -->
				<aside class="col-12 col-lg-3">
					<div class="card">
						<h2 class="card-header text-center">Consulta</h2>
						
						<div class="list-group list-group-flush">
							<a href="<%= request.getContextPath() %>/consultarConsulta?nombreDia=<%= URLEncoder.encode(nombreDia, "UTF-8") %>&nombreConsulta=<%= URLEncoder.encode(consulta.getNombre(), "UTF-8") %>" class="list-group-item list-group-item-action text-center">
								<div class="d-flex align-items-center justify-content-center mx-auto my-3 rounded overflow-hidden" style="width:200px; height:200px;">
									<% if (consulta.getImagen() == null || consulta.getImagen().isBlank()) { %>
										<i class="bi bi-clipboard2-pulse text-secondary d-block mb-2" style="font-size:6rem;"></i>
									<% } else { %>
										<img src="<%= request.getContextPath() %>/img/<%= consulta.getImagen() %>" style="width:100%; height:100%; object-fit: cover;" alt="<%= consulta.getNombre() %>" >
									<% } %>
								</div>
								<span class="semi-bold"><%= consulta.getNombre() %></span>
							</a>
						</div>
					</div>
				</aside>
			</div>
		</div>
		
		<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>