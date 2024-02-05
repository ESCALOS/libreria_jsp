package com.nanoka.weblibreria.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    private int id;
    private String dni;
    private String nombre;
    private String email;
    private String direccion;
}
