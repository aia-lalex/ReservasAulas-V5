package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class PermanenciaPorTramo extends Permanencia implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int PUNTOS=10;
	private Tramo tramo;
	// Constructor por defecto
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
		
	}
	// Constructor copia
	public PermanenciaPorTramo(PermanenciaPorTramo permanencia) {
		super(permanencia);
		setTramo(permanencia.getTramo());
	}
	// muestra tramo
	public Tramo getTramo() {
		return tramo;
	}
	// inserta tramo
	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}
	// muestra puntos
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDia(), tramo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PermanenciaPorTramo)) {
			return false;
		}
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return Objects.equals(getDia(), other.getDia()) && tramo == other.tramo;
	}

	
	@Override
	public String toString() {
		return String.format("%s, tramo=%s", super.toString(), tramo);
	}

}
