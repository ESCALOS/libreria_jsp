package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dao.PrecioProductoDao;
import com.nanoka.weblibreria.dao.VentaDtoDao;
import com.nanoka.weblibreria.dto.DetalleVentaDto;
import com.nanoka.weblibreria.dto.PrecioProductoDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.dto.VentaDto;
import com.nanoka.weblibreria.models.Cliente;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class VentaService {

    public void ingresarVenta(HttpServletRequest request, RespuestaDto respuesta, CrudService crudService) {
        int clienteId = Integer.parseInt(request.getParameter("cliente"));
        BigDecimal total = BigDecimal.ZERO;
        int cantidadProductos = Integer.parseInt(request.getParameter("cantidadProductos"));
        ArrayList<DetalleVentaDto> detallesVenta = new ArrayList<>();
        for (int i = 1; i <= cantidadProductos; i++) {
            String productoParam = request.getParameter("producto" + i);
            String cantidadParam = request.getParameter("cantidad" + i);
            if (productoParam != null && cantidadParam != null) {
                int producto = Integer.parseInt(productoParam);
                int cantidad = Integer.parseInt(cantidadParam);
                PrecioProductoDto precioProducto = new PrecioProductoDao().obtenerPrecioProductoPorId(producto);
                BigDecimal subtotal = precioProducto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
                DetalleVentaDto detalleVenta = DetalleVentaDto.builder()
                        .producto(precioProducto.getProducto())
                        .cantidad(cantidad)
                        .cantidadUnidad(precioProducto.getCantidad())
                        .nombreUnidad(precioProducto.getUnidadMedida().getNombre())
                        .precio(precioProducto.getPrecio())
                        .subtotal(subtotal)
                        .build();
                detallesVenta.add(detalleVenta);
                total = total.add(subtotal);
            }
        }
        VentaDto venta = VentaDto.builder()
                .cliente(Cliente.builder().id(clienteId).build())
                .fecha(new Timestamp(System.currentTimeMillis()))
                .total(total)
                .detallesVenta(detallesVenta)
                .build();
        crudService.crud(venta, new VentaDtoDao(), request.getParameter("accion"),respuesta);
    }
}
