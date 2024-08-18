package gestorAplicacion.hospedaje;

import java.util.ArrayList;
import java.util.Map;

import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import uiMain.Main;

public class Restaurante {

    private static final long serialVersionUID = 1L;
    private boolean permiteSuscripcion;
    private String nombre;
    private Destino hotel;
    private double precio;                              
    private ArrayList<Grupo> grupos; // mesas
    private Map<Integer,Grupo> numeroMesas; // numero de mesas por cada tipo
    private Map<Integer,Grupo> mesasDisponibles; // disponibilidad de mesas por cada tipo
                    //[Numero de mesa, [Tipo de mesa, cantidad de personas, precio]]


    public Restaurante asignarRestaurante(Reserva reserva){
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

    public void asignarMesaRestaurante(Reserva reserva){
        Restaurante restaurante = asignarRestaurante(reserva);
        ArrayList<Grupo> grupos = restaurante.getGrupos();
        ArrayList<String> listaOpcionesGrupos = new ArrayList<String>();

        for (Grupo grupo : grupos) {
            listaOpcionesGrupos.add(grupo.getTipoMesa());
        }

        String indiceGrupoEscogido = Main.ingresarOpcion("Seleccione el grupo de mesas que desea para su reserva: ",0, listaOpcionesGrupos);

        int indiceGrupoEscogidoInt = Integer.parseInt(indiceGrupoEscogido) - 1;
        System.out.println("Grupo de mesas escogido: " + grupos.get(indiceGrupoEscogidoInt).getTipoMesa());
        
        for (Cliente cliente : reserva.getClientes()) {
            cliente.setGrupos(grupos.get(indiceGrupoEscogidoInt));
        }

    }

    public void asignarMesaRestaurante(){


    }


    public static void main(String[] args) {
        
    

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