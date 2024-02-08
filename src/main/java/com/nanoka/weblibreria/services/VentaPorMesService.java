package com.nanoka.weblibreria.services;

import com.nanoka.weblibreria.dao.VentaDao;
import com.nanoka.weblibreria.dto.GraficoDto;
import com.nanoka.weblibreria.dto.VentaPorMesDto;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class VentaPorMesService {
    public GraficoDto<VentaPorMesDto> logica(HttpServletRequest request) {
        int anio = LocalDate.now().getYear();
        String label = "", data = "";
        if (request.getParameter("anio") != null) {
            anio = Integer.parseInt(request.getParameter("anio"));
        }
        ArrayList<VentaPorMesDto> ventasPorMesDto = new VentaDao().obtenerVentasPorMes(anio);
        for (VentaPorMesDto pmv : ventasPorMesDto) {
            label += "'" + pmv.labelMes()+ "',";
            data += pmv.getMontoTotal()+ ",";
        }
        if (data.length() > 0) {
            label = label.substring(0, label.length() - 1);
            data = data.substring(0, data.length() - 1);
        }
        return new GraficoDto<>(label,data,anio,ventasPorMesDto);
    }
}
