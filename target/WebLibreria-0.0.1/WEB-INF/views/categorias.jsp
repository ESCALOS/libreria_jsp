<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Lista de Categorías" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>Gestión de categorías</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Agregar</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Código,Nombre,Opciones"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="categoria">
        <tr>
            <td class="id">${categoria.getId()}</td>
            <td class="nombre">${categoria.getNombre()}</td>
            <td>
                <button type="button" class="btn btn-warning btnEditar" data-bs-toggle="modal" data-bs-target="#modal"><i class="fa-solid fa-pencil"></i></button>
                <button type="button" class="btn btn-danger btnEliminar"><i class="fa-solid fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
</c:set>

<!--Modal-->
<c:set var="cuerpoModal">
    <div class="form-group">
        <label for="nombre" class="form-label">Nombre</label>
        <input type="text" name="nombre" id="nombre" class="form-control" required/>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>