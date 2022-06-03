package org.iesalandalus.programacion.reservasaulas.mvc.vista.iutextual;

public enum Opcion {

	SALIR("0.- Salir.") {
		public void ejecutar() {
			vista.salir();
		}
	}
	, INSERTAR_AULA("1.- Insertar aula:") {
		public void ejecutar() {
			vista.insertarAula();
		}
	}
	, BORRAR_AULA("2.- Borrar aula:") {
		public void ejecutar() {
			vista.borrarAula();
		}
	}
	, BUSCAR_AULA("3.- Buscar aula:") {
		public void ejecutar() {
			vista.buscarAula();
		}
	}
	, LISTAR_AULAS("4.- Listar aulas:") {
		public void ejecutar() {
			vista.listarAulas();
		}
	}
	, INSERTAR_PROFESOR("5.- Insertar profesor:") {
		public void ejecutar() {
			vista.insertarProfesor();
		}
	}
	, BORRAR_PROFESOR("6.- Borrar profesor:") {
		public void ejecutar() {
			vista.borrarProfesor();
		}
	}
	, BUSCAR_PROFESOR("7.- Buscar profesor:") {
		public void ejecutar() {
			vista.buscarProfesor();
		}
	}
	, LISTAR_PROFESORES("8.- Listar profesores:") {
		public void ejecutar() {
			vista.listarProfesores();
		}
	}
	, INSERTAR_RESERVA("9.- Insertar reserva:") {
		public void ejecutar() {
			vista.realizarReserva();
		}
	}
	, BORRAR_RESERVA("10.- Borrar reserva:") {
		public void ejecutar() {
			vista.anularReserva();
		}
	}
	, LISTAR_RESERVAS("11.- Listar reservas:") {
		public void ejecutar() {
			vista.listarReservas();
		}
	}
	, LISTAR_RESERVAS_AULA("12.- Listar reservas por aula:") {
		public void ejecutar() {
			vista.listarReservasAula();
		}
	}
	, LISTAR_RESERVAS_PROFESOR("13.- Listar reservas por profesor:") {
		public void ejecutar() {
			vista.listarReservasProfesor();
		}
	}

	, CONSULTAR_DISPONIBILIDAD("14.- Consultar disponibilidad:") {
		public void ejecutar() {
			vista.consultarDisponibilidad();
		}
	};
	
	private String mensajeMostrado;
	private static Vista vista;

	private Opcion(String mensajeMostrado) {
		this.mensajeMostrado = mensajeMostrado;
	}

	public String getMensaje() {
		return mensajeMostrado;
	}

	public abstract void ejecutar();

	static void setVista(Vista vista) {
		Opcion.vista = vista;
	}

	public String toString() {
		return mensajeMostrado;
	}

	public static Opcion getOpcionSegunOrdinal(int ordinal) {
		return Opcion.values()[ordinal];
	}

	public static boolean esOrdinalValido(int ordinal) {
		if(ordinal>=0 && ordinal<Opcion.values().length)
			return true;
		return false;
	}

}

