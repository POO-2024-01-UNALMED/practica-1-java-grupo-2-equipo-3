package gestorAplicacion.manejoReserva;

import java.util.ArrayList;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;

public class Grupo {
    Transporte transporte;
    String idioma;          //Probablemente termine siendo un enum, discutir con Lau
    String clasificacion;   //Probablemente termine siendo un enum, discutir con Lau
    Actividad actividad;
    Guia guia;
    ArrayList<Cliente> clientes;
    String[] seguro;
    int[] fecha;            //Todavía no sabemos que hacer con las fechas
    double precioGuia;
    double preciotransporte;
    ArrayList<Grupo> grupos;
    final int CAPACIDAD;     //No es claro cuanto es

}
