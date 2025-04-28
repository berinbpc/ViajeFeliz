package tpViajeFeliz;

public class Alquiler extends ServicioSimple {
	private String pais, ciudad, fechaDevolucion;
	private double garantia;

	public Alquiler(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia,
			String fechaDevolucion) {
		super(costoBase, fechaInicio, cantidad);
		if (cantidad > 10) {
			throw new RuntimeException("Solo se puede de entre 1 a 10 personas a lo sumo");
		}
		if (pais == null || pais == "") {
			throw new RuntimeException("Pais no puede ser nulo");
		}
		if (ciudad == null || ciudad == "") {
			throw new RuntimeException("Ciudad no puede ser nula");
		}
		if (garantia == 0) {
			throw new RuntimeException("Garantia debe ser > 0");
		}
		if (fechaDevolucion == "" || fechaDevolucion == null) {
			throw new RuntimeException("fechaDevolucion no debe ser nula o vacia");
		}
		if (fechaInicio.compareTo(fechaDevolucion) > 0) {
			throw new RuntimeException("fecha inicio debe ser < que fechaDevolucion");
		}
		if (fechaDevolucion.compareTo(fechaInicio) < 0) {
			throw new RuntimeException("fechaDevolucion debe ser > que fechaInicio");
		}
		this.pais = pais;
		this.ciudad = ciudad;
		this.garantia = garantia;
		this.fechaDevolucion = fechaDevolucion;
		this.setCostoTotal(calcularCostoTotal());
	}

	@Override
	public double calcularCostoTotal() {
		Fecha fecha = new Fecha();
		double cantDias = fecha.dameCantDias(this.fechaInicio, this.fechaDevolucion);
		double costoDiario = costoBase * cantDias;
		if (this.cantidad >= 1 && this.cantidad <= 4) {
			return (costoDiario * cantidad) + garantia;
		}
		if (this.cantidad >= 5 && this.cantidad <= 8) {
			return (costoDiario * cantidad) * 2 + garantia;
		}
		if (this.cantidad == 9 || this.cantidad == 10) {
			return (costoDiario * cantidad) * 3 + garantia;
		}
		return (costoDiario * cantidad) + garantia;
	}

//Getters
	public String dameCiudad() {
		return ciudad;
	}

	public String damePais() {
		return pais;
	}

	public String dameFechaDevolucion() {
		return fechaDevolucion;
	}

	public double dameGarantia() {
		return garantia;
	}
}
