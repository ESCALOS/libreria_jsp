<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Redireccionando...</title>
    <meta http-equiv="refresh" content="0;url=<%= request.getContextPath() %>/main">
</head>
<body>
    Si no eres redirigido automáticamente, <a href="<%= request.getContextPath() %>/main">haz clic aquí</a>.
</body>
</html>