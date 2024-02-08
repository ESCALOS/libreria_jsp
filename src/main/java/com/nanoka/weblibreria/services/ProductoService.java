package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dao.PrecioProductoDao;
import com.nanoka.weblibreria.dao.ProductoDao;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.PrecioProducto;
import com.nanoka.weblibreria.models.Producto;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

public class ProductoService {

    public void logicaProducto(HttpServletRequest request, int id, String nombre, RespuestaDto respuesta, CrudService crudService) {
        int categoriaId = 0;
        int stock = 0;
        if (!"eliminar".equalsIgnoreCase(request.getParameter("accion"))) {
            categoriaId = Integer.parseInt(request.getParameter("categoria"));
            stock = Integer.parseInt(request.getParameter("stock"));
        }
        Producto producto = Producto.builder()
                .id(id)
                .nombre(nombre)
                .categoriaId(categoriaId)
                .stock(stock)
                .build();
        crudService.crud(producto, new ProductoDao(), request.getParameter("accion"),respuesta);
        if ("guardar".equalsIgnoreCase(request.getParameter("accion")) && !respuesta.isError()) {
            id = Integer.parseInt(respuesta.getMensaje());
            respuesta.setMensaje("Producto Agregado");
        }
        if (!"eliminar".equalsIgnoreCase(request.getParameter("accion"))) {
            int cantidadPrecios = Integer.parseInt(request.getParameter("cantidadPrecios"));
            for (int i = 1; i <= cantidadPrecios; i++) {
                String unidadMedidaParam = request.getParameter("unidadMedida" + i);
                String cantidadParam = request.getParameter("cantidad" + i);
                String precioParam = request.getParameter("precio" + i);

                if (unidadMedidaParam != null && cantidadParam != null && precioParam != null) {
                    int precioId = Integer.parseInt(request.getParameter("precioId" + i));
                    int unidadMedidaId = Integer.parseInt(unidadMedidaParam);
                    int cantidad = cantidadParam.isBlank() ? 0 : Integer.parseInt(cantidadParam);
                    BigDecimal precio = precioParam.isBlank() ? BigDecimal.ZERO : new BigDecimal(precioParam);
                    PrecioProductoDao precioProductoDao = new PrecioProductoDao();

                    PrecioProducto precioProducto = PrecioProducto.builder()
                            .id(precioId)
                            .productoId(id)
                            .unidadMedidaId(unidadMedidaId)
                            .cantidad(cantidad)
                            .precio(precio)
                            .build();
                    if (cantidad <= 0) {
                        if (precioId > 0) {
                            precioProductoDao.eliminar(precioProducto);
                        }
                    } else {
                        if (precioId == 0) {
                            precioProductoDao.insertar(precioProducto);
                        } else {
                            precioProductoDao.editar(precioProducto);
                        }
                    }
                }
            }
        }
    }
}
