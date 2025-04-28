package tpViajeFeliz;

public class Alojamiento extends ServicioSimple {
	private String pais, ciudad, fechaSalida, hotel;
	private double costoTraslado;

	public Alojamiento(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad,
			String fechaSalida, String hotel, double costoTraslado) {
		super(costoBase, fechaInicio, cantidad);
		if (cantidad > 6) {
			throw new RuntimeException("Solo se puede de entre 1 a 6 personas a lo sumo");
		}
		if (pais == null || pais == "") {
			throw new RuntimeException("Pais no puede ser nulo");
		}
		if (ciudad == null || ciudad == "") {
			throw new RuntimeException("Ciudad no puede ser nula");
		}
		if (fechaSalida == null || fechaSalida == "") {
			throw new RuntimeException("fechaSalida no puede ser nula");
		}
		if (fechaInicio.compareTo(fechaSalida) > 0) {
			throw new RuntimeException("fecha inicio debe ser < que fechaSalida");
		}
		if (fechaSalida.compareTo(fechaInicio) < 0) {
			throw new RuntimeException("fecha Salida debe ser > que fechaInicio");
		}
		if (hotel == null || hotel == "") {
			throw new RuntimeException("hotel no puede ser nulo o vacio");
		}
		if (costoTraslado == 0) {
			throw new RuntimeException("Costo traslado debe ser > 0");
		}
		this.pais = pais;
		this.ciudad = ciudad;
		this.fechaSalida = fechaSalida;
		this.hotel = hotel;
		this.costoTraslado = costoTraslado;
		this.setCostoTotal(calcularCostoTotal());
	}

	@Override
	public double calcularCostoTotal() {
		Fecha fecha = new Fecha();
		double cantDias = fecha.dameCantDias(fechaInicio, fechaSalida);
		if (cantidad <= 2) {
			return costoBase * cantDias + (costoTraslado);
		} else if (cantidad == 3 || cantidad == 4) {
			return ((costoBase * 2 * cantDias) + (costoTraslado));
		}
		return (costoBase * 2.5) * cantDias + (costoTraslado);
	}

//GETTERS
	public String damePais() {
		return pais;
	}

	public String dameCiudad() {
		return ciudad;
	}

	public String dameFechaSalida() {
		return fechaSalida;
	}

	public String dameHotel() {
		return hotel;
	}

	public double dameCostoTraslado() {
		return costoTraslado;
	}
}
