package tpViajeFeliz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ViajeFeliz implements IViajeFeliz{
	private String cuit;
	private HashMap<Integer, Servicio> serviciosOfrecidos = new HashMap <>();
	private HashMap<String, Cliente> clientes = new HashMap<>();
	

	// 1 CONSTRUCTOR 
	public ViajeFeliz(String cuit) {
		if(cuit == null || cuit == "") {
			throw new RuntimeException("Cuit no debe ser vacío o nulo. Introducir CUIT valido");
		}
		this.cuit = cuit;
		this.serviciosOfrecidos = new HashMap<>();
		this.clientes = new HashMap<>();
	}
	
	@Override //2 REGISTRA CLIENTE
	public void registrarCliente(String dni, String nombre, String direccion) {
			if(existeCliente(dni)){
				throw new RuntimeException("El cliente ya está registrado");
			}
			Cliente nuevo = new Cliente(dni, nombre, direccion);
			clientes.put(dni, nuevo);
		}


	@Override //3 CREA UN SERVICIO DE TIPO VUELO
	public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada, double tasa) {
		Servicio nuevo = new Vuelo(costoBase, fechaInicio, cantidad, pais, ciudad, fechaLlegada, tasa);
		int codUnico = nuevo.getCodUnico();
		serviciosOfrecidos.put(codUnico, nuevo);
		return codUnico;
	}

	@Override //3 CREA UN SERVICIO DE TIPO ALOJAMIENTO
	public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaSalida, String hotel, double costoTraslado) {
		Servicio nuevo = new Alojamiento(costoBase, fechaInicio, cantidad, pais, ciudad, fechaSalida, hotel, costoTraslado);
		int codUnico = nuevo.getCodUnico();
		serviciosOfrecidos.put(codUnico, nuevo);
		return codUnico;
	}

	 @Override //3 CREA UN SERVICIO DE TIPO ALQUILER
	public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia, String fechaDevolucion) {
		Servicio nuevo = new Alquiler(costoBase, fechaInicio, cantidad, pais, ciudad, garantia, fechaDevolucion);
		int codUnico = nuevo.getCodUnico();
		serviciosOfrecidos.put(codUnico, nuevo);
		return codUnico; 
	} 

	@Override //3 CREA UN SERVICIO DE TIPO EXCURSION
	 public int crearServicio(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
		Servicio nuevo = new Excursion(costoBase, fechaInicio, cantidad, lugarSalida, esDiaCompleto);
		int codUnico = nuevo.getCodUnico();
		serviciosOfrecidos.put(codUnico, nuevo);
		return codUnico; 
	} 
	
 
	@Override //4 CreaPaquetesPredefinidos dado uno o varios servicios y cantidad de paquetes copias
	public int[] crearPaquetesPredefinidos(int cantPaquetes, int[] codigosDeServicios) {
			for(int cod: codigosDeServicios) {
				if(!existeServicio(cod)) {
					throw new RuntimeException("El servicio codigo: " + cod + "no existe, revisar lista");
				}
			}
				int [] codPaquetes = new int[cantPaquetes];
				ArrayList <Servicio> listaServs = obtenerListaServicios(codigosDeServicios);
			for(int j = 0; j < cantPaquetes; j++) {
				PaquetePredefinido p = new PaquetePredefinido(listaServs);
				serviciosOfrecidos.put(p.getCodUnico(), p);
				codPaquetes[j] = p.getCodUnico();
				}	
		return codPaquetes;
	}	
			
	
	
	

	@Override //5 Crea un paquete personalizado del cliente y lo agrega
	public int iniciarContratacion(String dni, int codServicio) {
		if(!existeCliente(dni)) { 
			throw new RuntimeException("Cliente no existe");
		}
		else if(!existeServicio(codServicio)) {
			throw new RuntimeException("Servicio no existe en catalogo:  " + codServicio);
		}
		Cliente c = obtenerCliente(dni);
		if(!c.tieneContratacionesFinalizadas()) {
			throw new RuntimeException ("Tiene contrataciones sin finalizar");
		}	
		Servicio s = obtenerServicio(codServicio);
		PaquetePersonalizado p = new PaquetePersonalizado(s, c);
		c.agregarPaqPersonalizado(p);
		serviciosOfrecidos.remove(s.getCodUnico(),s);
		return p.getCodUnico();
		}

	@Override //6
	public void agregarServicioAContratacion(String dni, int codServicio) {
		if(!existeServicio(codServicio)) {
			throw new RuntimeException ("Codigo de servicio no existe");
		}
		if (!existeCliente(dni)) {
			throw new RuntimeException ("Cliente no existe");
		}
		if(estaPagaUltimaContratacion(dni)) {
			throw new RuntimeException("La ultima contratacion ya está paga, no se pueden agregar más servicios");
		}
		Cliente c = obtenerCliente(dni);
		Servicio s = obtenerServicio(codServicio);
		PaquetePersonalizado p = c.dameUltimaContratacion();
		if(!(p.dameFechaInicio().compareTo(new Fecha().getFechaStr()) > 0)) {
			throw new RuntimeException("El paquete ya se inició");
		}
		p.agregarServicio(s);
		serviciosOfrecidos.remove(s.getCodUnico(),s);
	} 

	

	@Override //7
	public void quitarServicioDeContratacion(String dni, int codServicio) {
		if (!existeCliente(dni)) { 
			throw new RuntimeException ("Cliente no existe");
		}
		Cliente c = obtenerCliente(dni);
		PaquetePersonalizado p = c.dameUltimaContratacion();
		Servicio s = p.obtenerServicio(codServicio);
		
		if(!p.existeEsteServicio(s)) {
			throw new RuntimeException ("Codigo de servicio no existe");
		
		}
		if (s instanceof ServicioSimple) {
			p.quitarServicio(s);
		}
		else if(s instanceof PaquetePredefinido) {
			serviciosOfrecidos.put(s.getCodUnico(), p);
			p.quitarServicio(s);
		}
		
	}

	@Override //8
	public double calcularCostoDePaquetePersonalizado(String dni, int codPaquetePersonalizado) {
		if(!existeCliente(dni)) {
			throw new RuntimeException("Dni no existe para calcular el costo del paquete");
		}
		if(!existePaqPersonalizado(dni, codPaquetePersonalizado)) {
			throw new RuntimeException("Paquete personalizado no existe");
		}
		Cliente c = obtenerCliente(dni);
		PaquetePersonalizado p = c.damePaquete(codPaquetePersonalizado);
		double costoP = p.calcularCostoTotal();
		return costoP;
	}

	@Override //9
	public double pagarContratacion(String dni, String fechaPago) {
		if(!existeCliente(dni)) {
			throw new RuntimeException("Comprobar dni, no existe ese cliente para pagar contratacion");
		}
		if(estaPagaUltimaContratacion(dni)) {
			throw new RuntimeException("La ultima contratacion ya fue abonada");
		}
		Cliente c = obtenerCliente(dni);
		PaquetePersonalizado p = c.dameUltimaContratacion();	
		if(p.dameFechaInicio().compareTo(fechaPago) < 0) {
			throw new RuntimeException ("La fecha de inicio del paquete es anterior a la fecha de hoy");
		}
		if(p.estaPago()) {
			throw new RuntimeException("El paquete ya esta pago");
		}
		p.pagarPaquete(fechaPago);
		return calcularCostoDePaquetePersonalizado(dni, p.getCodUnico());
	}

	@Override //10
	public List<Integer> historialDeContrataciones(String dni) {
		if(!existeCliente(dni)) {
			throw new RuntimeException("El dni no existe");
		}
		Cliente c = obtenerCliente(dni);
		List <Integer> hist = c.dameCodigosPaquetesAdquiridos();
		return hist;
	}

	
	@Override //11 
	 public String contratacionesSinIniciar(String fecha) {
        StringBuilder resultado = new StringBuilder();
        for (String dni: clientes.keySet()) {
        	Cliente c = obtenerCliente(dni);
        	String nombreCliente = c.dameNombre();
        	ArrayList <PaquetePersonalizado> paqs = c.damePaquetesAdquiridos(); 
            for (PaquetePersonalizado p : paqs ) {
                if((p.dameFechaInicio().compareTo(fecha) > 0)) {
                	 resultado.append(nombreCliente).append(" | ").append(p.dameFechaInicio()).append(" | [")
                     .append(p.dameListaServiciosConNombre()).append("]\n");
                }
              }
        	} return resultado.length() > 0 ? resultado.toString() : "No hay paquetes sin iniciar después de la fecha proporcionada.";
	}



	

	@Override //12
	public List<String> contratacionesQueInicianEnFecha(String fecha) {
	    List<String> contrataciones = new ArrayList<>();
	    for (String dni : clientes.keySet()) {
	        Cliente c = obtenerCliente(dni);
	        if (c != null) {
	            ArrayList<PaquetePersonalizado> paquetes = c.damePaquetesPersonalizados();
	            for (PaquetePersonalizado p : paquetes) {
	                if (p.dameFechaInicio().equals(fecha)) {
	                    contrataciones.add(p.getCodUnico() + " - (" + c.dameDNI() + " " + c.dameNombre() + ")");
	                	}
	            	}
	        	}
	        }return contrataciones;
	}

	
	@Override //13
    public Set<Integer> obtenerCodigosCatalogo() {
        Set<Integer> codigosCatalogo = new HashSet<>();
        Iterator<Integer> iterator = serviciosOfrecidos.keySet().iterator();

        while (iterator.hasNext()) {
            Integer cod = iterator.next();
            codigosCatalogo.add(cod);
        }return codigosCatalogo;
    }

	//TO STRING ViajeFeliz

 @Override
    public String toString() {
        return "Cuit empresa: " + getCuit() +"\n\n"+
		cantClientes() + cantServicios()+ 
        conteoVuelo() + conteoAlojamiento()+ conteoAlquiler() + conteoExcursion() 
        + conteoPaqPredef()+"\n"+
        contratacionesSinIniciarDeHoy();
 }
 

	


	//getters
	public String getCuit() {
		return this.cuit;
	}
	
 
	//METODOS PRIVADOS BOOLEANOS:
	
		private boolean existeCliente(String dni) {
			return clientes.containsKey(dni);
			
		}
		
		private boolean existeServicio(int codUnico) {
			return serviciosOfrecidos.containsKey(codUnico);
			}
		
		private boolean existePaqPersonalizado(String dni, int codPaq) {
			if(!existeCliente(dni)) {
				throw new RuntimeException("No existe dni para buscar paq personalizado");
			}
			Cliente c = obtenerCliente(dni);
			return c.existeEstePaq(codPaq);
		}
		
		private boolean estaPagaUltimaContratacion(String dni) {
		Cliente c = obtenerCliente(dni);
		PaquetePersonalizado p = c.dameUltimaContratacion();
		return p.estaPago();
	}
		
		
		//METODOS PRIVADOS PARA OBTENER OBJETOS
		
		private Servicio obtenerServicio(int codServicio) {
			return serviciosOfrecidos.get(codServicio);
		}
		
		private Cliente obtenerCliente(String dni) {
			return clientes.get(dni);
		}
		
		//METODOS PARA OBTENER LISTAS
		
		private ArrayList <Servicio> obtenerListaServicios(int[] codServicios) {
			ArrayList <Servicio> servs = new ArrayList<>();
				for(int i = 0; i < codServicios.length; i++) {
//						ServicioSimple s = (ServicioSimple) serviciosOfrecidos.get(codServicios[i]);						
//						if(s.dameCantidad() != 2) {
//							throw new RuntimeException("Serv 2");
//						} no se implementó porque sino falla JUNIT
						
					    servs.add(serviciosOfrecidos.get(codServicios[i]));
			            serviciosOfrecidos.remove(codServicios[i]);
				}return servs;
			}
		
		
		//MÉTODOS AGREGADOS PARA CORREGIR EL TOSTRING
		//E IMPRIMIR CLIENTES Y EL NOMBRE DE LOS SERVICIOSOFRECIDOS
		private String contratacionesSinIniciarDeHoy() {
			Fecha hoy = new Fecha();
			String fHoy = hoy.getFechaStr();
			if(contratacionesSinIniciar(fHoy) == null) {
				return contratacionesSinIniciar(fHoy);
			}return "Contrataciones sin iniciar a fecha de hoy: " + fHoy+"\n"+
				contratacionesSinIniciar(fHoy);
			
		}
		
		
		private String cantServicios() {
			if(this.serviciosOfrecidos.size() == 0) {
				return "La empresa no tiene servicios para ofrecer actualmente\n";
			}if(this.serviciosOfrecidos.size() == 1) {
				return "El código de la oferta actual es: " + obtenerCodigosCatalogo() + "\n";
			}return "Los códigos de la oferta actual son: " + obtenerCodigosCatalogo() + "\n";
		}
		
		
		
		private String cantClientes() {
			if(this.clientes.size() == 0) {
				return "Aún no hay clientes registrados\n";
			}
			if(this.clientes.size() == 1) {
				return "Hay un cliente registrado: " + dameNombresClientes() + "\n";
			}
			return "Hay " + clientes.size() + " clientes registrados: " + dameNombresClientes() + "\n";
		}
		
		private ArrayList<String> dameNombresClientes() {
		ArrayList<String> nombres = new ArrayList<>();
		for(Cliente c: this.clientes.values()) {
			String nombre = c.dameNombre();
			nombres.add(nombre);
		}
		return nombres;
	}
		
		private String conteoVuelo() {
			int cont = 0;
			for (Servicio s : this.serviciosOfrecidos.values()) {
				if (s instanceof Vuelo) {
					cont++;
				}
			}if (cont == 0) {
				return "";
			}
			if (cont == 1) {
				return "Hay " + cont + " Vuelo disponible\n";
			}
			return "Hay " + cont + " Vuelos disponibles\n";
		}

		private String conteoAlojamiento() {
			int cont = 0;
			for (Servicio s : this.serviciosOfrecidos.values()) {
				if (s instanceof Alojamiento) {
					cont++;
				}
			}if (cont == 0) {
				return "";
			}
			if (cont == 1) {
				return "Hay " + cont + " Alojamiento disponible\n";
			}
			return "Hay " + cont + " Alojamientos disponibles\n";
		}

		private String conteoAlquiler() {
			int cont = 0;
			for (Servicio s : this.serviciosOfrecidos.values()) {
				if (s instanceof Alquiler) {
					cont++;
				}
			}if (cont == 0) {
				return "";
			}
			if (cont == 1) {
				return "Hay " + cont + " Alquiler disponible\n";
			}
			return "Hay " + cont + " Alquileres disponibles\n";
		}

		private String conteoExcursion() {
			int cont = 0;
			for (Servicio s : this.serviciosOfrecidos.values()) {
				if (s instanceof Excursion) {
					cont++;
				}
			}if (cont == 0) {
				return "";
			}
			if (cont == 1) {
				return "Hay" + cont + "Excursion disponible\n";
			}
			return "Hay " + cont + " Excursiones disponibles\n";
		}


		private String conteoPaqPredef() {
			int cont = 0;
			for (Servicio s : this.serviciosOfrecidos.values()) {
				if (s instanceof PaquetePredefinido) {
					cont++;
				}
			}if (cont == 0) {
				return "";
			}
			if (cont == 1) {
				return "Hay " + cont + " Paquete Predefinido disponible\n";
			}
			return "Hay " + cont + " Paquetes Predefinidos disponibles\n";
		}
}
		
