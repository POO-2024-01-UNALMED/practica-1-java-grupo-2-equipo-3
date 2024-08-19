package gestorAplicacion.actividades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.hospedaje.Hotel;

import java.util.ArrayList;
public class Plan implements Serializable {
    private ArrayList<Plan> paquetes;
    private String tipo;
    private Destino destino;
    private ArrayList<Actividad> actividades;
    private double precio;
    private Hotel hotel;
    private int clasificacion;       //Probablemente enum
    private ArrayList<Grupo> grupos;
    private Reserva reserva;
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;

    public Plan(String tipo, Destino destino, ArrayList<Actividad> actividades, double precio, Hotel hotel, int clasificacion, ArrayList<Grupo> grupos, Reserva reserva) {
        this.tipo = tipo;
        this.destino = destino;
        this.actividades = actividades;
        this.precio = precio;
        this.hotel = hotel;
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
    	this.actividades=new ArrayList<>();
    }

    /**
     * Añade una actividad a la lista de actividades del plan
     * 
     */
    public void añadirActividad(Actividad actividad) {
		this.actividades.add(actividad);	
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
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
