/* global Swal */
let contador = 0;
let productos = [];
$(document).ready(function () {
    $("#modal").on("hidden.bs.modal", function () {
        $("#detalleProductos").empty();
        contador = 0;
    });

    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Registrar Movimiento");
        $("#accion").val("guardar");
        nuevoProducto();
    });

    $("#agregarProducto").on("click", function () {
        nuevoProducto();
    });
    cargarProductos();
});

function cargarProductos() {
    $.ajax({
        url: '/WebLibreria/api/v1',
        type: 'GET',
        data: {query: "productos"},
        dataType: 'json',
        success: function (data) {
            productos = data;
        },
        error: function (error) {
            console.error('Error al cargar las unidades de medida:', error);
        }
    });
}

function nuevoProducto() {
    contador++;
    $("#cantidadProductos").val(contador);
    let detalleProductoHtml = `    
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="producto${contador}" class="form-label">Producto N° ${contador}</label>
                            <select name="producto${contador}" id="producto${contador}" class="form-select">
    `;
    productos.forEach((producto) => {
        detalleProductoHtml += `
                                <option value="${producto.id}">${producto.nombre}</option>
        `;
    });

    detalleProductoHtml += `
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="cantidad${contador}" class="form-label">Cantidad</label>
                            <input type="number" name="cantidad${contador}" id="cantidad${contador}" class="form-control" value="1" min="1" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="monto${contador}" class="form-label">Monto</label>
                            <input type="number" name="monto${contador}" id="monto${contador}" class="form-control" value="1" min="0" />
                        </div>
                    </div>
                </div><hr>`;
    $("#detalleProductos").append(detalleProductoHtml);
}