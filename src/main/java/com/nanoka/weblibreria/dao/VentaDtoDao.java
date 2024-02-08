package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.DetalleVentaDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.dto.VentaDto;
import com.nanoka.weblibreria.models.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VentaDtoDao extends Conexion implements IDao<VentaDto>{

    @Override
    public ArrayList<VentaDto> obtenerTodos() {
        ArrayList<VentaDto> ventas = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT v.id, v.fecha, v.total, v.cliente_id, c.nombre as cliente, c.dni, c.email, c.direccion from Venta v INNER JOIN Cliente c ON c.id = v.cliente_id ORDER BY v.id DESC";
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
    public RespuestaDto insertar(VentaDto venta) {
        boolean error = true;
        String icon = "error";
        String mensaje;
        try {
            this.conectar();
            this.getCon().setAutoCommit(false);
            int ventaId = insertarVenta(this.getCon(), venta);
            for (DetalleVentaDto detalleVentaDto : venta.getDetallesVenta()) {
                insertarDetalleVenta(this.getCon(), detalleVentaDto,ventaId);
            }
            this.getCon().commit();
            error = false;
            icon = "success";
            mensaje = "Venta registrada";
        } catch (SQLException e) {
            System.out.println("Error al insertar: "+ e.getMessage());
            mensaje = "Error al insertar: "+ e.getMessage();
        }
        return RespuestaDto.builder().error(error).icon(icon).mensaje(mensaje).build();
    }

    @Override
    public RespuestaDto editar(VentaDto entidad) {
        return RespuestaDto.builder().error(false).icon("warning").mensaje("no soportado").build();
    }

    @Override
    public RespuestaDto eliminar(VentaDto entidad) {
        return RespuestaDto.builder().error(false).icon("warning").mensaje("no soportado").build();
    }
    private static int insertarVenta(Connection conexion, VentaDto venta) throws SQLException {
        String sql = "INSERT INTO Venta (total, cliente_id) VALUES (?, ?)";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBigDecimal(1, venta.getTotal());
            statement.setInt(2, venta.getCliente().getId());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("Error al insertar la venta, ninguna fila afectada.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Error al obtener el ID de la venta, ninguna clave generada.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private static void insertarDetalleVenta(Connection conexion, DetalleVentaDto detalleVenta, int id) throws SQLException {
        String sql = "INSERT INTO DetalleVenta (venta_id, producto_id, cantidad, cantidad_unidad, nombre_unidad, precio, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, detalleVenta.getProducto().getId());
            statement.setInt(3, detalleVenta.getCantidad());
            statement.setInt(4, detalleVenta.getCantidadUnidad());
            statement.setString(5, detalleVenta.getNombreUnidad());
            statement.setBigDecimal(6, detalleVenta.getPrecio());
            statement.setBigDecimal(7, detalleVenta.getSubtotal());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("Error al insertar el detalle de venta, ninguna fila afectada.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
