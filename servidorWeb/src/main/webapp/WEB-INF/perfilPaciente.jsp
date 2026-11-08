<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTPaciente"%>
<%@ page import="java.util.List"%>
<%-- Llamo al servlet consultarUsuario --%>
<%
DTPaciente paciente = (DTPaciente) request.getAttribute("usuario");
String nick = (String) request.getAttribute("nickname");
boolean perfilDeSiMismo = Boolean.TRUE.equals(request.getAttribute("perfilDeSiMismo"));
List<String> reservas = (List<String>) request.getAttribute("reservas");
String ctx = request.getContextPath();

boolean tieneFoto = paciente.getFoto() != null && !paciente.getFoto().isBlank();
String srcFoto = tieneFoto ? ctx + "/img/" + paciente.getFoto() : ctx + "/img/defaultIcon.jpg";
%>
<!DOCTYPE html>
<html lang="es-UY">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><%= nick %> | SaludUy</title>
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

  <jsp:include page="header.jsp"/>

  <div class="container py-5">

    <!-- Botón volver: arriba a la izquierda -->
    <div class="mt-4 mb-2">
      <a href="<%= ctx %>/consultarUsuario" class="btn btn-secondary">
        <i class="fa-solid fa-arrow-left"></i> Volver
      </a>
    </div>

    <!-- Perfil -->
    <div class="row" id="carta-1">

      <!-- Columna izquierda -->
      <div class="col-lg-4 d-flex flex-column">
        <div class="card mb-2">
          <div class="card-body">

            <div class="text-center">
              <div class="foto-perfil">
                <img src="<%= srcFoto %>" alt="Foto de perfil" class="image rounded-circle">
                <% if (perfilDeSiMismo) { %>
                  <div class="middle">
                    <div class="text">Cambiar foto</div>
                  </div>
                <% } %>
              </div>

              <h5 class="fs-3 mt-2"><%= paciente.getNickname() %></h5>
              <h6 class="text-muted mt-0 fs-5">Paciente</h6>
            </div>

            <div class="px-4">
              <p class="mb-2">
                <span class="fw-bold">Nickname:</span>
                <span class="text-muted ms-1"><%= paciente.getNickname() %></span>
              </p>
              <p class="mb-2">
                <span class="fw-bold">Nombre:</span>
                <span class="text-muted ms-1 campoEdit"><%= paciente.getNombre() %></span>
              </p>
              <p class="mb-2">
                <span class="fw-bold">Apellido:</span>
                <span class="text-muted ms-1 campoEdit"><%= paciente.getApellido() %></span>
              </p>
              <p class="mb-2">
                <span class="fw-bold">Correo:</span>
                <span class="text-muted ms-1"><%= paciente.getCorreo() %></span>
              </p>
            </div>

          </div>
        </div>
      </div>

      <!-- Columna derecha -->
      <div class="col-lg-8" id="carta-2">
        <div class="card h-100">
          <div class="card-body d-flex flex-column">

            <h5 class="fw-bold fs-4 mb-3">Información del paciente</h5>
            <hr class="mt-0">

            <div class="row py-3 fs-6">
              <div class="col-sm-4"><p class="mb-0 fw-bold">Fecha nacimiento</p></div>
              <div class="col-sm-8"><p class="text-muted mb-0 campoEdit"><%= paciente.getFechaNacimiento() %></p></div>
            </div>
            <hr class="my-0">

            <div class="row py-3 fs-6">
              <div class="col-sm-4"><p class="mb-0 fw-bold">Fecha ingreso</p></div>
              <div class="col-sm-8"><p class="text-muted mb-0 campoEdit"><%= paciente.getFechaIngreso() %></p></div>
            </div>
            <hr class="my-0">

            <div class="row py-3 fs-6">
              <div class="col-sm-4"><p class="mb-0 fw-bold">Antecedentes</p></div>
              <div class="col-sm-8"><p class="text-muted mb-0 campoEdit"><%= paciente.getAntecedentes() %></p></div>
            </div>

            <% if (perfilDeSiMismo) { %>
              <!-- Botones abajo a la derecha (solo perfil propio) -->
              <div class="mt-auto align-self-end pt-3 d-flex flex-column align-items-end gap-2">
                <div class="d-flex gap-2">
                  <button class="btn btn-danger" id="btn-cancelar">Cancelar</button>
                  <button class="btn btn-success" id="btn-confirmar">Confirmar</button>
                </div>
                <button class="btn btn-editar" id="btn-editar">Editar perfil</button>
              </div>
            <% } %>

          </div>
        </div>
      </div>

    </div>

    <% if (perfilDeSiMismo) { %>
      <!-- Reservas (solo perfil propio) -->
      <div class="card mt-3" id="carta-3">
        <div class="card-body">
          <p class="mb-2 fw-bold fs-6">Reservas</p>
          <hr class="mt-0 mb-0">
          <% if (reservas != null && !reservas.isEmpty()) {
               for (String reserva : reservas) { %>
            <div class="row py-3 fs-6">
              <div class="col">
                <a href="<%= ctx %>/consultarReserva?nombreConsulta=<%= java.net.URLEncoder.encode(reserva, "UTF-8") %>"
                   class="text-decoration-none"><%= reserva %></a>
              </div>
            </div>
            <hr class="my-0">
          <%   }
             } else { %>
            <p class="text-muted py-3 mb-0 fs-6">No hay reservas.</p>
          <% } %>
        </div>
      </div>
    <% } %>

  </div>

  <% if (perfilDeSiMismo) { %>
    <script src="<%= ctx %>/js/modificarDatosUsuario.js"></script>
  <% } %>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
          crossorigin="anonymous"></script>
  <script src="<%= ctx %>/js/mainjs.js"></script>

  <div class="col-md-6">
    <h5 class="text-center">Reservas</h5>
    <div class="list-group">
      <% if (reservas != null) {
           for (String reserva : reservas) { %>
        <a href="<%= request.getContextPath() %>/consultarReserva?nombreConsulta=<%= java.net.URLEncoder.encode(reserva, "UTF-8") %>"
           class="list-group-item list-group-item-action text-center">
          <%= reserva %>
        </a>
      <%   }
         } %>
    </div>
  </div>

  </div>
  <div class="text-center mt-4">
    <button type="button" class="btn btn-default" onclick="history.back()">Volver</button>
  </div>
  </div>
  </div>
  </div>
  <!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
</body>
</html>