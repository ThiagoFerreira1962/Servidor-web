<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="es-UY"> 
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Registrarse</title>
  <link rel="icon" type="<%= request.getContextPath() %>image/png" href="<%= request.getContextPath() %>/img/logo.png">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
  
</head>


<body>
<%
String nickname = request.getParameter("nickname") != null ? request.getParameter("nickname") : "";
String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
String apellido = request.getParameter("apellido") != null ? request.getParameter("apellido") : "";
String correo = request.getParameter("correo") != null ? request.getParameter("correo") : "";
String nroReg = request.getParameter("nroReg") != null ? request.getParameter("nroReg") : "";
String lic = request.getParameter("meses");  
String linkWeb = request.getParameter("linkWeb") != null ? request.getParameter("linkWeb") : "";
String costo = request.getParameter("costo") != null ? request.getParameter("costo") : "";
String[] listaEsp = (String []) request.getAttribute("listaEsp");
if (listaEsp == null){
	    listaEsp = new String[0];
	}
String [] marcadas = request.getParameterValues("especialidades");
List <String> espSeleccionadas = marcadas != null ? Arrays.asList(marcadas) : null;
if (espSeleccionadas == null){
		espSeleccionadas = new ArrayList<>();
	}

String errorFormato = (String) request.getAttribute("errorFormato");
String error = (String) request.getAttribute("error");
%>	

	

<header class="p-2 bg-dark text-white sticky-top" >
    	  <div class="container-fluid">
      		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        		<a href="<%= request.getContextPath() %>/inicio" class="d-flex align-items-center text-white text-decoration-none">
          			<img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo SaludUy" height="50" class="me-3">
          		</a>
          		<a href="<%= request.getContextPath() %>/inicio" class="nav-link text-white fs-5 px-3">Inicio</a>
      		</div>
    	  </div>
