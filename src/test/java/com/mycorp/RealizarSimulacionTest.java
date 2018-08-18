package com.mycorp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mycorp.dto.RealizarSimulacionDto;
import com.mycorp.soporte.ExcepcionContratacion;


public class RealizarSimulacionTest {

	private RealizarSimulacion realizarSimulacion;
	
	@Before
	public void init() {
		realizarSimulacion = new RealizarSimulacion();
	}
	
	@Test(expected = ExcepcionContratacion.class)
	public void testPropagarExcepcionContratacionSimulacion() throws ExcepcionContratacion, Exception {
		realizarSimulacion.realizarSimulacion(mockRealizarSimulacionDto());
	}
	
	private  RealizarSimulacionDto mockRealizarSimulacionDto() {
		RealizarSimulacionDto realizarSimulacion = new RealizarSimulacionDto();
		Map<String, Object> hmValores = new HashMap<>();
		hmValores.put("hmValores", new ArrayList<String>());
		realizarSimulacion.setHmValores(hmValores);
		return realizarSimulacion;
	}

}
