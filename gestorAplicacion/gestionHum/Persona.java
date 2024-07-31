package gestorAplicacion.gestionHum;

//Luego importo bien lo que falta
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
//Falta utilizar la accesibilidad

public class Persona {
	private String nombre;
    private ArrayList<Idiomas> idiomas;
    private Destino destino;
    int edad;
    private ArrayList<Object> seguro;    //Array list de 4 puestos, debo leer como se escribía
    Grupo grupo;
    
    public Persona(String nombre, int edad) {
    	this.nombre = nombre;
    	this.edad = edad;
    }


}