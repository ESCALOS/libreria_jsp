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
public class Venta {
    private int id;
    private String fecha;
    private BigDecimal total;
    private int clienteId;
}
