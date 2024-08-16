package gestorAplicacion.hospedaje;

import java.util.ArrayList;

import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;

public class Hotel {
    private String nombre;
    private Destino destino;
    private int numeroHabitaciones;
    private double precio;                              //[tipo,disponibles,capacidad]    
    private ArrayList<ArrayList<Object>> habitaciones; //[[doble,10,4],[sencilla,20,2],[Suite,30,8]]
    private ArrayList<ArrayList<Grupo>> grupos;

    //Veriifcar si hay disponilibilas habitaciones
    //revisar los grupos que estan asignaso al hotel, si un grupo tien la fecha que estamos buscando:
    //la lista de clietes que estan en ese grupo se suma




    public static void main(String[] args) {
    


    }

}
