package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable {

	private static final long serialVersionUID = 1L;
	private LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	
	// constructor por defecto
	public Permanencia(LocalDate dia) {
		setDia(dia);
	

	}
// constructor copia
	public Permanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw  new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		} else {
			setDia(permanencia.getDia());
		}
	}
// Devuelve el dia
	public LocalDate getDia() {
		return dia;
	}
// seleccionamos dia
	private void setDia(LocalDate dia) {
		if (dia == null) {
			throw new NullPointerException("ERROR: El día de una permanencia no puede ser nulo.");
		}
		this.dia = dia;
	}


	public abstract int getPuntos();

	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);

	@Override
	public String toString() {
		return String.format("día=%s", dia.format(FORMATO_DIA));
	}


}


