package com.nanoka.weblibreria.models;

import com.nanoka.weblibreria.interfaces.IInventariable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements IInventariable{
    private int id;
    private String nombre;
    private int categoriaId;
    private int stock;

    @Override
    public void ingresarStock(int cantidad) {
        this.stock+=cantidad;
    }
    
    @Override
    public void retirarStock(int cantidad) {
       this.stock-=cantidad;
    }
}
