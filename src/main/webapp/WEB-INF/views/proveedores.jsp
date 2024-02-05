<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Lista de Proveedores" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>Gestión de Proveedores</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Agregar</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Ruc,Nombre,Persona de Contacto,Telefono,Opciones"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="proveedor">
        <tr>
            <td class="d-none id">${proveedor.getId()}</td>
            <td class="ruc">${proveedor.getRuc()}</td>
            <td class="nombre">${proveedor.getNombre()}</td>
            <td class="persona_contacto">${empty proveedor.getPersonaContacto() ? 'Sin Contacto' : proveedor.getPersonaContacto()}</td>
            <td class="telefono">${empty proveedor.getTelefono() ? 'Sin telefóno' : proveedor.getTelefono()}</td>
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
                <label for="ruc" class="form-label">Ruc</label>
                <input type="number" name="ruc" id="ruc" class="form-control" required/>
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
                <label for="persona_contacto" class="form-label">Persona de Contacto</label>
                <input type="text" name="persona_contacto" id="persona_contacto" class="form-control"/>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="telefono" class="form-label">Telefono</label>
                <input type="number" name="telefono" id="telefono" class="form-control"/>
            </div>
        </div>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>