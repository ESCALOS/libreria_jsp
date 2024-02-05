/* global Swal */

function obtenerDatos(fila) {
    return {
        id: $(fila).find(".id").text(),
        ruc: $(fila).find(".ruc").text(),
        nombre: $(fila).find(".nombre").text(),
        personaContacto: $(fila).find(".persona_contacto").text(),
        telefono: $(fila).find(".telefono").text()
    };
}

function llenarCampos(fila) {
    let {id,ruc,nombre,personaContacto,telefono} = obtenerDatos(fila);
    $("#id").val(id);
    $("#ruc").val(ruc);
    $("#nombre").val(nombre);
    $("#persona_contacto").val(personaContacto);
    $("#telefono").val(telefono);
}

$(document).ready(function () {    
    $(document).on('click', '.btnGuardar', function () {
        $('form')[0].reset();
        $(".modal-title").text("Agregar Proveedor");
        $("#accion").val("guardar");
    });
    
    $(document).on('click', '.btnEditar', function () {
        llenarCampos($(this).closest('tr'),false);
        $(".modal-title").text("Editar Proveedor");
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