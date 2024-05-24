package gestorAplicacion.gestionHum;

import gestorAplicacion.manejoReserva.Suscripcion;
import gestorAplicacion.manejoReserva.Hotel;
import gestorAplicacion.manejoReserva.Plan;

public class Cliente extends Persona {
    Suscripcion suscripcion;
    Hotel hotel;
    Plan plan;
    String hospedaje;
}
