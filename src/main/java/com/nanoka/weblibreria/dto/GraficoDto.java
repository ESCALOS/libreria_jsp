package com.nanoka.weblibreria.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraficoDto <T> {
    private String label;
    private String data;
    private int anio;
    private ArrayList<T> lista;
}
