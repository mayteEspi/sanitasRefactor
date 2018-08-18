package com.mycorp.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.ProductoPolizas;

import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosContratacionPlan;
import wscontratacion.beneficiario.vo.ProductoCobertura;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;
import wscontratacion.contratacion.fuentes.parametros.DatosAsegurado;
import wscontratacion.contratacion.fuentes.parametros.DatosPersona;

public class ObtenerBeneficiariosServiceTest {

	
	private ObtenerBeneficiariosService obtenerBeneficiariosService;
	
	@Before
	public void init() {
		obtenerBeneficiariosService = new ObtenerBeneficiariosServiceImpl();
	}
	
	@Test
	public void realizarIncluisionBeneficiariosTest() {
		obtenerBeneficiariosService.obtenerBeneficiarios(mockDatosAlta(), mockProductoPolizas(),
				mockBeneficiarioPolizas(),mockDatosContratacionPlan());
	}
	
	@Test
	public void realizarAltaBeneficiarioTest() {
		
	}
	
	private DatosAlta mockDatosAlta(){
		DatosAlta datosAlta = new DatosAlta();
		DatosAsegurado datosAsegurado = new DatosAsegurado();
		//datosAsegurado.setProductosContratados(Arrays.asList(new ProductoPolizas()));
		datosAlta.setTitular(datosAsegurado);
		return datosAlta;
	}
	
	private List<ProductoPolizas> mockProductoPolizas(){
		return Arrays.asList(new ProductoPolizas(), new ProductoPolizas());
	}
	
	private List<BeneficiarioPolizas> mockBeneficiarioPolizas(){
		BeneficiarioPolizas beneficiariosPoliza = new BeneficiarioPolizas();
		DatosPersona datosPersona = new DatosPersona();
		datosPersona.setFNacimiento("31/03/1984");
		datosPersona.setGenSexo(2);
		beneficiariosPoliza.setDatosPersonales(datosPersona);
		return Arrays.asList(beneficiariosPoliza);
	}
	
	private DatosContratacionPlan mockDatosContratacionPlan(){
		return new DatosContratacionPlan();
	}
	
		
}
