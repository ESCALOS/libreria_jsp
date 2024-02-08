package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.MovimientoProductoDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.MovimientoProducto;
import com.nanoka.weblibreria.models.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovimientoProductoDao extends Conexion implements IDao<MovimientoProducto> {
    @Override
    public ArrayList<MovimientoProducto> obtenerTodos() {
        ArrayList<MovimientoProducto> movimientosProducto = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM MovimientoProducto";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MovimientoProducto movimientoProducto = MovimientoProducto.builder()
                        .id(resultSet.getInt("id"))
                        .productoId(resultSet.getInt("producto_id"))
                        .cantidad(resultSet.getInt("cantidad"))
                        .monto(resultSet.getBigDecimal("monto"))
                        .razon(resultSet.getString("razon"))
                        .fecha(resultSet.getTimestamp("fecha"))
                        .tipo(resultSet.getString("tipo"))
                        .build();

                movimientosProducto.add(movimientoProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener movimientoProductos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return movimientosProducto;
    }

    public ArrayList<MovimientoProductoDto> obtenerTodosDto() {
        ArrayList<MovimientoProductoDto> movimientosProducto = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT mp.id, mp.producto_id,p.nombre as producto, mp.cantidad,mp.monto,mp.razon,mp.fecha,mp.tipo FROM MovimientosProducto mp INNER JOIN Producto p ON p.id = mp.producto_id ORDER BY mp.fecha DESC";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = Producto.builder()
                        .id(resultSet.getInt("producto_id"))
                        .nombre(resultSet.getString("producto"))
                        .build();

                MovimientoProductoDto movimientoProducto = MovimientoProductoDto.builder()
                        .id(resultSet.getInt("id"))
                        .producto(producto)
                        .cantidad(resultSet.getInt("cantidad"))
                        .monto(resultSet.getBigDecimal("monto"))
                        .razon(resultSet.getString("razon"))
                        .fecha(resultSet.getTimestamp("fecha"))
                        .tipo(resultSet.getString("tipo"))
                        .build();

                movimientosProducto.add(movimientoProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los movimientos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return movimientosProducto;
    }

    @Override
    public RespuestaDto insertar(MovimientoProducto movimientoProducto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO MovimientosProducto(producto_id,cantidad,monto,razon,tipo) VALUES (?,?,?,?,?);";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, movimientoProducto.getProductoId());
            statement.setInt(2, movimientoProducto.getCantidad());
            statement.setBigDecimal(3, movimientoProducto.getMonto());
            statement.setString(4, movimientoProducto.getRazon());
            statement.setString(5, movimientoProducto.getTipo());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Movimiento registrado");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el movimiento: " + e.getMessage());
            respuesta.setError(true);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al registrar el movimiento: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(MovimientoProducto movimientoProducto) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setError(true);
        respuesta.setMensaje("No soportado");
        respuesta.setIcon("warning");
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(MovimientoProducto movimientoProducto) {
        RespuestaDto respuesta = new RespuestaDto();
        respuesta.setError(true);
        respuesta.setMensaje("No soportado");
        respuesta.setIcon("warning");
        return respuesta;
    }
}
