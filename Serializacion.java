import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import gestorAplicacion.manejoReserva.ContenedorDestinos;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.gestionHum.Guia;

public class Serializacion {

    public static void main(String[] args) throws IOException {

        // Crear contenedor de destinos
        ContenedorDestinos contenedor = new ContenedorDestinos();

        // Crear destinos
        Destino cartagena = new Destino("Cartagena");
        Destino bogota = new Destino("Bogotá");
        Destino medellin = new Destino("Medellín");

        // Agregar destinos al contenedor
        contenedor.addDestino(cartagena);
        contenedor.addDestino(bogota);
        contenedor.addDestino(medellin);

        // Crear guías y asignarles idiomas
        Guia guia1 = new Guia("Juan");
        guia1.addIdioma(Idiomas.ESPANOL);
        guia1.addIdioma(Idiomas.INGLES);

        Guia guia2 = new Guia("Maria");
        guia2.addIdioma(Idiomas.ESPANOL);
        guia2.addIdioma(Idiomas.FRANCES);

        Guia guia3 = new Guia("Carlos");
        guia3.addIdioma(Idiomas.ESPANOL);
        guia3.addIdioma(Idiomas.PORTUGUES);

        // Asignar guías a los destinos
        cartagena.getGuias().add(guia1);
        cartagena.getGuias().add(guia2);
        bogota.getGuias().add(guia3);

        // Serializar el contenedor de destinos
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("contenedorDestinos.txt"))) {
            out.writeObject(contenedor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}