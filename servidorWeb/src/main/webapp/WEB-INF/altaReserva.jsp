<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="datatypes.DTDiaConsulta" %>
<%@ page import="datatypes.DTBeneficioAnual" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alta Reserva</title>
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
	<!-- Función que permite que si hay un error al momento de dar de alta, los datos ingresados queden cargados -->
	<%! private String valor(HttpServletRequest req, String campo) {
			String v = req.getParameter(campo);
			return (v == null) ? "" : v;
		}
	%>
	<% 	List<DTDiaConsulta> diasConsulta = (List<DTDiaConsulta>) request.getAttribute("diasDeConsulta"); %>
	<% 	List<DTConsulta> consultas = (List<DTConsulta>) request.getAttribute("consultas"); %>
	<% 	List<DTBeneficioAnual> beneficios = (List<DTBeneficioAnual>) request.getAttribute("beneficios"); %>
	<%	String consultaSeleccionada = (String) request.getAttribute("consultaElegida"); %>
	<%	String diaSeleccionado = (String) request.getAttribute("diaElegido"); %>
	<% if (diaSeleccionado == null) {
		//ME TENGO QUE FIJAR SI VENGO DE UN FORWARD O POST FALLIDO
		diaSeleccionado = request.getParameter("dia");
		%>
	<% } %>
	<% if (consultaSeleccionada == null) {
		//ME TENGO QUE FIJAR SI VENGO DE UN FORWARD O POST FALLIDO
		consultaSeleccionada = request.getParameter("consulta");
		%>
	<% } %>

  <div class="container py-5 position-relative">
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card glass shadow-lg">
          <div class="card-body p-4">
            <h1 class="h3 text-center mb-4">Reserva</h1>
            
            <!-- Mensaje de error -->
            <% String error = (String) request.getAttribute("error"); %>
    		<% if (error != null) { %>
      			<div class="alert alert-danger"><%= error %></div>
 			<% } %>

            <form action="<%= request.getContextPath() %>/altaReserva" method="post">

				<div class="mb-3">
  					<label for="diaConsulta" class="form-label">Día de Consulta</label>
  					<select id="diaConsulta" name="dia" class="form-select" required 
  						onchange="window.location='<%= request.getContextPath() %>/altaReserva?nombreDia=' + encodeURIComponent(this.value)">
  					<option value="">Seleccione un día</option>
  					<% for(DTDiaConsulta dia : diasConsulta) { %>
  						<option value ="<%= dia.getNombre() %>" <%= dia.getNombre().equals(diaSeleccionado) ? "selected" : "" %>>
  							<%= dia %>
  						</option>
  					<% } %>
  				</select>
				</div>
        
              <div class="mb-3">
                <label for="consulta" class="form-label">Consulta</label>
                <select id="consulta" name="consulta" class="form-select" required>
                  <option value="">Seleccione una consulta</option>
                  <% if(consultas != null) { %>
                  	<% for(DTConsulta consulta : consultas) { %>
  						<option value ="<%= consulta.getNombre() %>" <%= consulta.getNombre().equals(consultaSeleccionada) ? "selected" : "" %>>
  							<%= consulta %>
  						</option>
  					<% } %>
  					<% } %>
                </select>
              </div>
              
            <% if (beneficios != null) {%>
			<div class="mb-3">
  				<label class="form-label">Beneficio anual <span class="text-muted">(opcional)</span></label>
  				<div class="list-group">

    			<label class="list-group-item list-group-item-action d-flex align-items-center gap-3">
      				<input class="form-check-input m-0" type="radio" name="beneficio" value="" checked>
      				<span class="flex-grow-1">No usar beneficio</span>
    			</label>

    			<% for (DTBeneficioAnual ben : beneficios) { %>
    			<label class="list-group-item list-group-item-action d-flex align-items-center gap-3">
      				<input class="form-check-input m-0" type="radio" name="beneficio" value="<%= ben.getAnio() %>">
      				<span class="flex-grow-1">Beneficio <%= ben.getAnio() %></span>
      				<span class="badge text-bg-secondary rounded-pill"><%= ben.getCantidadOrdenes() %> consultas</span>
    			</label>
    			<% } %>

  				</div>
			</div>
			<% } %>

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