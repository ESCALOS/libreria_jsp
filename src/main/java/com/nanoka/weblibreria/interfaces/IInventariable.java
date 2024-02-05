package com.nanoka.weblibreria.interfaces;

public interface IInventariable {
    /**
     * Ingresa productos al almacén
     * @param cantidad Cantidad ingresada
     */
    void ingresarStock(int cantidad);
    /**
     * Retira productos del almacén
     * @param cantidad Cantidad retirada
     */
    void retirarStock(int cantidad);
}
