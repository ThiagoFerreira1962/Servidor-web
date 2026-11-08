<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title> Modificar contraseña | SaludUy</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/img/logo.png">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
        crossorigin="anonymous">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
</head>
<body>
  <jsp:include page="mensajeExito.jsp"/> 
  <jsp:include page="header.jsp"/>
  <div class="card mx-auto mt-3" style="max-width: 500px;">
  <div class="card-body">

    <form id="formContrasena" method="post">

      <div class="mb-3">
        <label for="actual" class="form-label">Ingrese contraseña actual:</label>
        <input type="password" class="form-control" id="actual" name="actual" required>
      </div>

      <div class="mb-3">
        <label for="nueva" class="form-label">Ingrese nueva contraseña:</label>
        <input type="password" class="form-control" id="nueva" name="nueva" required>
      </div>

      <div class="mb-3">
        <label for="confirmar" class="form-label">Reingrese la nueva contraseña:</label>
        <input type="password" class="form-control" id="confirmar" name="confirmar" required>
      </div>

      <div class="text-center">
       <button type="button" class="btn btn-secondary" onclick="history.back()">Volver</button>
       <button type="submit" class="btn btn-default">Confirmar</button>
      </div>

    </form>
  </div>
</div>
  
  <script
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
    
  <script src="<%= request.getContextPath() %>/js/mainjs.js"></script>
</body>
</html>