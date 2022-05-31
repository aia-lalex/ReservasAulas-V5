package org.iesalandalus.programacion.reservasaulas.mvc.modelo;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.Aulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.Profesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.Reservas;


public class Modelo implements IModelo{



	private IProfesores profesores;
	private IAulas aulas;
	private IReservas reservas;
	/*
	public Modelo(IFuenteDatos fuenteDatos) {
		profesores = fuenteDatos.crearProfesores();
		aulas = fuenteDatos.crearAulas();
		reservas = fuenteDatos.crearReservas();
	}*/
	
	public Modelo() {
		reservas = new Reservas();
		aulas = new Aulas();
		profesores = new Profesores();
	}
	@Override
	public void comenzar() {
		profesores.comenzar();
		aulas.comenzar();
		reservas.comenzar();			
	}
	@Override
	public void terminar() {
		profesores.terminar();
		aulas.terminar();
		reservas.terminar();
	}
	
	
	public List<Aula> getAulas() {
		return aulas.getAulas();
	}

	public int getNumAulas() {
		return getAulas().size();
	}

	public List<String> representarAulas() {
		return aulas.representar();
	}

	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);
	}
		
	
	public void insertarAula(Aula insertar) throws OperationNotSupportedException {
		aulas.insertar(insertar);
	}

	public void borrarAula(Aula borrar) throws OperationNotSupportedException {
		aulas.borrar(borrar);
	}

	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}

	public int getNumProfesores() {
		return getNumProfesores();
	}

	public List<String> representarProfesores() {
		return profesores.representar();
	}

	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);
	}

	public void insertarProfesor(Profesor insertar) throws OperationNotSupportedException {
		profesores.insertar(insertar);
	}

	public void borrarProfesor(Profesor borrar) throws OperationNotSupportedException {
		profesores.borrar(borrar);
	}

	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}

	public int getNumReservas() {
		return reservas.getNumReservas();
	}

	public List<String> representarReservas() {
		return reservas.representar();
	}

	public Reserva buscarReserva(Reserva buscar) {
		return reservas.buscar(buscar);
	}

	public void realizarReserva(Reserva realizar) throws OperationNotSupportedException {
		reservas.insertar(realizar);
	}

	public void anularReserva(Reserva anular) throws OperationNotSupportedException {
		reservas.borrar(anular);
	}

	public List<Reserva> getReservaAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}

	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) throws OperationNotSupportedException {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}

}
