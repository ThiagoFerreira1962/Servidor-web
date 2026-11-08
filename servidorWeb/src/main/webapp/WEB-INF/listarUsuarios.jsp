<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTUsuario" %>
<%@ page import="datatypes.DTMedico" %>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLEncoder" %>

<!doctype html>

<html lang="es-UY">
<head>
<meta charset="UTF-8">

<title>Usuarios | Salud.uy</title>
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<!-- LINK PARA USAR BOOTSTRAP -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<!-- TAMBIEN DE BOOTSTRAP,ES PARA EL TAMAñO -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- LINKEO A CSS -->
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/base.css">
	<jsp:include page="header.jsp"/>
</head>
<body>
<% List <DTUsuario> usuarios=(List<DTUsuario>) request.getAttribute("usuarios"); %>
<div class="container py-5 position-relative">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card glass shadow-lg">
        <div class="card-body p-4">
        
		<a href="<%=request.getAttribute("volverA")%>" class="btn btn-secondary">
        <i class="bi bi-arrow-left"></i> Volver 
	    </a>
        <h1 class="h3 text-center mb-4">Consultar Usuarios</h1>
        
        <% if (usuarios != null && !usuarios.isEmpty()) { %>
          
          <!-- Encabezado -->
        <div class="d-flex align-items-center gap-4 px-3 pb-2 fw-semibold border-bottom">
            <span></span>
            <span class="flex-grow-1 text-center px-3">Nickname</span>
            <span style="px-3">Tipo</span>
        </div>

    <div class="list-group list-group-flush w-auto mx-auto">
        <% for (DTUsuario u : usuarios) { %>
            <% String tipo = (u instanceof DTMedico) ? "Médico" : "Paciente"; %>
            <a href="<%= request.getContextPath() %>/perfil?nick=<%= URLEncoder.encode(u.getNickname(), "UTF-8") %>"
               class="list-group-item list-group-item-action d-flex align-items-center gap-3">
                <img src="<%= request.getContextPath() %>/img/<%= u.getFoto() %>" class="avatar">
                <span class="flex-grow-1 text-center"><%= u.getNickname() %></span>
                <span class="text-secondary small"><%= tipo %></span>
            </a>
        <% } %>
    </div>

<% } else { %>
    <div class="list-group-item text-secondary text-center mt-2 mb-2">No hay usuarios</div>
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