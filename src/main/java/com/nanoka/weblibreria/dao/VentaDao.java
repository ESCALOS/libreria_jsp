package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.VentaDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.Cliente;
import com.nanoka.weblibreria.models.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentaDao extends Conexion implements IDao<Venta> {

    @Override
    public ArrayList<Venta> obtenerTodos() {
        ArrayList<Venta> ventas = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM Venta";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Venta venta = Venta.builder()
                        .id(resultSet.getInt("id"))
                        .fecha(resultSet.getTimestamp("fecha"))
                        .total(resultSet.getBigDecimal("total"))
                        .clienteId(resultSet.getInt("cliente_id"))
                        .build();

                ventas.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ventas: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return ventas;
    }

    public ArrayList<VentaDto> obtenerTodosDto() {
        ArrayList<VentaDto> ventas = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT v.fecha, v.total, v.cliente_id, c.nombre as cliente, c.dni, c.email, c.direccion from Venta v INNER JOIN Cliente c ON c.id = v.cliente_id;";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = Cliente.builder()
                        .id(resultSet.getInt("cliente_id"))
                        .nombre(resultSet.getString("cliente"))
                        .dni(resultSet.getString("dni"))
                        .direccion(resultSet.getString("direccion"))
                        .build();

                VentaDto venta = VentaDto.builder()
                        .id(resultSet.getInt("id"))
                        .fecha(resultSet.getTimestamp("fecha"))
                        .total(resultSet.getBigDecimal("total"))
                        .cliente(cliente)
                        .build();

                ventas.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ventas: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return ventas;
    }

    @Override
    public RespuestaDto insertar(Venta venta) {
        //No puede haber venta sin detalle.
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setError(true);
        respuesta.setIcon("warning");
        respuesta.setMensaje("No soportado");
        return respuesta;
    }

    @Override
    public RespuestaDto editar(Venta venta) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setError(true);
        respuesta.setIcon("error");
        respuesta.setMensaje("No soportado");
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(Venta venta) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM Venta WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, venta.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado eliminar el venta");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Venta eliminado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
            respuesta.setError(true);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar venta: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public VentaDto obtenerVentaPorId(int ventaId) {
        try {
            this.conectar();
            String query = "SELECT v.fecha, v.total, v.cliente_id, c.nombre as cliente, c.dni, c.email, c.direccion from Venta v INNER JOIN Cliente c ON c.id = v.cliente_id WHERE v.id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, ventaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Cliente cliente = Cliente.builder()
                            .id(resultSet.getInt("cliente_id"))
                            .nombre(resultSet.getString("cliente"))
                            .dni(resultSet.getString("dni"))
                            .direccion(resultSet.getString("direccion"))
                            .build();

                    VentaDto venta = VentaDto.builder()
                            .id(resultSet.getInt("id"))
                            .fecha(resultSet.getTimestamp("fecha"))
                            .total(resultSet.getBigDecimal("total"))
                            .cliente(cliente)
                            .build();

                    return venta;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener venta por ID: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return null;
    }
}
