package com.nanoka.weblibreria.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.nanoka.weblibreria.dao.PrecioProductoDao;
import com.nanoka.weblibreria.dao.UnidadMedidaDao;
import com.nanoka.weblibreria.dao.DetalleVentaDao;
import com.nanoka.weblibreria.dao.ClienteDao;
import com.nanoka.weblibreria.dao.VentaDao;
import com.nanoka.weblibreria.dto.DetalleVentaDto;
import com.nanoka.weblibreria.dto.PrecioProductoDto;
import com.nanoka.weblibreria.dto.VentaDto;
import com.nanoka.weblibreria.models.PrecioProducto;
import com.nanoka.weblibreria.models.Cliente;
import com.nanoka.weblibreria.models.UnidadMedida;
import com.nanoka.weblibreria.models.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ApiServlet", urlPatterns = {"/api/v1"})
public class ApiServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String query = request.getParameter("query");
        String json = "[]";
        if("preciosPorProducto".equalsIgnoreCase(query)) {
            json = obtenerPreciosPorProducto(request);
        }
        
        if("preciosProducto".equalsIgnoreCase(query)) {
            json = obtenerPreciosProducto();
        }
        
        if("unidadesMedida".equalsIgnoreCase(query)) {
            json = obtenerUnidadesMedida();
        }
        
        if("clientes".equalsIgnoreCase(query)) {
            json = obtenerClientes();
        }
        
        if("detallePorVenta".equalsIgnoreCase(query)) {
            json = obtenerDetallePorVenta(request);
        }

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private String obtenerDetallePorVenta(HttpServletRequest request) {
        int ventaId = Integer.parseInt(request.getParameter("ventaId"));
        ArrayList<DetalleVentaDto> detalleVentaDtos = new DetalleVentaDao().obtenerDetallePorVenta(ventaId);
        JsonObject data = new JsonObject();
        VentaDto ventaDto = new VentaDao().obtenerVentaPorId(ventaId);
        JsonArray detalles = new JsonArray();
        for(DetalleVentaDto detalleVentaDto : detalleVentaDtos) {
            detalles.add(crearDetalleVenta(detalleVentaDto));
        }
        data.add("detalles", detalles);
        JsonObject ventaJson = new JsonObject();
        ventaJson.add("detalles", detalles);
        ventaJson.addProperty("total", ventaDto.getTotal());
        return new Gson().toJson(ventaJson);
    }
    
    private String obtenerUnidadesMedida() {
        ArrayList<UnidadMedida> unidadesMedida = new UnidadMedidaDao().obtenerTodos();
        
        JsonArray unidades = new JsonArray();
        for(UnidadMedida um : unidadesMedida) {
            unidades.add(crearUM(UnidadMedida.builder().id(um.getId()).nombre(um.getNombre()).build()));
        }
        return new Gson().toJson(unidadesMedida);
    }
    
    private String obtenerClientes() {
        ArrayList<Cliente> clientes = new ClienteDao().obtenerTodos();
        
        JsonArray clientesJson = new JsonArray();
        for(Cliente cliente : clientes) {
            clientesJson.add(crearCliente(cliente));
        }
        return new Gson().toJson(clientesJson);
    }
    
    private String obtenerPreciosPorProducto(HttpServletRequest request) {
        int productId = Integer.parseInt(request.getParameter("productoId"));
        
        ArrayList<PrecioProducto> preciosProducto = new PrecioProductoDao().obtenerPorProducto(productId);
        
        JsonArray precios = new JsonArray();
        for(PrecioProducto pp : preciosProducto) {
            precios.add(crearPrecio(pp));
        }
        
        return new Gson().toJson(precios);
    }
    
    private String obtenerPreciosProducto() {
        ArrayList<PrecioProductoDto> preciosProducto = new PrecioProductoDao().obtenerTodosDto();
        
        JsonArray precios = new JsonArray();
        for(PrecioProductoDto pp : preciosProducto) {
            precios.add(crearPrecioDto(pp));
        }
        
        return new Gson().toJson(precios);
    }
    
    private JsonObject crearPrecio(PrecioProducto pp) {
        JsonObject precioJson = new JsonObject();
        precioJson.addProperty("id", pp.getId());
        precioJson.addProperty("unidadMedidaId", pp.getUnidadMedidaId());
        precioJson.addProperty("cantidad", pp.getCantidad());
        precioJson.addProperty("precio", pp.getPrecio());
        return precioJson;
    }
    
    private JsonObject crearPrecioDto(PrecioProductoDto pp) {
        JsonObject precioJson = new JsonObject();
        precioJson.addProperty("id", pp.getId());
        precioJson.add("producto", crearProducto(pp.getProducto()));
        precioJson.add("unidadMedida",crearUM(pp.getUnidadMedida()));
        precioJson.addProperty("cantidad", pp.getCantidad());
        precioJson.addProperty("precio", pp.getPrecio());
        return precioJson;
    }
    
    private JsonObject crearUM(UnidadMedida um) {
        JsonObject umJson = new JsonObject();
        umJson.addProperty("id", um.getId());
        umJson.addProperty("nombre", um.getNombre());
        return umJson;
    }
    
    private JsonObject crearCliente(Cliente cliente) {
        JsonObject clienteJson = new JsonObject();
        clienteJson.addProperty("id", cliente.getId());
        clienteJson.addProperty("nombre", cliente.getNombre());
        return clienteJson;
    }
    
    private JsonObject crearDetalleVenta(DetalleVentaDto detalleVentaDto) {
        JsonObject dvJson = new JsonObject();
        dvJson.add("producto", crearProducto(detalleVentaDto.getProducto()));
        dvJson.addProperty("cantidad", detalleVentaDto.getCantidad());
        dvJson.addProperty("cantidadUnidad", detalleVentaDto.getCantidadUnidad());
        dvJson.addProperty("nombreUnidad", detalleVentaDto.getNombreUnidad());
        dvJson.addProperty("precio", detalleVentaDto.getPrecio());
        dvJson.addProperty("subtotal", detalleVentaDto.getSubtotal());
        return dvJson;
    }
    
    private JsonObject crearProducto(Producto producto) {
        JsonObject productoJson = new JsonObject();
        productoJson.addProperty("id", producto.getId());
        productoJson.addProperty("nombre", producto.getNombre());
        return productoJson;
    }
}
