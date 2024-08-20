package baseDatos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.actividades.Plan;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Suscripcion;

public class Serializacion {

    public static void main(String[] args) throws IOException {

        // Crear ArrayList de todo lo que quiero guardar
        ArrayList<Destino> destinos = new ArrayList<>();
        ArrayList<Actividad> actividades = new ArrayList<>();
        ArrayList<Guia> guias = new ArrayList<>();
        ArrayList<Reserva> reservas = new ArrayList<>();
        ArrayList<Grupo> grupos = new ArrayList<>();
        ArrayList<Plan> planes = new ArrayList<>();



        // Crear destinos
        Destino cartagena = new Destino("Cartagena");
        destinos.add(cartagena);
        Destino bogota = new Destino("Bogotá");
        destinos.add(bogota);
        Destino medellin = new Destino("Medellin");
        destinos.add(medellin);

        //CREA TODOS LOS CASOS DE PRUEBA NECESARIOS PARA SERIALIZAR ACTIVIDADES, GUIAS, RESERVAS, GRUPOS Y PLANES



        // Crear actividades
        Actividad actividad1 = new Actividad("Tour por la ciudad", cartagena, TiposActividad.CULTURALES);
        Actividad actividad2 = new Actividad("Tour por la ciudad",bogota, TiposActividad.CULTURALES);
        Actividad actividad3 = new Actividad("Tour por la ciudad", medellin, TiposActividad.CULTURALES);
        Actividad actividad4 = new Actividad("Visita a museos",bogota, TiposActividad.CULTURALES);
        Actividad actividad5 = new Actividad("Visita a museos",medellin, TiposActividad.CULTURALES);
        Actividad actividad6 = new Actividad("Excursión a la playa",cartagena, TiposActividad.ACUATICAS);
        Actividad actividad7 = new Actividad("Gastronomía local", cartagena, TiposActividad.FAMILIARES);
        Actividad actividad8 = new Actividad("Gastronomía local",bogota, TiposActividad.FAMILIARES);
        Actividad actividad9 = new Actividad("Gastronomía local", cartagena, TiposActividad.FAMILIARES);
        Actividad actividad10 = new Actividad("Senderismo",cartagena, TiposActividad.ECOLOGICAS);
        Actividad actividad11 = new Actividad("Senderismo",bogota, TiposActividad.ECOLOGICAS);
        Actividad actividad12 = new Actividad("Senderismo",medellin, TiposActividad.ECOLOGICAS);
        Actividad actividad13 = new Actividad("Parapente",bogota, TiposActividad.EXTREMAS);
        Actividad actividad14 = new Actividad("Parapente", medellin, TiposActividad.EXTREMAS);
        Actividad actividad15 = new Actividad("Fútbol", bogota, TiposActividad.DEPORTIVAS);
        Actividad actividad16 = new Actividad("Fútbol", medellin, TiposActividad.DEPORTIVAS);
        Actividad actividad17 = new Actividad("Fútbol", cartagena, TiposActividad.DEPORTIVAS);




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

        Guia guia4 = new Guia("Pedro");
        guia4.addIdioma(Idiomas.ESPANOL);
        guia4.addIdioma(Idiomas.INGLES);

        Guia guia5 = new Guia("Luis");
        guia5.addIdioma(Idiomas.ESPANOL);
        guia5.addIdioma(Idiomas.FRANCES);

        Guia guia6 = new Guia("Ana");
        guia6.addIdioma(Idiomas.ESPANOL);
        guia6.addIdioma(Idiomas.PORTUGUES);

        Guia guia7 = new Guia("Sofia");
        guia7.addIdioma(Idiomas.ESPANOL);
        guia7.addIdioma(Idiomas.INGLES);

        Guia guia8 = new Guia("Lucia");
        guia8.addIdioma(Idiomas.ESPANOL);
        guia8.addIdioma(Idiomas.FRANCES);

        Guia guia9 = new Guia("Marta");
        guia9.addIdioma(Idiomas.ESPANOL);

        Guia guia10 = new Guia("Laura");
        guia10.addIdioma(Idiomas.ESPANOL);

        // Crear grupos

        Grupo grupo1 = new Grupo(guia1);
        Grupo grupo2 = new Grupo(guia2);
        Grupo grupo3 = new Grupo(guia3);
        Grupo grupo4 = new Grupo(guia4);
        Grupo grupo5 = new Grupo(guia5);
        Grupo grupo6 = new Grupo(guia6);
        Grupo grupo7 = new Grupo(guia7);
        Grupo grupo8 = new Grupo(guia8);
        Grupo grupo9 = new Grupo(guia9);
        Grupo grupo10 = new Grupo(guia10);




        // Crear reservas
        Reserva reserva1 = new Reserva(new Cliente("Carlos"));
        Reserva reserva2 = new Reserva(new Cliente("Juan"));
        Reserva reserva3 = new Reserva(new Cliente("Maria"));
        Reserva reserva4 = new Reserva(new Cliente("Pedro"));
        Reserva reserva5 = new Reserva(new Cliente("Luis"));
        Reserva reserva6 = new Reserva(new Cliente("Ana"));
        Reserva reserva7 = new Reserva(new Cliente("Sofia"));
        Reserva reserva8 = new Reserva(new Cliente("Lucia"));
        Reserva reserva9 = new Reserva(new Cliente("Marta"));
        Reserva reserva10 = new Reserva(new Cliente("Laura"));




        // Asignar guías a los destinos
        cartagena.getGuias().add(guia1);
        cartagena.getGuias().add(guia2);
        bogota.getGuias().add(guia3);
        bogota.getGuias().add(guia4);
        medellin.getGuias().add(guia5);
        medellin.getGuias().add(guia6);
        cartagena.getGuias().add(guia7);
        bogota.getGuias().add(guia8);
        medellin.getGuias().add(guia9);
        cartagena.getGuias().add(guia10);

        //Serializar las cosas
        destinos = Destino.getDestinos();
        for (Destino destino : Destino.getDestinos()) {
            for (Actividad actividad : destino.getActividades()) {
                actividades.add(actividad);
            }
        }
        guias = Guia.getGuias();
        reservas = Reserva.getReservasExistentes();
        grupos = Grupo.getGrupos();
        planes = Plan.getPaquetes();

        Serializador.serializarGrupos(grupos);
        Serializador.serializarReservas(reservas);
        Serializador.serializarActividades(actividades);
        Serializador.serializarDestinos(destinos);
        Serializador.serializarGuias(guias);
        Serializador.serializarPlanes(planes);

    }
}