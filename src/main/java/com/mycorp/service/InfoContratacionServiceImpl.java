package com.mycorp.service;

import com.mycorp.soporte.*;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.InfoContratacion;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;
import wscontratacion.contratacion.fuentes.parametros.DatosDomicilio;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class InfoContratacionServiceImpl implements InfoContratacionService {

	@Override
	public InfoContratacion obtenerInfoContratacion(final DatosAlta oDatosAlta,
			final List<BeneficiarioPolizas> lBeneficiarios, final List<ProductoPolizas> lProductos,
			final FrecuenciaEnum frecuencia, final Integer tipoOperacion) {
		final InfoContratacion infoContratacion = new InfoContratacion();

		infoContratacion.setCodigoPostal(
				String.format("%05d", ((DatosDomicilio) oDatosAlta.getDomicilios().get(0)).getCodPostal()));
		infoContratacion.setFechaEfecto(oDatosAlta.getFAlta());
		infoContratacion.setFrecuenciaPago(frecuencia.getValor());

		final Long idPoliza = oDatosAlta.getIdPoliza();
		// Si disponemos de la póliza se trata de una inclusión (productos o
		// beneficiarios)
		// o un alta en un póliza colectiva
		if (idPoliza != null && idPoliza != 0L) {
			rellenarDatosPoliza(oDatosAlta, tipoOperacion, infoContratacion, idPoliza);
		}
		if (oDatosAlta.getIdMediador() != null) {
			infoContratacion.setIdMediador(oDatosAlta.getIdMediador().intValue());
		}
		infoContratacion.setIdPlan(oDatosAlta.getIdPlan());

		return infoContratacion;
	}

	private void rellenarDatosPoliza(DatosAlta oDatosAlta, Integer tipoOperacion, InfoContratacion infoContratacion,
			Long idPoliza) {
		final DatosAltaAsegurados oDatosAltaAsegurados = (DatosAltaAsegurados) oDatosAlta;

		// El número de póliza debe indicarse para inclusiones de beneficiarios
		// y todas las operaciones (altas/inclusiones de productos) de pólizas
		// colectivas
		// No debe indicarse para inclusiones de productos particulares
		if (StaticVarsContratacion.INCLUSION_BENEFICIARIO == tipoOperacion.intValue()
				|| oDatosAltaAsegurados.getIdColectivo() > 0
				|| (oDatosAlta.getIdDepartamento() >= 0 && oDatosAlta.getIdEmpresa() != null)) {
			infoContratacion.setIdPoliza(idPoliza.intValue());
		}
		// El número de colectivo se debe incluir en inclusiones de beneficiarios
		if (StaticVarsContratacion.INCLUSION_BENEFICIARIO == tipoOperacion.intValue()) {
			infoContratacion.setIdColectivo(oDatosAltaAsegurados.getIdColectivo());
		}
		// El número de departamento debe incluirse en operaciones con pólizas
		// colectivas
		if (oDatosAlta.getIdDepartamento() >= 0) {
			infoContratacion.setIdDepartamento(oDatosAlta.getIdDepartamento());
		}

		// El número de empresa debe incluise en operaciones con pólizas colectivas
		if (oDatosAlta.getIdEmpresa() != null) {
			infoContratacion.setIdEmpresa(oDatosAlta.getIdEmpresa().intValue());
		}
	}
}
