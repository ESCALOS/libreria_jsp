/* global Swal */
    let contador = 0;
    let unidadesMedida = [];
$(document).ready(function () {
    $("#modal").on("hidden.bs.modal", function () {
        $("#detallePrecios").empty();
        contador = 0;
    });
    
    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Agregar Producto");
        $("#accion").val("guardar");
    });
    
    $(document).on('click', '.btnEditar', function () {
        llenarCampos($(this).closest('tr'),false);
        $(".modal-title").text("Editar Producto");
        $("#accion").val("editar");
        $.ajax({
            url: "/WebLibreria/api/v1",
            type: "GET",
            data: { productoId: $("#id").val(), query: "preciosPorProducto" },
            dataType: "json",
            success: function (data) {
                $("#detallePrecios").empty();
                for (let detalle of data) {
                    nuevoPrecio(detalle.id,detalle.unidadMedidaId,detalle.cantidad,detalle.precio);
                }
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
                $("#stock").val("0");
                $("#formulario").submit();
            }
        });
    });
    
    $("#agregarPrecio").on("click", function () {
        nuevoPrecio(0,1,0,0);
    });
    cargarUnidadesMedida();
});

function cargarUnidadesMedida() {
    $.ajax({
        url: '/WebLibreria/api/v1',
        type: 'GET',
        data: { query: "unidadesMedida" },
        dataType: 'json',
        success: function (data) {
            unidadesMedida = data;
        },
        error: function (error) {
            console.error('Error al cargar las unidades de medida:', error);
        }
    });
}

function obtenerDatos(fila) {
    return {
        id: $(fila).find(".id").text(),
        nombre: $(fila).find(".nombre").text(),
        categoria: $(fila).find(".categoria").text(),
        stock: $(fila).find(".stock").text()
    };
}

function llenarCampos(fila) {
    let {id,nombre,categoria,stock} = obtenerDatos(fila);
    $("#id").val(id);
    $("#nombre").val(nombre);
    $("#categoria option:contains(" + categoria + ")").prop('selected', true);
    $("#stock").val(stock);
}

function nuevoPrecio(id,unidadMedidaId,cantidad,precio) {
    contador++;
    $("#cantidadPrecios").val(contador);
    let detallePrecioHtml = `    
                <div class="row">
                    <input type="hidden" name="precioId${contador}" id="precioId${contador}" class="form-control" value="${id}" />
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="unidadMedida${contador}" class="form-label">Unidad de Medida</label>
                            <select name="unidadMedida${contador}" id="unidadMedida${contador}" class="form-select">
    `;
    unidadesMedida.forEach((um) => {
        detallePrecioHtml += `
                                <option value="${um.id}" ${um.id===unidadMedidaId ? 'selected' : ''}>${um.nombre}</option>
        `;
    });
    
    detallePrecioHtml += `
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="cantidad${contador}" class="form-label">Cantidad</label>
                            <input type="number" name="cantidad${contador}" id="cantidad${contador}" class="form-control" value="${cantidad}" />
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="form-group">
                            <label for="precio${contador}" class="form-label">Precio</label>
                            <input type="number" name="precio${contador}" id="precio${contador}" class="form-control" value="${precio}" step="0.1"/>
                        </div>
                    </div>
                </div>`;
    $("#detallePrecios").append(detallePrecioHtml);
}