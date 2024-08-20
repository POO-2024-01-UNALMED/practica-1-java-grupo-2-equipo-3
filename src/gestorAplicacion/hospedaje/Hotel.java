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
    private static final long serialVersionUID = 4L;
    private boolean permiteSuscripcion = false;
    private String nombre;
    private Destino destino;
    private int numeroHabitaciones;
    private double precio;                              //[tipo,disponibles,capacidad]    
    private Boolean cuentaConSuscripcion = false; // Boleeano si el cliente seleccion un hotel con suscripcion y todas las condiciones se cumplen
    double precioFinalHospedaje = 0;
      
    private ArrayList<Grupo> grupos;
    private Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidadHabitaciones;
    private ArrayList<Restaurante> restaurantes;

    public Hotel() {
        this.grupos = new ArrayList<>(); // Inicializar la lista de grupos
        this.restaurantes = new ArrayList<>();
    }
    /**
     * Muestra una planificación de hoteles según el idioma y otras opciones de búsqueda.
     *
     * @param opcBusqueda Una opción de búsqueda que define el formato de los datos devueltos. 
     *                    "1" para mostrar detalles específicos de restaurantes y hoteles, 
     *                    cualquier otro valor para mostrar un resumen general.
     * @param clasificacion La clasificación de los hoteles a filtrar.
     * @param tipo El tipo de actividad a filtrar.
     * @param fechas Una lista de fechas para filtrar los hoteles.
     * @param idioma El idioma para el cual se desea obtener la planificación.
     * @return Un ArrayList de objetos que contiene el nombre del hotel y listas con datos
     *         sobre hoteles y restaurantes según la opción de búsqueda.
     */
    public ArrayList<Object> mostrarPlaneacionHotel(String opcBusqueda,int clasificacion, TiposActividad tipo,ArrayList<ArrayList<Integer>> fechas,Idiomas idioma){
   	 ArrayList<Object> tabla=new ArrayList<Object>();
   	 
   	 ArrayList<String> posicion1=new ArrayList<>();//"1"=actividades familiares;"2"=disponibilidad de actividades
   	 ArrayList<String> posicion2=new ArrayList<>();;//"1"=actividades ecologicas;"2"=promedio de precios actividades
   	 ArrayList<String> posicion3=new ArrayList<>();;//"1"=actividades ecologicas;"2"=cantidad de personas
   	 ArrayList<String> posicion4=new ArrayList<>();;//"1"=actividades extremas;"2"=cantidad de guias
   	 ArrayList<String> posicion5=new ArrayList<>();;//"1"=actividades acuaticas;"2"= clasificacion mas solicitada
   	 ArrayList<String> posicion6=new ArrayList<>();;//"1"=actividades deportivas;"2"=oferta
   	 ArrayList<String> posicion7=new ArrayList<>();;//"1"=total de actividades;"2"=[];
   	 
   	 
   	ArrayList<Hotel> hotelesFiltrados=new ArrayList<>();
	 for (ArrayList<Integer> fecha:fechas) {
		 hotelesFiltrados.addAll(Hotel.mostrarHotelesFiltrados(destino, fecha));
	 }
   	 if(opcBusqueda.equals("1")) {
   		 posicion1.add(Integer.toString(restaurantes.size()));
  		 posicion2.addAll(Restaurante.mostrarNombres(restaurantes));
  		 posicion3.add(Restaurante.promedioPrecio(restaurantes)+"");
  		 posicion4.add(Integer.toString(hotelesFiltrados.size()));
  		 posicion5.add(promedioPreciosHoteles(hotelesFiltrados)+"");
  		 posicion6.add(definirOferta(hotelesFiltrados));
   	 }else {
   		 posicion1.add(Integer.toString(hotelesFiltrados.size()));
   		 posicion2.add(promedioPreciosHoteles(hotelesFiltrados)+"");
   		 posicion3.add(Integer.toString(Reserva.mostrarCantidadPersonasHotel(destino, fechas, this)));
   		 posicion4.add(Integer.toString(restaurantes.size()));
   		 posicion5.add(Integer.toString(Reserva.mostrarCantidadReservasHotel(destino, fechas, this)));
   		 posicion6.add(definirOferta(hotelesFiltrados));
   	 }
   	 
   	 tabla.add(0, nombre); //0.nombre del hotel
        tabla.add(1, posicion1); // 1."1"=actividades familiares;"2"=disponibilidad de actividades
        tabla.add(2, posicion2); // 2."1"=actividades ecologicas;"2"=promedio de precios actividades
        tabla.add(3, posicion3); // 3."1"=actividades ecologicas;"2"=cantidad de personas
        tabla.add(4, posicion4); // 4."1"=actividades extremas;"2"=cantidad de guias
        tabla.add(5, posicion5); // 5."1"=actividades acuaticas;"2"= clasificacion mas solicitada
        tabla.add(6, posicion6); // 6."1"=actividades deportivas;"2"=oferta
        tabla.add(7, posicion7); // 7."1"=total de actividades;"2"=[];
      
   	return tabla; 
   }
    public String definirOferta(ArrayList<Hotel> hotelesLista) {
   	 String oferta=null;
   	 if(hotelesLista.size()>cantidadHotelesDestino(destino)*0.75) {oferta="Alta";}
   	 else if(hotelesLista.size()>cantidadHotelesDestino(destino)*0.45) {oferta="Normal";}
   	 else {oferta="Baja";}
   	 return oferta;
    }
    /**
     * Filtra y devuelve una lista de hoteles disponibles en un destino específico para una fecha dada.
     *
     * @param destino  El destino donde se buscan los hoteles disponibles.
     * @param fecha    La fecha para la cual se busca disponibilidad. Se espera que sea una lista de enteros que representa la fecha.
     *
     * @return         Una lista de hoteles que están disponibles en el destino dado para la fecha especificada.
     */
    public static ArrayList<Hotel> mostrarHotelesFiltrados(Destino destino,ArrayList<Integer> fecha){
    	 ArrayList<Hotel> hotelesDisponibles = new ArrayList<>();
    	for(Hotel hotel:cargarHoteles()) {
    		if(hotel.getDestino().equals(destino)&&hotel.getDisponibilidadHabitaciones().get(fecha)!=null) {hotelesDisponibles.add(hotel);}
    	}
    	return hotelesDisponibles;
    }
    public static ArrayList<Hotel> mostrarHotelesFiltrados(Destino destino){
   	 ArrayList<Hotel> hotelesDisponibles = new ArrayList<>();
   	for(Hotel hotel:cargarHoteles()) {
   		if(hotel.getDestino().equals(destino)) {hotelesDisponibles.add(hotel);}
   	}
   	return hotelesDisponibles;
   }
    /**
     * Calcula el promedio de precios de una lista de hoteles.
     *
     * @param hoteles Una lista de objetos Hotel de los cuales se calculará el promedio de precios.
     * @return El promedio de los precios de los hoteles en la lista. Si la lista está vacía, retorna 0.
     */
    public static long promedioPreciosHoteles(ArrayList<Hotel> hoteles) {
        long promedio = 0;
        int cantidad = hoteles.size();
        
        if (cantidad == 0) {return 0;}
        for (Hotel hotel : hoteles) {
            promedio += hotel.precio;
        }
        return promedio / cantidad;
    }
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
    
    /**
     * Busca la cantidad de hoteles en un destino
   
     * @param destino el destino que se dea buscar
     * @return Hotel El objeto del hotel elegido
     */
    public static int cantidadHotelesDestino(Destino destino) {
    	int cantidadHoteles=0;
    	for(Hotel hotel:cargarHoteles()) {
    		if(hotel.getDestino().equals(destino)) {cantidadHoteles++;}
    	}
    	return cantidadHoteles;
    }
    
    /**
     * Calcula el promedio de los precios de hoteles para un destino específico.
     * 
     * @param destino  El destino para el cual se calculará el promedio de los precios de hoteles.
     * @return         El promedio de los precios de los hoteles en el destino especificado.
     */
    public static long promedioPreciosActividades(Destino destino) {
        long promedio = 0;
        int cantidad = cantidadHotelesDestino(destino);
        
        if (cantidad == 0) {return 0;}
        for (Hotel hotel : cargarHoteles()) {
            if (hotel.getDestino().equals(destino)) {promedio += hotel.precio;}
        }

        return promedio / cantidad;
    }
    //Veriifcar si hay disponilibilas habitaciones
    //revisar los grupos que estan asignaso al hotel, si un grupo tien la fecha que estamos buscando:
    //la lista de clietes que estan en ese grupo se suma

    

    public static ArrayList<Hotel> cargarHoteles() {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("src/baseDatos/listaHoteles.txt");
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
        ArrayList<Hotel> listaHoteles = cargarHoteles();
        if (hotel.permiteSuscripcion && reserva.getExisteSuscripcion()) {
            hotel.cuentaConSuscripcion = true;
        }
    
        ArrayList<String> listaString = new ArrayList<>();
        ArrayList<ArrayList<Cliente>> listaHabitacionesClientes = new ArrayList<>();
    
        // Verificar si las fechas de la reserva son nulas
        if (reserva.getFechas() == null) {
            throw new IllegalArgumentException("Las fechas de la reserva no pueden ser nulas.");
        }
    
        // Mostrar la disponibilidad de habitaciones en el hotel
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
            String mensaje = "Por favor seleccione las habitaciones que desea de cada tipo, de la siguiente forma [Num sencilla, Num Doble, Num suites]";
            String habitacionesEscogidas = Main.ingresarOpcion(mensaje, 3, listaString);
            habitacionesEscogidasArray = habitacionesEscogidas.split(" ");
            // [Número de sencillas, Número de dobles, Número de suites]
            totalHabitaciones = Integer.parseInt(habitacionesEscogidasArray[0]) +
                                Integer.parseInt(habitacionesEscogidasArray[1]) +
                                Integer.parseInt(habitacionesEscogidasArray[2]);
            Integer capacidadHabitacionesSeleccionada = 2 * Integer.parseInt(habitacionesEscogidasArray[0]) +
                                                        4 * Integer.parseInt(habitacionesEscogidasArray[1]) +
                                                        8 * Integer.parseInt(habitacionesEscogidasArray[2]);
    
            // Verificar si hay suficiente capacidad para cada tipo de habitación
            boolean capacidadSuficiente = true;
            for (int i = 0; i < listaString.size(); i++) {
                String[] partes = listaString.get(i).split(" - ");
                String tipoHabitacion = partes[0];
                int disponibles = Integer.parseInt(partes[1].split(": ")[1]);
                int seleccionadas = Integer.parseInt(habitacionesEscogidasArray[i]);
    
                if (seleccionadas > disponibles) {
                    capacidadSuficiente = false;
                    break;
                }
            }
    
            if (!capacidadSuficiente) {
                continue;
            }
    
            if (totalHabitaciones > numeroDeAdultos(reserva.getClientes())) {
                totalHabitaciones = 0;
            } else if (totalHabitaciones == 0) {
            } else if (capacidadHabitacionesSeleccionada < reserva.getClientes().size()) {
                totalHabitaciones = 0;
            } else {
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
                        continue;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
    
                // Verificar si el cliente es un adulto o un menor de edad
                if (cliente.getEdad() < 18) {  // Si el cliente es menor de edad
                    if (!hayAdultoEnHabitacion.get(indiceHabitacion)) {  // Verifica si no hay adultos en la habitación seleccionada
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
    
                    habitacionAsignada = true;
                }
            }
        }
    
        // Actualizar la capacidad de las habitaciones en el mapa disponibilidadHabitaciones del hotel
        for (int i = 0; i < listaHabitacionesIndividuales.size(); i++) {
            String tipoHabitacion = listaHabitacionesIndividuales.get(i);
            int habitacionesSeleccionadas = 0;
            if (tipoHabitacion.equals("sencilla")) {
                habitacionesSeleccionadas = numSencillas;
            } else if (tipoHabitacion.equals("doble")) {
                habitacionesSeleccionadas = numDobles;
            } else if (tipoHabitacion.equals("suite")) {
                habitacionesSeleccionadas = numSuites;
            }
    
            ArrayList<ArrayList<Object>> habitacionesDisponibles = hotel.getDisponibilidadHabitaciones().get(reserva.getFechas().get(0));
            for (ArrayList<Object> habitacion : habitacionesDisponibles) {
                if (habitacion.get(0).equals(tipoHabitacion)) {
                    habitacion.set(1, (Integer) habitacion.get(1) - habitacionesSeleccionadas);
                }
            }
        }
    
        for (int i = 0; i < listaHabitacionesIndividuales.size(); i++) {
            ArrayList<Cliente> clientes = listaHabitacionesClientes.get(i);
            String tipoHabitacion = listaHabitacionesIndividuales.get(i); // Obtén el tipo de habitación
            Grupo grupo = new Grupo(tipoHabitacion, clientes.size()); // Pasa el tipo de habitación al crear el grupo
            for (Cliente cliente : clientes) {
                cliente.addGrupo(grupo);
                hotel.agregarGrupo(grupo);
            }
        }
    
        hotel.precioFinalHospedaje = calcularPrecio(reserva);
    
        // Actualizar la lista de hoteles
        listaHoteles.removeIf(h -> h.getNombre().equals(hotel.getNombre()));
        listaHoteles.add(hotel);
    
    
        ///////////// SobreEscribir el serializable de la lista de hoteles con los cambios realizados ////////////////
    
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/baseDatos/listaHoteles.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(listaHoteles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return hotel.getGrupos();
    }

    public static double calcularPrecio(Reserva reserva) {
        Hotel hotel = reserva.getClientes().get(0).getHotel();
        Cliente cliente = reserva.getClientes().get(0);
        int cantidadClientes = reserva.getClientes().size();
        double precioBase = hotel.getPrecio();
        double precioTotal = cantidadClientes * precioBase;
        

        if (hotel.cuentaConSuscripcion) {
            precioTotal *= cliente.getSuscripcion().getDescHotel();
        }
        precioTotal = precioTotal * reserva.getFechas().size();

        hotel.precioFinalHospedaje = precioTotal;
        
        return precioTotal;
    }

    public static String desplegarHabitacionesReserva(Reserva reserva) {
        Hotel hotel = reserva.getClientes().get(0).getHotel();
        List<Grupo> grupos = hotel.getGrupos();
    
        StringBuilder resultado = new StringBuilder();
        resultado.append("Habitaciones asignadas para la reserva:\n");
        for (Grupo grupo : grupos) {
            ArrayList<Cliente> clientes = grupo.getClientes();
            if (clientes.containsAll(reserva.getClientes())) {
                resultado.append("Tipo de habitacion: ").append(grupo.getTipoHabitacion()).append("\n");
                resultado.append("Clientes en esta habitacion:\n");
                for (Cliente cliente : clientes) {
                    resultado.append(" - ").append(cliente.getNombre()).append(" (Edad: ").append(cliente.getEdad()).append(")\n");
                }
                resultado.append("Capacidad de la habitacion: ").append(grupo.getCapacidad()).append("\n\n");
            }
        }
        return resultado.toString();
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

    public double getPrecioFinalHospedaje(){
        return precioFinalHospedaje;
    }

    public void setPrecioFinalHospedaje(double precio){
        this.precioFinalHospedaje = precio;

    }

    

    

}
