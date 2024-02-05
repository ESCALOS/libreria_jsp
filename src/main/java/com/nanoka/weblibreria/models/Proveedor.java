package com.nanoka.weblibreria.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proveedor {
    private int id;
    private String ruc;
    private String nombre;
    private String personaContacto;
    private String telefono;
}
