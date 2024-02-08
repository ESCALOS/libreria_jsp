package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.dto.DetalleVentaDto;
import com.nanoka.weblibreria.models.DetalleVenta;
import com.nanoka.weblibreria.models.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DetalleVentaDao extends Conexion implements IDao<DetalleVenta> {

    @Override
    public ArrayList<DetalleVenta> obtenerTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RespuestaDto insertar(DetalleVenta entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RespuestaDto editar(DetalleVenta entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public RespuestaDto eliminar(DetalleVenta entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<DetalleVentaDto> obtenerDetallePorVenta(int detalleId) {
        ArrayList<DetalleVentaDto> detalleVentaDtos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT dv.id, dv.producto_id, p.nombre as nombre_producto, dv.cantidad, dv.cantidad_unidad, dv.nombre_unidad, dv.precio, dv.subtotal FROM DetalleVenta dv INNER JOIN Producto p ON p.id = dv.producto_id WHERE dv.venta_id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, detalleId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = Producto.builder()
                        .id(resultSet.getInt("producto_id"))
                        .nombre(resultSet.getString("nombre_producto"))
                        .build();

                DetalleVentaDto detalleVentaDto = DetalleVentaDto.builder()
                        .id(resultSet.getInt("id"))
                        .producto(producto)
                        .cantidad(resultSet.getInt("cantidad"))
                        .cantidadUnidad(resultSet.getInt("cantidad_unidad"))
                        .nombreUnidad(resultSet.getString("nombre_unidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .subtotal(resultSet.getBigDecimal("subtotal"))
                        .build();
                detalleVentaDtos.add(detalleVentaDto);
            }
                
        } catch (SQLException e) {
            System.out.println("Error al obtener los detalles de venta: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return detalleVentaDtos;
    }
}
