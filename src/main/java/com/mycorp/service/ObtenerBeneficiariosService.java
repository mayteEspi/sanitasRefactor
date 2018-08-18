package com.mycorp.service;

import java.util.List;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.ProductoPolizas;

import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosContratacionPlan;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;

public interface ObtenerBeneficiariosService {

	public Beneficiario[] obtenerBeneficiarios(DatosAlta oDatosAlta, List<ProductoPolizas> lProductos,
			List<BeneficiarioPolizas> lBeneficiarios, DatosContratacionPlan oDatosPlan);

}
