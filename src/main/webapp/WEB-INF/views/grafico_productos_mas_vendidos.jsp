<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="titulo" value="Productos más vendidos" />

<c:set var="contenedor">
    <div class="row">
        <div class="col-sm-3">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Productos más vendidos</h3>
                </div>
                <div class="card-body">
                    <form action="${pageContext.servletContext.contextPath}/main">
                        <input type="hidden" name="page" value="grafico_productos_mas_vendidos"/>
                        <div class="form-group mb-4">
                            <label for="anio" class="form-label">Año:</label>
                            <input name="anio" class="form-control" placeholder="Ingrese el año" value="${graficoDto.getAnio()}"/>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Enviar</button>
                    </form>
                    <br>
                    <table class="table table-bordered">
                        <thead>
                            <tr class="table-primary">
                                <th class="text-center">Producto</th>
                                <th class="text-center">Cantidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="pmv">
                                <tr>
                                    <td>${pmv.getProducto()}</td>
                                    <td style="text-align: right">${pmv.getCantidad()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
        <div class="col-sm-9">
            <div class="text-center">
                <h2>Productos más vendidos del año ${graficoDto.getAnio()}</h2>
                <div>
                    <canvas id="myChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</c:set>

<c:set var="scripts">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js"></script>
    <script>
        var ctx = document.getElementById("myChart").getContext("2d");
        var myChart = new Chart(ctx, {
            type: "pie",
            data: {
                labels: [${graficoDto.getLabel()}],
                datasets: [
                    {
                        label: [${graficoDto.getAnio()}],
                        data: [${graficoDto.getData()}],
                        backgroundColor: ["#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f"]
                    }
                ]
            }
        });
    </script>
</c:set>

<%@ include file="../templates/layout.jsp" %>