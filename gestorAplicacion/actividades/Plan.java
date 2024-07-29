package gestorAplicacion.actividades;

import gestorAplicacion.manejoReserva.*;
import gestorAplicacion.hospedaje.Hotel;




import java.util.ArrayList;
public class Plan {
    ArrayList<Plan> paquetes;
    String tipo;
    Destino destino;
    ArrayList<Actividad> actividades;
    double precio;
    Hotel hotel;
    String clasificacion;       //Probablemente enum
    ArrayList<Grupo> grupos;
    Reserva reserva;

}
