package com.nanoka.weblibreria.dto;

import com.nanoka.weblibreria.models.Producto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVentaDto {
    private int id;
    private Producto producto;
    private int cantidadTotal;
    private int cantidadUnidad;
    private String nombreUnidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
