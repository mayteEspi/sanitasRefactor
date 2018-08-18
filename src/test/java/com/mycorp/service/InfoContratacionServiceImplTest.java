package com.mycorp.service;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.DatosAltaAsegurados;
import com.mycorp.soporte.FrecuenciaEnum;
import com.mycorp.soporte.ProductoPolizas;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.InfoContratacion;
import org.junit.Before;
import org.junit.Test;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;
import wscontratacion.contratacion.fuentes.parametros.DatosDomicilio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoContratacionServiceImplTest {

	private static final int ID_PLAN = 123;
	private static final int ID_DEPARTAMENTO = 1234;
	private static final long ID_POLIZA = 1234567L;
	private static final int ID_COLECTIVO = 4567;

	private InfoContratacionService infoContratacionService;

	@Before
	public void setUp() {
		infoContratacionService = new InfoContratacionServiceImpl();
	}

	@Test
	public void obtenerInfoContratacionBasico() {
		DatosAlta oDatosAlta = new DatosAlta();
		oDatosAlta.setDomicilios(Arrays.asList(new DatosDomicilio(), new DatosDomicilio()));
		oDatosAlta.setIdPlan(ID_PLAN);
		List<BeneficiarioPolizas> lBeneficiarios = new ArrayList<>();
		List<ProductoPolizas> lProductos = new ArrayList<>();
		FrecuenciaEnum frecuencia = FrecuenciaEnum.MENSUAL;
		Integer tipoOperacion = 1;

		InfoContratacion infoContratacion = infoContratacionService.obtenerInfoContratacion(oDatosAlta, lBeneficiarios,
				lProductos, frecuencia, tipoOperacion);

		assertThat(infoContratacion).isNotNull();
		assertThat(infoContratacion.getIdPlan()).isEqualTo(ID_PLAN);

	}

	@Test
	public void obtenerInfoContratacionConIdPoliza() {
		DatosAltaAsegurados oDatosAlta = new DatosAltaAsegurados();
		oDatosAlta.setDomicilios(Arrays.asList(new DatosDomicilio(), new DatosDomicilio()));
		oDatosAlta.setIdPoliza(ID_POLIZA);
		oDatosAlta.setIdDepartamento(ID_DEPARTAMENTO);
		oDatosAlta.setIdPlan(ID_PLAN);
		List<BeneficiarioPolizas> lBeneficiarios = new ArrayList<>();
		List<ProductoPolizas> lProductos = new ArrayList<>();
		FrecuenciaEnum frecuencia = FrecuenciaEnum.MENSUAL;
		Integer tipoOperacion = 1;

		InfoContratacion infoContratacion = infoContratacionService.obtenerInfoContratacion(oDatosAlta, lBeneficiarios,
				lProductos, frecuencia, tipoOperacion);

		assertThat(infoContratacion).isNotNull();
		assertThat(infoContratacion.getIdPlan()).isEqualTo(ID_PLAN);
		assertThat(infoContratacion.getIdDepartamento()).isEqualTo(ID_DEPARTAMENTO);

	}

	@Test
	public void obtenerInfoContratacionConIdPolizaYInclusionBeneficiario() {
		DatosAltaAsegurados oDatosAlta = new DatosAltaAsegurados();
		oDatosAlta.setDomicilios(Arrays.asList(new DatosDomicilio(), new DatosDomicilio()));
		oDatosAlta.setIdPoliza(ID_POLIZA);
		oDatosAlta.setIdDepartamento(ID_DEPARTAMENTO);
		oDatosAlta.setIdPlan(ID_PLAN);
		oDatosAlta.setIdColectivo(ID_COLECTIVO);
		List<BeneficiarioPolizas> lBeneficiarios = new ArrayList<>();
		List<ProductoPolizas> lProductos = new ArrayList<>();
		FrecuenciaEnum frecuencia = FrecuenciaEnum.MENSUAL;
		Integer tipoOperacion = 2;

		InfoContratacion infoContratacion = infoContratacionService.obtenerInfoContratacion(oDatosAlta, lBeneficiarios,
				lProductos, frecuencia, tipoOperacion);

		assertThat(infoContratacion).isNotNull();
		assertThat(infoContratacion.getIdPlan()).isEqualTo(ID_PLAN);
		assertThat(infoContratacion.getIdDepartamento()).isEqualTo(ID_DEPARTAMENTO);
		assertThat(infoContratacion.getIdColectivo()).isEqualTo(ID_COLECTIVO);

	}
}