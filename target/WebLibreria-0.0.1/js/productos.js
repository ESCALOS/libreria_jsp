/* global Swal */

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

$(document).ready(function () {    
    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Agregar Producto");
        $("#accion").val("guardar");
    });
    
    $(document).on('click', '.btnEditar', function () {
        llenarCampos($(this).closest('tr'),false);
        $(".modal-title").text("Editar Producto");
        $("#accion").val("editar");
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
});