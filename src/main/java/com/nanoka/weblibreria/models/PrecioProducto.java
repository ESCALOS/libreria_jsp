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
public class PrecioProducto {
    private int id;
    private int productoId;
    private int unidadMedidaId;
    private int cantidad;
    private BigDecimal precio;
}
