package com.mycorp.dto;

import java.util.List;
import java.util.Map;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.ProductoPolizas;

import lombok.Data;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;

@Data
public class RealizarSimulacionDto {
	
	private DatosAlta oDatosAlta; 
	private List<ProductoPolizas> lProductos;
	private List<BeneficiarioPolizas> lBeneficiarios;
	private boolean desglosar;
	private Map<String, Object> hmValores;

}
