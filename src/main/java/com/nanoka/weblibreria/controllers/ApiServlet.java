/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.nanoka.weblibreria.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.nanoka.weblibreria.dao.PrecioProductoDao;
import com.nanoka.weblibreria.dao.UnidadMedidaDao;
import com.nanoka.weblibreria.models.PrecioProducto;
import com.nanoka.weblibreria.models.UnidadMedida;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
        String json = "";
        if("preciosPorProducto".equalsIgnoreCase(query)) {
            json = obtenerPreciosPorProducto(request);
        }
        
        if("unidadesMedida".equalsIgnoreCase(query)) {
            json = obtenerUnidadesMedida();
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
    
    private String obtenerUnidadesMedida() {
        ArrayList<UnidadMedida> unidadesMedida = new UnidadMedidaDao().obtenerTodos();
        
        JsonArray unidades = new JsonArray();
        for(UnidadMedida um : unidadesMedida) {
            unidades.add(crearUM(um.getId(),um.getNombre()));
        }
        return new Gson().toJson(unidadesMedida);
    }
    
    private String obtenerPreciosPorProducto(HttpServletRequest request) {
        int productId = Integer.parseInt(request.getParameter("productoId"));
        
        ArrayList<PrecioProducto> preciosProducto = new PrecioProductoDao().obtenerPorProducto(productId);
        
        JsonArray precios = new JsonArray();
        for(PrecioProducto pp : preciosProducto) {
            precios.add(crearPrecio(pp.getId(), pp.getUnidadMedidaId(),pp.getCantidad(), pp.getPrecio()));
        }
        
        return new Gson().toJson(precios);
    }
    
    private JsonObject crearPrecio(int id,int um, int cantidad, BigDecimal precio) {
        JsonObject precioJson = new JsonObject();
        precioJson.addProperty("id", id);
        precioJson.addProperty("unidadMedidaId", um);
        precioJson.addProperty("cantidad", cantidad);
        precioJson.addProperty("precio", precio);
        return precioJson;
    }
    
    private JsonObject crearUM(int id, String nombre) {
        JsonObject umJson = new JsonObject();
        umJson.addProperty("id", id);
        umJson.addProperty("nombre", nombre);
        return umJson;
    }
}