</header>
<div class="container min-vh-100 d-flex align-items-start justify-content-center py-4">
  <div class="row justify-content-center w-100">
    <div class="col-12 col-md-8 col-lg-6 col-xl-6">
      <div class="card shadow-lg">
        <div class="card-body p-4">
          

          <h1 class="h3 text-center mb-4">Registrar Médico</h1>
          <div id="alertas"></div>

          <form id="formReg" method="POST" enctype="multipart/form-data" onsubmit="return validarFormularioMedico()">
            <fieldset id="camposReg">
              <div class="row g-2">

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="nickname" id="nickname" type="text" class="form-control" value ="<%=nickname%>" placeholder="Nickname" required>
                    <label for="nickname" class="form-label">Nickname</label>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="correo" id="correo" type="email" class="form-control" value="<%=correo%>" placeholder="Correo" required>
                    <label for="correo" class="form-label">Correo</label>
                  </div>  
                </div>

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="nombre" id="nombre" type="text" class="form-control" value="<%=nombre%>" placeholder="Nombre" required>
                    <label for="nombre" class="form-label">Nombre</label>
                  </div>   
                </div>

                <div class="col-md-6">
                  <div class="form-floating">    
                    <input name="apellido" id="apellido" type="text" class="form-control" value="<%=apellido%>" placeholder="Apellido" required>
                    <label for="apellido" class="form-label">Apellido</label>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="nroReg" id="nroReg" type="text" class="form-control" value="<%=nroReg%>" placeholder="Nro. Registro" required>
                    <label for="nroReg" class="form-label">Nro. Registro</label>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-floating">  
                    <select id="meses" name="meses" class="form-select">
                    <option value="0" <%= "Enero".equals(lic) ? "selected" : "" %>>Enero</option>
                    <option value="1" <%= "Febrero".equals(lic) ? "selected" : "" %>>Febrero</option>
                    <option value="2" <%= "Marzo".equals(lic) ? "selected" : "" %>>Marzo</option>
                    <option value="3" <%= "Abril".equals(lic) ? "selected" : "" %>>Abril</option>
                    <option value="4" <%= "Mayo".equals(lic) ? "selected" : "" %>>Mayo</option>
                    <option value="5" <%= "Junio".equals(lic) ? "selected" : "" %>>Junio</option>
                    <option value="6" <%= "Julio".equals(lic) ? "selected" : "" %>>Julio</option>
                    <option value="7" <%= "Agosto".equals(lic) ? "selected" : "" %>>Agosto</option>
                    <option value="8" <%= "Septiembre".equals(lic) ? "selected" : "" %>>Septiembre</option>
                    <option value="9" <%= "Octubre".equals(lic) ? "selected" : "" %>>Octubre</option>
                    <option value="10" <%= "Noviembre".equals(lic) ? "selected" : "" %>>Noviembre</option>
                    <option value="11" <%= "Diciembre".equals(lic) ? "selected" : "" %>>Diciembre</option>
                    </select>
                    <label for="meses" class="form-label">Mes de Licencia</label>
                  </div>
                </div>

                <div class="col-md-7">
                  <div class="form-floating">
                    <input name="linkWeb" id="linkWeb" type="text" class="form-control" placeholder="Página Web">
                    <label for="linkWeb" class="form-label">Página Web <span class="text-muted">(opcional)</span></label>
                  </div>
                </div>

                <div class="col-md-5">
                  <div class="input-group">
                    <div class="form-floating">
                     <input name="costo" id="costo" type="number" min="0" class="form-control" value="<%=costo%>" placeholder="Porcentaje adicional" required>
                     <label for="costo">Porcentaje Adicional</label>
                    </div>
                    <span class="input-group-text">%</span>
                  </div>
                </div>

                <div class="col-12">
                  <label class="form-label">Especialidades</label>
                  <div id="especialidades" class="d-flex flex-wrap gap-2 p-2 border rounded" style="max-height: 150px; overflow-y: auto;" oninput="this.classList.remove('is-invalid')">
							 
					 <% for (String esp : listaEsp){ %>
						<input type="checkbox" class="btn-check" name="especialidades" value="<%=esp%>" autocomplete="off" <%= espSeleccionadas.contains(esp) ? "checked" : "" %> id="<%=esp%>">
                    	<label class="esp-no-marcada" for="<%=esp%>"><%=esp%></label>	
					 <% } %>
                  </div>
                  <div class="invalid-feedback"></div>
                </div>

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="contrasena" id="contrasena" type="password" class="form-control" placeholder="Contraseña" required>
                    <label for="contrasena" class="form-label">Contraseña</label>
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="form-floating">
                    <input name="confContrasena" id="confContr" type="password" class="form-control" placeholder="Confirmar Contraseña" oninput="this.classList.remove('is-invalid')" required>
                    <label for="confContrasena" class="form-label">Confirmar Contraseña</label>
                    <div class="invalid-feedback"></div>
                  </div>
                </div>

                <div class="col-12">
                  <label for="foto" class="form-label">Foto de Perfil</label>
                  <input name="foto" id="foto" type="file" accept=".png, .jpg, .jpeg" class="form-control" placeholder="Foto de Perfil">
                </div>

				<% if (error != null) { %>
  				 <div class="text-danger">
  				 	<%= error %>
  				 </div>
				<% } else if (errorFormato != null){%>
				 <div class="text-danger">
  				 	<%= errorFormato %>
  				 </div>
  				<%} %>
  				
                <div class="col-12 d-grid">
                  <button type="submit" name="registrarse" class="btn btn-default">Registrarse</button>
                </div>

              </div>
            </fieldset>
          </form>

          <hr class="my-4">
          <p class="mb-1 text-center">¿Ya está registrado? <a href="<%= request.getContextPath() %>/iniciarSesion">Iniciar Sesión</a></p>
          <p class="mb-0 text-center">¿Desea registrarse como Paciente? <a href="<%= request.getContextPath() %>/registrarUsuario?rol=paciente">Click aquí</a></p>

        </div>
      </div>
    </div>
  </div>
</div>

<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="<%= request.getContextPath() %>/js/validaciones.js"></script>
</body>

</html>