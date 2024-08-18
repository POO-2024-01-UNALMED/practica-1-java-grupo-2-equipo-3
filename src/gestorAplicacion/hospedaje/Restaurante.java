package gestorAplicacion.hospedaje;

import java.util.ArrayList;
import java.util.Map;

import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;

public class Restaurante {

    private static final long serialVersionUID = 1L;
    private boolean permiteSuscripcion;
    private String nombre;
    private Destino destino;
    private int numeroHabitaciones;
    private double precio;                              //[tipo,disponibles,capacidad]    
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;

    private ArrayList<ArrayList<Grupo>> grupos;
    

}
