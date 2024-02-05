<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${titulo}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/index.jsp">Libreria</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'productos' or empty param.page}">active</c:if> aria-current="page" href="${pageContext.servletContext.contextPath}/main?page=productos">Productos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'categorias'}">active</c:if> aria-current="page" href="${pageContext.servletContext.contextPath}/main?page=categorias">Categorías</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'unidades'}">active</c:if> aria-current="page" href="${pageContext.servletContext.contextPath}/main?page=unidades">Unidades</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'clientes'}">active</c:if>" href="${pageContext.servletContext.contextPath}/main?page=clientes">Clientes</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'proveedores'}">active</c:if>" href="${pageContext.servletContext.contextPath}/main?page=proveedores">Proveedores</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${param.page eq 'consulta'}">active</c:if>" href="${pageContext.servletContext.contextPath}/main?page=consulta">Consulta</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <main>
            <div class="container">
                ${cabecera}
                <hr>
                <%@ include file="tabla.jsp" %>
                <%@ include file="modal.jsp" %>
            </div>
        </main>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.servletContext.contextPath}/js/${empty param.page ? 'clientes' : param.page}.js"></script>
        <c:if test="${not empty requestScope.message}">
            <script>
                Swal.fire({
                    title: "${requestScope.message}",
                    icon: "${requestScope.icon}"
                });
            </script>
        </c:if>
    </body>
</html>
