package gestorAplicacion.gestionHum;

import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;

public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 4L; // Agregado para la compatibilidad de serialización
    protected int edad;
    protected String nombre;
    protected Destino destino;
    protected ArrayList<Idiomas> idiomas;

    public Persona(String nombre, Destino destino, int edad) {
        this.idiomas = new ArrayList<>();
        this.nombre = nombre;
        this.destino = destino;
        this.edad = edad;    
    }

    public Persona(String nombre, int edad) {
        this.idiomas = new ArrayList<>();
        this.nombre = nombre;
        this.edad = edad;
    }

    // Constructor sin parámetros
    ////porfis dejenlo asi lo necesito para mi funcionalidad:(
    public Persona() {
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nEdad: " + edad + "\nDestino: " + destino + "\nIdiomas: " + idiomas;
    }

    /**
     * Ingresa los idiomas que domina la persona a partir de una cadena.
     *
     * @param idiomas Una cadena que representa los idiomas.
     */
    public void ingresarIdiomas(String idiomas) {
        for (String idioma : idiomas.split(",")) {
            this.idiomas.add(Idiomas.valueOf(idioma.toUpperCase()));
        }
    }

    // Métodos de acceso
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

    public void addIdioma(Idiomas idioma) {
        this.idiomas.add(idioma);
    }
}