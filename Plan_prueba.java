import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.manejoReserva.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


public class Plan_prueba {

    ArrayList <Destino> lista_destinos = new ArrayList <Destino>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        

        FileInputStream fileInputStream = new FileInputStream("contenedorDestinos.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ContenedorDestinos cotenedorDestinos = (ContenedorDestinos) objectInputStream.readObject();

        Scanner entrada = new Scanner(System.in);

        ArrayList<Destino> destinos = new ArrayList<Destino>();

        destinos = (ArrayList<Destino>) cotenedorDestinos.getDestinos();

        ///////////////////////////////////////// Creacion de destinos /////////////////////////////////////////


        System.out.println(Destino.destinosPorIdioma());

        mejoresOpcionesDestino();
        
        switch (entrada.nextInt()) {
            case 1:
                segunActividad();
                filtroActividad(destinos, entrada);
                break;
            case 2:
                segunIdioma();
                break;
            case 3:
                
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }

        
        




        /* 


        Scanner entrada = new Scanner(System.in);
        menuCantidadPersonas();
        int cantidadPersonas = entrada.nextInt();
        System.out.println(cantidadPersonas);
        menuDestino();

        switch (entrada.nextInt()) {
            case 1:
                
                break;

            case 2:
                mejoresOpcionesDestino();
                break;
        
            default:
                break;
        }
        switch (entrada.nextInt()) {
            case 1:
                menuActividad();
                break;
        
            default:
                break;
        }*/
    }


    public static void menuCantidadPersonas() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        
        String mensaje1 = "Por favor, ingrese la cantidad de personas que realizaran el viaje";
        System.out.printf("| %-60s |", mensaje1);

        System.out.printf("%n|%69s ","|");
        System.out.printf("%n|%69s ","|");
        System.out.printf("%n|%69s %n","|");

        System.out.printf("%s%n","|____________________________________________________________________|");

    }

    public static void menuDestino() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        System.out.printf("| %-66s |%n", "Ingresar Destino");
        
        String mensaje1 = "Seleccione la opcion que desea realizar";
        System.out.printf("| %-66s |", mensaje1);

        System.out.printf("%n|%69s ","|"); // Salto de linea

        String mensaje2 = "1) Ingresar destino";
        System.out.printf("%n| %-66s |", mensaje2);

        String mensaje3 = "2) Buscar mejores opciones";
        System.out.printf("%n| %-66s |", mensaje3);

        System.out.printf("%n|%69s %n","|");



        System.out.printf("%s%n","|____________________________________________________________________|");

    }   


    public static void mejoresOpcionesDestino() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        System.out.printf("| %-66s |%n", "Filtar por:");

        System.out.printf("|%69s ","|"); // Salto de linea
             
        
        String opcion1 = "1) según actividad";
        System.out.printf("%n| %-66s |%n", opcion1);

        String opcion2 = "2) según idioma";
        System.out.printf("| %-66s |%n", opcion2);

        String opcion3 = "3) según fecha";
        System.out.printf("| %-66s |%n", opcion3);

        System.out.printf("|%n|%69s %n","|");

        System.out.printf("%s%n","|____________________________________________________________________|");

        System.out.println("");
    }


    public static void segunActividad() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        System.out.printf("| %-66s |%n", "Actividades");
    
        System.out.printf("|%69s ","|"); // Salto de linea
    
        String opcion1 = "1) Cultural";
        System.out.printf("%n| %-66s |%n", opcion1);
    
        String opcion2 = "2) Familiar";
        System.out.printf("| %-66s |%n", opcion2);
    
        String opcion3 = "3) Ecologica";
        System.out.printf("| %-66s |%n", opcion3);
    
        String opcion4 = "4) Extrema";
        System.out.printf("| %-66s |%n", opcion4);
    
        String opcion5 = "5) Acuatica";
        System.out.printf("| %-66s |%n", opcion5);
    
        String opcion6 = "6) Deportiva";
        System.out.printf("| %-66s |%n", opcion6);
    
        System.out.printf("|%n|%69s %n","|");
    
        System.out.printf("%s%n","|____________________________________________________________________|");
    
        System.out.println("");
        
    }

    public static void filtroActividad(ArrayList<Destino> destinos, Scanner entrada) {
        int opcion = entrada.nextInt();

        for (Destino destino : destinos) {
            switch (opcion) {
                case 1: // Cultural
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.CULTURALES)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                case 2: // Familiar
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.FAMILIARES)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                case 3: // Ecologica
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.ECOLOGICAS)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                case 4: // Extrema
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.EXTREMAS)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                case 5: // Acuatica
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.ACUATICAS)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                case 6: // Deportiva
                    destino.getActividades().forEach(actividad -> {
                        if (actividad.getTipo().contains(TiposActividad.DEPORTIVAS)) {
                            System.out.println(destino.getNombre());
                        }
                    });
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public static void segunIdioma() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        System.out.printf("| %-66s |%n", "Idiomas Disponibles");
    
        System.out.printf("|%69s ","|"); // Salto de linea
    
        String opcion1 = "1) Inglés";
        System.out.printf("%n| %-66s |%n", opcion1);
    
        String opcion2 = "2) Portugués";
        System.out.printf("| %-66s |%n", opcion2);
    
        String opcion3 = "3) Español";
        System.out.printf("| %-66s |%n", opcion3);
    
        String opcion4 = "4) Francés";
        System.out.printf("| %-66s |%n", opcion4);
    
        String opcion5 = "5) Italiano";
        System.out.printf("| %-66s |%n", opcion5);
    
        System.out.printf("|%n|%69s %n","|");
    
        System.out.printf("%s%n","|____________________________________________________________________|");
    
        System.out.println("");
    }

    }

        




