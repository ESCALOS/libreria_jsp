/* global Swal */
let contador = 0;
let preciosProducto = [];
let subtotales = [];
$(document).ready(function () {
    $("#modal").on("hidden.bs.modal", function () {
        $("#detallePrecios").empty();
        contador = 0;
    });

    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Registrar venta");
        $(".form-venta").css("display", "flex");
        $(".detalle").css("display", "none");
        $(".btnSave").css("display", "block");
        $("#accion").val("guardar");
        nuevoProducto();
    });

    $(document).on('click', '.btnVer', function () {
        llenarCampos($(this).closest('tr'), false);
        $(".modal-title").text("Ver detalle");
        $("#accion").val("ver");
        $(".form-venta").css("display", "none");
        $(".detalle").css("display", "block");
        $(".btnSave").css("display", "none");
        $.ajax({
            url: "/WebLibreria/api/v1",
            type: "GET",
            data: {ventaId: $("#id").val(), query: "detallePorVenta"},
            dataType: "json",
            success: function (data) {
                crearTablaDetalle(data);
            },
            error: function (error) {
                console.error("Error al obtener datos del producto:", error);
            }
        });
    });
    $(document).on('click', '.btnEliminar', function () {
        Swal.fire({
            title: "¿Seguro de eliminar?",
            text: "Está acción es irreversible",
            icon: "info",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Eliminar!",
            cancelButtonText: "Cancelar"
        }).then((result) => {
            if (result.isConfirmed) {
                let fila = $(this).closest('tr');
                $("#accion").val("eliminar");
                $("#id").val($(fila).find(".id").text());
                $("#formulario").submit();
            }
        });
    });

    $("#agregarProducto").on("click", function () {
        nuevoProducto();
    });
    cargarPreciosProducto();
});

function cargarPreciosProducto() {
    $.ajax({
        url: '/WebLibreria/api/v1',
        type: 'GET',
        data: {query: "preciosProducto"},
        dataType: 'json',
        success: function (data) {
            preciosProducto = data;
        },
        error: function (error) {
            console.error('Error al cargar los productos', error);
        }
    });
}

function obtenerDatos(fila) {
    return {
        id: $(fila).find(".id").text(),
        cliente: $(fila).find(".cliente").text(),
        fecha: $(fila).find(".fecha").text()
    };
}

function llenarCampos(fila) {
    let {id, cliente, fecha} = obtenerDatos(fila);
    $("#id").val(id);
    $("#cliente_detalle").text("Cliente: " + cliente);
    $("#fecha_detalle").text("Fecha: " + fecha);
}

function crearTablaDetalle( {detalles, total}) {
    $("#tabla-detalle").empty();
    let tablaDetalle = `
        <table class="table">
                <thead>
                    <tr>
                        <th class="bg-secondary-subtle">Producto</th>
                        <th class="bg-secondary-subtle">Cantidad</th>
                        <th class="bg-secondary-subtle">P. Unitario</th>
                        <th class="bg-secondary-subtle">Total</th>
                    </tr>
                </thead>
                <tbody>
            `;
            detalles.forEach((detalle) => {
                tablaDetalle+=   `
                    <tr>
                        <td>${detalle.producto.nombre}</td>
                        <td>${detalle.cantidad} ${detalle.nombreUnidad} (${detalle.cantidad*detalle.cantidadUnidad}u)</td>
                        <td>S/. ${detalle.precio.toFixed(2)}</td>
                        <td style="background-color: #ffe0b2;text-align:right">S/. ${detalle.subtotal.toFixed(2)}</td>
                    </tr>`;
            });
        tablaDetalle+=`
                </tbody>
                <tfoot>
                    <tr>
                        <td class="text-center fw-bold" colspan="3">Total</td>
                        <td style="background-color: #ffe0b2;text-align:right;font-weight:bold">S/. ${total.toFixed(2)}</td>
                    </tr>
                </tfoot>
            </table>
    `;
    $("#tabla-detalle").append(tablaDetalle);
}

function nuevoProducto() {
    contador++;
    $("#cantidadProductos").val(contador);
    let detallePrecioHtml = `    
                <div class="row">
                    <input type="hidden" name="precioId${contador}" id="precioId${contador}" class="form-control" />
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="producto${contador}" class="form-label">Producto</label>
                            <select name="producto${contador}" id="producto${contador}" class="form-select" onchange="calcularSubtotal(${contador})">
    `;
    preciosProducto.forEach((producto) => {
        detallePrecioHtml += `
                                <option value="${producto.id}">${producto.producto.nombre} (${producto.unidadMedida.nombre})</option>
        `;
    });

    detallePrecioHtml += `
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="cantidad${contador}" class="form-label">Cantidad</label>
                            <input type="number" name="cantidad${contador}" id="cantidad${contador}" class="form-control" min="0" oninput="calcularSubtotal(${contador})" value="1"/>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="subtotal${contador}" class="form-label">Subtotal</label>
                            <input type="text" name="subtotal${contador}" id="subtotal${contador}" class="form-control" readonly step="0.1" value="${preciosProducto[0].precio.toFixed(2)}"/>
                        </div>
                    </div>
                </div>`;
    $("#detallePrecios").append(detallePrecioHtml);
    subtotales.push(preciosProducto[0].precio);
    calcularSubtotal(1);
}

function calcularSubtotal(i) {
      let productoId = $('#producto'+i).val();
      let cantidad = $('#cantidad'+i).val();
      let producto = preciosProducto.find(item => item.id === parseInt(productoId));
      if (producto) {
        let subtotal = cantidad * producto.precio;
        $('#subtotal'+i).val(subtotal.toFixed(2));
        subtotales[i-1] = subtotal;
        sumarSubtotales();
      } else {
        $('#subtotal'+i).val('');
      }
    }
    
    function sumarSubtotales() {
        let sumaSubtotales = subtotales.reduce(function (acumulador, subtotal) {
            return acumulador + subtotal;
        }, 0);
        $("#montoTotal").val("S/ "+sumaSubtotales.toFixed(2));
    }