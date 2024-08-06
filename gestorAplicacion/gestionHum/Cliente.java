package gestorAplicacion.gestionHum;

import java.util.ArrayList;

import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.Suscripcion;
import gestorAplicacion.gestionHum.Persona;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Destino;



public class Cliente extends Persona {
	private ArrayList<Grupo> grupos;
	private Suscripcion suscripcion;
	private Hotel hotel;
	private Grupo habitacion;

	  

    


    public Cliente(String nombre, Destino destino, int edad) {
        super(nombre, destino, edad);
    }
    public Cliente(String nombre, int edad) {
        super(nombre, edad);
    }



    public void cancelarActividad(Actividad actividad, ArrayList<Integer> fecha) {
        //NO SE QUE HACEEEER

    }

	//////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    private void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    private void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    private void setHabitacion(Grupo habitacion) {
        this.habitacion = habitacion;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Grupo getHabitacion() {
        return habitacion;
    }

    
}


