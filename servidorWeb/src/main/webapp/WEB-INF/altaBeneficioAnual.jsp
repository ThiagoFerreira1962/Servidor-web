<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.DTPaciente" %>

<!DOCTYPE html>
<html lang="es-UY">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<title>Alta Beneficio Anual | SaludUy</title>
	<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
		
</head>
<body>
	<!-- HEADER -->
  	<jsp:include page="header.jsp" />
  	<!-- Función que permite que si hay un error al momento de dar de alta, los datos ingresados queden cargados -->
	<%! private String valor(HttpServletRequest req, String campo) {
			String v = req.getParameter(campo);
			return (v == null) ? "" : v;
		}
	%>
	<% List<DTPaciente> pacientes = (List<DTPaciente>) request.getAttribute("pacientes"); %>
	<% String pacienteSeleccionado = valor(request, "nicknamePaciente"); %>
	
  	<div class="container py-5 position-relative">
    	<div class="row justify-content-center">
      		<div class="col-lg-8">
        		<div class="card glass shadow-lg">
          			<div class="card-body p-4">
            			<h1 class="h3 text-center mb-4">Alta de Beneficio Anual</h1>
            			
            			<!-- Mensaje de error -->
            			<% String error = (String) request.getAttribute("error"); %>
    					<% if (error != null) { %>
      						<div class="alert alert-danger"><%= error %></div>
 					   	<% } %>
 					   	
            			<form action="<%= request.getContextPath() %>/altaBeneficioAnual" method="post" onsubmit="return validarFormularioBeneficioAnual()">
            			
            				<div class="mb-3">
            					<label for="nicknamePaciente" class="form-label">Paciente</label>
            					<% if (pacientes == null || pacientes.isEmpty()) { %>
            						<div class="alert alert-warning">No hay pacientes registrados en el sistema.</div>
            					<% } else { %>
	            					<select id="nicknamePaciente" name="nicknamePaciente" class="form-select" required>
		            					<option value="">Seleccione un paciente</option>
			            				<% for (DTPaciente p : pacientes) { %>
			            					<option value="<%= p.getNickname() %>" <%= p.getNickname().equals(pacienteSeleccionado) ? "selected" : "" %>><%= p.getNickname() %></option>
			            				<% } %>
		            				</select>
		            				<% } %>
            				</div>
            				
            				<div class="mb-3">
            					<label for="anioBA" class="form-label">Año del beneficio anual</label>
            					<input type="number" id="anioBA" name="anio" class="form-control" step="1" min="0" required oninput="this.classList.remove('is-invalid')" value="<%= valor(request, "anio") %>">
            					<div class="invalid-feedback"></div>
            				</div>
            				
            				<div class="mb-3">
            					<label for="cantOrdenes" class="form-label">Cantidad de órdenes de consultas</label>
            					<input type="number" id="cantOrdenes" name="cantOrdenes" class="form-control" step="1" min="0" required oninput="this.classList.remove('is-invalid')" value="<%= valor(request, "cantOrdenes") %>">
            					<div class="invalid-feedback"></div>
            				</div>
            				
            				<div class="mb-3">
            					<label for="cantTickets" class="form-label">Cantidad de tickets de medicamentos</label>
            					<input type="number" id ="cantTickets" name="cantTickets" class="form-control" step="1" min="0" required oninput="this.classList.remove('is-invalid')" value="<%= valor(request, "cantTickets") %>">
            					<div class="invalid-feedback"></div>
            				</div>
 					   	    
 					   	    <div class="d-flex justify-content-center gap-2">
 					   	    	<button type="submit" class="btn btn-default">Confirmar</button>
 					   	    	<button type="button" class="btn btn-secondary" onclick="volver()">Cancelar</button>
 					   	    </div>
            			</form>
            		</div>
            		
            		<% if (Boolean.TRUE.equals(request.getAttribute("confirmarEdicion"))) { %>
						<div class="modal fade" id="modalConfirmar" tabindex="-1" data-bs-backdrop="static">
						    <div class="modal-dialog modal-dialog-centered">
						        <div class="modal-content">
						            <div class="modal-header">
						                <h5 class="modal-title">Beneficio anual existente</h5>
						            </div>
						            <div class="modal-body">
						                El paciente ya tiene un beneficio anual para el año <%= valor(request, "anio") %>.
						                ¿Desea modificarlo con los datos ingresados?
						            </div>
						            <div class="modal-footer d-flex justify-content-center gap-2">
						                <form action="<%= request.getContextPath() %>/altaBeneficioAnual" method="post">
						                    <input type="hidden" name="nicknamePaciente" value="<%= valor(request, "nicknamePaciente") %>">
						                    <input type="hidden" name="anio" value="<%= valor(request, "anio") %>">
						                    <input type="hidden" name="cantOrdenes" value="<%= valor(request, "cantOrdenes") %>">
						                    <input type="hidden" name="cantTickets" value="<%= valor(request, "cantTickets") %>">
						                    <input type="hidden" name="confirmado" value="true">
						                    <button type="submit" class="btn btn-default">Confirmar</button>
						                </form>
						                <a href="<%= request.getContextPath() %>/inicio" class="btn btn-secondary">Cancelar</a>
						            </div>
						        </div>
						    </div>
						</div>
					<% } %>

            	</div>
            </div>
		</div>
	</div>
	<!-- JAVASCRIPT -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
	<script src="<%= request.getContextPath() %>/js/validaciones.js"></script>
	<script>
    	window.addEventListener("load", function () {
        	new bootstrap.Modal(document.getElementById("modalConfirmar")).show();
    	});
	</script>
	
</body>
</html>