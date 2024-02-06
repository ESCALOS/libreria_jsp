<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<fmt:setLocale value = "es_PE"/>
<c:set var="titulo" value="Vender" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>Venta</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Ingresar venta</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Número,Cliente,Fecha,Monto,Opciones"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="venta">
        <tr>
            <td class="id">${venta.getId()}</td>
            <td class="nombre">${venta.getCliente().getNombre()}</td>
            <td class="categoria"><fmt:formatDate value="${venta.getFecha()}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
            <td class="categoria"><fmt:formatNumber value = "${venta.getTotal()}" type = "currency"/></td>
            <td>
                <button type="button" class="btn btn-primary btnVer"><i class="fa-solid fa-eye"></i></button>
                <button type="button" class="btn btn-danger btnEliminar"><i class="fa-solid fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
</c:set>

<!--Modal-->
<c:set var="cuerpoModal">
    <div class="row">
        <input type="hidden" name="cantidadPrecios" id="cantidadPrecios" value="0"/>
        <div class="col-sm-5">
            <div class="form-group">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre" class="form-control" required/>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label for="categoria" class="form-label">Categoria</label>
                <select name="categoria" id="categoria" class="form-select">
                    <c:forEach items="${categorias}" var="categoria">
                        <option value="${categoria.getId()}">${categoria.getNombre()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <label for="stock" class="form-label">Stock</label>
                <input type="number" name="stock" id="stock" value="0" class="form-control"/>
            </div>
        </div>
        <h5 class="mt-4 fw-bold">Precios:</h5>
        <div id="detallePrecios" class="mb-4">
        </div>
        <button type="button" class="btn btn-primary" id="agregarPrecio">Añadir</button>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>