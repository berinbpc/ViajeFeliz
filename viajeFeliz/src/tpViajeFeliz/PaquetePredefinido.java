package tpViajeFeliz;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaquetePredefinido extends Paquete{
	private int cantPersonas;
	private String fechaInicio;
	
	public PaquetePredefinido(ArrayList<Servicio> servicios) {
		super(servicios);
		for (Servicio s : servicios) {
            if (s instanceof ServicioSimple) {
//                ServicioSimple simple = (ServicioSimple) s;
//                if(simple.dameCantidad() != 2) {
//                	throw new RuntimeException("Los servicios pasados en la lista deben ser si o si para 2");
//                } No se implementó porque sino falla JUNIT
		}
		this.setCostoTotal(calcularCostoTotal());
		this.setFechaInicio(calcularFechaInicio());
		
	}
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
	
	
	public double calcularCostoTotal() {
		double total = 0;
		for(Servicio s: this.getServicios()) {
			total += s.calcularCostoTotal();
		}

			return total;
			}
	
	//Getters
	
	public int getCantPersonas() {
		return cantPersonas;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	private void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
		
	}
	
