package gestorAplicacion.hospedaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.NoSuchElementException;

import gestorAplicacion.actividades.Plan;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.gestionHum.Cliente;
import uiMain.Main;

public class Hotel implements  Serializable{
    private static final long serialVersionUID = 1L;
    private boolean permiteSuscripcion;
    private String nombre;
    private Destino destino;
    private int numeroHabitaciones;
    private double precio;                              //[tipo,disponibles,capacidad]    
    private ArrayList<ArrayList<Object>> diponibilidadHabitaciones; //[[20,8,2024] = [doble,10,4],[sencilla,20,2],[Suite,30,8]]
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
            if (hotel.getDestino().getNombre().toUpperCase().equals(reserva.getDestino().getNombre().toUpperCase())) {
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



    
    public static boolean hoteleConSuscripcion(Hotel hotel, Reserva reserva){
        ArrayList<Hotel> hotelesConSuscripcion = new ArrayList<Hotel>();

        
        if (reserva.getExisteSuscripcion()) {
            if (hotel.permiteSuscripcion) {
                hotelesConSuscripcion.add(hotel);
                return true;
                }            
            }
        
        
        return false;

    }

    public static Hotel asignarHotel(Reserva reserva, ArrayList<Hotel> listaHoteles){

        ArrayList<Hotel> hotelesEnDestino =  hotelesEnDestino(reserva, listaHoteles);
        ArrayList<Hotel> hotelesDisponibles = verificarDisponibilidadHotel(reserva, hotelesEnDestino);

        if (hotelesDisponibles.size() == 0) {
            System.out.println("No hay hoteles disponibles para la reserva seleccionada.");
            return null;
        }

        ArrayList<String> hotelesADesplegar = new ArrayList<>();

        

        // Agrega los hoteles disponibles y su informacion para que el Cliente Decida
        for (Hotel hotel : hotelesDisponibles) {
    hotelesADesplegar.add(
        hotel.getNombre() + 
        "\n Habitaciones para fechas: " + hotel.getDisponibilidadHabitaciones().get(reserva.getFechas().get(0)) + 
        "\n Precio Base: " + hotel.getPrecio() +
        "\n Cuenta con posibilidad de descuento: " + (Hotel.hoteleConSuscripcion(hotel,reserva) ? "Sí" : "No")
        );

        }

        String indiceHotelEscogido = Main.ingresarOpcion("Seleccione el hotel en el cual se desea hospedar", 0, hotelesADesplegar);
        int indiceHotelEscogidoInt = Integer.parseInt(indiceHotelEscogido) - 1;
        System.out.println("Hotel escogido: " + hotelesDisponibles.get(indiceHotelEscogidoInt).getNombre());
        return hotelesDisponibles.get(indiceHotelEscogidoInt);




    }

    public static Integer numeroDeAdultosReserva(Reserva reserva) {
        int adultos = 0;
        for (Cliente cliente : reserva.getClientes()) {
            if (cliente.getEdad() >= 18) {
                adultos++;
            }
        }
        return adultos;
    }

    public static void asignarHabitacion(Reserva reserva, Hotel hotel) {
        ArrayList<String> listaString = new ArrayList<String>();
        // lista de habitaciones disponibles
        for (ArrayList<Object> habitacion : hotel.getDisponibilidadHabitaciones().get(reserva.getFechas().get(0))) {
            listaString.add(habitacion.get(0) + " - Disponibles: " + habitacion.get(1) + " - Capacidad: " + habitacion.get(2));
        }

        Integer totalHabitaciones = 0;

        while (totalHabitaciones < numeroDeAdultosReserva(reserva)) {
            String habitacionesEscogidas = Main.ingresarOpcion("Porfavor seleccione las habitaciones que desea de cada tipo, de la siguiente forma [Num sencilla, Num Doble, Num suites] \nTenga presente que no pude asiganr un numero Total de habitaciones mayor al numero de adultos en su reserva, " + "numero de adultos: " + 
            numeroDeAdultosReserva(reserva), 1, listaString);
        
            String[] habitacionesEscogidasArray = habitacionesEscogidas.split(" ");
            //[Numero de sencillas, Numero de dobles, Numero de suites]
            totalHabitaciones = Integer.parseInt(habitacionesEscogidasArray[0]) + Integer.parseInt(habitacionesEscogidasArray[1]) + Integer.parseInt(habitacionesEscogidasArray[2]);
            if(totalHabitaciones > numeroDeAdultosReserva(reserva)){
                System.out.println("El numero total de habitaciones no puede ser mayor al numero de adultos en la reserva");
            }
            
        }


    
    }
    
        

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// main ////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {


        // ejemplos de Destinos
        Destino destino1 = new Destino("Medellin");
        Destino destino2 = new Destino("Cartagena");

        // fechas de ejemplo
        ArrayList<ArrayList<Integer>> fechas1 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(18, 8, 2024)),
            new ArrayList<>(Arrays.asList(19, 8, 2024))
        ));
        

        //reservas
        Reserva reserva1 = new Reserva(
            destino1, // destino
            new ArrayList<>(Arrays.asList(Idiomas.ESPANOL, Idiomas.INGLES)), // idiomas
            fechas1, // fechas
            "5", // clasificación (ahora int)
            "Todo Incluido", // tipoPlan
            true, // existeSuscripcion
            null // plan
        );


        // Añadir clientes a la reserva
        reserva1.añadirCliente("Juan Pérez", 30);
        reserva1.añadirCliente("Ana Gómez", 25);

        ArrayList<Hotel> listaHoteles = cargarHoteles();
        

        Hotel hotelSeleccionado = asignarHotel(reserva1, listaHoteles);

        asignarHabitacion(reserva1, hotelSeleccionado);


        

        


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

    public void getPermiteSuscripcion(boolean permiteSuscripcion) {
        this.permiteSuscripcion = permiteSuscripcion;
    }

    public void setPermiteSuscripcion(boolean permiteSuscripcion) {
        this.permiteSuscripcion = permiteSuscripcion;
    }

    

}
