package tpViajeFeliz;

public class Vuelo extends ServicioSimple {
	private String pais, ciudad;
	private String fechaLlegada;
	private double tasa;

	public Vuelo(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada,
			double tasa) {
		super(costoBase, fechaInicio, cantidad);
		if (pais == null || pais == "") {
			throw new RuntimeException("Pais no puede ser nulo");
		}
		if (ciudad == null || ciudad == "") {
			throw new RuntimeException("Ciudad no puede ser nula");
		}
		if (fechaLlegada == null || fechaLlegada == "") {
			throw new RuntimeException("Fecha llegada no puede ser nula");
		}
		if (fechaInicio.compareTo(fechaLlegada) > 0) {
			throw new RuntimeException("Fecha Inicio no puede ser mayor a fecha llegada");
		}
		if (fechaLlegada.compareTo(fechaInicio) < 0) {
			throw new RuntimeException("Fecha Llegada no puede ser menor a fecha inicio");
		}
		if (tasa == 0) {
			throw new RuntimeException("Tasa debe ser > 0");
		}
		this.pais = pais;
		this.ciudad = ciudad;
		this.fechaLlegada = fechaLlegada;
		this.tasa = tasa;
		this.setCostoTotal(calcularCostoTotal());
	}

	@Override
	public double calcularCostoTotal() {
		double costoPorCant = costoBase * cantidad;
		return costoPorCant + (costoPorCant * tasa);
	}

//GETTERS
	public String damePais() {
		return pais;
	}

	public String dameCiudad() {
		return ciudad;
	}

	public String dameFechaLlegada() {
		return fechaLlegada;
	}

	public double dameTasa() {
		return tasa;
	}
}
