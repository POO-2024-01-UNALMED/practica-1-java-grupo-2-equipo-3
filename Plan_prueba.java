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

        ///////////////////////////////////////// Creacion de destinos /////////////////////////////////////////

        //destinos
        FileInputStream fileInputStream = new FileInputStream("Cartagena.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Destino cartagena1 = (Destino) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(cartagena1.getNombre());
        





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
        System.out.printf("| %-66s |%n", "Mejores Opciones de Destino");

        System.out.printf("|%69s ","|"); // Salto de linea
             
        
        String opcion1 = "1) según actividad";
        System.out.printf("%n| %-66s |%n", opcion1);

        String opcion2 = "2) Destino según idioma";
        System.out.printf("| %-66s |%n", opcion2);

        String opcion3 = "3) Destino según fecha";
        System.out.printf("| %-66s |%n", opcion3);

        System.out.printf("|%n|%69s %n","|");

        System.out.printf("%s%n","|____________________________________________________________________|");

        System.out.println("");
    }


    public static void menuActividad() {
        System.out.printf("%s%n|%69s %n"," ____________________________________________________________________","|");
        System.out.printf("| %-66s |%n", "Actividades");

        System.out.printf("|%69s ","|"); // Salto de linea
             
        
        String opcion1 = "1) Playa";
        System.out.printf("%n| %-66s |%n", opcion1);

        String opcion2 = "2) Montaña";
        System.out.printf("| %-66s |%n", opcion2);

        String opcion3 = "3) Ciudad";
        System.out.printf("| %-66s |%n", opcion3);

        System.out.printf("|%n|%69s %n","|");

        System.out.printf("%s%n","|____________________________________________________________________|");

        System.out.println("");
    }

}