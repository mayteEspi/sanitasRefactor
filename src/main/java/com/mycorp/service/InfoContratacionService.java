package com.mycorp.service;

import java.util.List;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.FrecuenciaEnum;
import com.mycorp.soporte.ProductoPolizas;

import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.InfoContratacion;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;

public interface InfoContratacionService {

	public InfoContratacion obtenerInfoContratacion(DatosAlta oDatosAlta, List<BeneficiarioPolizas> lBeneficiarios,
			List<ProductoPolizas> lProductos, FrecuenciaEnum frecuencia, Integer tipoOperacion);

}
