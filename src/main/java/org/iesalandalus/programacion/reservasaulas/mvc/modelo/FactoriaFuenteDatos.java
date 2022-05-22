package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

public enum FactoriaFuenteDatos {
	
	FICHEROS {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	},
	MEMORIA {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};
	
	private FactoriaFuenteDatos() {
		
	}
	
	public abstract IFuenteDatos crear();
}
