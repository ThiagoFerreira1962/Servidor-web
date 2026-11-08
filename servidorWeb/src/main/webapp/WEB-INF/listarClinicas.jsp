<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTClinica" %>
<%@ page import="java.net.URLEncoder" %>


<!doctype html>

<html lang="es-UY">
<head>
<meta charset="utf-8">
<!-- TÍTULO QUE APARECE EN LA PESTAÑA -->
<title>Clínicas | SaludUy</title>


<!-- LINK PARA USAR BOOTSTRAP -->
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<!-- TAMBIEN DE BOOTSTRAP,ES PARA EL TAMAÑO -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- LINKEO A CSS -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/base.css">
	<jsp:include page="header.jsp"/>
</head>
<body>
	<% List<DTClinica> clinicas = (List<DTClinica>) request.getAttribute("clinicas"); %>
	<div class="container py-5 position-relative">
	  <div class="row justify-content-center">
	    <div class="col-lg-8">
	      <div class="card glass shadow-lg">
	        <div class="card-body p-4">
	        
				<a href="<%=request.getAttribute("volverA")%>" class="btn btn-secondary">
        		<i class="bi bi-arrow-left"></i> Volver 
			    </a>
			    
	          <h1 class="h3 text-center mb-4">Consultar Clínicas</h1>

	          <% if (clinicas != null && !clinicas.isEmpty()) { %>
				<div class="list-group list-group-flush w-auto mx-auto">
					<% for (DTClinica c : clinicas) { %>
				        <a href="<%= request.getContextPath() %>/consultarClinica?nombreClinica=<%= URLEncoder.encode(c.getNombre(), "UTF-8") %>" class="list-group-item list-group-item-action d-flex align-items-center gap-3">
				            <img src="<%= request.getContextPath() %>/img/<%= c.getFoto() %>" class="avatar">
				            <span><%= c.getNombre() %></span>
				        </a>
					<% } %>
				  </div>
			  <% } else { %>
				<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay clínicas</div>
			  <% } %>

	        </div>
	      </div>
	    </div>
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