package gestorAplicacion.gestionHum;

//Luego importo bien lo que falta
import java.util.ArrayList;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
//Falta utilizar la accesibilidad
public class Persona {
    String nombre;
    int edad;
    Destino destino;
    String[] idioma;
    String[] seguro;    //Array list de 4 puestos, debo leer como se escribía
    Grupo grupo;



    public Persona(String nombre, int edad, Destino destino, String[] idioma, String[] seguro, Grupo grupo) {
        this.nombre = nombre;
        this.edad = edad;
        this.destino = destino;
        this.idioma = idioma;
        this.seguro = seguro;
        this.grupo = grupo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    } 

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public void setIdioma(String[] idioma) {
        this.idioma = idioma;
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

    public String[] getIdioma() {
        return idioma;
    }

    public String[] getSeguro() {
        return seguro;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    

}