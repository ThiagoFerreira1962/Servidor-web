<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-UY"> 
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Registrarse</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
</head>

<body>
<%
String nickname = request.getParameter("nickname") != null ? request.getParameter("nickname") : "";
String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
String apellido = request.getParameter("apellido") != null ? request.getParameter("apellido") : "";
String correo = request.getParameter("correo") != null ? request.getParameter("correo") : "";
String ci = request.getParameter("ci") != null ? request.getParameter("ci") : "";
String antecedentes = request.getParameter("antecedentes") != null ? request.getParameter("antecedentes") : "";
String fechaNac = request.getParameter("fechaNac") != null ? request.getParameter("fechaNac") : "";

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

        <h1 class="h3 text-center mb-4">Registrar Paciente</h1>
        <div id="alertas"></div>

        <form id="formReg" method="POST" onsubmit="return validarFormularioPaciente()" enctype="multipart/form-data">
          <fieldset id="camposReg">
            <div class="row g-2">
                  
             <input type="hidden" name="rol" value="paciente">
             
              <div class="col-12">
                <div class="form-floating">
                  <input name="nickname" id="nickname" type="text" value="<%= nickname%>" class="form-control" placeholder="Nickname" oninput="this.classList.remove('is-invalid')" required>
                  <label for="nickname"  class="form-label">Nickname</label>
                 </div>
               </div>

              <div class="col-md-6">
                <div class="form-floating">
                  <input name="nombre" id="nombre" type="text" value="<%=nombre%>" class="form-control" placeholder="Nombre"required>
                  <label for="nombre"  class="form-label">Nombre</label>
                </div>
              </div>
              
              <div class="col-md-6">
                <div class="form-floating">
                  <input name="apellido" id="apellido" type="text" value="<%=apellido%>" class="form-control" placeholder="Apellido" required>
                  <label for="apellido"  class="form-label">Apellido</label>
                </div> 
              </div>    

              <div class="col-12">
                <div class="form-floating">
                  <input name="correo" id="correo" type="email" value="<%=correo%>" class="form-control" placeholder="Correo" required>
                  <label for="correo"  class="form-label">Correo</label>
                </div>
              </div>
              
              <div class="col-md-6">
                <div class="form-floating">
                  <input name="fechaNac" id="fechaNac" type="date" value="<%=fechaNac%>" class="form-control" placeholder="Fecha de Nacimiento" oninput="this.classList.remove('is-invalid')" required>
                  <label for="fechaNac"  class="form-label">Fecha de Nacimiento</label>	
                  <div class="invalid-feedback" ></div>
                </div>
              </div>  
              
              <div class="col-md-6">
                <div class="form-floating">    
                  <input name="ci" id="ci" type="text" value="<%=ci%>" class="form-control" placeholder="C.I." oninput="this.classList.remove('is-invalid')">
                  <label for="ci"  class="form-label">C.I.</label>
                  <div class="invalid-feedback"></div>
                </div>
              </div>
              
              <div class="col-12">
                <div class="form-floating">  
                  <textarea name="antecedentes" id="antecedentes" class="form-control" placeholder="Antecedentes"><%=antecedentes %></textarea>
                  <label for="antecedentes"  class="form-label">Antecedentes</label>
                </div>  
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
        <p class="mb-0 text-center">¿Desea registrarse como Médico? <a href="<%= request.getContextPath() %>/registrarUsuario?rol=medico">Click aquí</a></p>

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
