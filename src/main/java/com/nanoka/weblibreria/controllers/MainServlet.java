package com.nanoka.weblibreria.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nanoka.weblibreria.dao.*;
import com.nanoka.weblibreria.models.*;
import com.nanoka.weblibreria.dto.*;
import com.nanoka.weblibreria.services.*;

@WebServlet(name = "MainServlet", urlPatterns = {"/main"})
public class MainServlet extends HttpServlet {

    private final CrudService crudService;
    private final ProductoService productoService;
    private final VentaService ventaService;

    public MainServlet() {
        this.crudService = new CrudService();
        this.productoService = new ProductoService();
        this.ventaService = new VentaService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page") == null ? "ventas" : (String) request.getParameter("page");
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
                case "ventas" :
                    data = new VentaDtoDao().obtenerTodos();
                    request.setAttribute("clientes", new ClienteDao().obtenerTodos());
                    break;
                case "grafico_productos_mas_vendidos":
                    GraficoDto<ProductoMasVendidosDto> resultado = new ProductosMasVendidosService().logica(request);
                    request.setAttribute("graficoDto", resultado);
                    data = resultado.getLista();
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
        String page = request.getParameter("page") == null ? "ventas" : (String) request.getParameter("page");
        @SuppressWarnings("rawtypes")
        ArrayList data;
        RespuestaDto respuesta = RespuestaDto.builder().mensaje("Ocurrio un error").icon("error").error(true).build();
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
                    crudService.crud(cliente, new ClienteDao(), request.getParameter("accion"),respuesta);
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
                    crudService.crud(proveedor, new ProveedorDao(), request.getParameter("accion"),respuesta);
                    data = new ProveedorDao().obtenerTodos();
                    break;
                case "categorias":
                    Categoria categoria = Categoria.builder()
                            .id(id)
                            .nombre(nombre)
                            .build();
                    crudService.crud(categoria, new CategoriaDao(), request.getParameter("accion"),respuesta);
                    data = new CategoriaDao().obtenerTodos();
                    break;
                case "unidades":
                    UnidadMedida unidadMedida = UnidadMedida.builder()
                            .id(id)
                            .nombre(nombre)
                            .build();
                    crudService.crud(unidadMedida, new UnidadMedidaDao(), request.getParameter("accion"),respuesta);
                    data = new UnidadMedidaDao().obtenerTodos();
                    break;
                case "productos":
                    productoService.logicaProducto(request, id, nombre, respuesta, crudService);
                    data = new ProductoDao().obtenerTodosDto();
                    request.setAttribute("categorias", new CategoriaDao().obtenerTodos());
                    break;
                case "ventas":
                    ventaService.ingresarVenta(request, respuesta, crudService);
                    data = new VentaDtoDao().obtenerTodos();
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

    
}
