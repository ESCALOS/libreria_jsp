package com.nanoka.weblibreria.dto;

import com.nanoka.weblibreria.models.Cliente;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDto {
    private int id;
    private Timestamp fecha;
    private BigDecimal total;
    private Cliente cliente;
    private ArrayList<DetalleVentaDto> detallesVenta;
}
