<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Lista de Clientes" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>Gestión de clientes</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Agregar</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Dni,Nombre,Correo,Dirección,Opciones"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="cliente">
        <tr>
            <td class="d-none id">${cliente.getId()}</td>
            <td class="dni">${cliente.getDni()}</td>
            <td class="nombre">${cliente.getNombre()}</td>
            <td class="email">${empty cliente.getEmail() ? 'Sin correo' : cliente.getEmail()}</td>
            <td class="direccion">${empty cliente.getDireccion() ? 'Sin correo' : cliente.getDireccion()}</td>
            <td>
                <button type="button" class="btn btn-warning btnEditar" data-bs-toggle="modal" data-bs-target="#modal"><i class="fa-solid fa-pencil"></i></button>
                <button type="button" class="btn btn-danger btnEliminar"><i class="fa-solid fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
</c:set>

<!--Modal-->
<c:set var="cuerpoModal">
    <div class="row">
        <div class="col-sm-6">
            <div class="form-group">
                <label for="dni" class="form-label">Dni</label>
                <input type="number" name="dni" id="dni" class="form-control" />
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre" class="form-control" required/>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="email" class="form-label">Correo</label>
                <input type="email" name="email" id="email" class="form-control"/>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="direccion" class="form-label">Dirección</label>
                <input type="text" name="direccion" id="direccion" class="form-control"/>
            </div>
        </div>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>