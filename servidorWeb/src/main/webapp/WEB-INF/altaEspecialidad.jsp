<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es-UY">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<title>Alta Especialidad | SaludUy</title>
	<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
		
</head>
<body>
	<!-- HEADER -->
  	<jsp:include page="header.jsp" />
  	
	<div class="container py-5 position-relative">
    	<div class="row justify-content-center">
      		<div class="col-lg-8">
        		<div class="card glass shadow-lg">
          			<div class="card-body p-4">
            			<h1 class="h3 text-center mb-4">Alta de Especialidad</h1>
            			
            			<!-- Mensaje de error -->
            			<% String error = (String) request.getAttribute("error"); %>
    					<% if (error != null) { %>
      						<div class="alert alert-danger"><%= error %></div>
 					   	<% } %>
 					   	
            			<form action="<%= request.getContextPath() %>/altaEspecialidad" method="post">
            			
            				<div class="mb-3">
            					<label for="nombreEspecialidad" class="form-label">Nombre de la Especialidad</label>
            					<input type="text" name="nombreEspecialidad" class="form-control" required>
            				</div>
            				
            				<div class="mb-3">
            					<label for="porcentajeAdicional" class="form-label">Porcentaje Adicional</label>
            					<input type="number" name="porcentajeAdicional" class="form-control" step="0.01" min="0" required>
            				</div>
 					   	    
 					   	    <div class="d-flex justify-content-center gap-2">
 					   	    	<button type="submit" class="btn btn-default">Confirmar</button>
 					   	    	<button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
 					   	    </div>
            			</form>
            		</div>
            	</div>
            </div>
		</div>
	</div>
	<!-- JAVASCRIPT -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>