<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>

<% String[] especialidades = (String[]) request.getAttribute("especialidades"); %>
<% String destino = (String) request.getAttribute("destinoEspecialidad"); %>
<% if (destino == null) { %>
	<% destino="/consultarConsulta"; %>
<% } %>

<div class="card mb-3">
	<h2 class="card-header text-center">Especialidades</h2>
	<div class="list-group list-group-flush">
		<% if (especialidades != null && (especialidades.length > 0)) { %>
			<% for (String esp : especialidades) { %>
				<a href="<%= request.getContextPath() %><%= destino %><%= destino.contains("?") ? "&" : "?" %>especialidad=<%= URLEncoder.encode(esp, "UTF-8") %>" class="list-group-item list-group-item-action text-center"><%= esp %></a>
			<% } %>
		<% } else { %>
			<div class="list-group-item text-secondary text-center mt-2 mb-2">No hay especialidades</div>
		<% } %>
	</div>
</div>