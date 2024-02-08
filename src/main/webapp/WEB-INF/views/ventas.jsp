<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<fmt:setLocale value = "es_PE"/>
<c:set var="titulo" value="Ventas" />

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
            <td class="cliente">${venta.getCliente().getNombre()}</td>
            <td class="fecha"><fmt:formatDate value="${venta.getFecha()}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
            <td class="monto"><fmt:formatNumber value = "${venta.getTotal()}" type = "currency"/></td>
            <td>
                <button type="button" class="btn btn-primary btnVer" data-bs-toggle="modal" data-bs-target="#modal"><i class="fa-solid fa-eye"></i></button>
                <button type="button" class="btn btn-danger btnEliminar"><i class="fa-solid fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
</c:set>

<!--Modal-->
<c:set var="cuerpoModal">
    <div class="row form-venta">
        <input type="hidden" name="cantidadProductos" id="cantidadProductos" value="0"/>
        <div class="col-sm-8">
            <div class="form-group">
                <label for="categoria" class="form-label">Cliente</label>
                <select name="cliente" id="cliente" class="form-select">
                    <c:forEach items="${clientes}" var="cliente">
                        <option value="${cliente.getId()}">${cliente.getNombre()} (${cliente.getDni()})</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="form-group">
                <label for="montoTotal" class="form-label">Total</label>
                <input type="text" id="montoTotal" name="montoTotal" class="form-control" readonly/>
            </div>
        </div>
        <h5 class="mt-4 fw-bold">Productos</h5>
        <div id="detallePrecios" class="mb-4">
        </div>
        <button type="button" class="btn btn-primary" id="agregarProducto">Añadir</button>
    </div>
    <div class="detalle">
        <div class="row">
            <div class="col-7">
                <h4 id="cliente_detalle"></h4>
            </div>
            <div class="col-5">
                <h5 id="fecha_detalle"></h5>
            </div>
        </div>
        <div id="tabla-detalle">
            
        </div>
    </div>
</c:set>

<%@ include file="../templates/layout.jsp" %>