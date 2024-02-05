<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <c:forTokens items="${cabeceraTabla}" delims="," var="nombre">
                        <th>${nombre}</th>
                    </c:forTokens>
                </tr>
            </thead>
            <tbody>
                ${cuerpoTabla}
            </tbody>
        </table>
    </div>