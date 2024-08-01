package gestorAplicacion.manejoReserva;

import java.util.ArrayList;  
import gestorAplicacion.gestionHum.Cliente;
public class Suscripcion {
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private String tipo;
    private ArrayList<Integer> vencimiento;       //Otra instancia de una fecha
    private int capacidad;
    private double precio;
    private float descTransporte;
    private float descTour;
    private float descHotel;


}
