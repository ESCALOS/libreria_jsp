/* global Swal */

function obtenerDatos(fila) {
    return {
        id: $(fila).find(".id").text(),
        dni: $(fila).find(".dni").text(),
        nombre: $(fila).find(".nombre").text(),
        email: $(fila).find(".email").text(),
        direccion: $(fila).find(".direccion").text()
    };
}

function llenarCampos(fila) {
    let {id,dni,nombre,email,direccion} = obtenerDatos(fila);
    $("#id").val(id);
    $("#dni").val(dni);
    $("#nombre").val(nombre);
    $("#email").val(email);
    $("#direccion").val(direccion);
}

$(document).ready(function () {    
    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Agregar Cliente");
        $("#accion").val("guardar");
    });
    
    $(document).on('click', '.btnEditar', function () {
        llenarCampos($(this).closest('tr'),false);
        $(".modal-title").text("Editar Cliente");
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