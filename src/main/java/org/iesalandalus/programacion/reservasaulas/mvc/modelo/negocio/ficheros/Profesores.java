package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

	private List<Profesor> coleccionProfesores;
	private static final String NOMBRE_FICHERO_PROFESORES = "datos/profesores.dat";

// contructor por defecto
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}
	
	public Profesores(IProfesores profes) {
		if (profes == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		setProfesores(profes);
	}

	@Override
	public void comenzar() {
		leer();
	}
	// lee fichero de profesoreas

	private void leer() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroProfesores))) {
			Profesor profesor = null;
			do {
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			} while (profesor != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de profesores.");
		} catch (EOFException e) {
			System.out.println("Fichero profesores leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	// Metodo escribir

	private void escribir() {
		File ficheroAulas = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))) {
			for (Profesor profesor : coleccionProfesores)
				salida.writeObject(profesor);
			System.out.println("Fichero profesores escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de profesores.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}

	@Override
	public void terminar() {
		escribir();
	}

	// Crea un profesor
	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se pueden instanciar profesores nulos.");
		}
		this.coleccionProfesores = profesores.getProfesores();
	}

// copia todos los profesores
	private List<Profesor> copiaProfundaProfesores() {
		List<Profesor> otrosProfesores = new ArrayList<>();
		Iterator<Profesor> it = coleccionProfesores.iterator();
		while (it.hasNext()) {
			otrosProfesores.add(new Profesor(it.next()));
		}
		return otrosProfesores;
	}

// lista todos los profesoreas
	public List<Profesor> getProfesores() {
		List<Profesor> copiaProfundaProfesores = copiaProfundaProfesores();
		copiaProfundaProfesores.sort(Comparator.comparing(Profesor::getCorreo));
		return copiaProfundaProfesores;

	}

	// Muestra el numero de profesores

	public int getNumProfesores() {
		return coleccionProfesores.size();
	}

// inserta un profesor
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		} else {
			coleccionProfesores.add(profesor);
		}

	}

	// Buscar un profesor
	public Profesor buscar(Profesor profesor) throws IllegalArgumentException, NullPointerException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		Iterator<Profesor> it = coleccionProfesores.iterator();
		while (it.hasNext()) {
			Profesor profeBuscado = it.next();
			if (profeBuscado.equals(profesor)) {
				return new Profesor(profeBuscado);
			}
		}
		return null;

	}

	// Borra un profesor
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		boolean borrado = false;
		Iterator<Profesor> it = coleccionProfesores.iterator();
		while (it.hasNext()) {
			if (it.next().equals(profesor)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		}
	}

// lista los profesores
	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		;
		Iterator<Profesor> it = coleccionProfesores.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}

}
