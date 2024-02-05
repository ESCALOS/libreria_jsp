package com.nanoka.weblibreria.dto;

import com.nanoka.weblibreria.models.Producto;
import com.nanoka.weblibreria.models.UnidadMedida;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrecioProductoDto {
    private int id;
    private Producto producto;
    private UnidadMedida unidadMedida;
    private int cantidad;
    private BigDecimal precio;
}
