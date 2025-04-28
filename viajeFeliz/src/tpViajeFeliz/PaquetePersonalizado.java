package tpViajeFeliz;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaquetePersonalizado extends Paquete{
	private boolean estaPago;
	private Cliente cliente;
	private String fechaPago;
	
		public PaquetePersonalizado(Servicio s, Cliente c) {
		super(s);
		if(c == null) {
			throw new RuntimeException("Cliente no puede ser nulo para iniciar un paq personalizado");
		}
		this.cliente = c;
		this.setFechaInicio(calcularFechaInicio());
		this.setCostoTotal(calcularCostoTotal());
	}
		
		public String calcularFechaInicio() {
			LocalDate ld = LocalDate.parse("8000-12-28");
			Fecha nueva = new Fecha();
			String s1 = nueva.getFechaStr();
			for(Servicio s : this.getServicios()) {
				if(s.dameFechaInicio().compareTo(ld.toString()) < 0) {
					s1 = s.dameFechaInicio();
				}
				
		        }
			return s1; 
		}
	
		  
		public String dameListaServiciosConNombre() {
			   StringBuilder resultado = new StringBuilder();
		
			ArrayList<Servicio> serviciosDelPaquete = this.getServicios();
          for (int i = 0; i < serviciosDelPaquete.size(); i++) {
              Servicio serv = serviciosDelPaquete.get(i);
              if (serv instanceof Vuelo) {
                  resultado.append("Vuelo, ");
              } else if (serv instanceof Alojamiento) {
                  resultado.append("Alojamiento, ");
              } else if (serv instanceof Alquiler) {
                  resultado.append("Alquiler, ");
              } else if (serv instanceof Excursion) {
                  resultado.append("Excursion, ");
              }
              else if (serv instanceof PaquetePredefinido) {
		            PaquetePredefinido paquetePredefinido = (PaquetePredefinido) serv;
		            resultado.append(paquetePredefinido.dameListaServiciosConNombre()).append(", ");
		        }
              if (resultado.length() > 0) {
      	        resultado.setLength(resultado.length() - 2);
      	    } 
              if (i < serviciosDelPaquete.size() - 1) {
      	    	resultado.append(", ");
      	    }

          }return resultado.toString();
		}
		

		

		@Override
		public double calcularCostoTotal() {
			double costoTotal = 0;
			int cantServicios = this.getServicios().size();
			if(cantServicios == 1) {
				double costoServ = this.getServicios().get(0).getCostoTotal();
				costoTotal += costoServ;
			}else if(cantServicios == 2) {
				double costoS1 = this.getServicios().get(0).getCostoTotal();
				double costoS2 = this.getServicios().get(1).getCostoTotal();
				double primerDesc = costoS1 - (costoS1 *0.05);
				double segundoDesc =costoS2 - (costoS2 *0.05);
				costoTotal += (primerDesc + segundoDesc);
			}
			if(this.getServicios().size() >=3) {
				double descuentos = 0;
				
				
				for(int i = 0; i < this.getServicios().size();i++){
					double descuentoServ = this.getServicios().get(i).getCostoTotal()*0.10;
					descuentos += descuentoServ;
				}costoTotal -= descuentos;
			}
			
			return costoTotal;
		}
		
		
		
	public Servicio obtenerServicio(int codUnicoServ) {
		Servicio ss = null;
		for(int i = 0; i < this.getServicios().size(); i++) {
			if(getServicios().get(i).getCodUnico() == codUnicoServ) {
				ss = getServicios().get(i);
			}
		}return ss;
	}
	
	public void pagarPaquete(String fechaPago2) {
		if(this.fechaInicio.compareTo(fechaPago2) < 0) {
			throw new RuntimeException("La fecha de inicio es menor a la fecha de pago");
		}
		this.estaPago = true;
		this.fechaPago = fechaPago2;
		
	}


	
	

	//GETTERS / SETTERS
	
	private void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public boolean estaPago() {
		return estaPago;
	}
	
	public Cliente dameCliente() {
		return cliente;
	}
	
	public String dameFechaPago() {
		return fechaPago;
	}
	

	

}

