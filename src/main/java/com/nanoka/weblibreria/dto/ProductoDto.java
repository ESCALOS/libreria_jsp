package com.nanoka.weblibreria.dto;

import com.nanoka.weblibreria.models.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDto {
    private int id;
    private String nombre;
    private Categoria categoria;
    private int stock;
}
