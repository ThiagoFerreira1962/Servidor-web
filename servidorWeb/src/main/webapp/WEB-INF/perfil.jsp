<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTUsuario"%>
<%@ page import="java.util.List"%>
<%@ page import="datatypes.DTPaciente" %>
<%@ page import="datatypes.DTMedico" %>
<%@ page import="datatypes.DTConsulta" %>
<%@ page import="datatypes.DTBeneficioAnual" %>
<%@ page import="datatypes.EstadoConsulta" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%-- Llamo al servlet Perfil --%>
<%
boolean perfilDeSiMismo = Boolean.TRUE.equals(request.getAttribute("perfilDeSiMismo"));
List<String> especialidades = (List<String>) request.getAttribute("especialidades");
List<String> especialidadesQueNoTiene = (List<String>) request.getAttribute("especialidadesQueNoTiene");
List<DTConsulta> consultas = (List<DTConsulta>) request.getAttribute("consultas");
List<DTBeneficioAnual> beneficiosAnuales = (List<DTBeneficioAnual>) request.getAttribute("beneficiosAnuales");
String ctx = request.getContextPath();
DTUsuario usuario = (DTUsuario) request.getAttribute("usuario");

boolean tieneFoto = usuario.getFoto() != null && !usuario.getFoto().isBlank();
String srcFoto = tieneFoto ? ctx + "/img/" + usuario.getFoto() : ctx + "/img/defaultMedico.jpg";
%>
<!DOCTYPE html>
<html lang="es-UY">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><%=usuario.getNickname()%> | SaludUy</title>
  <link rel="icon" type="image/png" href="<%= ctx %>/img/logo.png">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
        crossorigin="anonymous">
  <link rel="stylesheet" href="<%= ctx %>/css/base.css">
