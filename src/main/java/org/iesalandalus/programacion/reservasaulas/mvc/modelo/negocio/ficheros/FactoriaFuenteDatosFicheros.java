package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class FactoriaFuenteDatosFicheros implements IFuenteDatos{

	public FactoriaFuenteDatosFicheros() {
		
	}
	
	@Override
	public IAulas crearAulas() { // Crea aulas
		return new Aulas();
	}

	@Override
	public IProfesores crearProfesores() { // Crea profesores
		return new Profesores();
	}

	@Override
	public IReservas crearReservas() { // Crea reserva
		return new Reservas();
	}

}
