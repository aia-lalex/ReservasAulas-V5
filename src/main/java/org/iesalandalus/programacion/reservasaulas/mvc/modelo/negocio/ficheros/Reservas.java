package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas {

	private List<Reserva> coleccionReservas;
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.f;
	private static final String NOMBRE_FICHERO_RESERVAS = "datos/reservas.dat";

	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

	public Reservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		setReservas(reservas);
	}

	@Override
	public void comenzar() {
		leer();
	}

// Leemos el fichero
	private void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Reserva reserva = null;
			do {
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while (reserva != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de reservas.");
		} catch (EOFException e) {
			System.out.println("Fichero reservas leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

// Escribimos en el fichero
	private void escribir() {
		File ficheroAulas = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))) {
			for (Reserva reserva : coleccionReservas)
				salida.writeObject(reserva);
			System.out.println("Fichero reservas escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de reservas.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}

	// Metodo terminar y llamamos a escribir
	@Override
	public void terminar() {
		escribir();
	}

	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		this.coleccionReservas = reservas.getReservas();
	}

// Hacemos copia de reservas y listamos reservas
	private List<Reserva> copiaProfundaReservas() {
		List<Reserva> otrasReservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			otrasReservas.add(new Reserva(it.next()));
		}
		return otrasReservas;
	}

// listamos reservas
	public List<Reserva> getReservas() {
		List<Reserva> reservasOrdenadas = copiaProfundaReservas();
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservasOrdenadas;
	}

	// Listamos cantidad de reservas
	public int getNumReservas() {
		return coleccionReservas.size();
	}

// Insertamos reservas
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		Reserva reservaExistente = getReservasAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaExistente != null) {
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorTramo
					&& reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException(
						"ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorHora
					&& reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException(
						"ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException(
					"ERROR: Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		} else {
			coleccionReservas.add(new Reserva(reserva));
		}
	}

	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		LocalDate diaReserva = reserva.getPermanencia().getDia();
		LocalDate dentroDeUnMes = LocalDate.now().plusMonths(1);
		LocalDate primerDiaMesSiguiente = LocalDate.of(dentroDeUnMes.getYear(), dentroDeUnMes.getMonth(), 1);
		return diaReserva.isAfter(primerDiaMesSiguiente) || diaReserva.equals(primerDiaMesSiguiente);
	}

// Muestra puntos gastados por reserva
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntosGastados = 0;
		for (Reserva reservaProfesor : getReservasProfesorMes(reserva.getProfesor(),
				reserva.getPermanencia().getDia())) {
			puntosGastados += reservaProfesor.getPuntos();
		}
		return puntosGastados + reserva.getPuntos();
	}

// Busca aula por profesor y dia
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mes) {
		if (profesor == null) {
			throw new NullPointerException("No se pueden buscar reservas de un profesor nulo.");
		}
		List<Reserva> reservasProfesor = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			LocalDate diaReserva = reserva.getPermanencia().getDia();
			if (reserva.getProfesor().equals(profesor) && diaReserva.getMonthValue() == mes.getMonthValue()
					&& diaReserva.getYear() == mes.getYear()) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}

// Busca aula por aula y dia
	private Reserva getReservasAulaDia(Aula aula, LocalDate dia) {
		if (dia == null) {
			throw new NullPointerException("ERROR: No se puede buscar reserva para un día nulo.");
		}
		for (Reserva reserva : coleccionReservas) {
			LocalDate diaReserva = reserva.getPermanencia().getDia();
			Aula aulaReserva = reserva.getAula();
			if (diaReserva.equals(dia) && aulaReserva.equals(aula)) {
				return reserva;
			}
		}
		return null;
	}

// Buscar reserva
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		int indice = coleccionReservas.indexOf(reserva);

		if (indice != -1) {
			return new Reserva(coleccionReservas.get(indice));
		} else {
			return null;
		}
	}

// Borrar reserva
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una reserva nula.");
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		}

		if (!coleccionReservas.remove(reserva)) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		}
	}

// Representar lista reservas

	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}

// Muestra reservas por profesor
	public List<Reserva> getReservasProfesor(Profesor profesor) {

		if (profesor == null)
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		List<Reserva> reservaProfesor = new ArrayList<>();

		for (Reserva reserva : coleccionReservas) {
			if (reserva.getProfesor().equals(profesor))
				reservaProfesor.add(new Reserva(reserva));
		}

		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getCorreo);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		reservaProfesor.sort(Comparator.comparing(Reserva::getProfesor, comparadorProfesor)
				.thenComparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservaProfesor;
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null)
			throw new IllegalArgumentException("No se pueden consultar las reservas de una permanencia nula.");
		List<Reserva> reservaPermanencia = new ArrayList<>();

		for (Reserva reserva : coleccionReservas) {
			if (reserva.getPermanencia().equals(permanencia))
				reservaPermanencia.add(new Reserva(reserva));
		}

		return reservaPermanencia;
	}

// Muestra reservas por aula
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> reservasOrdenadas = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if (reserva.getAula().equals(aula)) {
				reservasOrdenadas.add(new Reserva(reserva));
			}
		}
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};
		reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservasOrdenadas;
	}

// Consultamos disponibilidad de aulas
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		List<Reserva> copiaReservas = getReservasAula(aula);
		if (copiaReservas.isEmpty()) {
			return true;
		} else {
			Iterator<Reserva> it = copiaReservas.iterator();
			while (it.hasNext()) {
				Reserva reserva = it.next();
				if (reserva.getPermanencia() instanceof PermanenciaPorTramo
						&& permanencia instanceof PermanenciaPorHora) {
					throw new OperationNotSupportedException("ERROR: Ese día posee una permanencia de otro tipo, no se puede comprobar.");
				}
				if (reserva.getPermanencia() instanceof PermanenciaPorHora
						&& permanencia instanceof PermanenciaPorTramo) {
					throw new OperationNotSupportedException("ERROR: Ese día posee una permanencia de otro tipo, no se puede comprobar.");
				}
				if (reserva.getPermanencia().equals(permanencia)) {
					return false;
				}

			}
			return true;
		}
	
	}

}
