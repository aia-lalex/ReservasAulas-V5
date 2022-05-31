package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iugrafica.VistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iutextual.Consola;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

public class MainAppIUTextual {

	public static void main(String[] args) {
		Consola.mostrarCabecera("Alejandro Hurtado Navarro");
		IVistaReservasAulas vista = new VistaReservasAulas();
		IModelo modelo = new Modelo();
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

}