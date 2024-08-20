package gestorAplicacion.hospedaje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import uiMain.*;

public class Restaurante implements Serializable{

    private static final long serialVersionUID = 5L;
    private boolean permiteSuscripcion;
    private String nombre;
    private Destino hotel;
    private double precio;                              
    private ArrayList<Grupo> grupos; // mesas
    private Map<Integer,Grupo> numeroMesas; // numero de mesas por cada tipo
    private Map<Integer,Grupo> mesasDisponibles; // disponibilidad de mesas por cada tipo
                    //[Numero de mesa, [Tipo de mesa, cantidad de personas, precio]]


    public Restaurante(String nombre, ArrayList<Grupo> grupos){

        this.nombre = nombre;
        this.grupos = grupos;
    }

    /**
     * Muestra los restaurantes disponibles
     * @param restaurantesLista
     * @return
     */
    public static ArrayList<String> mostrarNombres(ArrayList<Restaurante> restaurantesLista){
    	ArrayList<String> nombres=new ArrayList<>();
    	for(Restaurante restaurante:restaurantesLista) {
    		nombres.add(restaurante.nombre);
    	}
    	return nombres;
    }
    public static double promedioPrecio(ArrayList<Restaurante> restaurantesLista){
    	double promedio=0;
    	if(restaurantesLista.size()==0) {return 0;}
    	int cantidad=restaurantesLista.size();
    	for(Restaurante restaurante:restaurantesLista) {
    		promedio+=restaurante.precio;
    	}
    	return promedio/cantidad;
    }

    /**
     * Asigna el restaurante a los clientes de la reserva
     * @param reserva
     * @return
     */
    public static Restaurante asignarRestaurante(Reserva reserva){
        Hotel hotel = reserva.getClientes().get(0).getHotel();
        ArrayList<Restaurante> restaurantes = hotel.getRestaurantes();

        ArrayList<String> listaOpcionesRestaurantes = new ArrayList<String>();

        for (Restaurante restaurante : restaurantes) {
            listaOpcionesRestaurantes.add(restaurante.getNombre());
        }

        String indiceRestauranteEscogido = Main.ingresarOpcion("Seleccione el restaurante que desea: ",0, listaOpcionesRestaurantes);

        int indiceRestauranteEscogidoInt = Integer.parseInt(indiceRestauranteEscogido) - 1;
        System.out.println("Hotel escogido: " + restaurantes.get(indiceRestauranteEscogidoInt).getNombre());
        
        for (Cliente cliente : reserva.getClientes()) {
            cliente.setRestaurantes(restaurantes.get(indiceRestauranteEscogidoInt));
        }
        return restaurantes.get(indiceRestauranteEscogidoInt);

    }

    /**
     * Asigna las mesas del restaurante a los clientes de la reserva
     * @param reserva
     * @param restaurante
     */
    public static void asignarMesaRestaurante(Reserva reserva, Restaurante restaurante){ {
        ArrayList<Grupo> grupos = restaurante.getGrupos();
        ArrayList<String> listaOpciones = new ArrayList<String>();
    
        for (Grupo grupo : grupos) {
            listaOpciones.add(grupo.getTipoMesa());
        }
    
        boolean mesasAsignadas = false;
        String[] mesasEscogidasArray = new String[3]; // Initialize with default values
        int totalMesas;
        int capacidadMesasSeleccionada;
    
        while (!mesasAsignadas) {
            String mensaje = "Seleccione el número de mesas de cada tipo [Sencilla, Doble, Gran mesa]: ";
            String mesasEscogidas = Main.ingresarOpcion(mensaje, 3, listaOpciones);
            mesasEscogidasArray = mesasEscogidas.split(" ");
            // [Número de sencillas, Número de dobles, Número de gran mesa]
            totalMesas = Integer.parseInt(mesasEscogidasArray[0]) +
                         Integer.parseInt(mesasEscogidasArray[1]) +
                         Integer.parseInt(mesasEscogidasArray[2]);
            capacidadMesasSeleccionada = 2 * Integer.parseInt(mesasEscogidasArray[0]) +
                                         4 * Integer.parseInt(mesasEscogidasArray[1]) +
                                         12 * Integer.parseInt(mesasEscogidasArray[2]);
    
            if (totalMesas > Hotel.numeroDeAdultos(reserva.getClientes())) {
                totalMesas = 0;
            } else if (totalMesas == 0) {
                //System.out.println("Debe seleccionar al menos una mesa.");
            } else if (capacidadMesasSeleccionada < reserva.getClientes().size()) {
                totalMesas = 0;
                //"La capacidad total de las mesas seleccionadas es menor que el número de clientes.");
            } else {
                mesasAsignadas = true;
            }
        }
    
        for (Cliente cliente : reserva.getClientes()) {
            cliente.setMesaRestaurante(grupos.get(Integer.parseInt(mesasEscogidasArray[0]) - 1));
        }}
    
        //System.out.println("Mesas asignadas correctamente.");
    }

    /**
     * Calcula el número de mesas disponibles en el restaurante
     * @param reserva
     * @return
     */
    public int numeroDeMesasDisponibles(Reserva reserva){
        int numeroDeMesas = 0;
        for (Grupo grupo: this.grupos){
            if (grupo.getTipoMesa().equals("Sencilla")){
                numeroDeMesas =+ 2;
            }
            else if (grupo.getTipoMesa().equals("Doble")){
                numeroDeMesas =+ 4;
            }
            else if (grupo.getTipoMesa().equals("Gran mesa")){
                numeroDeMesas =+ 12;
            }
        }
        return numeroDeMesas;
    }
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// METODOS DE ACCESO ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

    public boolean isPermiteSuscripcion() {
    return permiteSuscripcion;
                    }

    public void setPermiteSuscripcion(boolean permiteSuscripcion) {
        this.permiteSuscripcion = permiteSuscripcion;}

    public String getNombre() {
        return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Destino getHotel() {
        return hotel;
    }

    public void setHotel(Destino hotel) {
        this.hotel = hotel;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(ArrayList<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Map<Integer, Grupo> getNumeroMesas() {
        return numeroMesas;
    }

    public void setNumeroMesas(Map<Integer, Grupo> numeroMesas) {
        this.numeroMesas = numeroMesas;
    }



    



    
    










}