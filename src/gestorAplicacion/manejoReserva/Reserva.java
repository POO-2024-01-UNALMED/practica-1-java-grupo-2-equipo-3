package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.actividades.Plan;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.hospedaje.Hotel;


public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ArrayList<Reserva> reservasExistentes = new ArrayList<>();
    private ArrayList<Cliente> clientes;
    private int codigo;
    private static int ultimoCodigo=0;
    private Destino destino;
    private ArrayList<Idiomas> idiomas;
    private ArrayList<ArrayList<Integer>> fechas;
    private int clasificacion;
    private String tipoPlan;
    private boolean existeSuscripcion;
    private Plan plan;
    private boolean itsPlaneacion;
    
    /**
     * Constructor sin parametros de la clase Reserva.
     * Lo necesito asi para mi funcionalidad porfis no lo cambien :(
     * 
     * Asigna un código único a la reserva actual incrementando la variable estática `ultimoCodigo`.
     */
    public Reserva() {
		this.codigo = ++ultimoCodigo;
		this.itsPlaneacion=true;
	}
    public Reserva(Cliente titular, ArrayList<ArrayList<Integer>> fechasViaje) {
        this.codigo = ++ultimoCodigo;
        this.clientes = new ArrayList<Cliente>();
        this.idiomas = new ArrayList<Idiomas>();
        this.clientes.add(titular);
        this.fechas = fechasViaje;
        this.existeSuscripcion = tieneSuscripcion();
        reservasExistentes.add(this);
    }

    public Reserva(Cliente titular, ArrayList<ArrayList<Integer>> fechasViaje, Idiomas idioma, Destino destino){
        this.codigo = ++ultimoCodigo;
        this.clientes = new ArrayList<Cliente>();
        this.clientes.add(titular);
        this.fechas = fechasViaje;
        this.idiomas = new ArrayList<Idiomas>();
        this.idiomas.add(idioma);
        this.destino = destino;
        reservasExistentes.add(this);
    }

    public Reserva(Destino destino, ArrayList<Idiomas> idiomas, ArrayList<ArrayList<Integer>> fechas, int clasificacion, String tipoPlan, boolean existeSuscripcion, Plan plan) {
    	this.codigo = ++ultimoCodigo;
        this.destino = destino;
        this.idiomas = idiomas;
        this.fechas = fechas;
        this.clasificacion = clasificacion;
        this.tipoPlan = tipoPlan;
        this.existeSuscripcion = existeSuscripcion;
        this.plan = plan;
        this.clientes = new ArrayList<Cliente>();
        this.itsPlaneacion=false;
        reservasExistentes.add(this);
    }

    @Override
    public String toString() {
        return  "Estos son los datos de su reserva: \n" +
                "codigo=" + codigo +
                "\n destino=" + destino +
                "\n idiomas=" + idiomas +
                "\n fechas=" + fechas +
                "\n clasificacion=" + clasificacion +
                "\n tipoPlan=" + tipoPlan +
                "\n actividades" + plan.getActividades();
    }


    /**
     * Busca una reserva en la lista de reservas existentes a partir de su código.
     *
     * @param codigo El código de la reserva a buscar.
     * @return La reserva con el código especificado, o null si no se encuentra.
     */
    public static Reserva buscarReserva(int codigo) { 
        for (int i = 0; i < reservasExistentes.size(); i++) {
            if (codigo == reservasExistentes.get(i).codigo) {
                return reservasExistentes.get(i);
            }
        }
        return null;
    }

    //método escogerPlan de la clase Reserva. En este método buscamos todas las actividades posibles a realizar según la clasificación dada que se encuentren en el destino con el método actividadesDisponiblesDestino de la clase Destino, que se le entregará a escogerActividades de la clase Plan donde se seleccionará la misma cantidad de actividades que los días que se van a quedar sin importar el orden de selección y se asigna la lista de actividades de plan.

    /**
     * Escoge un plan de actividades para la reserva actual.
     *
     * @param tipoEscogido El tipo de plan a escoger.
     * @return Una lista de actividades posibles para el plan, o null si no hay actividades disponibles.
     */
    public ArrayList<Actividad> escogerPlan(String tipoEscogido) {
        ArrayList<Actividad> actividadesPosibles = destino.actividadesDisponiblesDestino(clasificacion, clientes.size());
        //Qué hacer si no hay actividades disponibles en un destino que cumpla con los criterios de clasificación y cantidad de personas
        if (actividadesPosibles.isEmpty()) {
            return null;
        }
        else if (fechas.size() >= actividadesPosibles.size()) {
            return null;
        }
        Plan plan = new Plan(tipoEscogido, this);
        this.plan = plan;
        return actividadesPosibles;
    }


    /**
     * Muestra una lista de días consecutivos a partir de una fecha dada.
     *
     * @param cantidadDias La cantidad de días a mostrar.
     * @param fechaInicio       La fecha de inicio en formato [día, mes, año].
     * @return Una lista de listas que representan las fechas en formato [día, mes, año].
     */
    public static ArrayList<ArrayList<Integer>> mostrarDias(int cantidadDias, ArrayList<Integer> fechaInicio) {
        int dia = fechaInicio.get(0);
        int mes = fechaInicio.get(1);
        int año = fechaInicio.get(2);

        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (cantidadDias > 0) {
            int diasMes = diasPorMes[mes - 1];
            int cantidadRestante = cantidadDias;
            for (int i = 1; i < cantidadDias + 1; i++) {
                if (dia > diasMes) {
                    break;
                }
                ArrayList<Integer> fechas = new ArrayList<>();
                fechas.add(dia);
                fechas.add(mes);
                fechas.add(año);
                listaFechas.add(fechas);
                dia++;
                cantidadRestante--;
            }

            if (cantidadRestante == 0) {
                break;
            }

            cantidadDias = cantidadRestante;
            dia = 1;
            mes++;
            if (mes > 12) {
                mes = 1;
                año++;
            }
        }
        return listaFechas;
    }

    /**
     * VARIAS OPCIONES DE ENTRADA
     * Verifica si una lista de números en una cadena está dentro de un rango específico.
     *
     * @param i       El límite superior del rango.
     * @param entrada La cadena que contiene los números a verificar.
     * @return true si todos los números están dentro del rango, false en caso contrario.
     */
    public static boolean verificarLista(int i, String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            return false;
        } else {
            String[] numeros = entrada.split(" ");
            for (String str : numeros) {
                try {
                    int num = Integer.parseInt(str);
                    if (num < 1 || num > i) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * UNICA OPCION
     * Verifica si un número es un entero y si está dentro de un rango específico
     *
     * @param max El límite superior del rango.(si se ingresa 0 solo verifica si es positivo)
     * @param str La cadena que contiene el número a verificar.
     * @return true si el número está dentro del rango, false en caso contrario.
     */
    public static boolean verificarNumero(int max, String str) {
    	try {
	        int num = Integer.parseInt(str);
	        if (max == 0) {
	        	if(num<0) {return false;}
	            return true;
	        }
	        return num >= 1 && num <= max;
	    } catch (NumberFormatException e) {
	        return false;
	    }
    }

    /**
     * Convierte una cadena de fecha en una lista de enteros.
     *
     * @param fechaInicio La cadena de fecha en formato "dd/mm/yyyy".
     * @return Una lista de enteros que representa la fecha [día, mes, año].
     */
    public static ArrayList<Integer> listaFecha(String fechaInicio) {
        String[] R_listaString = fechaInicio.split("/");
        ArrayList<Integer> fecha = new ArrayList<>();
        for (String x : R_listaString) {
            int numero = Integer.parseInt(x);
            fecha.add(numero);
        }
        return fecha;
    }

    /**
     * Verifica si una cadena de fecha es válida.
     *
     * @param fecha La cadena de fecha a verificar.
     * @return true si la fecha es válida, false en caso contrario.
     */
    public static boolean verificarFecha(String fecha) {
        if (fecha == null) {
            return false;
        }
        String[] listaString = fecha.split("/");
        if (listaString.length == 2) {
            if (Reserva.verificarNumero(12, listaString[0])) {
                return true;
            }
        } else if (listaString.length == 3) {
            if (Reserva.verificarNumero(12, listaString[1])) {
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * LISTA DE TODOS LOS DIAS DEL MES (1,MES/AÑO/)
     * Muestra una lista de fechas en función de la opción de fecha y una fecha dada.
     *
     * @param opcFecha La opción de fecha ("1" para mostrar todos los días del mes, de lo contrario solo un día específico).
     * @param fecha    La cadena de fecha en formato "dd/mm/yyyy" o "mm/yyyy".
     * @return Una lista de listas que representan las fechas en formato [día, mes, año].
     */
    public static ArrayList<ArrayList<Integer>> mostrarListaFechas(String opcFecha, String fecha) {
        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();
        String[] listaString = fecha.split("/");

        if (opcFecha.equals("1")) {
            int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            int mes = Integer.parseInt(listaString[0]);
            int año = Integer.parseInt(listaString[1]);

            int cantidadDias = diasPorMes[mes - 1];
            ArrayList<Integer> fechaInicio = new ArrayList<>(Arrays.asList(1, mes, año));

            listaFechas = Reserva.mostrarDias(cantidadDias, fechaInicio);
        } else {
            int dia = Integer.parseInt(listaString[0]);
            int mes = Integer.parseInt(listaString[1]);
            int año = Integer.parseInt(listaString[2]);
            ArrayList<Integer> fechaDia = new ArrayList<>(Arrays.asList(dia, mes, año));

            listaFechas.add(fechaDia);
        }
        return listaFechas;
    }

    /**
     * Convierte una lista genérica en una lista de cadenas.
     *
     * @param lista La lista genérica a convertir.
     * @return La lista convertida de cadenas, o null si no se puede convertir.
     */
    public static ArrayList<String> convertirTipo(Object lista) {
        if (lista instanceof ArrayList<?>) {
            ArrayList<?> listaGenerica = (ArrayList<?>) lista;

            for (Object elemento : listaGenerica) {
                if (!(elemento instanceof String)) {
                    return null;
                }
            }
            @SuppressWarnings("unchecked")
            ArrayList<String> listaString = (ArrayList<String>) lista;
            return listaString;
        }
        return null;
    }
    
    /**
     * Convierte un objeto de tipo Object a una lista de fechas si es posible.
     * Verifica que el objeto sea de tipo ArrayList y que cada sublista contenga elementos de tipo Integer.
     * 
     * @param obj El objeto que se desea convertir.
     * @return Un ArrayList<ArrayList<Integer>> si la conversión es posible, o null si no es posible.
     */
    public static ArrayList<ArrayList<Integer>> convertirListaFechas(Object obj) {
        if (obj instanceof ArrayList<?>) {
            for (Object subList : (ArrayList<?>) obj) {
                if (!(subList instanceof ArrayList<?> && 
                      ((ArrayList<?>) subList).stream().allMatch(item -> item instanceof Integer))) {
                    return null; // Devuelve null si la estructura no es válida
                }
            }
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<Integer>> listaConvertida = (ArrayList<ArrayList<Integer>>) obj;
            return listaConvertida;
        }
        return null; // Devuelve null si el objeto no es una lista
    }
   
    /**
     * Muestra el nombre del mes correspondiente a un número de mes.
     *
     * @param fecha El número del mes (1-12).
     * @return El nombre del mes en español.
     */
    public static String mostrarMes(int fecha) {
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        return meses[fecha - 1];
    }
    /**
     * Comprueba si la lista ingresada es la lista de todos los dias de un mes
     *
     * @param listaFechas lista de fechas a comprobar.
     * @return 'true' si es la lista del mes o 'false' si es una lista diferente.
     */
    public static boolean comprobarEsMes(ArrayList<ArrayList<Integer>> listaFechas) {
    	String mes=listaFechas.get(0).get(1)+"/"+listaFechas.get(0).get(2);
    	ArrayList<ArrayList<Integer>> listaMes=Reserva.mostrarListaFechas("1", mes);
    	if(listaFechas.equals(listaMes)) {
    		return true;
    	}
    	return false;
    }
    /**
     * Retorna la fecha en string
     *
     * @param fecha la fecha a convertir
     * @return String de la fecha
     */
    public static String mostrarFechaString(ArrayList<Integer> fecha) {
    	String fechaString="["+fecha.get(0)+"/"+mostrarMes(fecha.get(1))+"/"+fecha.get(2)+"]";
    	return fechaString;
    }
    /**
     * Determina la clasificación más común para un destino específico basado en las reservas existentes.
     * 
     * @param destino  El destino para el cual se calculará la clasificación más común.
     * 
     * @return         La clasificación más común para el destino. Si no hay reservas para el destino, devolverá 1 por defecto.
     */
    public static int mostrarClasificacionComun(Destino destino) {
    	int clasificacionComun=1;
    	int cantidadMayor=0;
    	for(int i=1;i<=4;i++) {
    		int cantidadClasificacion=0;
    		for(Reserva reserva:reservasExistentes) {
    			if(reserva.destino.equals(destino)&&reserva.clasificacion==i) {cantidadClasificacion++;}
    		}
    		if(cantidadClasificacion>cantidadMayor) {
    			cantidadMayor=cantidadClasificacion;
    			clasificacionComun=i;
    		}
    	}
    	return clasificacionComun;
    }
    public static int mostrarClasificacionComun(Destino destino,ArrayList<ArrayList<Integer>> fechas,Idiomas idioma) {
    	int clasificacionComun=1;
    	int cantidadMayor=0;
    	for(int i=1;i<=4;i++) {
    		int cantidadClasificacion=0;
    		for(ArrayList<Integer>fecha:fechas) {
    			for(Reserva reserva:reservasExistentes) {
        			if(reserva.destino.equals(destino)&&reserva.clasificacion==i&&reserva.fechas.contains(fecha)) {cantidadClasificacion++;}
        		}
    		}
    		if(cantidadClasificacion>cantidadMayor) {
    			cantidadMayor=cantidadClasificacion;
    			clasificacionComun=i;
    		}
    	}
    	return clasificacionComun;
    }
    /**
     * Calcula la cantidad total de personas que han reservado en un destino específico.
     * 
     * @param destino  El destino para el cual se desea calcular la cantidad total de personas.
     * @return         La cantidad total de personas (clientes) que han reservado en el destino especificado.
     */
    public static int mostrarCantidadPersonasDestino(Destino destino) {
    	int cantidad=0;
    	for(Reserva reserva:reservasExistentes) {
    		if(reserva.destino.equals(destino)) {cantidad+=reserva.clientes.size();}
    	}
    	return cantidad;
    }
    /**
     * Calcula la cantidad total de personas que han reservado en un destino y fecha específica.
     * 
     * @param destino  El destino para el cual se desea calcular la cantidad total de personas.
     * @param fecha    La fecha para la cual se desea calcular la cantidad total de personas.
     * @return         La cantidad total de personas (clientes) que han reservado en el destino especificado.
     */
    public static int mostrarCantidadPersonasDestino(Destino destino, ArrayList<ArrayList<Integer>> fechas) {
    	int cantidad=0;
    	for(ArrayList<Integer> fecha:fechas) {
    		for(Reserva reserva:reservasExistentes) {
        		if(reserva.destino.equals(destino)&&reserva.fechas.contains(fecha)) {cantidad+=reserva.clientes.size();}
        	}
    	}
    	
    	return cantidad;
    }
    /**
     * Calcula la cantidad total de personas que han reservado en un destino, idioma y fecha específica.
     * 
     * @param destino  El destino para el cual se desea calcular la cantidad total de personas.
     * @param fecha    La fecha para la cual se desea calcular la cantidad total de personas.
     * @param idioma   el idioma para la cual se desea calcular la cantidad total de personas.
     * @return         La cantidad total de personas (clientes) que han reservado en el destino especificado.
     */
    public static int mostrarCantidadPersonasDestino(Destino destino, ArrayList<ArrayList<Integer>> fechas,Idiomas idioma) {
    	int cantidad=0;
    	for(ArrayList<Integer> fecha:fechas) {
    		for(Reserva reserva:reservasExistentes) {
        		if(reserva.destino.equals(destino)&&reserva.fechas.contains(fecha)&&reserva.idiomas.contains(idioma)) {cantidad+=reserva.clientes.size();}
        	}
    	}
    	return cantidad;
    }
    public static int mostrarCantidadPersonasHotel(Destino destino, ArrayList<ArrayList<Integer>> fechas,Hotel hotel) {
    	int cantidad=0;
    	for(ArrayList<Integer> fecha:fechas) {
    		for(Reserva reserva:reservasExistentes) {
        		if(reserva.destino.equals(destino)&&reserva.fechas.contains(fecha)&&reserva.clientes.get(0).getHotel().equals(hotel)) {cantidad+=reserva.clientes.size();}
        	}
    	}
    	return cantidad;
    }
    public static int mostrarCantidadReservasHotel(Destino destino, ArrayList<ArrayList<Integer>> fechas,Hotel hotel) {
    	int cantidad=0;
    	for(ArrayList<Integer> fecha:fechas) {
    		for(Reserva reserva:reservasExistentes) {
        		if(reserva.destino.equals(destino)&&reserva.fechas.contains(fecha)&&reserva.clientes.get(0).getHotel().equals(hotel)) {cantidad++;}
        	}
    	}
    	return cantidad;
    }
    public static int mostrarCantidadReservasActividad(Destino destino, ArrayList<ArrayList<Integer>> fechas,Actividad actividad) {
    	int cantidad=0;
    	for(ArrayList<Integer> fecha:fechas) {
    		for(Reserva reserva:reservasExistentes) {
        		if(reserva.destino.equals(destino)&&reserva.fechas.contains(fecha)&&reserva.plan.getActividades().contains(actividad)) {cantidad++;}
        	}
    	}
    	return cantidad;
    }
    /**
     * Determina la actividad más popular en un destino específico, basada en la cantidad de personas que han reservado esa actividad.
     * 
     * @param destino  El destino para el cual se desea encontrar la actividad más popular.
     * @return         La actividad más popular en el destino especificado. 
     */
    public static Actividad actividadPrincipalDestino(Destino destino) {
        Actividad actividadComun = null;
        int cantidadMayor = 0;
        for (Actividad actividad : destino.getActividades()) {
            int cantidadPersonas = 0;
            for (Reserva reserva : reservasExistentes) {
                if (reserva.destino.equals(destino) && reserva.plan.getActividades().contains(actividad)) {
                    cantidadPersonas++;
                }
            }
            if (cantidadPersonas > cantidadMayor) {
                cantidadMayor = cantidadPersonas;
                actividadComun = actividad;
            }
        }
        return actividadComun;
    }

    public boolean tieneSuscripcion() {
    	clientes.get(0).getSuscripcion();
        if(clientes.get(0).getSuscripcion()==null) {
        	return false;
        }
        return true;
    }

    public void agregarIdioma(Idiomas idioma) {
    	idiomas.add(idioma);
    }
/////////////////////////MÉTODOS DE INSTANCIA////////////////////////////////////////////

    /**
    * Añade un cliente a la lista de clientes de la reserva.
    *
    * @param nombre El nombre del cliente.
    * @param edad   La edad del cliente.
    */
    public void añadirCliente(String nombre, int edad) {
    	Cliente cliente = new Cliente(nombre, edad);
    	clientes.add(cliente);
    }

    public String aplicarSuscripcion(boolean existeSuscripcion) {
        Suscripcion suscripcion = clientes.getFirst().getSuscripcion();
        int capacidadSuscripcion = suscripcion.getCapacidad();
        for(int i=0; i<capacidadSuscripcion; i++) {
            clientes.get(i).setSuscripcion(suscripcion);
        }
        if (clientes.size() > capacidadSuscripcion) {
            return "La cantidad de personas excede la capacidad de la suscripción, por lo que el descuento se aplicará solo a las primeras " + capacidadSuscripcion + " personas.";
        }
        else {
            return "La suscripción se registró de manera exitosa para todos los integrantes de la reserva.";
        }
    }

    public int menorEdad() {
        int menor = clientes.getFirst().getEdad();
        for (Cliente cliente : clientes) {
            if (cliente.getEdad() < menor) {
                menor = cliente.getEdad();
            }
        }
        return menor;
    }

    public void asignarClasificacion() {
        int menorEdad = menorEdad();
        if (menorEdad < 7) {
            clasificacion = 1;
        } else if (menorEdad < 15) {
            clasificacion = 2;
        } else if (menorEdad < 18) {
            clasificacion = 3;
        } else {
            clasificacion = 4;
        }
    }



//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Métodos de acceso//////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<Reserva> getReservasExistentes() {
        return reservasExistentes;
    }

    public static void setReservasExistentes(ArrayList<Reserva> reservasExistentes) {
        Reserva.reservasExistentes = reservasExistentes;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public ArrayList<Idiomas> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(ArrayList<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public ArrayList<ArrayList<Integer>> getFechas() {
        return fechas;
    }

    public void setFechas(ArrayList<ArrayList<Integer>> fechas) {
        this.fechas = fechas;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public boolean getExisteSuscripcion() {
        return existeSuscripcion;
    }

    public void setExisteSuscripcion(boolean existeSuscripcion) {
        this.existeSuscripcion = existeSuscripcion;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }


}


