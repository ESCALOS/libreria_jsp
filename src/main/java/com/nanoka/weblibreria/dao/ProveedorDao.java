package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.Proveedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class ProveedorDao extends Conexion implements IDao<Proveedor> {
    @Override
    public ArrayList<Proveedor> obtenerTodos() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM Proveedor";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Proveedor proveedor = Proveedor.builder()
                        .id(resultSet.getInt("id"))
                        .ruc(resultSet.getString("ruc"))
                        .nombre(resultSet.getString("nombre"))
                        .personaContacto(resultSet.getString("persona_contacto"))
                        .telefono(resultSet.getString("telefono"))
                        .build();

                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener proveedores: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return proveedores;
    }

    @Override
    public RespuestaDto insertar(Proveedor proveedor) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO Proveedor (ruc, nombre, persona_contacto, telefono) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, proveedor.getRuc());
            statement.setString(2, proveedor.getNombre());
            statement.setString(3, proveedor.getPersonaContacto());
            statement.setString(4, proveedor.getTelefono());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado en el controlador del proveedor");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Proveedor agregado");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al insertar proveedor: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar proveedor: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(Proveedor proveedor) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE Proveedor SET ruc = ?, nombre = ?, persona_contacto = ?, telefono = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, proveedor.getRuc());
            statement.setString(2, proveedor.getNombre());
            statement.setString(3, proveedor.getPersonaContacto());
            statement.setString(4, proveedor.getTelefono());
            statement.setInt(5, proveedor.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Proveedor actualizado");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar proveedor: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(Proveedor proveedor) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM Proveedor WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, proveedor.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Proveedor eliminado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar proveedor: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public Proveedor obtenerProveedorPorId(int proveedorId) {
        try {
            this.conectar();
            String query = "SELECT * FROM Proveedor WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, proveedorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Proveedor.builder()
                            .id(resultSet.getInt("id"))
                            .ruc(resultSet.getString("ruc"))
                            .nombre(resultSet.getString("nombre"))
                            .personaContacto(resultSet.getString("personaContacto"))
                            .telefono(resultSet.getString("telefono"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener proveedor por ID: " + e.getMessage());
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
            if (mensajeError.contains("ruc")) {
                respuesta.setMensaje("El RUC ya está registrado en la base de datos.");
            } else {
                respuesta.setMensaje("No me preguntes porque no sé");
            }
        } else {
            respuesta.setMensaje(e.getMessage());
        }
    }
}
