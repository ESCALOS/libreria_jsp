/* global Swal */

function obtenerDatos(fila) {
    return {
        id: $(fila).find(".id").text(),
        nombre: $(fila).find(".nombre").text()
    };
}

function llenarCampos(fila) {
    let {id,nombre} = obtenerDatos(fila);
    $("#id").val(id);
    $("#nombre").val(nombre);
}

$(document).ready(function () {    
    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Agregar Categoría");
        $("#accion").val("guardar");
    });
    
    $(document).on('click', '.btnEditar', function () {
        llenarCampos($(this).closest('tr'),false);
        $(".modal-title").text("Editar Categoría");
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
                $("#formulario").submit();
            }
        });
    });
});