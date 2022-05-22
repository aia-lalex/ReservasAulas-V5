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

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas{

	private List <Aula> coleccionAulas;
	private static final String NOMBRE_FICHERO_AULAS = "datos/aulas.dat";

	// constructor por defecto

	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}
	
	public Aulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		setAulas(aulas);
	}
	
	private void setAulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		this.coleccionAulas = aulas.getAulas();
	}

	@Override
	public void comenzar() {
		leer();
	}
// Metodo leer fichero
	private void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Aula aula = null;
			do {
				aula = (Aula) entrada.readObject();
				insertar(aula);
			} while (aula != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de aulas.");
		} catch (EOFException e) {
			System.out.println("Fichero aulas leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
// Escribe en archivo

	private void escribir() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))){
			for (Aula aula : coleccionAulas)
				salida.writeObject(aula);
			System.out.println("Fichero aulas escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de aulas.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}
	// metodo terminar y grabar en archivo
	@Override
	public void terminar() {
		escribir();
	}
	
	public List<Aula> getAulas() {
		List<Aula> copiaProfundaAulas = copiaProfundaAulas(coleccionAulas);
		copiaProfundaAulas.sort(Comparator.comparing(Aula::getNombre));
		return copiaProfundaAulas;
	}

	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> copiaAulas = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			copiaAulas.add(new Aula(it.next()));
		}
		return copiaAulas;
	}

	public int getNumAulas() {
		return coleccionAulas.size();
	}
// Inserta Aula
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		if (coleccionAulas.contains(aula)){
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");

		} else coleccionAulas.add(new Aula(aula));
	}



// Borra aula
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}

		if (!coleccionAulas.remove(aula)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		}
	}
	public Aula buscar(Aula aula) throws IllegalArgumentException, NullPointerException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			Aula aulaBuscada = it.next();
			if (aulaBuscada.equals(aula)) {
				return new Aula(aulaBuscada);
			}
		}
		return null;

	}


	// To string

	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}
}
