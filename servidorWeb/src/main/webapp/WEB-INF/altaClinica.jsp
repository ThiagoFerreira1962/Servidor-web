<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-UY">

<head>
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<title>Alta Clinica | SaludUy</title>

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
</head>
<body>
	<!-- HEADER -->
  	<jsp:include page="header.jsp" />
	<div class="container py-5 position-relative">
    	<div class="row justify-content-center">
      		<div class="col-lg-8">
        		<div class="card glass shadow-lg">
          			<div class="card-body p-4">
            			<h1 class="h3 text-center mb-4">Alta de Clinica</h1>
	            		<!-- Preparo Mensaje de error por si algo sale mal -->
            			<% String error = (String) request.getAttribute("error"); %> 
						<% if (error != null) { %>
      						<div class="alert alert-danger"><%= error %></div>
						<% } %>
 					   	<!-- Mandamos al post -->
 					   	<form action="<%= request.getContextPath() %>/altaClinica"
 					   		method="post" enctype="multipart/form-data">
 					   	<div class="mb-3">
            				<label for="nombreClinica" class="form-label">Nombre de la Clinica</label>
            				<input type="text" name="nombreClinica" class="form-control" required>
            			</div>	
            			<div class="mb-3">
            				<label for="descripcion" class="form-label">Descripcion</label>
            				<input type="text" name="descripcion" class="form-control" required>
            			</div>
            			<div class="mb-3">
            				<label for="direccion" class="form-label">Direccion</label>
            				<input type="text" name="direccion" class="form-control" required>
            			</div>
            			<div class="mb-3">
            				<label for="costoTicketMedicamento" class="form-label">Costo Medicamento</label>
            				<input type="number" name="costoTicketMedicamento" class="form-control" step="1.0" min="0" required>
            			</div>
            			<div class="mb-3">
            				<label for="costoOrdenConsulta" class="form-label">Costo Consulta</label>
            				<input type="number" name="costoOrdenConsulta" class="form-control" step="1.0" min="0" required>
            			</div>
            			<div class="mb-3">
            				<label for="sitioWeb" class="form-label">Sitio Web <span class="text-muted">(opcional)</span></label>
            				<input type="text" name="sitioWeb" class="form-control"> <!-- sin required es opcional el sitio web -->
            			</div>
            			<div class="mb-3">
    						<label for="foto" class="form-label">Imagen de la Clinica <span class="text-muted">(opcional)</span></label>
    						<input type="file" id="foto" name="foto"
           					class="form-control" accept=".png, .jpg, .jpeg">
						</div>
						<div class="d-flex justify-content-center gap-2">
    						<button type="submit" class="btn btn-default">Confirmar</button>
    						<button type="button" class="btn btn-secondary"
            				onclick="history.back()">Cancelar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>
