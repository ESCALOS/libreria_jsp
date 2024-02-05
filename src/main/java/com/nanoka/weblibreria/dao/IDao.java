package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.dto.RespuestaDto;
import java.util.ArrayList;
/**
 * Interfaz para encapsular logica general de en la BD
 * @param <T> Modelo a operar
 */
public interface IDao<T> {
    ArrayList<T> obtenerTodos();
    /**
     * Inserta un nuevo registro en la entidad
     * @param entidad Modelo a trabajar
     * @return Cantidad de filas afectadas, si es 0 hubo un error
     */
    RespuestaDto insertar(T entidad);
    /**
     * Editar un registro en la entidad
     * @param entidad Modelo a trabajar
     * @return Cantidad de filas afectadas, si es 0 hubo un error
     */
    RespuestaDto editar(T entidad);
    /**
     * Elimina un registro en la entidad
     * @param entidad Modelo a trabajar
     * @return Cantidad de filas afectadas, si es 0 hubo un error
     */
    RespuestaDto eliminar(T entidad);
}
