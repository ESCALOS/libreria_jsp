package com.nanoka.weblibreria.dao;

import com.nanoka.weblibreria.conexion.Conexion;
import com.nanoka.weblibreria.dto.ProductoDto;
import com.nanoka.weblibreria.dto.RespuestaDto;
import com.nanoka.weblibreria.models.Producto;
import com.nanoka.weblibreria.models.Categoria;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ArrayList;

public class ProductoDao extends Conexion implements IDao<Producto> {

    @Override
    public ArrayList<Producto> obtenerTodos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT * FROM Producto";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Producto producto = Producto.builder()
                        .id(resultSet.getInt("id"))
                        .nombre(resultSet.getString("nombre"))
                        .categoriaId(resultSet.getInt("categoria_id"))
                        .stock(resultSet.getInt("stock"))
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

    public ArrayList<ProductoDto> obtenerTodosDto() {
        ArrayList<ProductoDto> productos = new ArrayList<>();
        try {
            this.conectar();
            String query = "SELECT p.id, p.nombre, c.id as categoria_id, c.nombre as categoria_nombre, p.stock from Producto p INNER JOIN Categoria c ON p.categoria_id = c.id;";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Categoria categoria = Categoria.builder()
                        .id(resultSet.getInt("categoria_id"))
                        .nombre(resultSet.getString("categoria_nombre"))
                        .build();

                ProductoDto producto = ProductoDto.builder()
                        .id(resultSet.getInt("id"))
                        .nombre(resultSet.getString("nombre"))
                        .categoria(categoria)
                        .stock(resultSet.getInt("stock"))
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

    @Override
    public RespuestaDto insertar(Producto producto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "{CALL InsertarProducto(?, ?, ?, ?)}";
            CallableStatement callableStatement = this.getCon().prepareCall(query);
            callableStatement.setString(1, producto.getNombre());
            callableStatement.setInt(2, producto.getCategoriaId());
            callableStatement.setInt(3, producto.getStock());
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.execute();
            int idGenerado = callableStatement.getInt(4);
            if (idGenerado == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado al insertar al producto");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje(Integer.toString(idGenerado));
                System.out.println(respuesta.getMensaje());
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e, respuesta);
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            respuesta.setError(true);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto editar(Producto producto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "UPDATE Producto SET nombre = ?, categoria_id = ?, stock = ? WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setString(1, producto.getNombre());
            statement.setInt(2, producto.getCategoriaId());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado al editar el producto");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Producto actualizado");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            errorClaveUnica(e, respuesta);
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            respuesta.setError(true);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al actualizar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    @Override
    public RespuestaDto eliminar(Producto producto) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            this.conectar();
            String query = "DELETE FROM Producto WHERE id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, producto.getId());
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas == 0) {
                respuesta.setError(true);
                respuesta.setIcon("warning");
                respuesta.setMensaje("Error inesperado eliminar el producto");
            } else {
                respuesta.setError(false);
                respuesta.setIcon("success");
                respuesta.setMensaje("Producto eliminado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            respuesta.setError(true);
            respuesta.setIcon("error");
            respuesta.setMensaje("Error al insertar producto: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return respuesta;
    }

    public ProductoDto obtenerProductoPorId(int productoId) {
        try {
            this.conectar();
            String query = "SELECT p.id, p.nombre, c.id as categoria_id, c.nombre as categoria_nombre, p.stock from Producto p INNER JOIN Categoria c ON p.categoria_id = c.id WHERE p.id = ?";
            PreparedStatement statement = this.getCon().prepareStatement(query);
            statement.setInt(1, productoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Categoria categoria = Categoria.builder()
                            .id(resultSet.getInt("categoria_id"))
                            .nombre(resultSet.getString("categoria_nombre"))
                            .build();

                    ProductoDto producto = ProductoDto.builder()
                            .id(resultSet.getInt("id"))
                            .nombre(resultSet.getString("nombre"))
                            .categoria(categoria)
                            .stock(resultSet.getInt("stock"))
                            .build();
                    
                    return producto;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto por ID: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return null;
    }

    private void errorClaveUnica(SQLIntegrityConstraintViolationException e, RespuestaDto respuesta) {
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
            respuesta.setMensaje("Error inesperado por que está repetido");
        }
    }
}
