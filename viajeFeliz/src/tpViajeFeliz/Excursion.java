package tpViajeFeliz;

public class Excursion extends ServicioSimple {
	private String lugarSalida;
	private boolean esDiaCompleto;

	public Excursion(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
		super(costoBase, fechaInicio, cantidad);
		if (lugarSalida == "" || lugarSalida == null) {
			throw new RuntimeException("Lugar salida no puede ser nulo");
		}
		this.lugarSalida = lugarSalida;
		this.esDiaCompleto = esDiaCompleto;
		this.setCostoTotal(calcularCostoTotal());
	}

	@Override
	public double calcularCostoTotal() {
		double basePorCant = costoBase * cantidad;
		double costoTotal = 0;
		if (!esDiaCompleto) {
			if (cantidad == 2) {
				costoTotal += basePorCant - (basePorCant * 0.05);
			} else if (cantidad == 3 || cantidad == 4) {
				costoTotal += basePorCant - (basePorCant * 0.10);
			} else if (cantidad <= 1 || cantidad > 4) {
				costoTotal += basePorCant;
			}
		} else if (esDiaCompleto) {
			basePorCant *= 2;
			if (cantidad == 2) {
				costoTotal += basePorCant - (basePorCant * 0.05);
			} else if (cantidad == 3 || cantidad == 4) {
				costoTotal += basePorCant - (basePorCant * 0.10);
			} else if (cantidad <= 1 || cantidad > 4) {
				costoTotal += basePorCant;
			}
		}
		return costoTotal;
	}

//Getters
	public String dameLugarSalida() {
		return lugarSalida;
	}

	public boolean esDiaCompleto() {
		return esDiaCompleto;
	}
}
