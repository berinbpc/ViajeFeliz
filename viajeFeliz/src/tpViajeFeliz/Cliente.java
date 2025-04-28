package tpViajeFeliz;

import java.util.ArrayList;

public class Cliente {
	private String nombre, direccion, dni;
	private ArrayList<PaquetePersonalizado> paquetesPersonalizados = new ArrayList<>();

	public Cliente(String dni, String nombre, String direccion) {
		if (nombre == "" || nombre == null) {
			throw new RuntimeException("Nombre no puede ser vacio o nulo");
		}
		if (direccion == "" || direccion == null) {
			throw new RuntimeException("Direccion no puede ser vacio o nulo");
		}
		if (dni == "" || dni == null) {
			throw new RuntimeException("Dni no puede ser vacio o nulo");
		}
		this.nombre = nombre;
		this.direccion = direccion;
		this.dni = dni;
		this.paquetesPersonalizados = new ArrayList<>();
	}
	
	public void agregarPaqPersonalizado(PaquetePersonalizado paq) {
		paquetesPersonalizados.add(paq);
	}
	public void quitarPaqPersonalizado(PaquetePersonalizado paq) {
		paquetesPersonalizados.remove(paq);
	}

	public boolean existeEstePaq(int codPaq) {
		if (paquetesPersonalizados.size() == 0) {
			return true;
		}
		for (PaquetePersonalizado p : paquetesPersonalizados) {
			if (p.getCodUnico() == codPaq) {
				return true;
			}
		}
		return false;
	}

	public boolean tieneContratacionesFinalizadas() {
		boolean result = true;
		if (paquetesPersonalizados.size() == 0) {
			return true;
		} else if (paquetesPersonalizados.size() == 1) {
			return paquetesPersonalizados.get(0).estaPago();
		}
		for (PaquetePersonalizado p : paquetesPersonalizados) {
			if (!p.estaPago()) {
				return false;
			}
		}
		return result;
	}

	
	

	public PaquetePersonalizado dameUltimaContratacion() {
		if (paquetesPersonalizados.size() == 0) {
			return null;
		}
		return paquetesPersonalizados.get(paquetesPersonalizados.size() - 1);
	}

	public String dameFechaInicioUltimoPaq() {
		PaquetePersonalizado p = dameUltimaContratacion();
		String fechaInicio = p.dameFechaInicio();
		return fechaInicio;
	}

	public PaquetePersonalizado damePaquete(int codPaq) {
		for (PaquetePersonalizado p : paquetesPersonalizados) {
			if (p.getCodUnico() == codPaq) {
				return p;
			}
		}
		return null;
	}
	

//DEVUELVE PAQUETES QUE ESTAPAGO == TRUE;
	public ArrayList<PaquetePersonalizado> damePaquetesAdquiridos() {
		ArrayList<PaquetePersonalizado> paqs = new ArrayList<>();
		for (PaquetePersonalizado p : paquetesPersonalizados) {
			if (p.estaPago()) {
				paqs.add(p);
			}
		}
		return paqs;
	}

	public ArrayList<Integer> dameCodigosPaquetesAdquiridos() {
		ArrayList<Integer> paqs = new ArrayList<>();
		for (PaquetePersonalizado p : paquetesPersonalizados) {
			if (p.estaPago()) {
				paqs.add(p.getCodUnico());
			}
		}
		return paqs;
	}

	public PaquetePersonalizado damePaqueteConFecha(String fecha) {
		if (this.paquetesPersonalizados != null) {
			for (PaquetePersonalizado p : this.paquetesPersonalizados) {
				if (p.dameFechaInicio().equals(fecha)) {
					return p;
				}
			}
		}
		return null;
	}

//GETTERS
	public String dameNombre() {
		return this.nombre;
	}

	public String dameDireccion() {
		return this.direccion;
	}

	public String dameDNI() {
		return this.dni;
	}

	@Override
	public String toString() {
		return this.nombre + this.dni + this.direccion;
	}

	public ArrayList<PaquetePersonalizado> damePaquetesPersonalizados() {
		return this.paquetesPersonalizados;
	}

	public boolean tieneContratacion(Paquete paquete) {
		return paquetesPersonalizados.contains(paquete);
	}
}