</head>
<body>
  <jsp:include page="mensajeExito.jsp"/>
  <jsp:include page="header.jsp"/>

  <div class="container py-5">

    <!-- Botón volver: arriba a la izquierda -->
    <div class="mt-4">
      <a href="<%=request.getAttribute("volverA")%>" class="btn btn-secondary">
        <i class="fa-solid fa-arrow-left"></i>Volver 
      </a>
    </div>
       <!-- Perfil -->
    <div class="row" id="carta-1">

      <!-- Columna izquierda -->
      <div class="col-lg-4 d-flex flex-column">
        <div class="card flex-grow-1 mt-3">
          <div class="card-body">

            <div class="text-center">
              <div class="foto-perfil">
                <img src="<%= srcFoto %>" alt="Foto de perfil" class="image rounded-circle">
                <% if (perfilDeSiMismo) { %>
                  <div class="middle">
                    <label for="imagen" class="btn-default btn btn-sm mb-0">Cambiar foto</label>
                    <input type="file" name="foto" id="imagen" form="formPerfil"
                           accept=".png, .jpg, .jpeg" class="d-none" onchange="this.form.requestSubmit()">
                  </div>
                <% } %>
              </div>

              <h5 class="fs-3 mt-2"><%= usuario.getNickname() %></h5>
              <% if (usuario instanceof DTMedico) { %>
                <h6 class="text-muted mt-0 fs-5">Médico</h6>
              <% } else if (usuario instanceof DTPaciente) { %>
                <h6 class="text-muted mt-0 fs-5">Paciente</h6>
              <% } %>
            </div>

          </div>

          <div class="px-4">
            <p class="mb-2 d-flex align-items-center gap-2" style="min-height: 2rem;">
              <span class="fw-bold">Nickname:</span>
              <span class="text-muted"><%= usuario.getNickname() %></span>
            </p>
            <p class="mb-2 d-flex align-items-center gap-2" style="min-height: 2rem;">
              <span class="fw-bold">Nombre:</span>
              <input type="text" name="nombre" form="formPerfil" class="form-control-plaintext text-muted p-0 m-0 flex-grow-1 campoEdit" style="width: auto; min-height: 0; line-height: inherit;" value="<%= usuario.getNombre() %>" readonly>
            </p>
            <p class="mb-2 d-flex align-items-center gap-2" style="min-height: 2rem;">
              <span class="fw-bold">Apellido:</span>
              <input type="text" name="apellido" form="formPerfil" class="form-control-plaintext text-muted p-0 m-0 flex-grow-1 campoEdit" style="width: auto; min-height: 0; line-height: inherit;" value="<%= usuario.getApellido() %>" readonly>
            </p>
            <p class="mb-2 d-flex align-items-center gap-2" style="min-height: 2rem;">
              <span class="fw-bold">Correo:</span>
              <span class="text-muted text-break"><%= usuario.getCorreo() %></span>
            </p>
          </div>

        </div>
      </div>

      <!-- Columna derecha: solo la info del médico/paciente -->
      <form id="formPerfil" action="<%= request.getContextPath() %>/perfil?nick=<%= java.net.URLEncoder.encode(usuario.getNickname(), "UTF-8") %>" method="post" enctype="multipart/form-data" class="col-lg-8 mt-3">

        <div id="carta-2" class="h-100">
          <div class="card h-100">
            <div class="card-body d-flex flex-column">

              <% if (usuario instanceof DTMedico) {
                   DTMedico med = (DTMedico) usuario; %>
                <h5 class="fw-bold fs-4 mb-3">Información del médico</h5>
                <hr class="mt-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4"><p class="mb-0 fw-bold">Nº registro</p></div>
                  <div class="col-sm-8"><input type="text" name="nroRegistro" class="form-control-plaintext text-muted mb-0 campoEdit" value="<%= med.getNroRegistro() %>" readonly></div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4"><p class="mb-0 fw-bold">Sitio web</p></div>
                  <div class="col-sm-8"><input type="text" name="sitioWeb" class="form-control-plaintext text-muted mb-0 campoEdit" value="<%= med.getSitioWeb() %>" readonly></div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4"><p class="mb-0 fw-bold">Mes licencia</p></div>
                  <div class="col-sm-8"><input type="text" name="mesLicencia" class="form-control-plaintext text-muted mb-0 campoEdit" value="<%= med.getLicencia() %>" readonly></div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4"><p class="mb-0 fw-bold">Porcentaje adicional (%)</p></div>
                  <div class="col-sm-8"><input type="text" name="costoAdicional" class="form-control-plaintext text-muted mb-0 campoEdit" value="<%= String.format("%.2f", med.getCostoAdicional()) %>" readonly></div>
                </div>

              <% } else if (usuario instanceof DTPaciente) { %>
                <% DTPaciente paciente = (DTPaciente) usuario; %>

                <h5 class="fw-bold fs-4 mb-3">Información del paciente</h5>
                <hr class="mt-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4">
                    <p class="mb-0 fw-bold">Cédula de identidad</p>
                  </div>
                  <div class="col-sm-8">
                    <input type="text" name="ci" id="ci"
                           class="form-control-plaintext text-muted mb-0 campoEdit"
                           value="<%= paciente.getCi() %>" readonly>
                  	<div class="invalid-feedback"></div>
                  </div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4">
                    <p class="mb-0 fw-bold">Fecha nacimiento</p>
                  </div>
                  <div class="col-sm-8">
                    <input type="date" name="fechaNacimiento" id="fechaNacimiento"
                           class="form-control-plaintext text-muted mb-0 campoEdit"
                           value="<%= paciente.getFechaNacimiento() %>" readonly>
                  	<div class="invalid-feedback"></div>
                  </div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4">
                    <p class="mb-0 fw-bold">Fecha ingreso</p>
                  </div>
                  <div class="col-sm-8">
                    <input type="date" name="fechaIngreso" id="fechaIngreso"
                           class="form-control-plaintext text-muted mb-0 campoEdit"
                           value="<%= paciente.getFechaIngreso() %>" readonly>
                  	<div class="invalid-feedback"></div>
                  </div>
                </div>
                <hr class="my-0">

                <div class="row py-3 fs-6">
                  <div class="col-sm-4">
                    <p class="mb-0 fw-bold">Antecedentes</p>
                  </div>
                  <div class="col-sm-8">
                    <input type="text" name="antecedentes"
                           class="form-control-plaintext text-muted mb-0 campoEdit"
                           value="<%= paciente.getAntecedentes() %>" readonly>
                  </div>
                </div>

              <% } %>

