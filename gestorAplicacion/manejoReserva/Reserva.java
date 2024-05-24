package gestorAplicacion.manejoReserva;

import java.util.ArrayList;
import gestorAplicacion.gestionHum.Cliente;
public class Reserva {
    long codigo;
    Destino destino;
    String[] idioma;        //prob un enum
    int cantidadPersonas;
    ArrayList<Cliente> clientes;
    int cantidadDias;
    int[] fechaLlegada;
    String clasificacion;
    String tiposPlan;
    boolean existeSuscripcion;
    Plan plan;

}
