package com.nanoka.weblibreria.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    private int id;
    private String nombre;
    private int categoriaId;
    private int stock;
}
