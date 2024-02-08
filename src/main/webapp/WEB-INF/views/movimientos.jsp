<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Movimientos de Productos" />

<c:set var="cabecera" >
    <div class="row m-2">
        <div class="col-9"><h1>${titulo}</h1></div>
        <div class="col-3 align-self-center">
            <div class="d-grid gap-2">
                <button type="button" class="btn btn-success btn-md btnGuardar" data-bs-toggle="modal" data-bs-target="#modal">Agregar Movimiento</button>
            </div>
        </div>
    </div>
</c:set>

<!--Tabla-->    
<c:set var="cabeceraTabla" value="Producto,Tipo,Cantidad,Monto,Razón,Fecha"></c:set>
<c:set var="cuerpoTabla">
    <c:forEach items="${data}" var="movimiento">
        <tr>
            <td class="d-none id">${movimiento.getId()}</td>
            <td class="producto">${movimiento.getProducto().getNombre()}</td>
            <td class="tipo"><span class="badge ${"INGRESO".equalsIgnoreCase(movimiento.getTipo()) ? 'bg-success' : 'bg-danger'}">${movimiento.getTipo()}</span></td>
            <td class="cantidad">${movimiento.getCantidad()}u</td>
            <td class="monto">S/. ${movimiento.getMonto()}</td>
            <td class="razon">${movimiento.getRazon()}</td>
            <td class="fecha">${movimiento.getFecha()}</td>
        </tr>
    </c:forEach>
</c:set>

<!--Modal-->
<c:set var="cuerpoModal">
    <div class="row">
        <input type="hidden" name="cantidadProductos" id="cantidadProductos" value="0"/>
        <div class="col-sm-12">
            <div class="form-group">
                <label for="tipo" class="form-label">Tipo de Movimiento</label>
                <select name="tipo" id="tipo" class="form-select">
                    <option>INGRESO</option>
                    <option>SALIDA</option>
                </select>
            </div>
        </div>
        <h5 class="mt-4 fw-bold">Productos:</h5>
        <div id="detalleProductos" class="mb-4">
        </div>
        <button type="button" class="btn btn-primary" id="agregarProducto">Añadir</button>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>