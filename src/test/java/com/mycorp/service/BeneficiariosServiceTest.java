package com.mycorp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.ProductoPolizas;

import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosCobertura;
import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosContratacionPlan;
import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosPlanProducto;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;
import wscontratacion.contratacion.fuentes.parametros.DatosAsegurado;
import wscontratacion.contratacion.fuentes.parametros.DatosPersona;
import wscontratacion.contratacion.fuentes.parametros.DatosProductoAlta;

public class BeneficiariosServiceTest {

	private static final int GEN_SEX = 2;
	private static final String FECH_NACI_BENEFICIARIO = "31/03/1984";
	private static final int ID_PRODUCTO = 1;

	private BeneficiariosService obtenerBeneficiariosService;

	@Before
	public void init() {
		obtenerBeneficiariosService = new BeneficiariosServiceImpl();
	}

	@Test
	public void realizarIncluisionBeneficiariosTest() {
		Beneficiario[] beneficiarios = obtenerBeneficiariosService.obtenerBeneficiarios(mockDatosAlta(),
				mockProductoPolizas(), mockBeneficiarioPolizas(), mockDatosContratacionPlan());
		assertThat(beneficiarios).isNotNull();
		assertThat(beneficiarios[0].getFechaNacimiento()).isEqualTo(FECH_NACI_BENEFICIARIO);
	}

	@Test
	public void realizarAltaBeneficiarioTest() {
		Beneficiario[] beneficiarios = obtenerBeneficiariosService.obtenerBeneficiarios(mockDatosAlta(),
				mockProductoPolizas(), null, mockDatosContratacionPlan());
		assertThat(beneficiarios).isNotNull();
		assertThat(beneficiarios[0].getFechaNacimiento()).isEqualTo(FECH_NACI_BENEFICIARIO);
		assertThat(beneficiarios[0].getSexo()).isEqualTo(GEN_SEX);
	}

	private DatosAlta mockDatosAlta() {
		DatosAlta datosAlta = new DatosAlta();
		DatosPersona datosPersonales = new DatosPersona();
		datosPersonales.setFNacimiento(FECH_NACI_BENEFICIARIO);
		datosPersonales.setGenSexo(GEN_SEX);
		DatosAsegurado datosAsegurado = new DatosAsegurado();
		datosAsegurado.setDatosPersonales(datosPersonales);
		datosAsegurado.setProductosContratados(mockProductosPolizas());
		datosAlta.setTitular(datosAsegurado);
		return datosAlta;
	}

	private List<DatosProductoAlta> mockProductosPolizas() {
		DatosProductoAlta datosProducto = new DatosProductoAlta();
		datosProducto.setIdProducto(ID_PRODUCTO);
		return Arrays.asList(datosProducto);
	}

	private List<ProductoPolizas> mockProductoPolizas() {
		return Arrays.asList(new ProductoPolizas(), new ProductoPolizas());
	}

	private List<BeneficiarioPolizas> mockBeneficiarioPolizas() {
		BeneficiarioPolizas beneficiariosPoliza = new BeneficiarioPolizas();
		DatosPersona datosPersona = new DatosPersona();
		datosPersona.setFNacimiento(FECH_NACI_BENEFICIARIO);
		datosPersona.setGenSexo(GEN_SEX);
		beneficiariosPoliza.setDatosPersonales(datosPersona);
		return Arrays.asList(beneficiariosPoliza);
	}

	private DatosContratacionPlan mockDatosContratacionPlan() {
		DatosContratacionPlan datosContratacionPlan = spy(new DatosContratacionPlan());
		List<DatosPlanProducto> datosPlanProducto = new ArrayList<DatosPlanProducto>();
		DatosPlanProducto datoPlanProducto = spy(new DatosPlanProducto());
		datoPlanProducto.setIdProducto((long) ID_PRODUCTO);
		datosPlanProducto.add(datoPlanProducto);
		DatosCobertura datoCobertura = spy(new DatosCobertura());
		datoCobertura.setCapitalMinimo(2);
		datoCobertura.setSwObligatorio(true);
		datoCobertura.setIdCobertura((long) 2);
		DatosCobertura datosCobertura = spy(datoCobertura);
		Mockito.when(datoPlanProducto.getCoberturas()).thenReturn(Arrays.asList(datosCobertura));
		Mockito.when(datosContratacionPlan.getProductos()).thenReturn(datosPlanProducto);
		return datosContratacionPlan;
	}

}
