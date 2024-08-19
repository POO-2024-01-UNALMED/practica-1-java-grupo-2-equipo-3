package uiMain;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.*;
import gestorAplicacion.gestionHum.*;
import java.util.ArrayList;
import java.util.Arrays;


import gestorAplicacion.manejoReserva.Reserva;

import java.util.ArrayList;
import java.util.Arrays;

public class Funcionalidad4 {
    public static void ModificarReserva() {
        boolean terminarModificarReserva = true;
        while(terminarModificarReserva) {
            Reserva reservaAModificar = Reserva.buscarReserva(Main.ingresarEntero("Ingrese el código de la reserva que desea modificar: \n"));

    }
































    /////////////////////////////////////////////////////// ALEJANDRO ///////////////////////////////////////////////////////

    


    public static void mostrarReserva(Reserva reserva){

        ArrayList<ArrayList<Integer>> fechas = reserva.getFechas();
        ArrayList<Idiomas> idiomas = reserva.getIdiomas();
        ArrayList<Cliente> clientes = reserva.getClientes();  
        
        String menu = "Su reserva es la siguiente: \n" + 
        "Fechas de la reserva:" + fechas + "\n" +
        "Idiomas:" + idiomas + "\n" +
        "Clientes:" + clientes + "\n";
        
        System.out.println(menu);

    }

    public static boolean preguntaRealizarCambios(){
        
        String entrada = Main.ingresarOpcion("Desea realizar cambios a su resevar", 0, new ArrayList<>(Arrays.asList("Si", "No")));

        int entradaInt = Integer.parseInt(entrada) - 1;

        if(entradaInt == 1){
            return true;
        }
        return false;
        
    }

    public static Integer preuntaReservaCambiar(){

        String entrada = Main.ingresarOpcion("Que desea modificar de la reserva", 0, new ArrayList<>(Arrays.asList("Fechas", "Idiomas", "Clientes")));

        return Integer.parseInt(entrada) - 1;

    }

    public static void modificarFechas(Reserva reserva){

        
        reserva.setFechas(null);
        boolean salir = false;
        while(!salir){
            ArrayList<ArrayList<Integer>> fechas = Main.ingresarFecha("1");
            reserva.setFechas(fechas);
            String entrada = Main.ingresarOpcion("Desea agregar mas fechas", 0, new ArrayList<>(Arrays.asList("Si", "No")));
            int entradaInt = Integer.parseInt(entrada) - 1;
            if(entradaInt == 1){
                salir = true;
            }

        }
        


    }

}