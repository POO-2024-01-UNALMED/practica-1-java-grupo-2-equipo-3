package gestorAplicacion.hospedaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.*;

import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.gestionHum.Cliente;
import uiMain.Main;

public class Hotel implements  Serializable{
    private String nombre;
    private Destino destino;
    private int numeroHabitaciones;
    private double precio;                              //[tipo,disponibles,capacidad]    
    private ArrayList<ArrayList<Object>> diponibilidadHabitaciones; //[[doble,10,4],[sencilla,20,2],[Suite,30,8]]
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;

    private ArrayList<ArrayList<Grupo>> grupos;
    

    //Veriifcar si hay disponilibilas habitaciones
    //revisar los grupos que estan asignaso al hotel, si un grupo tien la fecha que estamos buscando:
    //la lista de clietes que estan en ese grupo se suma

    

    public static ArrayList<Hotel> cargarHoteles() {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("serializables/listaHoteles.txt");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            hoteles = (ArrayList<Hotel>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hoteles;
    }


    ///////////////////////////Aignar Habitacion /////////////////////////////
    ArrayList<Hotel> hoteles = cargarHoteles();

    public static ArrayList<Hotel> hotelesEnDestino(Reserva reserva, ArrayList<Hotel> hoteles){
        reserva.getDestino();
        ArrayList<Hotel> hotelesEnDestino = new ArrayList<Hotel>();

        for (Hotel hotel : hoteles) {
            if (hotel.getDestino().equals(reserva.getDestino())) {
                hotelesEnDestino.add(hotel);
            }
        }

        return hotelesEnDestino;
  
    }

    public static ArrayList<Hotel> verificarDisponibilidadHotel(Reserva reserva, ArrayList<Hotel> hotelesEnDestino) {
        ArrayList<Hotel> hotelesDisponibles = new ArrayList<>();
        int totalClientes = reserva.getClientes().size();
        int adultos = 0;

        // Contar el número de adultos en la reserva
        for (Cliente cliente : reserva.getClientes()) {
            if (cliente.getEdad() >= 18) {
                adultos++;
            }
        }

        if (adultos < 1) {
            return hotelesDisponibles; // Retornar lista vacía si no hay adultos.
        }

        ArrayList<ArrayList<Integer>> fechasReserva = reserva.getFechas();

        for (Hotel hotel : hotelesEnDestino) {
            boolean hotelDisponible = true;

            for (ArrayList<Integer> fecha : fechasReserva) {
                ArrayList<ArrayList<Object>> habitacionesDisponibles = hotel.getDisponibilidadHabitaciones().get(fecha);
                
                if (habitacionesDisponibles == null) {
                    hotelDisponible = false; // Si no hay habitaciones disponibles para la fecha, el hotel no está disponible.
                    break;
                }

                int capacidadTotal = 0;

                // Calcular la capacidad total disponible para la fecha específica
                for (ArrayList<Object> habitacion : habitacionesDisponibles) {
                    int disponibles = (int) habitacion.get(1);
                    int capacidad = (int) habitacion.get(2);

                    capacidadTotal += disponibles * capacidad;
                }

                if (capacidadTotal < totalClientes) {
                    hotelDisponible = false;
                    break;
                }
            }

            if (hotelDisponible) {
                hotelesDisponibles.add(hotel);
            }
        }

        return hotelesDisponibles;
    }



    public static void asignarHabitacion(Reserva reserva, ArrayList<Hotel> listaHoteles){

        ArrayList<Hotel> hotelesEnDestino =  hotelesEnDestino(reserva, listaHoteles);
        ArrayList<Hotel> hotelesDisponibles = verificarDisponibilidadHotel(reserva, hotelesEnDestino);

        ArrayList<>

        Main.ingresarOpcion("Seleccion el hotel en el que se desea alojar ", 1, null);
        
        
            




    }


    public static void main(String[] args) {


   
        asignarHabitacion(null, null);

    }






    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////// Metodos De Acceso /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    public String getNombre() {
        return nombre;
    }

    public Destino getDestino() {
        return destino;
    }

    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public double getPrecio() {
        return precio;
    }

    public Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> getDisponibilidadHabitaciones() {
        return disponibilidadHabitaciones;
    } 

    public ArrayList<ArrayList<Grupo>> getGrupos() {
        return grupos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public void setNumeroHabitaciones(int numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDisponibilidadHabitaciones(Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones) {
        this.disponibilidadHabitaciones = disponibilidadHabitaciones;
        
    }

    public void setGrupos(ArrayList<ArrayList<Grupo>> grupos) {
        this.grupos = grupos;
    }

    

}
