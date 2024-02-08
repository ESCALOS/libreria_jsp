package com.nanoka.weblibreria.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaPorMesDto {
    private int mes;
    private int totalVentas;
    private BigDecimal montoTotal;
    
    public String labelMes() {
        String vec[] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Setiembre","Octubre","Noviembre","Diciembre" };
        return vec[mes-1];
    }
}
