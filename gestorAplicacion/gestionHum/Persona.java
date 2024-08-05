package gestorAplicacion.gestionHum;

//Luego importo bien lo que falta
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
//Falta utilizar la accesibilidad
public class Persona {
    private String nombre;
    private Destino destino;
    private ArrayList<Idiomas> idiomas;
    private String[] seguro;    //Array list de 4 puestos, debo leer como se escribía

    public Persona(String nombre, Destino destino) {
        this.idiomas = new ArrayList<>();
        this.nombre = nombre;
        this.destino = destino;
    
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

    public void setSeguro(String[] seguro) {
        this.seguro = seguro;
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

    public String[] getSeguro() {
        return seguro;
    }

    

}