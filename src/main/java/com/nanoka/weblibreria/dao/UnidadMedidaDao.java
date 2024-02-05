package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.UnidadMedida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class UnidadMedidaDao extends Conexion implements IDao<UnidadMedida>{
    @Override
    public ArrayList<UnidadMedida> obtenerTodos() {
        ArrayList<UnidadMedida> unidadMedidas = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM UnidadMedida";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UnidadMedida unidadMedida = UnidadMedida.builder()
                        .id(resultSet.getInt("id"))
                        .nombre(resultSet.getString("nombre"))
                        .build();

                unidadMedidas.add(unidadMedida);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener unidades de medida: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return unidadMedidas;
    }

    @Override
    public RespuestaDto insertar(UnidadMedida unidadMedida) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO UnidadMedida (nombre) VALUES (?)";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, unidadMedida.getNombre());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Unidad de medida agregada");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al insertar unidad de medida: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar unidad de medida: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(UnidadMedida unidadMedida) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE UnidadMedida SET nombre = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, unidadMedida.getNombre());
            statement.setInt(2, unidadMedida.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Unidad de medida actualizada");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al actualizar unidad de medida: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar unidadMedida: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(UnidadMedida unidadMedida) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM UnidadMedida WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, unidadMedida.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Unidad de medida eliminada");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar unidad de medida: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar unidad de medida: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public UnidadMedida obtenerUnidadMedidaPorId(int unidadMedidaId) {
        try {
            this.conectar();
            String query = "SELECT * FROM UnidadMedida WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, unidadMedidaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return UnidadMedida.builder()
                            .id(resultSet.getInt("id"))
                            .nombre(resultSet.getString("nombre"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener unidad de medida por ID: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return null;
    }
    private void errorClaveUnica(SQLIntegrityConstraintViolationException e,RespuestaDto respuesta) {
        if (e.getErrorCode() == 1062) {
            String mensajeError = e.getMessage().toLowerCase();
            respuesta.setError(false);
            respuesta.setIcon("error");
            if (mensajeError.contains("nombre")) {
                respuesta.setMensaje("La unidad de medida ya está registrado en la base de datos.");
            } else {
                respuesta.setMensaje("Hubo un error, no me preguntes que no sé que pasó");
            }
        } else {
            respuesta.setMensaje("Error inesperado");
        }
    }
}
