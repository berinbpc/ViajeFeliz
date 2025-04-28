package tpViajeFeliz;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Servicio {
	private int codUnico;
	private double costoTotal;
	protected String fechaInicio;
	private static final AtomicInteger idCounter = new AtomicInteger(1);

	public Servicio() {
		this.codUnico = generarCodigoUnico();
	}

	public Servicio(String fechaInicio) {
		if (fechaInicio == null || fechaInicio == "") {
			throw new RuntimeException("Fecha de inicio esta vacía");
		}
		this.codUnico = generarCodigoUnico();
		this.fechaInicio = fechaInicio;
	}

	protected abstract double calcularCostoTotal();

	private int generarCodigoUnico() {
		return idCounter.getAndIncrement();
	}

	public int getCodUnico() {
		return codUnico;
	}

	public double getCostoTotal() {
		return this.costoTotal;
	}

	public void setCostoTotal(double c) {
		this.costoTotal = c;
	}

	public String dameFechaInicio() {
		return this.fechaInicio;
	}
}
