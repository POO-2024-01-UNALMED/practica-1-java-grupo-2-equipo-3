package gestorAplicacion.actividades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.hospedaje.Hotel;

import java.util.ArrayList;
public class Plan implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ArrayList<Plan> paquetes;
    private String tipo;
    private Destino destino;
    private ArrayList<Actividad> actividades;
    private double precio;
    private int clasificacion;       //Probablemente enum
    private ArrayList<Grupo> grupos;
    private Reserva reserva;
    private int cantidadDias;
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;

    public Plan(String tipo, Destino destino, ArrayList<Actividad> actividades, int clasificacion, Reserva reserva) {
        this.tipo = tipo;
        this.destino = destino;
        this.actividades = actividades;
        this.clasificacion = clasificacion;
        this.reserva = reserva;
        this.reserva.setPlan(this);
        this.tipo = reserva.getTipoPlan();
        this.grupos = new ArrayList<Grupo>();
    }

    public Plan(String tipo, Reserva reserva) {
        this.tipo = tipo;
        this.reserva = reserva;
        this.actividades = new ArrayList<>();
        this.clasificacion = reserva.getClasificacion();
        this.destino = reserva.getDestino();
        this.reserva.setTipoPlan(this.tipo);

    }

    public Plan() {
        this.actividades = new ArrayList<>();
    }
    public ArrayList<Object> mostrarPlaneacionPlan(String opcBusqueda,int clasificacion, TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma){
   	 ArrayList<Object> tabla=new ArrayList<Object>();
   	 
   	 ArrayList<String> posicion1=new ArrayList<>();//"1"=actividades familiares;"2"=disponibilidad de actividades
   	 ArrayList<String> posicion2=new ArrayList<>();;//"1"=actividades ecologicas;"2"=promedio de precios actividades
   	 ArrayList<String> posicion3=new ArrayList<>();;//"1"=actividades ecologicas;"2"=cantidad de personas
   	 ArrayList<String> posicion4=new ArrayList<>();;//"1"=actividades extremas;"2"=cantidad de guias
   	 ArrayList<String> posicion5=new ArrayList<>();;//"1"=actividades acuaticas;"2"= clasificacion mas solicitada
   	 
   	 ArrayList<Actividad> actividadesFiltradas=new ArrayList<>();
   	 for(Actividad actividad:actividades) {
			if(actividad.verificarFiltrosActividad(clasificacion, tipo, fecha, idioma)) {actividadesFiltradas.add(actividad);}
		 }
   	 
   	if(opcBusqueda.equals("1")) {
		 posicion1.add(Integer.toString(cantidadDias));
  		 posicion2.add(Integer.toString(actividades.size()));
  		 posicion3.addAll(Actividad.mostrarNombres(actividadesFiltradas));
  		 posicion4.add(Actividad.mostrarClasificacion(this.clasificacion));
  		 posicion5.add(precio+"$");
	}else {
		posicion1.add(Integer.toString(cantidadDias));
  		 posicion2.add(Actividad.mostrarClasificacion(this.clasificacion));
  		 posicion3.add(Integer.toString(actividades.size()));
  		 posicion4.add(mostrarTipoPredominante().getNombre());
  		 posicion5.add(mostrarTipoPredominante().getDificultad());
	}

   	 tabla.add(0, destino); //0.tipo de plan
        tabla.add(1, posicion1); // 1."1"=actividades familiares;"2"=disponibilidad de actividades
        tabla.add(2, posicion2); // 2."1"=actividades ecologicas;"2"=promedio de precios actividades
        tabla.add(3, posicion3); // 3."1"=actividades ecologicas;"2"=cantidad de personas
        tabla.add(4, posicion4); // 4."1"=actividades extremas;"2"=cantidad de guias
        tabla.add(5, posicion5); // 5."1"=actividades acuaticas;"2"= clasificacion mas solicitada
       
      
   	return tabla; 
   }
    public static ArrayList<Plan> mostrarPaquetesDestino(Destino destino){
    	ArrayList<Plan> paquetesEnDestino=new ArrayList<>();
    	for(Plan paquete:paquetes) {
    		if(paquete.destino.equals(destino)) {paquetesEnDestino.add(paquete);}
    	}
    	return 	paquetesEnDestino;
    }
    /**
     * Añade una actividad a la lista de actividades del plan
     */
    public void añadirActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    /**
     * muestra los nombres de las actividades ingresadas, para que el usuario pueda seleccionarlas
     *
     * @param actividades
     * @return ArrayList<String> con los nombres de las actividades
     */
    public static ArrayList<String> mostrarNombreActividad(ArrayList<Actividad> actividades) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Actividad actividad : actividades) {
            nombres.add(actividad.getNombre());
        }
        return nombres;
    }
    public TiposActividad mostrarTipoPredominante() {
    	int cantidadMayor=0;
    	TiposActividad tipoPredominante=null;
    	for(TiposActividad tipo:TiposActividad.values()) {
    		int cantidad=0;
    		for(Actividad actividad:actividades) {
        		if(actividad.getTipo().contains(tipo)) {cantidad++;}
        	}
    		if(cantidad>cantidadMayor) {
    			cantidadMayor=cantidad;
    			tipoPredominante=tipo;
    		}
    	}
    	return tipoPredominante;
    }

    /**
     * muestra los nombres de las actividades existentes en el plan
     *
     * @return ArrayList<String> con los nombres de las actividades
     */
    public ArrayList<String> mostrarNombreActividad() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Actividad actividad : this.actividades) {
            nombres.add(actividad.getNombre());
        }
        return nombres;
    }

    /**
     * Selecciona las actividades iniciales del plan antes de ingresar la fecha
     *
     * @param actividadesDisponibles
     * @return ArrayList<String> con los nombres de las actividades
     */
    public ArrayList<Actividad> escogerActividadesIniciales(ArrayList<Actividad> actividadesDisponibles, ArrayList<String> seleccionadas) {
        ArrayList<Actividad> seleccionInicial = new ArrayList<>();
        for (String nombre : seleccionadas) {
            for (Actividad actividad : actividadesDisponibles) {
                if (actividad.getNombre().equals(nombre)) {
                    seleccionInicial.add(actividad);
                }
            }
        }
        return seleccionInicial;
    }

    /**
     * Muestra las actividades disponibles para el día
     *
     * @param fecha
     * @param seleccionInicial obtenida en escogerActividadesIniciales
     * @return ArrayList<Actividad> con las actividades disponibles
     */
    public ArrayList<Actividad> actividadesDisponiblesDia(ArrayList<Integer> fecha, ArrayList<Actividad> seleccionInicial) {
        ArrayList<Actividad> actividadesDisponibles = new ArrayList<>();
        for (Actividad actividad : seleccionInicial) {
            ArrayList<Grupo> existenGrupos = Grupo.buscarGrupo(fecha, actividad, this.reserva.getIdiomas().get(0), this.reserva.getClientes());
            if (existenGrupos.size() > 0) {
                actividadesDisponibles.add(actividad);
            } else if (existenGrupos.isEmpty()) {
                ArrayList<Guia> guiasCapacitados = actividad.buscarGuia(this.reserva.getIdiomas().get(0));
                ArrayList<Guia> guiasConDisponibilidad = Guia.buscarDisponibilidad(guiasCapacitados, fecha);
                if (guiasConDisponibilidad.size() > 0) {
                    actividadesDisponibles.add(actividad);
                }
            }
        }
        return actividadesDisponibles;
    }
    
    /**
     * Escoge las actividades del día
     *
     * @param actividadesPosibles obtenidas en actividadesDisponiblesDia
     * @param actividadEscogida  obtenidas en mostrarNombreActividad después de que el usuario las selecciona
     * @param fecha
     */
    public void escogerActividadesDia(ArrayList<Actividad> actividadesPosibles, ArrayList<String> actividadEscogida, ArrayList<Integer> fecha) {
        for (String nombre : actividadEscogida) {
            for (Actividad actividad : actividadesPosibles) {
                if (actividad.getNombre().equals(nombre)) {
                    this.actividades.add(actividad);
                    ArrayList<Grupo> existenGrupos = Grupo.buscarGrupo(fecha, actividad, this.reserva.getIdiomas().get(0), this.reserva.getClientes());
                    if (existenGrupos.size() > 0) {
                        existenGrupos.get(0).getListaReservas().add(this.reserva.getClientes());
                        grupos.add(existenGrupos.get(0));
                    } else {
                        Grupo grupo = new Grupo(actividad, this.reserva.getClientes(), fecha, this.reserva.getIdiomas().get(0));
                        this.grupos.add(grupo);
                    }
                }
            }
        }
        asignarPrecio();
    }

    /**
     * Escoge las actividades del día para un paquete
     *
     * @param actividadesPosibles obtenidas en actividadesDisponiblesDia
     * @param actividadEscogida  obtenidas en mostrarNombreActividad después de que el usuario las selecciona
     * @param fecha
     */
    public void escogerActividadesDiaPaquete(ArrayList<Actividad> actividadesPosibles, ArrayList<String> actividadEscogida, ArrayList<Integer> fecha) {
        for (String nombre : actividadEscogida) {
            for (Actividad actividad : actividadesPosibles) {
                if (actividad.getNombre().equals(nombre)) {
                    ArrayList<Grupo> existenGrupos = Grupo.buscarGrupo(fecha, actividad, this.reserva.getIdiomas().get(0), this.reserva.getClientes());
                    Grupo grupo = new Grupo(actividad, this.reserva.getClientes(), fecha, this.reserva.getIdiomas().get(0));
                    this.grupos.add(grupo);
                }
            }
        }
        asignarPrecio();
    }

    /**
     * Muestra los paquetes turísticos disponibles que cumplan con los parámetros
     *
     * @param cantidadPersonas
     * @param destino
     * @param clasificacion
     * @param fechas
     * @return ArrayList<Plan> con los paquetes turísticos disponibles
     */
    public static ArrayList<Plan> paquetesDisponibles(int cantidadPersonas, Destino destino, int clasificacion, ArrayList<ArrayList<Integer>> fechas) {

        ArrayList<Plan> paquetesPosibles = new ArrayList<>();
        for (Plan plan : paquetes) {
            ArrayList<ArrayList<Integer>> fechasDisponibles = new ArrayList<>();
            for (ArrayList<Integer> fecha : fechas) {
                if (plan.getDestino().equals(destino) &&
                        plan.getClasificacion() <= clasificacion &&
                        plan.getActividades().size() >= fechas.size()) {
                    for (Actividad actividad : plan.getActividades()) {
                        ArrayList<Grupo> existenGrupos = Grupo.buscarGrupo(fecha, actividad, plan.getReserva().getIdiomas().get(0), cantidadPersonas);
                        if (!existenGrupos.isEmpty()) {
                            fechasDisponibles.add(fecha);
                        }
                    }
                }
            }
            if (fechasDisponibles.size() == fechas.size()) {
                paquetesPosibles.add(plan);
            }
        }
        return paquetesPosibles;
    }

    /**
     * Retorna el tipo del plan
     *
     * @param valor obtenido por ingresarOpcion
     * @return String del tipo de plan
     */
    public static String asignarTipo(int valor) {
        if (valor == 1) {
            return "PP";
        } else if (valor == 2) {
            return "PT";
        }
        return "";
    }

    public void asignarPrecio() {
        for (Actividad actividad : getActividades()) {
            this.precio += actividad.getPrecio();
        }
    }

    /**
     * Muestra como imprimir los paquetes turísticos
     * @param plan
     * @return
     */
    public static String stringPaqueteTuristico(Plan plan) {
        String paquete = "Destino: " + plan.getDestino().getNombre() + "\n";
        paquete += "Actividades: ";
        for (Actividad actividad : plan.getActividades()) {
            paquete += actividad.getNombre() + ", ";
        }
        paquete += "\nPrecio por persona: " + plan.getPrecio() + "\n";
        return paquete;
    }


    ////////////////////////////Métodos de acceso////////////////////////////

    public static ArrayList<Plan> getPaquetes() {
        return paquetes;
    }

    public static void setPaquetes(ArrayList<Plan> paquetes1) {
        paquetes = paquetes1;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(ArrayList<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}

