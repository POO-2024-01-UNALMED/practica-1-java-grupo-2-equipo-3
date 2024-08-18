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
    private String clasificacion;       //Probablemente enum
    private ArrayList<Grupo> grupos;
    private Reserva reserva;
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;

    public Plan(String tipo, Destino destino, ArrayList<Actividad> actividades, double precio, Hotel hotel, String clasificacion, ArrayList<Grupo> grupos, Reserva reserva) {
        this.tipo = tipo;
        this.destino = destino;
        this.actividades = actividades;
        this.precio = precio;
        this.hotel = hotel;
        this.clasificacion = clasificacion;
        this.grupos = grupos;
        this.reserva = reserva;
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
