package com.nanoka.weblibreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDto {
    private String mensaje;
    private String icon;
    private boolean error;
}
