package tpViajeFeliz;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Paquete extends Servicio{
	
	private ArrayList <Servicio> servicios = new ArrayList<>();
	public Paquete(ArrayList <Servicio> servicios){
		super();
		if(servicios.isEmpty() || servicios == null) {
			throw new RuntimeException("La lista de servicios de un paquete no puede estar vacía");
		}
		this.servicios = servicios;
	}
	
	public Paquete(Servicio s) {
		if(s == null) {
			throw new RuntimeException("Servicio no puede ser nulo en un paquete");
		}
		this.servicios.add(s);
	}
		
	
	@Override
	protected abstract double calcularCostoTotal();

	public String dameListaServiciosConNombre() {
		   StringBuilder resultado = new StringBuilder();
	
		ArrayList<Servicio> serviciosDelPaquete = this.getServicios();
for (int i = 0; i < serviciosDelPaquete.size(); i++) {
    Servicio serv = serviciosDelPaquete.get(i);
    if (serv instanceof Vuelo) {
        resultado.append("Vuelo");
    } else if (serv instanceof Alojamiento) {
        resultado.append("Alojamiento");
    } else if (serv instanceof Alquiler) {
        resultado.append("Alquiler");
    } else if (serv instanceof Excursion) {
        resultado.append("Excursion");
    }
	     if (i < serviciosDelPaquete.size() - 1) {
	    	resultado.append(", ");
	    }

  }return resultado.toString();
	}
	
	


	@Override
	public String dameFechaInicio() {
        LocalDate fechaInicio = null;

        // Iterar sobre los servicios para encontrar la fecha de inicio más temprana
        for (Servicio servicio : getServicios()) {
            String fechaServicioStr = servicio.dameFechaInicio();
            LocalDate fechaServicio = LocalDate.parse(fechaServicioStr);

          
            if (fechaInicio == null || fechaServicio.isBefore(fechaInicio)) {
                fechaInicio = fechaServicio;
            }
        }

        // Devolver la fecha de inicio más temprana como cadena en formato "yyyy-MM-dd"
        return fechaInicio != null ? fechaInicio.toString() : null;
    }
	public void agregarServicio(Servicio s) {
		this.servicios.add(s);
	}
	
	public void quitarServicio(Servicio s) {
		this.servicios.remove(s);
	}
	
	public boolean existeEsteServicio(Servicio s) {
		return this.servicios.contains(s);
	}
	
	public boolean existeEsteServicio(int codServicio) {
		for(Servicio s: this.getServicios()) {
			if(s.getCodUnico() == codServicio) {
				return true;
			}
		}return false; 
	}


	public ArrayList <Servicio> getServicios() {
		return servicios;
	}
	
	public void setServicios(ArrayList<Servicio> lista) {
		this.servicios = lista;
	}

}