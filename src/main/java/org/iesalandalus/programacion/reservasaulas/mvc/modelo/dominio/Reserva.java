package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;

public class Reserva implements Serializable{

	private static final long serialVersionUID = 1L;
	private Permanencia permanencia;
	private Profesor profesor;
	private Aula aula;
// Constructor por defecto
	
	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);

	}
// Constructor copia
	public Reserva(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		setProfesor(reserva.getProfesor());
		setAula(reserva.getAula());
		setPermanencia(reserva.getPermanencia());

	}
// Hace reserva a nombre de profesor
	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = new Profesor(profesor);
	}
// Muestra profesor de la reserva
	public Profesor getProfesor() {

		return new Profesor(profesor);
	}
// Reserva de aula
	private void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula = new Aula(aula);
	}
	public Aula getAula() {

		return new Aula(aula);
	}
// Hace reserva con permanencia
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		if (permanencia instanceof PermanenciaPorTramo) {
			this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		} else if (permanencia instanceof PermanenciaPorHora) {
			this.permanencia = new PermanenciaPorHora((PermanenciaPorHora)permanencia);
		}
	}

	
// Muestra reserva con permanencia
	public Permanencia getPermanencia() {

		Permanencia copiaPermanencia = null;
		if (this.permanencia instanceof PermanenciaPorTramo) {
			copiaPermanencia = new PermanenciaPorTramo((PermanenciaPorTramo)this.permanencia);
		} else if (this.permanencia instanceof PermanenciaPorHora) {
			copiaPermanencia = new PermanenciaPorHora((PermanenciaPorHora)this.permanencia);
		}
		return copiaPermanencia;
	}

// Muestra reserva ficticia
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		return new Reserva(Profesor.getProfesorFicticio("alexbalwing@hotmail.com"), aula, permanencia);
	}
// Muestra puntos
	public float getPuntos() {
		return permanencia.getPuntos()+ aula.getPuntos();
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aula == null) ? 0 : aula.hashCode());
		result = prime * result + ((permanencia == null) ? 0 : permanencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (aula == null) {
			if (other.aula != null)
				return false;
		} else if (!aula.equals(other.aula))
			return false;
		if (permanencia == null) {
			if (other.permanencia != null)
				return false;
		} else if (!permanencia.equals(other.permanencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, puntos=%.1f", profesor, aula, permanencia, getPuntos());
	}




}
