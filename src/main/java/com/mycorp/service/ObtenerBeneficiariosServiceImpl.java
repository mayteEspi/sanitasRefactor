package com.mycorp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.mycorp.soporte.BeneficiarioPolizas;
import com.mycorp.soporte.DatosAseguradoInclusion;
import com.mycorp.soporte.ProductoPolizas;

import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosCobertura;
import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosContratacionPlan;
import es.sanitas.bravo.ws.stubs.contratacionws.consultasoperaciones.DatosPlanProducto;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Cobertura;
import es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Producto;
import wscontratacion.beneficiario.vo.ProductoCobertura;
import wscontratacion.contratacion.fuentes.parametros.DatosAlta;
import wscontratacion.contratacion.fuentes.parametros.DatosProductoAlta;

@Service
public class ObtenerBeneficiariosServiceImpl implements ObtenerBeneficiariosService{

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public  Beneficiario[] obtenerBeneficiarios(
			final DatosAlta oDatosAlta, final List<ProductoPolizas> lProductos,
			final List<BeneficiarioPolizas> lBeneficiarios, final DatosContratacionPlan oDatosPlan) {
		final List<Beneficiario> beneficiarios = new ArrayList<>();

		// Si hay lista de beneficiarios se trata de una inclusion de beneficiarios
		if (lBeneficiarios != null && lBeneficiarios.size() > 0) {
			for (final BeneficiarioPolizas oBeneficiario : lBeneficiarios) {
				final es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario beneficiario = new es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario();
				beneficiario.setFechaNacimiento(
						cambiarFecha(oBeneficiario.getDatosPersonales().getFNacimiento(), oDatosAlta.getFAlta()));
				beneficiario.setParentesco(11);
				beneficiario.setSexo(oBeneficiario.getDatosPersonales().getGenSexo());
				if (oBeneficiario.getDatosPersonales().getIdProfesion() > 0) {
					beneficiario.setIdProfesion(oBeneficiario.getDatosPersonales().getIdProfesion());
				} else {
					beneficiario.setIdProfesion(1);
				}
				beneficiario.setNombre(oBeneficiario.getDatosPersonales().getNombre());
				final Producto[] productos = obtenerProductosAsegurado(
						oDatosAlta.getTitular().getProductosContratados(), oDatosPlan);
				beneficiario.setListaProductos(productos);
				/*
				 * @TODO NO BORRAR!!! String tarjeta = oBeneficiario.getSNumTarjetaSanitas();
				 * if( !StringUtils.isEmpty( tarjeta ) ) { obtenerProcedencia(tarjeta,
				 * oBeneficiario.getDatosPersonales(), beneficiario); }
				 */
				beneficiarios.add(beneficiario);
			}
		} else {
			// Si no hay lista de beneficiarios se trata de un alta
			// Primero se procesa el titular
			Beneficiario beneficiario = new Beneficiario();

			beneficiario.setFechaNacimiento(
					cambiarFecha(oDatosAlta.getTitular().getDatosPersonales().getFNacimiento(), oDatosAlta.getFAlta()));
			beneficiario.setParentesco(1);
			// aunque se permite el genero 3 cuando no hay uno definido no podemos usarlo.
			// Así que enviamos un 2 (por temas de ginecologia tambien).
			beneficiario.setSexo(oDatosAlta.getTitular().getDatosPersonales().getGenSexo() == 0 ? 2
					: oDatosAlta.getTitular().getDatosPersonales().getGenSexo());
			beneficiario.setIdProfesion(1);
			beneficiario.setNombre(String.valueOf(oDatosAlta.getTitular().getDatosPersonales().getNombre()));
			if (oDatosAlta.getTitular() instanceof DatosAseguradoInclusion) {
				final DatosAseguradoInclusion dai = (DatosAseguradoInclusion) oDatosAlta.getTitular();
				if (dai.getSIdCliente() != null && dai.getSIdCliente() > 0) {
					beneficiario.setIdCliente(dai.getSIdCliente().intValue());
				}
			}

			// Si hay lista de productos se incluyen como productos añadidos al alta
			Producto[] productos = obtenerProductosAsegurado(oDatosAlta.getTitular().getProductosContratados(),
					oDatosPlan);
			if (lProductos != null && !lProductos.isEmpty()) {
				productos = ArrayUtils.addAll(productos,
						obtenerProductos(lProductos.get(0).getProductos(), oDatosPlan));
			}
			beneficiario.setListaProductos(productos);

			/*
			 * // Las procedencias pueden venir de tarjetas Sanitas List<String> tarjetas =
			 * ( ( DatosAltaAsegurados )oDatosAlta ).getLNumTarjAsegurados(); if (tarjetas
			 * != null && !tarjetas.isEmpty()){ String tarjeta = tarjetas.get( 0 );
			 * obtenerProcedencia(tarjeta, oDatosAlta.getTitular().getDatosPersonales(),
			 * beneficiario); }else{ beneficiario.setProcedencia(
			 * obtenerProcedencia(lProductos) ); }
			 */
			beneficiarios.add(beneficiario);

			// Y luego se procesan el resto de asegurados
			if (oDatosAlta.getAsegurados() != null && oDatosAlta.getAsegurados().size() > 0) {
				final Iterator<DatosAseguradoInclusion> iteradorAsegurados = oDatosAlta.getAsegurados().iterator();
				int contadorBeneficiario = 1;
				while (iteradorAsegurados.hasNext()) {
					final DatosAseguradoInclusion oDatosAsegurado = iteradorAsegurados.next();

					beneficiario = new es.sanitas.seg.simulacionpoliza.services.api.simulacion.vo.Beneficiario();

					beneficiario.setFechaNacimiento(
							cambiarFecha(oDatosAsegurado.getDatosPersonales().getFNacimiento(), oDatosAlta.getFAlta()));
					beneficiario.setParentesco(11);
					// aunque se permiten el genero 3 cuando no hay uno definido no podemos usarlo.
					// Así que enviamos un 2 (por temas de ginecologia tambien).
					beneficiario.setSexo(oDatosAsegurado.getDatosPersonales().getGenSexo() == 0 ? 2
							: oDatosAsegurado.getDatosPersonales().getGenSexo());
					beneficiario.setNombre(oDatosAsegurado.getDatosPersonales().getNombre());
					beneficiario.setIdProfesion(1);
					if (oDatosAsegurado.getSIdCliente() != null) {
						beneficiario.setIdCliente(oDatosAsegurado.getSIdCliente().intValue());
					}

					productos = obtenerProductosAsegurado(oDatosAsegurado.getProductosContratados(), oDatosPlan);
					if (lProductos != null && !lProductos.isEmpty()) {
						productos = ArrayUtils.addAll(productos,
								obtenerProductos(lProductos.get(contadorBeneficiario).getProductos(), oDatosPlan));
					}
					beneficiario.setListaProductos(productos);

					/*
					 * if (tarjetas != null && tarjetas.size() > contadorBeneficiario){ String
					 * tarjeta = tarjetas.get( contadorBeneficiario ); obtenerProcedencia(tarjeta,
					 * oDatosAsegurado.getDatosPersonales(), beneficiario); }else{
					 * beneficiario.setProcedencia( obtenerProcedencia(lProductos) ); }
					 */
					beneficiarios.add(beneficiario);
					contadorBeneficiario++;
				}
			}
		}

		return beneficiarios.toArray(new Beneficiario[0]);
	}

