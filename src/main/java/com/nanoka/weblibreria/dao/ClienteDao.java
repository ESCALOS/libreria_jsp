package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class ClienteDao extends Conexion implements IDao<Cliente> {

    @Override
    public ArrayList<Cliente> obtenerTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM Cliente";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = Cliente.builder()
                        .id(resultSet.getInt("id"))
                        .dni(resultSet.getString("dni"))
                        .nombre(resultSet.getString("nombre"))
                        .email(resultSet.getString("email"))
                        .direccion(resultSet.getString("direccion"))
                        .build();

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return clientes;
    }

    @Override
    public RespuestaDto insertar(Cliente cliente) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO Cliente (dni, nombre, email, direccion) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, cliente.getDni());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getDireccion());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Cliente agregado");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar cliente: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(Cliente cliente) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE Cliente SET dni = ?, nombre = ?, email = ?, direccion = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, cliente.getDni());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getDireccion());
            statement.setInt(5, cliente.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Cliente actualizado");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e,respuesta);
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar cliente: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(Cliente cliente) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM Cliente WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, cliente.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Cliente eliminado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar cliente: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public Cliente obtenerClientePorId(int clienteId) {
        try {
            this.conectar();
            String query = "SELECT * FROM Cliente WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, clienteId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Cliente.builder()
                            .id(resultSet.getInt("id"))
                            .dni(resultSet.getString("dni"))
                            .nombre(resultSet.getString("nombre"))
                            .email(resultSet.getString("email"))
                            .direccion(resultSet.getString("direccion"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente por ID: " + e.getMessage());
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
            if (mensajeError.contains("dni")) {
                respuesta.setMensaje("El DNI ya está registrado en la base de datos.");
            } else if (mensajeError.contains("email")) {
                respuesta.setMensaje("El correo ya está registrado en la base de datos.");
            } else {
                respuesta.setMensaje("Resale al de arriba");
            }
        } else {
            respuesta.setMensaje("Error inesperado");
        }
    }
}