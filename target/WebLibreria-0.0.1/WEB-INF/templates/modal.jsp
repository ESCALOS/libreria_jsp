<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="modal"  tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="formulario" action="${pageContext.servletContext.contextPath}/main" role="form" method="POST">
                <input type="hidden" id="accion" name="accion" value="guardar" />
                <input type="hidden" id="id" name="id" value="0" />
                <input type="hidden" id="page" name="page" value="${empty param.page ? 'ventas' : param.page}" />
                <div class="modal-header">
                    <h5 class="modal-title"></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body modal-dialog-scrollable mx-2">
                    ${cuerpoModal}
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>