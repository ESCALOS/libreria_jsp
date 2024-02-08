package com.nanoka.weblibreria.models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVenta {
    private int id;
    private int ventaId;
    private int productoId;
    private int cantidad;
    private int cantidadUnidad;
    private String nombreUnidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
