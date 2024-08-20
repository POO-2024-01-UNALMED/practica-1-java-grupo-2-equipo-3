package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Suscripcion;

public class Serializador {
    private static File archivo = new File("");

    public static void serializarReservas(ArrayList<Reserva> reservas) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\reservas.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(reservas);

            System.out.println("Serializando..........");

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarDestinos(ArrayList<Destino> destinos) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\destinos.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(destinos);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarActividades(ArrayList<Actividad> actividades) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\actividades.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(actividades);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarGrupos(ArrayList<Grupo> grupos) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\grupos.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(grupos);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarGuias(ArrayList<Guia> guias) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\guias.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(guias);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }