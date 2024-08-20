package gestorAplicacion.actividades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.hospedaje.Hotel;

import java.util.ArrayList;
public class Plan implements Serializable {
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

    public Plan(String tipo, Destino destino, ArrayList<Actividad> actividades, double precio, int clasificacion, ArrayList<Grupo> grupos, Reserva reserva) {
        this.tipo = tipo;
        this.destino = destino;
        this.actividades = actividades;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.grupos = grupos;
        this.reserva = reserva;
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

    public void escogerActividadesDia(ArrayList<Actividad> actividadesPosibles, ArrayList<String> actividadEscogidas, ArrayList<Integer> fecha) {
        for (String nombre : actividadEscogidas) {
            for (Actividad actividad : actividadesPosibles) {
                if (actividad.getNombre().equals(nombre)) {
                    this.actividades.add(actividad);
                    ArrayList<Grupo> existenGrupos = Grupo.buscarGrupo(fecha, actividad, this.reserva.getIdiomas().get(0), this.reserva.getClientes());
                    if (existenGrupos.size() > 0) {
                        existenGrupos.get(0).addCliente(this.reserva.getClientes().get(0));
                    } else {
                        ArrayList<Guia> guiasCapacitados = actividad.buscarGuia(this.reserva.getIdiomas().get(0));
                        ArrayList<Guia> guiasConDisponibilidad = Guia.buscarDisponibilidad(guiasCapacitados, fecha);
                        if (guiasConDisponibilidad.size() > 0) {
                            Grupo grupo = new Grupo(fecha, actividad, guiasConDisponibilidad.get(0), this.reserva.getClientes());
                            this.grupos.add(grupo);
                        }
                    }
                }
            }
        }

    }


    /**
     * Muestra los paquetes turísticos disponibles que cumplan con los parámetros
     *
     * @param cantidadPersonas
     * @param destino
     * @param clasificacion
     * @param dias
     * @return ArrayList<Plan> con los paquetes turísticos disponibles
     */
    public static ArrayList<Plan> paquetesDisponibles(int cantidadPersonas, Destino destino, int clasificacion, int dias) {
        ArrayList<Plan> paquetesDisponibles = new ArrayList<>();
        for (Plan plan : paquetes) {
            if (plan.getDestino().equals(destino) && plan.getClasificacion() == clasificacion && plan.getActividades().size() >= dias && plan.getClasificacion() <= clasificacion) {
                paquetesDisponibles.add(plan);
            }
        }
        return paquetesDisponibles;
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

    public ArrayList<Plan> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(ArrayList<Plan> paquetes) {
        this.paquetes = paquetes;
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