<% if (perfilDeSiMismo) { %>
  <div class="mt-auto pt-3 w-100 d-flex justify-content-between align-items-center">

    <!-- Modificar contraseña: abajo a la izquierda -->
    <a href="<%= ctx %>/modificarContrasena"
       class="btn btn-outline-secondary"
       id="btn-modificar-contrasenia"
       style="width: fit-content;">
      <i class="fa-solid fa-key"></i> Modificar contraseña
    </a>

    <!-- Los tres botones en la misma línea -->
    <div class="d-flex align-items-center gap-2">
      <button type="button" class="btn btn-danger" id="btn-cancelar">Cancelar</button>
      <button type="submit" class="btn btn-success" id="btn-confirmar">Confirmar</button>
      <button type="button" class="btn btn-editar" id="btn-editar">Editar perfil</button>
    </div>

  </div>
<% } %>

            </div>
          </div>
        </div>

      </form>
    </div>
    <!-- Fin row -->

 <% if (usuario instanceof DTMedico) { %>

      <!-- Especialidades del médico (debajo de las dos cards) -->
      <div class="card mt-3" id="carta-3">
        <div class="card-body">

          <div class="row fs-6">
            <div class="col-sm-9"><p class="mb-2 fw-bold">Especialidades</p></div>
            <div class="col-sm-3"></div>
          </div>
          <hr class="mt-0 mb-0">

          <% if (especialidades != null) {
               for (String especialidad : especialidades) { %>
            <div class="row py-3 fs-6 align-items-center">
              <div class="col-sm-9"><p class="mb-0"><%= especialidad %></p></div>
              <div class="col-sm-3 text-end">
                <% if (perfilDeSiMismo) { %>
                   <form action="<%= ctx %>/perfil?nick=<%= java.net.URLEncoder.encode(usuario.getNickname(), "UTF-8") %>"
          				method="post" class="d-inline">
          				<input type="hidden" name="accion" value="borrarEspecialidad">
          				<input type="hidden" name="espElegida" value="<%= especialidad %>">
	                  	<button type="submit" class="btn btn-delete">
	                    <span class="mdi mdi-delete mdi-24px"></span>
	                    <span class="mdi mdi-delete-empty mdi-24px"></span>
	                    <span>Borrar</span>
	                  </button>
                  </form>
                <% } %>
              </div>
            </div>
            <hr class="my-0">
          <%   }
             } %>

          <% if (perfilDeSiMismo && especialidadesQueNoTiene != null) { %>
            <div class="dropdown pt-3 text-center">
              <button type="button" data-bs-toggle="dropdown"
                      class="btn btn-sm btn-outline-success dropdown-toggle" id="agregarEsp"
                      aria-expanded="false">
                <i class="fa-solid fa-plus"></i> Agregar especialidad
              </button>
              <div class="dropdown-menu">
                <% for (String esp : especialidadesQueNoTiene) { %>
                	<form action="<%= ctx %>/perfil?nick=<%= java.net.URLEncoder.encode(usuario.getNickname(), "UTF-8") %>" method="post">
                		<input type="hidden" name="accion" value="agregarEspecialidad">
                		<input type="hidden" name="espAgregar" value="<%= esp %>">
                		<button type="submit" class="dropdown-item"><%= esp %></button>
                	</form>
	             <% } %>
              </div>
            </div>
          <% } %>

        </div>
      </div>
      <!-- Consultas (debajo de especialidades) -->
	  <% if (!perfilDeSiMismo){ %>
      	<div class="card mt-5">
		   				<h2 class="card-header text-center">Consultas</h2>
		   				<% if (consultas != null && !consultas.isEmpty()) {%>
			   				<div class="table-responsive">
			   					<table class="table table-striped table-hover align-middle mb-0">
									<tbody>
									<% for (DTConsulta consulta : consultas) { %>
										<tr>
											<td><%= consulta.getNombre() %></td>
											<td><a href="<%= request.getContextPath() %>/consultarConsulta?nombreConsulta=<%= consulta.getNombre() %>" class="ms-1 text-decoration-none" title="Ver detalle de la consulta"><i class="bi bi-eye"></i></a></td>
										</tr>
									<% } %>
									</tbody>
								</table>
							</div>
						<% } else { %>
							<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay consultas</div>
						<% } %>
					</div>

    <% } %>
	<% } %>
	
    <!-- Beneficios anuales del paciente (solo paciente viendo su propio perfil) -->
    <% if (usuario instanceof DTPaciente && perfilDeSiMismo) { %>
    <div class="card mt-3" id="carta-5">
      <h2 class="card-header text-center">Mis Beneficios Anuales</h2>
      <% if (beneficiosAnuales != null && !beneficiosAnuales.isEmpty()) { %>
        <div class="table-responsive">
          <table class="table table-striped table-hover align-middle mb-0">
            <thead class="table-light">
              <tr>
                <th>Año del beneficio</th>
                <th>Cantidad de órdenes de consultas</th>
                <th>Cantidad de tickets de medicamento</th>
              </tr>
            </thead>
            <tbody>
            <% for (DTBeneficioAnual ba : beneficiosAnuales) { %>
              <tr>
                <td><%= ba.getAnio() %></td>
                <td><%= ba.getCantidadOrdenes() %></td>
                <td><%= ba.getCantidadTickets() %></td>
              </tr>
            <% } %>
            </tbody>
          </table>
        </div>
      <% } else { %>
        <div class="list-group-item text-secondary text-center mt-2 mb-2">No hay beneficios anuales</div>
      <% } %>
    </div>
    <% } %>

    <% if (perfilDeSiMismo) { %>
      <script src="<%= ctx %>/js/modificarDatosUsuario.js"></script>
    <% } %>


  </div>
  <!-- Fin container -->

  <!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
    
  <script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>
