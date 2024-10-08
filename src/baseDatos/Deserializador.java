package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.actividades.Plan;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Suscripcion;

public class Deserializador {
    private static File path = new File("");

    public static void deserializarReservas() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\reservas.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Reserva> reservas = (ArrayList<Reserva>) o.readObject();

            Reserva.setReservasExistentes(reservas);

            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

    public static void deserializarDestinos() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\destinos.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Destino> destinos = (ArrayList<Destino>) o.readObject();

            Destino.setDestinos(destinos);

            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

    public static void deserializarActividades() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\actividades.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Actividad> actividades = (ArrayList<Actividad>) o.readObject();

            for (Actividad actividad : actividades) {
                for (Destino destino : Destino.getDestinos()) {
                    if (actividad.getDestino().equals(destino)){
                        destino.getActividades().add(actividad);
                    }
                }
            }


            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }


    //Verificar donde mandar grupos y serializar tambien Guias
    public static void deserializarGrupos() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\grupos.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Grupo> grupos = (ArrayList<Grupo>) o.readObject();

            Grupo.setGrupos(grupos);

                f.close();
                o.close();

            } catch(FileNotFoundException e){
                System.out.println("Archivo no encontrado");
            } catch(IOException e){
                System.out.println("Error inicializando flujo de entrada");
            } catch(ClassNotFoundException e){
                System.out.println("Clase no encontrada");
            }
        }

    public static void deserializarGuias() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\guias.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Guia> guias = (ArrayList<Guia>) o.readObject();

            Guia.setGuias(guias);

            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

    public static void deserializarPlanes() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\planes.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Plan> planes = (ArrayList<Plan>) o.readObject();

            Plan.setPaquetes(planes);

            f.close();
            o.close();

            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado");
            } catch (IOException e) {
                System.out.println("Error inicializando flujo de entrada");
            } catch (ClassNotFoundException e) {
                System.out.println("Clase no encontrada");
            }
        }
    }


