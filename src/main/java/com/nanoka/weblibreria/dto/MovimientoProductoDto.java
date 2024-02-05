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
public class MovimientoProductoDto {
    private int id;
    private Producto producto;
    private int cantidad;
    private BigDecimal monto;
    private String razon;
    private String fecha;
}
