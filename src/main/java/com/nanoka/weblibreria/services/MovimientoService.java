package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dao.MovimientoProductoDao;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.MovimientoProducto;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

public class MovimientoService {
    public void ingresarVenta(HttpServletRequest request, RespuestaDto respuesta, CrudService crudService) {
        String tipo = request.getParameter("tipo").toUpperCase();
        String razon = request.getParameter("razon");
        int cantidadProductos = Integer.parseInt(request.getParameter("cantidadProductos"));
        for (int i = 1; i <= cantidadProductos; i++) {
            String productoParam = request.getParameter("producto" + i);
            String cantidadParam = request.getParameter("cantidad" + i);
            String montoParam = request.getParameter("monto" + i);
            if (productoParam != null && cantidadParam != null && montoParam != null && razon != null) {
                int productoId = Integer.parseInt(productoParam);
                int cantidad = Integer.parseInt(cantidadParam);
                BigDecimal monto = BigDecimal.valueOf(Integer.parseInt(cantidadParam));
                MovimientoProducto movimientoProducto = MovimientoProducto.builder()
                        .productoId(productoId)
                        .cantidad(cantidad)
                        .monto(monto)
                        .razon(razon)
                        .tipo(tipo)
                        .build();
                crudService.crud(movimientoProducto, new MovimientoProductoDao(), request.getParameter("accion"),respuesta);
            }
        }
    }
}
