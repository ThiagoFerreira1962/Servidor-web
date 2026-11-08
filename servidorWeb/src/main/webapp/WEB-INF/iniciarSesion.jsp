<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-UY">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Iniciar Sesión</title>
  <link rel="icon" type="image/png" href=" <%= request.getContextPath() %>/img/logo.png">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
</head>
<body>

<header class="p-2 bg-dark text-white sticky-top" >
    	  <div class="container-fluid">
      		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        		<a href="<%= request.getContextPath() %>/inicio" class="d-flex align-items-center text-white text-decoration-none">
          			<img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo SaludUy" height="50" class="me-3">
          		</a>
          		<a href="<%= request.getContextPath() %>/inicio" class="nav-link text-white fs-5 px-3"> Inicio</a>
      		</div>
    	  </div>
</header>

<div class="container min-vh-100 d-flex align-items-start justify-content-center py-5">
  <div class="row justify-content-center w-100">
    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
      <div class="card shadow-lg">
        <div class="card-body p-4">
          <h1 class="h3 text-center mb-4">Iniciar Sesión</h1>
            <form method="post" action="<%= request.getContextPath() %>/iniciarSesion">
              
               <div class="row g-3">
                  <div class="col-12">
                      <div class="form-floating">
                        <input name="nickname" id="nickname" type="text" class="form-control" placeholder="Nickname">
                        <label for = "nickname" class="form-label">Nickname</label>
                      </div>
                  </div>
                
                  <div class="col-12">
                      <div class="form-floating">
                        <input name="contrasena" id="contrasena" type="password" class="form-control" placeholder="Contraseña">
                        <label for = "contrasena" class="form-label">Contraseña</label>
                      </div>
                 </div>
                 
                 <% if (request.getAttribute("error") != null) { %>
  				 <div class="text-danger">
  				 	<%= request.getAttribute("error") %>
  				 </div>
				<% } %>
                 
			
                  <div class="col-12 d-grid">
                    <button type="submit" name="iniciarSesion" class="btn btn-default">Iniciar Sesión</button>
                  </div>
                 </div>
                 
              </form>
            
          <p>¿No está registrado?</p>
          <p><a href="<%= request.getContextPath() %>/registrarUsuario?rol=paciente"> Registrarse como Paciente</a> / <a href="<%= request.getContextPath() %>/registrarUsuario?rol=medico">Registrarse como Médico</a></p>

        </div>
      </div>
    </div>
  </div>
</div>
		<!-- ESTO ES PARA JAVASCRIPT, DEJAR ABAJO DEL TODO EN BODY -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
		<script src="<%= request.getContextPath()%>/js/mainjs.js"></script>
</body>
</html>
