<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
	HttpSession sesion = request.getSession(false);
    String mensaje = (sesion == null) ? null : (String) sesion.getAttribute("mensaje");
    if (mensaje != null) {
        sesion.removeAttribute("mensaje");   // se muestra una sola vez
    }
    String errorGlobal = (String) session.getAttribute("errorGlobal");
    if (errorGlobal != null) {
        session.removeAttribute("errorGlobal");	 // se muestra una sola vez
    }
%>
<% if (mensaje != null || errorGlobal != null) { %>
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
    
    	<% if (mensaje != null) { %>
	        <div id="toastMensaje" class="toast btn-default sin-hover" role="alert" aria-live="assertive" aria-atomic="true">
	            <div class="toast-header btn-default sin-hover">
	                <img src="<%= request.getContextPath() %>/img/logo.png" class="rounded me-2" alt="logo" height="20">
	                <strong class="me-auto">SaludUY</strong>
	                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Cerrar"></button>
	            </div>
	            <div class="toast-body"><%= mensaje %></div>
	        </div>
	    <% } %>
	    
	    <% if (errorGlobal != null) { %>
	    	<div id="toastError" class="toast text-bg-danger" role="alert" aria-live="assertive" aria-atomic="true">
        		<div class="toast-header text-bg-danger">
            		<img src="<%= request.getContextPath() %>/img/logo.png" class="rounded me-2" alt="logo" height="20">
            		<strong class="me-auto">SaludUY</strong>
           			<button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Cerrar"></button>
        		</div>
      			<div class="toast-body"><%= errorGlobal %></div>
    		</div>
	    <% } %>
	    
    </div>
    
    <script>
    	window.addEventListener("load", function () {
        	var exito = document.getElementById("toastMensaje");
        	if (exito) {
            	new bootstrap.Toast(exito, { delay: 4000 }).show();
        	}
        	var error = document.getElementById("toastError");
        	if (error) {
            	new bootstrap.Toast(error, { delay: 6000 }).show();
        	}
    	});
	</script>
<% } %>