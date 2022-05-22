package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;


import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;


public class Controlador implements IControlador {

	private IVista vista;
	private IModelo modelo;
	// constructor por defecto
	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no	puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);

	}
	// Comenzar en modelo y vista
	@Override
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}
	// Terminar en modelo y vista
	@Override
	public void terminar() {
		modelo.terminar();
		System.out.println("Hasta luegorrr");
	}
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertarAula(aula);
	}

	public void insertarProfesor(Profesor profesor) throws
	OperationNotSupportedException {
		modelo.insertarProfesor(profesor);
	}
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrarAula(aula);
	}
	public void borrarProfesor(Profesor profesor) throws
	OperationNotSupportedException {
		modelo.borrarProfesor(profesor);
	}
	public Aula buscarAula(Aula aula) {
		return modelo.buscarAula(aula);
	}
	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscarProfesor(profesor);
	}
	public List<String> representarAulas() {
		return modelo.representarAulas();
	}
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
	}
	public List<String> representarReservas() {
		return modelo.representarReservas();
	}
	public void realizarReserva(Reserva reserva) throws
	OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	public void anularReserva(Reserva reserva) throws
	OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservaAula(aula);
	}
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanecia) throws OperationNotSupportedException {
		return modelo.consultarDisponibilidad(aula, permanecia);
	}
}
