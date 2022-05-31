package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.iugrafica.VistaReservasAulas;

public class MainAppIUGrafica {

	public static void main(String[] args) {
		IVistaReservasAulas vista = new VistaReservasAulas();
		IModelo modelo = new Modelo();
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}
}

