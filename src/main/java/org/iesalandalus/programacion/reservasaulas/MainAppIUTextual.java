package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iutextual.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iugrafica.VistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iutextual.Consola;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.*;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iugrafica.VistaReservasAulas;

public class MainAppIUTextual {

	public static void main(String[] args) {
		Consola.mostrarCabecera("Alejandro Hurtado Navarro");
	//	IVistaReservasAulas vista = new VistaReservasAulas();
	//	IModelo modelo = new Modelo();
	//	IControlador controlador = new Controlador(modelo, vista);
	//	controlador.comenzar();
		IModelo modelo = null;
		modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		IVista vista = new Vista();
		//IVista vista = new VistaGrafica();
		//IVista vista = new VistaTexto();
		//IVista vista = procesarArgumentosVista
		IControlador controlador = new ControladorT(modelo, vista);
		System.out.println();
		controlador.comenzar();
	}
	
	

}