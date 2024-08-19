package gestorAplicacion.hospedaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.lang.reflect.Array;
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
      
    private ArrayList<Grupo> grupos;
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;
    private ArrayList<Restaurante> restaurantes;

    /**
     * Muestra una lista de hoteles disponibles según una reserva específica.
     *
     *
     * @param reserva La reserva que contiene la información de destino y fechas.
     * @return Una lista de cadenas con la información de los hoteles disponibles.
     */
    public static ArrayList<String> mostrarHotelesDisponibles(Reserva reserva) {
        ArrayList<Hotel> hotelesEnDestino = hotelesEnDestino(reserva, cargarHoteles());
        ArrayList<Hotel> hotelesDisponibles = verificarDisponibilidadHotel(reserva, hotelesEnDestino);

        ArrayList<String> hotelesADesplegar = new ArrayList<>();
        // Agrega los hoteles disponibles y su información para que el Cliente Decida
        for (Hotel hotel : hotelesDisponibles) {
            hotelesADesplegar.add(
                hotel.getNombre() +
                "\n Habitaciones para fechas: " + hotel.getDisponibilidadHabitaciones().get(reserva.getFechas().get(0)) +
                "\n Precio Base: " + hotel.getPrecio() +
                "\n Cuenta con posibilidad de descuento: " + (Hotel.hoteleConSuscripcion(hotel, reserva) ? "Sí" : "No")
            );
        }
        return hotelesADesplegar;
    }
    /**
     * Busca el hotel elegido de la ista de hoteles disponibles según una reserva específica.
     *
     *
     * @param reserva La reserva que contiene la información de destino y fechas.
     * @param opcElegida un string de la opcion elegida
     * @return Hotel El objeto del hotel elegido
     */
    public static Hotel buscarHotelLista(Reserva reserva, String opcElegida) {
    	ArrayList<Hotel> hotelesEnDestino = hotelesEnDestino(reserva, cargarHoteles());
        ArrayList<Hotel> hotelesDisponibles = verificarDisponibilidadHotel(reserva, hotelesEnDestino);
        Hotel hotel=hotelesDisponibles.get(Integer.parseInt(opcElegida)-1);
        return hotel;
    }
    

    //Veriifcar si hay disponilibilas habitaciones
    //revisar los grupos que estan asignaso al hotel, si un grupo tien la fecha que estamos buscando:
    //la lista de clietes que estan en ese grupo se suma

    

    public static ArrayList<Hotel> cargarHoteles() {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("src/serializables/listaHoteles.txt");
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

        System.out.println("Total de clientes: " + totalClientes);

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
        
        for (Cliente cliente : reserva.getClientes()) {
            cliente.setHotel(hotelesDisponibles.get(indiceHotelEscogidoInt));
        }
        return hotelesDisponibles.get(indiceHotelEscogidoInt);




    }

    public static Integer numeroDeAdultos(ArrayList<Cliente> clientes) {
        int adultos = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getEdad() >= 18) {
                adultos++;
            }
        }
        return adultos;
    }


    


    public static ArrayList<Grupo> asignarHabitacion(Reserva reserva, Hotel hotel) {
        hotel = asignarHotel(reserva, cargarHoteles());
        ArrayList<String> listaString = new ArrayList<>();
        ArrayList<ArrayList<Cliente>> listaHabitacionesClientes = new ArrayList<>();
        
        // Mostrar la disponibilidad de habitaciones en el hotel
        System.out.println("Disponibilidad de habitaciones en el hotel:");
        for (ArrayList<Object> habitacion : hotel.getDisponibilidadHabitaciones().get(reserva.getFechas().get(0))) {
            String tipoHabitacion = (String) habitacion.get(0);
            Integer disponibles = (Integer) habitacion.get(1);
            Integer capacidad = (Integer) habitacion.get(2);
            listaString.add(tipoHabitacion + " - Disponibles: " + disponibles + " - Capacidad: " + capacidad);
            // Inicializar la lista de clientes para cada habitación
            listaHabitacionesClientes.add(new ArrayList<>()); 
        }
        
        Integer totalHabitaciones = 0;
        String[] habitacionesEscogidasArray = new String[3];
        boolean habitacionesAsignadas = false;
        
        while (!habitacionesAsignadas) {
            String mensaje = "";
            String habitacionesEscogidas = Main.ingresarOpcion(mensaje, 3, listaString);
            habitacionesEscogidasArray = habitacionesEscogidas.split(" ");
            // [Número de sencillas, Número de dobles, Número de suites]
            totalHabitaciones = Integer.parseInt(habitacionesEscogidasArray[0]) +
                                Integer.parseInt(habitacionesEscogidasArray[1]) +
                                Integer.parseInt(habitacionesEscogidasArray[2]);
            Integer capacidadHabitacionesSeleccionada = 2 * Integer.parseInt(habitacionesEscogidasArray[0]) +
                                                        4 * Integer.parseInt(habitacionesEscogidasArray[1]) +
                                                        8 * Integer.parseInt(habitacionesEscogidasArray[2]);

            System.out.println("Capacidad de habitaciones seleccionadas: " + capacidadHabitacionesSeleccionada.toString());
            System.out.println("Numero de Cliente: " + reserva.getClientes().size());
        
            if (totalHabitaciones > numeroDeAdultos(reserva.getClientes())) {
                System.out.println("El número total de habitaciones no puede ser mayor al número de adultos en la reserva.");
                totalHabitaciones = 0;
                
            }

            else if(totalHabitaciones == 0) {
                System.out.println("No se han asignado habitaciones. Por favor intente nuevamente.");
                
            }

            else if (capacidadHabitacionesSeleccionada < reserva.getClientes().size()) {
                System.out.println("La capacidad total de las habitaciones seleccionadas no es suficiente para alojar a todos los adultos en la reserva.");
                totalHabitaciones = 0;             
                
            }
        
             else {
                habitacionesAsignadas = true;
            }
        }
        
        ArrayList<String> listaHabitacionesIndividuales = new ArrayList<>();
        ArrayList<Integer> capacidadHabitaciones = new ArrayList<>();
        ArrayList<Boolean> hayAdultoEnHabitacion = new ArrayList<>();  // Para verificar si hay un adulto en cada habitación
        
        // Crear las habitaciones individuales basadas en la selección
        int numSencillas = Integer.parseInt(habitacionesEscogidasArray[0]);
        int numDobles = Integer.parseInt(habitacionesEscogidasArray[1]);
        int numSuites = Integer.parseInt(habitacionesEscogidasArray[2]);
        
        for (int i = 0; i < numSencillas; i++) {
            listaHabitacionesIndividuales.add("sencilla");
            capacidadHabitaciones.add(2);
            hayAdultoEnHabitacion.add(false);  // Inicialmente no hay adultos en ninguna habitación
        }
        for (int i = 0; i < numDobles; i++) {
            listaHabitacionesIndividuales.add("doble");
            capacidadHabitaciones.add(4);
            hayAdultoEnHabitacion.add(false);
        }
        for (int i = 0; i < numSuites; i++) {
            listaHabitacionesIndividuales.add("suite");
            capacidadHabitaciones.add(8);
            hayAdultoEnHabitacion.add(false);
        }
        
        // Asignar habitaciones a cada cliente
        for (Cliente cliente : reserva.getClientes()) {
            boolean habitacionAsignada = false;
            while (!habitacionAsignada) {
                // Construir el menú directamente como una cadena
                String menu = "";
                for (int i = 0; i < listaHabitacionesIndividuales.size(); i++) {
                    menu += (i + 1) + ". Habitación " + (i + 1) + ": " + listaHabitacionesIndividuales.get(i) + " - capacidad " + capacidadHabitaciones.get(i) + "\n";
                }
        
                String entrada1 = Main.ingresarOpcion(
                    "Seleccione en qué habitación desea alojar a " + cliente.getNombre() + " (Digite el número de una única opción):\n" + menu,
                    0, new ArrayList<>()
                );
        
                int indiceHabitacion;
                try {
                    indiceHabitacion = Integer.parseInt(entrada1) - 1;
                    if (indiceHabitacion < 0 || indiceHabitacion >= listaHabitacionesIndividuales.size()) {
                        System.out.println("La opción ingresada es incorrecta, por favor intente nuevamente.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("La opción ingresada es incorrecta, por favor intente nuevamente.");
                    continue;
                }
        
                // Verificar si el cliente es un adulto o un menor de edad
                if (cliente.getEdad() < 18) {  // Si el cliente es menor de edad
                    if (!hayAdultoEnHabitacion.get(indiceHabitacion)) {  // Verifica si no hay adultos en la habitación seleccionada
                        System.out.println("No puede asignar un menor de edad a una habitación sin adultos. Seleccione otra habitación.");
                        continue;
                    }
                }
        
                // Disminuir la capacidad de la habitación y asignar al cliente
                if (capacidadHabitaciones.get(indiceHabitacion) > 0) {
                    capacidadHabitaciones.set(indiceHabitacion, capacidadHabitaciones.get(indiceHabitacion) - 1);
        
                    // Añadir cliente a la lista correspondiente
                    listaHabitacionesClientes.get(indiceHabitacion).add(cliente);
        
                    // Si el cliente es un adulto, marcar que hay un adulto en esa habitación
                    if (cliente.getEdad() >= 18) {
                        hayAdultoEnHabitacion.set(indiceHabitacion, true);
                    }
                    
                    cliente.setNombre("");
                    System.out.println("Cliente " + cliente.getNombre() + " asignado a habitación tipo " + listaHabitacionesIndividuales.get(indiceHabitacion));
                    
                    
                    habitacionAsignada = true;
                } else {
                    System.out.println("No hay capacidad en la habitación seleccionada.");
                }

                
            }

            
        }
        for (int i = 0; i < listaHabitacionesIndividuales.size(); i++) {
            ArrayList<Cliente> clientes = listaHabitacionesClientes.get(i);
            String tipoHabitacion = listaHabitacionesIndividuales.get(i); // Obtén el tipo de habitación
            Grupo grupo = new Grupo(tipoHabitacion, clientes.size() ); // Pasa el tipo de habitación al crear el grupo
            for (Cliente cliente : clientes) {
                cliente.addGrupo(grupo);
                hotel.agregarGrupo(grupo); 
                
            }
        }

    
        return hotel.getGrupos();

        
        
        
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
            5, // clasificación (ahora int)
            "Todo Incluido", // tipoPlan
            true, // existeSuscripcion
            null // plan
        );


        // Añadir clientes a la reserva
        Cliente cliente1 = new Cliente("Juan Pérez", 30);
        Cliente cliente2 = new Cliente("María López", 25);
        Cliente cliente3 = new Cliente("Pedro Gómez", 10);
        ArrayList<Cliente> clientes = new ArrayList<>(Arrays.asList(cliente1, cliente2, cliente3));
        reserva1.setClientes(clientes);
    

        ArrayList<Grupo> grupos = asignarHabitacion(reserva1, null);

        System.out.println("Hotel asignado: " + cliente1.getHotel().getNombre());

        ArrayList<Grupo> grupos3 = asignarHabitacion(reserva1, null);


        

        


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

    public ArrayList<Grupo> getGrupos() {
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

    public void setGrupos(ArrayList<Grupo> grupos) {
        this.grupos = grupos;
    }

    public void getPermiteSuscripcion(boolean permiteSuscripcion) {
        this.permiteSuscripcion = permiteSuscripcion;
    }

    public void setPermiteSuscripcion(boolean permiteSuscripcion) {
        this.permiteSuscripcion = permiteSuscripcion;
    }

    public void agregarGrupo(Grupo grupo) {
        this.grupos.add(grupo);
    }

    public void setRestaurantes(ArrayList<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public ArrayList<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    

    

}
