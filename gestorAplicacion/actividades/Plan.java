package gestorAplicacion.actividades;

import gestorAplicacion.manejoReserva.*;
import gestorAplicacion.hospedaje.Hotel;

import java.util.ArrayList;
public class Plan {
    private ArrayList<Plan> paquetes;
    private String tipo;
    private Destino destino;
    private ArrayList<Actividad> actividades;
    private double precio;
    private Hotel hotel;
    private String clasificacion;       //Probablemente enum
    private ArrayList<Grupo> grupos;
    private Reserva reserva;

}
