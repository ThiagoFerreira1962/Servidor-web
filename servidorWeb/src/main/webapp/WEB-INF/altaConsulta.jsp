<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="datatypes.DTDiaConsulta" %>
<%@ page import="datatypes.DiaSemana" %>
<%@ page import="java.net.URLEncoder" %>

<!DOCTYPE html>
<html lang="es-UY">

<head>
<link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<title>Alta Consulta | SaludUy</title>

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
<%! private String valor(HttpServletRequest req, String campo) {
	String v = req.getParameter(campo);
	return (v == null) ? "" : v;
	}
%>

<% List<DTDiaConsulta> lista = (List<DTDiaConsulta>) request.getAttribute("diasConsulta"); %>
<% String diaSeleccionado = (String) request.getAttribute("nomDiaConsulta"); %>
<% if (diaSeleccionado == null) { %>
	<!-- Me tengo que fijar si vengo de un forward del detalle del día de consulta o de un POST fallido -->
	<% diaSeleccionado = request.getParameter("nomDiaConsulta"); %>
<% } %>

  <div class="container py-5 position-relative">
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card glass shadow-lg">
          <div class="card-body p-4">
            <h1 class="h3 text-center mb-4">Alta de Consulta</h1>

            <!-- Mensaje de error -->
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
     			<div class="alert alert-danger"><%= error %></div>
 			<% } %>


            <form action="<%= request.getContextPath() %>/altaConsulta" method="post" enctype="multipart/form-data">

              <!-- LISTADO DE DÍAS DE CONSULTA -->
            <div class="mb-4">
  <label for="diaConsulta" class="form-label">Día de consulta</label>
<select id="diaConsulta" name="nomDiaConsulta" class="form-select" required>
    <option value="">Seleccione un día de consulta</option>
    <% if (lista != null) {
        for (DTDiaConsulta dt : lista) { %>
            <option value="<%= dt.getNombre() %>" <%= dt.getNombre().equals(diaSeleccionado) ? "selected" : ""%>>
                <%= dt.getNombre() %>
            </option>
    <%  }
    } %>
</select>
    </div>
              <div class="mb-3">
                <label for="nombre" class="form-label">Nombre de la consulta</label>
                <input type="text" id="nombre" name="nombreConsulta" class="form-control" required value="<%= valor(request, "nombreConsulta") %>">
              </div>

              <div class="mb-3">
                <label for="fecha" class="form-label">Fecha</label>
                <input type="date" id="fecha" name="fecha" class="form-control" required value="<%= valor(request, "fecha") %>">
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="numeroSala" class="form-label">Número de sala</label>
                  <input type="number" id="numSala" name="numSala" class="form-control" min="1" required value="<%= valor(request, "numSala") %>">
                </div>
                <div class="col-md-6 mb-3">
                  <label for="cantidadNumeros" class="form-label">Cantidad de números</label>
                  <input type="number" id="cantidadNumeros" name="nroDisp" class="form-control" min="1" required value="<%= valor(request, "nroDisp") %>">
                </div>
              </div>

              <div class="mb-3">
                <label for="imagen" class="form-label">Imagen (opcional)</label>
                <input type="file" id="imagen" name="foto" class="form-control" accept=".png, .jpg, .jpeg">
              </div>

              <div class="d-flex justify-content-center gap-2 mt-4">
                <button type="submit" class="btn btn-default">Confirmar</button>
                <% String volver = (diaSeleccionado != null) ? request.getContextPath() + "/consultarDiaConsulta?nombre=" + URLEncoder.encode(diaSeleccionado, "UTF-8") : request.getContextPath() + "/inicio"; %>
				<a href="<%= volver %>" class="btn btn-secondary">Cancelar</a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
  <script src="${pageContext.request.contextPath}/js/mainjs.js"></script>
</body>
</html>