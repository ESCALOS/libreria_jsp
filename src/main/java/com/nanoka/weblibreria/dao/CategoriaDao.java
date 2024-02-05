package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class CategoriaDao extends Conexion implements IDao<Categoria>{

    @Override
    public ArrayList<Categoria> obtenerTodos() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM Categoria";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Categoria categoria = Categoria.builder()
                        .id(resultSet.getInt("id"))
                        .nombre(resultSet.getString("nombre"))
                        .build();

                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener categorias: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return categorias;
    }

    @Override
    public RespuestaDto insertar(Categoria categoria) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO Categoria (nombre) VALUES (?)";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, categoria.getNombre());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Categoria agregada");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al insertar categoria: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar categoria: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(Categoria categoria) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE Categoria SET nombre = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, categoria.getNombre());
            statement.setInt(2, categoria.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Categoria actualizada");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar categoria: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(Categoria categoria) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM Categoria WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, categoria.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Categoria eliminada");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar categoria: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public Categoria obtenerCategoriaPorId(int categoriaId) {
        try {
            this.conectar();
            String query = "SELECT * FROM Categoria WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, categoriaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Categoria.builder()
                            .id(resultSet.getInt("id"))
                            .nombre(resultSet.getString("nombre"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener categoria por ID: " + e.getMessage());
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
                respuesta.setMensaje("La categoría ya está registrado en la base de datos.");
            } else {
                respuesta.setMensaje("Resale al de arriba");
            }
        } else {
            respuesta.setMensaje("Error inesperado");
        }
    }
}
