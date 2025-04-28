package tpViajeFeliz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Fecha {
    private LocalDate fecha;
    private static DateTimeFormatter FORMATTER= DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static String fechaMockeada = null;
    
   
    public Fecha() {
        this.fecha = LocalDate.now();
    }
    
    public static void setFechaMockeada(String fecha) {
        fechaMockeada = fecha;
    }
    
    public Fecha(LocalDate ld) {
    	this.fecha = ld;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(String fechaStr) {
        this.fecha = LocalDate.parse(fechaStr, FORMATTER);
    }
    
    public double dameCantDias(String fechaInicio, String fechaDevolucion) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        // Convertir las fechas de String a LocalDate
	        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
	        LocalDate devolucion = LocalDate.parse(fechaDevolucion, formatter);

	        // Calcular la diferencia en días
	        double dias = ChronoUnit.DAYS.between(inicio, devolucion);

	        // Convertir a int y retornar
	        return dias;
	}

    public String getFechaStr() {
        if (fechaMockeada != null) {
            return fechaMockeada;
        }
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public String fechaMenor(String fechaStr1, String fechaStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha1 = LocalDate.parse(fechaStr1, formatter);
        LocalDate fecha2 = LocalDate.parse(fechaStr2, formatter);
        
        if (fecha1.isBefore(fecha2)) {
            return fechaStr1;
        } else {
            return fechaStr2;
        }
    }
    
    public boolean esFechaMenor(String fechaStr1, String fechaStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha1 = LocalDate.parse(fechaStr1, formatter);
        LocalDate fecha2 = LocalDate.parse(fechaStr2, formatter);
        return fecha1.isBefore(fecha2);
    }
 


}
