package gestorAplicacion.gestionHum;

//Luego importo bien lo que falta
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
//Falta utilizar la accesibilidad
public class Persona {
    private String nombre;
    private int edad;
    private Destino destino;
    private ArrayList<Idiomas> idiomas;
    private String[] seguro;    //Array list de 4 puestos, debo leer como se escribía
    private Grupo grupo;



    public Persona(String nombre, int edad, Destino destino, ArrayList<Idiomas> idiomas, String[] seguro, Grupo grupo) {
        this.nombre = nombre;
        this.edad = edad;
        this.destino = destino;
        this.idiomas = idiomas;
        this.seguro = seguro;
        this.grupo = grupo;
    }

    public Persona(String nombre, int edad, Destino destino){
        this.nombre = nombre;
        this.edad = edad;
        this.destino = destino;
    }
    
    public Persona (String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }






    




    

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    } 

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public void setIdioma(ArrayList<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public void setSeguro(String[] seguro) {
        this.seguro = seguro;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public Destino getDestino() {
        return destino;
    }

    public ArrayList<Idiomas> getIdiomas() {
        return idiomas;
    }

    public String[] getSeguro() {
        return seguro;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    

}