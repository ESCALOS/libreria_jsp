
package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.PrecioProductoDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.UnidadMedida;
import com.nanoka.weblibreria.models.Producto;
import com.nanoka.weblibreria.models.PrecioProducto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrecioProductoDao  extends Conexion implements IDao<PrecioProducto>{
    
    @Override
    public ArrayList<PrecioProducto> obtenerTodos() {
        ArrayList<PrecioProducto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM PreciosProducto";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PrecioProducto producto = PrecioProducto.builder()
                        .id(resultSet.getInt("id"))
                        .productoId(resultSet.getInt("producto_id"))
                        .unidadMedidaId(resultSet.getInt("unidad_medida_id"))
                        .cantidad(resultSet.getInt("cantidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .build();

                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return productos;
    }
    
    public ArrayList<PrecioProducto> obtenerPorProducto(int productoId) {
        ArrayList<PrecioProducto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM PreciosProducto where producto_id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, productoId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PrecioProducto producto = PrecioProducto.builder()
                        .id(resultSet.getInt("id"))
                        .productoId(resultSet.getInt("producto_id"))
                        .unidadMedidaId(resultSet.getInt("unidad_medida_id"))
                        .cantidad(resultSet.getInt("cantidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .build();

                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return productos;
    }

    public ArrayList<PrecioProductoDto> obtenerTodosDto() {
        ArrayList<PrecioProductoDto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT pp.id, pp.producto_id, p.nombre as nombre_producto, pp.unidad_medida_id, um.nombre as nombre_unidad, pp.cantidad, pp.precio from PreciosProducto pp INNER JOIN Producto p ON p.id = pp.producto_id INNER JOIN UnidadMedida um ON um.id = pp.unidad_medida_id";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = Producto.builder()
                        .id(resultSet.getInt("producto_id"))
                        .nombre(resultSet.getString("nombre_producto"))
                        .build();
                
                UnidadMedida unidadMedida = UnidadMedida.builder()
                        .id(resultSet.getInt("unidad_medida_id"))
                        .nombre(resultSet.getString("nombre_unidad"))
                        .build();

                PrecioProductoDto precioProductoDto = PrecioProductoDto.builder()
                        .id(resultSet.getInt("id"))
                        .producto(producto)
                        .unidadMedida(unidadMedida)
                        .cantidad(resultSet.getInt("cantidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .build();

                productos.add(precioProductoDto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return productos;
    }

    public ArrayList<PrecioProductoDto> obtenerPorProductoDto(int productoId) {
        ArrayList<PrecioProductoDto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT pp.producto_id, p.nombre as nombre_producto, pp.unidad_medida_id, um.nombre as nombre_um, pp.id, pp.cantidad, pp.precio from PreciosProducto pp INNER JOIN Producto p ON pp.producto_id = p.id INNER JOIN UnidadMedida um ON pp.unidad_medida_id = um.id WHERE pp.producto_id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, productoId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = Producto.builder()
                        .id(resultSet.getInt("producto_id"))
                        .nombre(resultSet.getString("nombre_producto"))
                        .build();
                
                UnidadMedida unidadMedida = UnidadMedida.builder()
                        .id(resultSet.getInt("unidad_medida_id"))
                        .nombre(resultSet.getString("nombre_um"))
                        .build();

                PrecioProductoDto precioProductoDto = PrecioProductoDto.builder()
                        .id(resultSet.getInt("id"))
                        .producto(producto)
                        .unidadMedida(unidadMedida)
                        .cantidad(resultSet.getInt("cantidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .build();

                productos.add(precioProductoDto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return productos;
    }
    
    @Override
    public RespuestaDto insertar(PrecioProducto precioProducto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "INSERT INTO PreciosProducto (producto_id, unidad_medida_id, cantidad, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, precioProducto.getProductoId());
            statement.setInt(2, precioProducto.getUnidadMedidaId());
            statement.setInt(3, precioProducto.getCantidad());
            statement.setBigDecimal(4, precioProducto.getPrecio());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado al insertar al producto, no hubo ninguna fila afectada");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("PrecioProducto agregado");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(PrecioProducto precioProducto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE PreciosProducto SET producto_id = ?, unidad_medida_id = ?, cantidad = ?, precio = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, precioProducto.getProductoId());
            statement.setInt(2, precioProducto.getUnidadMedidaId());
            statement.setInt(3, precioProducto.getCantidad());
            statement.setBigDecimal(4, precioProducto.getPrecio());
            statement.setInt(5, precioProducto.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado al editar, no fue afectada ninguna fila");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("PrecioProducto actualizado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al actualizar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(PrecioProducto producto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM PreciosProducto WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, producto.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado al eliminar, solo eso me esta fallando ptm");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("PrecioProducto eliminado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            respuesta.setError(false);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public PrecioProductoDto obtenerPrecioProductoPorId(int precioProductoId) {
        try {
            this.conectar();
            String query = "SELECT pp.producto_id, p.nombre as nombre_producto, pp.unidad_medida_id, um.nombre as nombre_um, pp.id, pp.cantidad, pp.precio from PreciosProducto pp INNER JOIN Producto p ON pp.producto_id = p.id INNER JOIN UnidadMedida um ON pp.unidad_medida_id = um.id WHERE pp.id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, precioProductoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Producto producto = Producto.builder()
                        .id(resultSet.getInt("producto_id"))
                        .nombre(resultSet.getString("nombre_producto"))
                        .build();
                
                UnidadMedida unidadMedida = UnidadMedida.builder()
                        .id(resultSet.getInt("unidad_medida_id"))
                        .nombre(resultSet.getString("nombre_um"))
                        .build();

                PrecioProductoDto precioProductoDto = PrecioProductoDto.builder()
                        .id(resultSet.getInt("id"))
                        .producto(producto)
                        .unidadMedida(unidadMedida)
                        .cantidad(resultSet.getInt("cantidad"))
                        .precio(resultSet.getBigDecimal("precio"))
                        .build();
                    
                    return precioProductoDto;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto por ID: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return null;
    }
}
