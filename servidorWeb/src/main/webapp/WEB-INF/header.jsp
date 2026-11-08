<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
</head>
<body>
	<%	String rol = (String) session.getAttribute("rol");
		String foto = (String) session.getAttribute("foto");
		String nick = (String) session.getAttribute("nickname");
	%>

	<%	if ("medico".equals(rol)) {
	%>
		<!-- HEADER MEDICO -->
		<header class="p-2 bg-dark text-white sticky-top" >
    	  <div class="container-fluid">
      		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
    			<!-- IMAGEN CON SU LINK -->
        		<a href="<%= request.getContextPath() %>/inicio" class="d-flex align-items-center mb-3 mb-lg-0 text-white text-decoration-none">
          			<img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo SaludUy" height="50" class="my-n5 me-3">
        		</a>
        		<!-- BARRA DE BUSQUEDA -->
        		<div class="col-12 col-lg d-flex justify-content-center mb-3 mb-lg-0">
        			<form class="d-flex w-100" style="max-width: 600px;" role="search">
          			<input type="search" class="form-control form-control-dark me-2" placeholder="Buscar..." aria-label="Buscar">
          				<button type="submit" class="btn btn-default" aria-label="Buscar">
            				<i class="bi bi-search"></i>
         	 			</button>
        			</form>
      			</div>
      			<div class="dropdown text-end">
          			<a href="#" class="d-block link-light text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
          				 <img src="<%= request.getContextPath() %>/img/<%=foto %>" alt="mdo" width="50" height="50" class="rounded-circle">
       				</a>
      				<ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1" style="">
           				<li><a class="dropdown-item" href="<%= request.getContextPath() %>/perfil?nick=<%=nick%>">Perfil</a></li>
           				<li><hr class="dropdown-divider"></li>
           				<li><a class="dropdown-item" href="<%= request.getContextPath() %>/cerrarSesion">Cerrar Sesión</a></li>
          			</ul>
        		</div>
      		</div>
    	  </div>
  		</header>
			
  	<% } else if ("paciente".equals(rol)) {	
  	%>
  		<!-- HEADER PACIENTE -->
		<header class="p-2 bg-dark text-white sticky-top" >
    	  <div class="container-fluid">
      		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
    			<!-- IMAGEN CON SU LINK -->
        		<a href="<%= request.getContextPath() %>/inicio" class="d-flex align-items-center mb-3 mb-lg-0 text-white text-decoration-none">
          			<img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo SaludUy" height="50" class="my-n5 me-3">
        		</a>
        		<!-- BARRA DE BUSQUEDA -->
        		<div class="col-12 col-lg d-flex justify-content-center mb-3 mb-lg-0">
        			<form class="d-flex w-100" style="max-width: 600px;" role="search">
          			<input type="search" class="form-control form-control-dark me-2" placeholder="Buscar..." aria-label="Buscar">
          				<button type="submit" class="btn btn-default" aria-label="Buscar">
            				<i class="bi bi-search"></i>
         	 			</button>
        			</form>
      			</div>
      			<div class="dropdown text-end me-2">
          			<a href="#" class="d-block link-light text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
          				 <img src="<%= request.getContextPath() %>/img/<%=foto %>" alt="mdo" width="50" height="50" class="rounded-circle">
       				</a>
      				<ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1" style="">
           				<li><a class="dropdown-item" href="<%= request.getContextPath() %>/perfil?nick=<%=nick%>">Perfil</a></li>
           				<li><hr class="dropdown-divider"></li>
           				<li><a class="dropdown-item" href="<%= request.getContextPath() %>/cerrarSesion">Cerrar Sesión</a></li>
          			</ul>
        		</div>
      		</div>
    	  </div>
  		</header>
  		
  	<% } else {
  	%>	
  		<!-- HEADER VISITANTE-->
		<header class="p-2 bg-dark text-white sticky-top" >
    	  <div class="container-fluid">
      		<div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        		<a href="<%= request.getContextPath() %>/inicio" class="d-flex align-items-center mb-3 mb-lg-0 text-white text-decoration-none">
          			<img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo SaludUy" height="50" class="my-n5 me-3">
        		</a>
        		<div class="col-12 col-lg d-flex justify-content-center mb-3 mb-lg-0">
        			<form class="d-flex w-100" style="max-width: 600px;" role="search">
          			<input type="search" class="form-control form-control-dark me-2" placeholder="Buscar..." aria-label="Buscar">
          				<button type="submit" class="btn btn-default" aria-label="Buscar">
            				<i class="bi bi-search"></i>
         	 			</button>
        			</form>
      			</div>
        		<div class="text-end">
          			<button type="button" class="btn btn-default" onclick="window.location.href='<%= request.getContextPath() %>/iniciarSesion'">Acceder</button>
        		</div>
      		</div>
    	  </div>
  		</header>
  
  	<% } 
  	%>	
  	
  	<div class="">
  	</div>
  	
</body>
</html>