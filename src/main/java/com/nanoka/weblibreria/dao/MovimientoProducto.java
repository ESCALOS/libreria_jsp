package com.nanoka.weblibreria.dao;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoProducto {
    private int id;
    private int productoId;
    private int cantidad;
    private BigDecimal monto;
    private String razon;
    private String fecha;
}
