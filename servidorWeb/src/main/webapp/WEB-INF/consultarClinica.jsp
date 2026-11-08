<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="datatypes.DTClinica"%>
<%@ page import="datatypes.DTDiaConsultaDetallado" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%-- Llamo al servlet consultarClinica pasando el nombre --%>
<%
DTClinica clinica = (DTClinica) request.getAttribute("infoClinica");
%>
<!doctype html>
<html lang="es-UY">
<head>
<meta charset="utf-8">
<!-- TITULO QUE APARECE EN LA PESTAÑA -->
<title><%= clinica.getNombre() %> | SaludUy</title>

<!-- LINK PARA USAR BOOTSTRAP -->
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<!-- TAMBIEN DE BOOTSTRAP,ES PARA EL TAMAÑO -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- LINKEO A CSS -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/base.css">
</head>
<body>
	<!-- HEADER -->
	<jsp:include page="header.jsp"/>

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
						<div class="list-group list-group-flush">
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
			
			<!-- Detalle de la clínica -->
			<div class="col-12 col-lg-7">
			  <div class="card shadow-sm p-4 rounded-4">
				<div class="d-grid align-items-center mb-4" style="grid-template-columns: 1fr auto 1fr;">

    			<div class="text-start">
       				 <a href="<%= request.getAttribute("volverA") %>" class="btn btn-secondary">
            		 <i class="bi bi-arrow-left"></i> Volver
       				 </a>
			    </div>
    			
    			<h2 class="mb-0 text-center"><%= clinica.getNombre() %></h2>
    			
			    <div></div>
				</div>
				
				<div class="row g-4">
				<div class="col-md-7">
					</div>
						<% if (clinica.getFoto() != null && !clinica.getFoto().isBlank()) { %>
	    					<div class="col-12 text-center">
	        					
	        					<div>
	            					<img src="<%= request.getContextPath() + "/img/" + clinica.getFoto() %>"
	                 				class="img-fluid rounded"
	                 				style="max-height: 300px;"
	                 				alt="Imagen de la clínica">
	        					</div>
	    					</div>
						<% } %>
	
						<div class="col-md-6">
							<label class="form-label fw-bold">Descripción</label>
							<p class="form-control-plaintext"><%= clinica.getDescripcion() %></p>
						</div>
	
						<div class="col-md-6">
							<label class="form-label fw-bold">Dirección</label>
							<p class="form-control-plaintext"><%= clinica.getDireccion() %></p>
						</div>
	
						<div class="col-md-6">
	    					<label class="form-label fw-bold">Sitio Web</label>
	    					<p class="form-control-plaintext"><%= clinica.getSitioWeb() %></p>
						</div>
	
						<div class="col-md-6">
							<label class="form-label fw-bold">Costo Ticket</label>
							<p class="form-control-plaintext">$<%= clinica.getCostoTicket() %></p>
						</div>
	
						<div class="col-md-6">
							<label class="form-label fw-bold">Costo Consulta</label>
							<p class="form-control-plaintext">$<%= clinica.getCostoConsulta() %></p>
					</div>

					</div>
				</div>
			</div>
			
			<!-- Columna derecha que tiene todos los días de consulta de la clínica -->
				<aside class="col-12 col-lg-3">
					<div class="card">
						<h2 class="card-header text-center">Días de Consulta</h2>
						
						<div class="list-group list-group-flush">
						<% List<DTDiaConsultaDetallado> dias = (List<DTDiaConsultaDetallado>) request.getAttribute("diasConsulta"); %>
							<% if (dias != null && !dias.isEmpty()) { %>
								<% for (DTDiaConsultaDetallado dc : dias) { %>
									<a href="<%= request.getContextPath() %>/consultarDiaConsulta?nombreDia=<%= URLEncoder.encode(dc.getNombre(), "UTF-8") %>" class="list-group-item list-group-item-action text-center">
										<div class="d-flex align-items-center justify-content-center mx-auto mb-2" style="width:64px; height:64px;">
											<% if (dc.getImagen() == null || dc.getImagen().isBlank()) { %>
												<i class="bi bi-clipboard2-pulse text-secondary" style="font-size:4rem;"></i>
											<% } else { %>
												<img src="<%= request.getContextPath() %>/img/<%= dc.getImagen() %>" class="rounded" style="width:100%; height:100%; object-fit:cover;" alt="<%= dc.getNombre() %>">
											<% } %>
										</div>
										<div>
											<h3 class="h6 mb-1 fw-semibold"><%= dc.getNombre() %></h3>
											<p class="mb-0 text-secondary small"><%= dc.getDescripcion() %></p>
										</div>
									</a>
								<% } %>
							<% } else { %>
								<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay días de consulta</div>
							<% } %>
						</div>
					</div>
				</aside>
		</div>
	</div>
	<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
		crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>