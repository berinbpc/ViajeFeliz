package tpViajeFeliz;

public abstract class ServicioSimple extends Servicio {
	protected double costoBase;
	protected int cantidad;

	public ServicioSimple(double costoBase, String fechaInicio, int cantidad) {
		super(fechaInicio);
		if (costoBase == 0) {
			throw new RuntimeException("Introduzca costo base >0");
		}
		if (cantidad == 0) {
			throw new RuntimeException("Cantidad no puede ser cero, mínimo una persona");
		}
		this.costoBase = costoBase;
		this.cantidad = cantidad;
	}

	protected abstract double calcularCostoTotal();

	public int dameCantidad() {
		return cantidad;
	}
}
