package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Profesor implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String ER_TELEFONO = "[96]\\d{8}";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	public String nombre;
	public String correo;
	public String telefono;

// Constructor por defecto
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}
// constructor con tres parametros
	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}
// Constructor copia
	public Profesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		setNombre(profesor.getNombre());
		setCorreo(profesor.getCorreo());
		setTelefono(profesor.getTelefono());
	}
// Set nombre del profesor
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		}
		this.nombre = formateaNombre(nombre);
	}
	// Formatea nombre del profesor
	private String formateaNombre(String nombre){ // Formateo del nombre, eliminamos espacios y ponemos la primera en mayuscula
		
		char priLetra;
		String mayusculas; 
		String nombreFormateado="";
		nombre = nombre.trim(); // Eliminamos espacio y tabulaciones
		String [] nombr = nombre.replaceAll("\\s{2,}", " ").split(" ");
		
		for (int i=0; i<nombr.length; i++) {
			priLetra = nombr[i].charAt(0);
			mayusculas = priLetra+"".toUpperCase();
			nombreFormateado += mayusculas+nombr[i].substring(1).toLowerCase() + " ";
		}

		nombreFormateado = nombreFormateado.substring(0,nombreFormateado.length()-1);
		
		return nombreFormateado;
	}
	// inserta correo
	public void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		this.correo = correo;
	}
	// inserta telefono
	public void setTelefono(String telefono) {
		if (telefono != null && !telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
		}
		this.telefono = telefono;
	}
// Muestra nombre
	public String getNombre() {
		return nombre;
	}
	// Muestra correo
	public String getCorreo() {
		return correo;
	}
	// Muestra telefono
	public String getTelefono() {
		return telefono;
	}

	// Muestra profesor ficticio
	public static Profesor getProfesorFicticio(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		return new Profesor("Alex", correo, "655948136");
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(ER_CORREO, ER_TELEFONO, correo, nombre, telefono);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String cadenaTelefono = (telefono == null) ? "" : ", teléfono=" + telefono;
		return String.format("nombre=%s, correo=%s%s", nombre, correo, cadenaTelefono);
	}



}



