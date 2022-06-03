package org.iesalandalus.programacion.reservasaulas.mvc.vista.iutextual;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

public interface IVista {


	void setControlador(IControlador controlador);

	void comenzar();

	void terminar();

}