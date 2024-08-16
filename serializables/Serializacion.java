import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import gestorAplicacion.manejoReserva.ContenedorDestinos;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;

public class Serializacion {

    public static void main(String[] args) throws IOException {

        // Crear contenedor de destinos
        ContenedorDestinos contenedor = new ContenedorDestinos();

        // Crear destinos
        Destino cartagena = new Destino("Cartagena");
        Destino bogota = new Destino("Bogotá");
        Destino medellin = new Destino("Medellin");

        // Crear actividades
        Actividad actividad1 = new Actividad("Tour por la ciudad", TiposActividad.CULTURALES);
        Actividad actividad2 = new Actividad("Visita a museos", TiposActividad.CULTURALES);
        Actividad actividad3 = new Actividad("Excursión a la playa", TiposActividad.ACUATICAS);
        Actividad actividad4 = new Actividad("Gastronomía local", TiposActividad.FAMILIARES);
        Actividad actividad5 = new Actividad("Senderismo", TiposActividad.ECOLOGICAS);
        Actividad actividad6 = new Actividad("Parapente", TiposActividad.EXTREMAS);
        Actividad actividad7 = new Actividad("Fútbol", TiposActividad.DEPORTIVAS);

        // Asignar actividades a los destinos
        cartagena.getActividades().add(actividad1);
        cartagena.getActividades().add(actividad3);
        cartagena.getActividades().add(actividad4);

        bogota.getActividades().add(actividad2);
        bogota.getActividades().add(actividad4);
        bogota.getActividades().add(actividad5);

        medellin.getActividades().add(actividad1);
        medellin.getActividades().add(actividad6);
        medellin.getActividades().add(actividad7);

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