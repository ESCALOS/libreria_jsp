package com.nanoka.weblibreria.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nanoka.weblibreria.dao.*;
import com.nanoka.weblibreria.models.*;
import com.nanoka.weblibreria.dto.RespuestaDto;

@WebServlet(name = "MainServlet", urlPatterns = {"/main"})
public class MainServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page") == null ? "productos" : (String) request.getParameter("page");
        @SuppressWarnings("rawtypes")
        ArrayList data;
        try {
            switch (page) {
                case "clientes":
                    data = new ClienteDao().obtenerTodos();
                    break;
                case "proveedores":
                    data = new ProveedorDao().obtenerTodos();
                    break;
                case "categorias":
                    data = new CategoriaDao().obtenerTodos();
                    break;
                case "unidades":
                    data = new UnidadMedidaDao().obtenerTodos();
                    break;
                case "productos":
                    data = new ProductoDao().obtenerTodosDto();
                    request.setAttribute("categorias", new CategoriaDao().obtenerTodos());
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }
            request.setAttribute("data", data);
            request.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String page = request.getParameter("page") == null ? "productos" : (String) request.getParameter("page");
        @SuppressWarnings("rawtypes")
        ArrayList data;
        RespuestaDto respuesta;
        try {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
            switch (page) {
                case "clientes":
                    String dni = request.getParameter("dni");
                    String email = request.getParameter("email");
                    String direccion = request.getParameter("direccion");
                    Cliente cliente = Cliente.builder()
                            .id(id)
                            .dni(dni)
                            .nombre(nombre)
                            .email(email)
                            .direccion(direccion)
                            .build();
                    respuesta = crud(cliente, new ClienteDao(), request.getParameter("accion"));
                    data = new ClienteDao().obtenerTodos();
                    break;
                case "proveedores":
                    String ruc = request.getParameter("ruc");
                    String personaContacto = request.getParameter("persona_contacto");
                    String telefono = request.getParameter("telefono");
                    Proveedor proveedor = Proveedor.builder()
                            .id(id)
                            .ruc(ruc)
                            .nombre(nombre)
                            .personaContacto(personaContacto)
                            .telefono(telefono)
                            .build();
                    respuesta = crud(proveedor, new ProveedorDao(), request.getParameter("accion"));
                    data = new ProveedorDao().obtenerTodos();
                    break;
                case "categorias":
                    Categoria categoria = Categoria.builder()
                            .id(id)
                            .nombre(nombre)
                            .build();
                    respuesta = crud(categoria, new CategoriaDao(), request.getParameter("accion"));
                    data = new CategoriaDao().obtenerTodos();
                    break;
                case "unidades":
                    UnidadMedida unidadMedida = UnidadMedida.builder()
                            .id(id)
                            .nombre(nombre)
                            .build();
                    respuesta = crud(unidadMedida, new UnidadMedidaDao(), request.getParameter("accion"));
                    data = new UnidadMedidaDao().obtenerTodos();
                    break;
                case "productos":
                    int categoriaId = 0;
                    int stock = 0;
                    if(!"eliminar".equalsIgnoreCase(request.getParameter("accion"))) {
                        categoriaId = Integer.parseInt(request.getParameter("categoria"));
                        stock = Integer.parseInt(request.getParameter("stock"));
                    }
                    
                    Producto producto = Producto.builder()
                            .id(id)
                            .nombre(nombre)
                            .categoriaId(categoriaId)
                            .stock(stock)
                            .build();
                    respuesta = crud(producto, new ProductoDao(), request.getParameter("accion"));
                    data = new ProductoDao().obtenerTodosDto();
                    request.setAttribute("categorias", new CategoriaDao().obtenerTodos());
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }
            request.setAttribute("data", data);
            request.setAttribute("message", respuesta.getMensaje());
            request.setAttribute("icon", respuesta.getIcon());
            request.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private <T> RespuestaDto crud(T data, IDao<T> dao, String accion) {
        RespuestaDto respuesta = new RespuestaDto();
        try {
            switch (accion) {
                case "guardar":
                    respuesta = dao.insertar(data);
                    break;
                case "editar":
                    respuesta = dao.editar(data);
                    break;
                case "eliminar":
                    respuesta = dao.eliminar(data);
                    break;
                default:
                    respuesta.setMensaje("Datos corrompidos");
                    respuesta.setIcon("warning");
                    respuesta.setError(true);
            }
        } catch (Exception e) {
            respuesta.setMensaje(e.getMessage());
            respuesta.setIcon("error");
            respuesta.setError(true);
        }
        return respuesta;
    }
}
