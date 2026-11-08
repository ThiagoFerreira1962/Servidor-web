<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="datatypes.DiaSemana" %>

<!DOCTYPE html>
<html lang="es-UY">
<head>
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	
<title>Alta Día de Consulta | SaludUy</title>

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
  	<jsp:include page="header.jsp"/>
  	
	<!-- Función que permite que si hay un error al momento de dar de alta, los datos ingresados queden cargados -->
	<%! private String valor(HttpServletRequest req, String campo) {
			String v = req.getParameter(campo);
			return (v == null) ? "" : v;
		}
	%>
	<% List<String> clinicas = (List<String>) request.getAttribute("clinicas"); %>
	<% String[] especialidades = (String[]) request.getAttribute("especialidades"); %>


  <div class="container py-5 position-relative">
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card glass shadow-lg">
          <div class="card-body p-4">
            <h1 class="h3 text-center mb-4">Alta de Día de Consulta</h1>
            
            <!-- Mensaje de error -->
            <% String error = (String) request.getAttribute("error"); %>
    		<% if (error != null) { %>
      			<div class="alert alert-danger"><%= error %></div>
 			<% } %>

            <form action="<%= request.getContextPath() %>/altaDiaConsulta" method="post" enctype="multipart/form-data">

              <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" id="nombre" name="nombre" class="form-control" required value="<%= valor(request, "nombre") %>">
              </div>

              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea id="descripcion" name="descripcion" class="form-control" rows="3" required ><%= valor(request, "descripcion") %></textarea>
              </div>

				<div class="mb-3">
  					<label for="diaSemana" class="form-label">Día de la semana</label>
  					<select id="diaSemana" name="diaSemana" class="form-select" required>
  					<% String diaSeleccionado = valor(request, "diaSemana"); %>
  					<option value="">Seleccione un día</option>
  					<% for(DiaSemana d : DiaSemana.values()) { %>
  						<option value ="<%= d %>" <%= d.toString().equals(diaSeleccionado) ? "selected" : "" %>><%= d %></option>
  					<% } %>
  				</select>
				</div>
              <div class="mb-3">
                <label for="horaInicio" class="form-label">Hora de inicio</label>
                <input type="time" id="horaInicio" name="horaInicio" class="form-control" required value="<%= valor(request, "horaInicio") %>">
              </div>

              <div class="mb-3">
                <label for="especialidad" class="form-label">Especialidad</label>
                <% String especialidadSeleccionada = valor(request, "especialidad"); %>
                <select id="especialidad" name="especialidad" class="form-select" required>
                  <option value="">Seleccione una especialidad</option>
                  <%
				if (especialidades != null) {
                	for (String e: especialidades) { %>
                		<option value="<%=e %>"<%= e.equals(especialidadSeleccionada) ? "selected" : "" %>><%=e %> </option>
					<%}
				}%>
                </select>
              </div>

              <div class="mb-3">
                <label for="clinica" class="form-label">Clínica</label>
                <% String clinicaSeleccionada = valor(request, "clinica"); %>
                <select id="clinica" name="clinica" class="form-select" required>
                <option value="">Seleccione una clínica</option>
                <% if (clinicas != null) {
                	for (String c: clinicas){ %>
                		<option value="<%= c %>"<%= c.equals(clinicaSeleccionada) ? "selected" : "" %>><%=c %> </option>
                	<%} 
                	}%>

                  
                </select>
              </div>

              <div class="mb-3">
                <label for="imagen" class="form-label">Imagen (opcional)</label>
                <input type="file" id="imagen" name="foto" class="form-control" accept=".png, .jpg, .jpeg">
              </div>

              <div class="d-flex justify-content-center gap-2 mt-4">
                <button type="submit" class="btn btn-default">Confirmar</button>
                <a href="<%= request.getContextPath() %>/inicio" class="btn btn-secondary">Cancelar</a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
  <script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>