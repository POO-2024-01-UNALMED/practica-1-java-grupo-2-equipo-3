package gestorAplicacion.gestionHum;

import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.hospedaje.Restaurante;
import gestorAplicacion.manejoReserva.Suscripcion;
import gestorAplicacion.gestionHum.Persona;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.actividades.Plan;



public class Cliente extends Persona {
    private static final long serialVersionUID = 2L; // Agregado para la compatibilidad de serialización

    private ArrayList<Restaurante> restaurantes;
	private ArrayList<Grupo> grupos;
	private Suscripcion suscripcion;
	private Hotel hotel;
	private Grupo habitacion;
    private Grupo mesaRestaurante;
    
	  
	@Override
	public String toString() {
        if(this.suscripcion != null) {
            String sus = suscripcion.getTipo();
            return nombre + "con" + edad + "años." + "Suscripcion de tipo: " + sus;
        }
		return nombre + "con" + edad + "años.";
	}
    


    public Cliente(String nombre, Destino destino, int edad) {
        super(nombre, destino, edad);
        this.restaurantes = new ArrayList<>(); // Inicializar la lista de restaurantes

    }
    public Cliente(String nombre, int edad) {
        super(nombre, edad);
        this.grupos = new ArrayList<>();
        this.restaurantes = new ArrayList<>(); // Inicializar la lista de restaurantes

    }

    //porfis dejenlo asi lo necesito para mi funcionalidad:(
    public Cliente() {
    	this.setEdad(18);
    }

    /**
     * Método que identifica si el cliente es mayor de edad
     * @return boolean
     */
    public boolean mayorDeEdad() {
        return this.getEdad() >= 18;
    }

    public void cancelarActividad(Actividad actividad, ArrayList<Integer> fecha) {
        //NO SE QUE HACEEEER

    }

	//////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setHabitacion(Grupo habitacion) {
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

    public void addGrupo(Grupo grupo) {
        grupos.add(grupo);
    }

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }   

    public void setRestaurantes(Restaurante restaurante) {
        restaurantes.add(restaurante);
    }

    public ArrayList<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setGrupos(Grupo grupo) {
        grupos.add(grupo);
    }

    public void setMesaRestaurante(Grupo mesaRestaurante) {
        this.mesaRestaurante = mesaRestaurante;
    }

    public Grupo getMesaRestaurante() {
        return mesaRestaurante;
    }

    


  
}


