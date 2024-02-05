<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Lista de Productos" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>Gestión de Productos</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Agregar</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Nombre,Categoria,Stock,Opciones"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="producto">
        <tr>
            <td class="d-none id">${producto.getId()}</td>
            <td class="nombre">${producto.getNombre()}</td>
            <td class="categoria">${producto.getCategoria().getNombre()}</td>
            <td class="stock">${producto.getStock()}</td>
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
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre" class="form-control" required/>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="tipodept" class="form-label">Categoria</label>
                <select name="categoria" id="categoria" class="form-select">
                    <c:forEach items="${categorias}" var="categoria">
                        <option value="${categoria.getId()}">${categoria.getNombre()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group">
                <label for="stock" class="form-label">Stock</label>
                <input type="number" name="stock" id="stock" value="0" class="form-control"/>
            </div>
        </div>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>