	/**
	 * Método que recibe una fecha en formato String. Si la fecha está en formato
	 * edad, lo transforma a formato fecha.
	 *
	 * @param fecha
	 * @return la nueva fecha
	 **/
	private String cambiarFecha(String fecha, final String fechaAlta) {
		String convertida = fecha;

		if (fecha == null || "//".equals(fecha)) {
			// Si viene null, le ponemos que tiene 18
			fecha = "18";
		}

		if (fecha != null && !fecha.contains("/")) {
			final int edad = Integer.valueOf(fecha);
			final Calendar dob = Calendar.getInstance();
			dob.add(Calendar.YEAR, -edad);
			dob.set(Calendar.DAY_OF_MONTH, 1);
			final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			convertida = sdf.format(dob.getTime());
		}
		return convertida;
	}

	private Producto[] obtenerProductosAsegurado(final List<DatosProductoAlta> productosCobertura,
			final DatosContratacionPlan oDatosPlan) {
		final List<Producto> productos = new ArrayList<>();
		if (productosCobertura != null && !productosCobertura.isEmpty()) {
			for (final DatosProductoAlta producto : productosCobertura) {
				productos.add(obtenerProducto(producto, oDatosPlan));
			}
		}
		return productos.toArray(new Producto[0]);
	}

	private Producto obtenerProducto(final DatosProductoAlta productoAlta, final DatosContratacionPlan oDatosPlan) {
		final Producto producto = new Producto();
		final int idProducto = productoAlta.getIdProducto();
		producto.setIdProducto(idProducto);
		producto.setListaCoberturas(obtenerCoberturas(idProducto, oDatosPlan));
		return producto;
	}

	private Producto obtenerProducto(final ProductoCobertura productoCobertura,
			final DatosContratacionPlan oDatosPlan) {
		final Producto producto = new Producto();
		final int idProducto = productoCobertura.getIdProducto();
		producto.setIdProducto(idProducto);
		producto.setListaCoberturas(obtenerCoberturas(idProducto, oDatosPlan));
		return producto;
	}

	private Cobertura[] obtenerCoberturas(final int idProducto, final DatosContratacionPlan oDatosPlan) {
		final List<Cobertura> coberturas = new ArrayList<>();

		final Iterator<DatosPlanProducto> iteradorProdsPlan = oDatosPlan.getProductos().iterator();
		boolean found = false;
		while (iteradorProdsPlan.hasNext() && !found) {
			final DatosPlanProducto productoPlan = iteradorProdsPlan.next();
			if (idProducto == productoPlan.getIdProducto()) {
				found = true;
				for (final DatosCobertura oDatosCobertura : productoPlan.getCoberturas()) {
					if (oDatosCobertura.isSwObligatorio() && oDatosCobertura.getCapitalMinimo() != null
							&& oDatosCobertura.getCapitalMinimo() > 0) {
						final Cobertura cobertura = new Cobertura();
						cobertura.setCapital(Double.valueOf(oDatosCobertura.getCapitalMinimo()));
						cobertura.setIdCobertura(oDatosCobertura.getIdCobertura().intValue());
						coberturas.add(cobertura);
					}
				}
			}
		}

		return coberturas.toArray(new Cobertura[0]);
	}
	
	private Producto[] obtenerProductos( final List< ProductoCobertura > productosCobertura,
            final DatosContratacionPlan oDatosPlan ) {
        final List< Producto > productos = new ArrayList< >();
        if( productosCobertura != null && !productosCobertura.isEmpty() ) {
            for( final ProductoCobertura producto : productosCobertura ) {
                productos.add( obtenerProducto( producto, oDatosPlan ) );
            }
        }

        return productos.toArray( new Producto[ 0 ] );
    }

}
