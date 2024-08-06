package gestorAplicacion.gestionHum;

//Luego importo bien lo que falta
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
//Falta utilizar la accesibilidad
public class Persona {
	private int edad;
    private String nombre;
    private Destino destino;
    private ArrayList<Idiomas> idiomas;

    public Persona(String nombre, Destino destino,int edad) {
        this.idiomas = new ArrayList<>();
        this.nombre = nombre;
        this.destino = destino;
        this.edad=edad;    
    }

    
    /**
     * Ingresa los idiomas que domina la persona a partir de una cadena.
     *
     * @param idiomas Una cadena que representa los idiomas.
     */
    public void ingresarIdiomas(String idiomas) {
        for (String posicion : idiomas.split(" ")) {
            int numero = Integer.parseInt(posicion);
            for (Idiomas idioma : Idiomas.values()) {
                if (numero == idioma.getPosicion()) {
                    this.idiomas.add(idioma);
                }
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public void setIdioma(ArrayList<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

  
    public String getNombre() {
        return nombre;
    }


    public Destino getDestino() {
        return destino;
    }

    public ArrayList<Idiomas> getIdiomas() {
        return idiomas;
    }


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}

  
    

}