package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dto.GraficoDto;
import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import com.nanoka.weblibreria.dto.ProductoMasVendidosDto;
import com.nanoka.weblibreria.dao.VentaDao;
import java.util.ArrayList;

public class ProductosMasVendidosService {

    public GraficoDto<ProductoMasVendidosDto> logica(HttpServletRequest request) {
        int anio = LocalDate.now().getYear();
        String label = "", data = "";
        if (request.getParameter("anio") != null) {
            anio = Integer.parseInt(request.getParameter("anio"));
        }
        ArrayList<ProductoMasVendidosDto> productosMasVendidos = new VentaDao().obtenerProductosMasVendidos(anio);
        for (ProductoMasVendidosDto pmv : productosMasVendidos) {
            label += "'" + pmv.getProducto() + "',";
            data += pmv.getCantidad() + ",";
        }
        if (data.length() > 0) {
            label = label.substring(0, label.length() - 1);
            data = data.substring(0, data.length() - 1);
        }
        return new GraficoDto<>(label,data,anio,productosMasVendidos);
    }
}
