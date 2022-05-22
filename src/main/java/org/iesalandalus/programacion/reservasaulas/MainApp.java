package org.iesalandalus.programacion.reservasaulas;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;


public class MainApp {


	public static void main(String[] args) {
		IModelo modelo = null;
		modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		IVista vista = new Vista();
		//IVista vista = new VistaGrafica();
		//IVista vista = new VistaTexto();
		//IVista vista = procesarArgumentosVista
		IControlador controlador = new Controlador(modelo, vista);
		System.out.println();
		controlador.comenzar();
	}
}
