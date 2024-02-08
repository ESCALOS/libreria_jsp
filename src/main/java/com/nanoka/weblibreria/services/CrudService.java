
package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dao.IDao;
import com.nanoka.weblibreria.dto.RespuestaDto;

public class CrudService {
    public <T> void crud(T data, IDao<T> dao, String accion, RespuestaDto respuesta) {
        try {
            switch (accion) {
                case "guardar":
                    RespuestaDto respuestaInsertar = dao.insertar(data);
                    respuesta.setMensaje(respuestaInsertar.getMensaje());
                    respuesta.setIcon(respuestaInsertar.getIcon());
                    respuesta.setError(respuestaInsertar.isError());
                    break;
                case "editar":
                    RespuestaDto respuestaEditar= dao.editar(data);
                    respuesta.setMensaje(respuestaEditar.getMensaje());
                    respuesta.setIcon(respuestaEditar.getIcon());
                    respuesta.setError(respuestaEditar.isError());
                    break;
                case "eliminar":
                    RespuestaDto respuestaEliminar = dao.eliminar(data);
                    respuesta.setMensaje(respuestaEliminar.getMensaje());
                    respuesta.setIcon(respuestaEliminar.getIcon());
                    respuesta.setError(respuestaEliminar.isError());
                    break;
                default:
                    respuesta.setMensaje("Datos corrompidos");
                    respuesta.setIcon("warning");
                    respuesta.setError(true);
            }
        } catch (Exception e) {
            respuesta.setMensaje(e.getMessage());
            respuesta.setIcon("error");
            respuesta.setError(true);
        }
    }
}
