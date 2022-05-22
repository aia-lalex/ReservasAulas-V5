package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;

public class Aula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int puestos;
	private String nombre;
	private float PUNTOSXPUESTO = 0.5f;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;

	public Aula(String nombre, int puestos) { // Metodo constructor
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}
		setNombre(nombre);
		setPuestos(puestos);
	}

	public Aula(Aula aula) { // Constructor copia
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}
		setNombre(aula.getNombre());
		setPuestos(aula.getPuestos());
	}

// Establecemos nombre de aula
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
		}
		this.nombre = nombre;
	}

// Muestra puestos
	public int getPuestos() {
		return puestos;
	}

	private void setPuestos(int puestos) {
		if (puestos < MIN_PUESTOS || puestos > MAX_PUESTOS) {
			throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
		}
		this.puestos = puestos;
	}

	public String getNombre() {
		return nombre;
	}

	public float getPuntos() {
		return puestos * PUNTOSXPUESTO;
	}

// Muestra aula ficticia	
	public static Aula getAulaFicticia(String nombre) {
		return new Aula(nombre, 18);
	}

	// creamos hashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Aula other = (Aula) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	// creamos el metodo toString
	@Override
	public String toString() {
		return "nombre=" + this.nombre + ", puestos=" + this.puestos;
	}

}
