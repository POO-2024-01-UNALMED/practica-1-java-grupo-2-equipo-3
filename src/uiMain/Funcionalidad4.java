package uiMain;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Persona;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.*;

import javax.net.ssl.HostnameVerifier;


public class Funcionalidad4 {
    public static void ModificarReserva() {
        boolean terminarModificarReserva = true;
        while(terminarModificarReserva) {
            Reserva reservaAModificar = Reserva.buscarReserva(Main.ingresarEntero("Ingrese el código de la reserva que desea modificar: \n"));

    }
































    /////////////////////////////////////////////////////// ALEJANDRO ///////////////////////////////////////////////////////

    public static Reserva ingreasrCodigoReserva() {
    Integer codigo = Main.ingresarEntero("Ingrese el codigo de la reserva que desea modificar: ");
    Reserva reserva = null;
    
    try (FileInputStream fis = new FileInputStream("reserva_" + codigo + ".txt");
         ObjectInputStream ois = new ObjectInputStream(fis)) {
        reserva = (Reserva) ois.readObject();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    
    return reserva;
}

    


    public static void mostrarReserva(Reserva reserva){

        Main.imprimirReserva(reserva);

    }

    public static boolean preguntaRealizarCambios(){
        
        String entrada = Main.ingresarOpcion("Desea realizar cambios a su resevar", 0, new ArrayList<>(Arrays.asList("Si", "No")));

        int entradaInt = Integer.parseInt(entrada);

        if(entradaInt == 2){
            return true;
        }
        return false;
        
    }

    public static Integer preuntaReservaCambiar(){

        String entrada = Main.ingresarOpcion("Que desea modificar de la reserva", 0, new ArrayList<>(Arrays.asList("Fechas", "Idiomas", "Clientes")));

        return Integer.parseInt(entrada) - 1;

    }

    public static void agregarFecha(Reserva reserva) {
        ArrayList<ArrayList<Integer>> fechasAnteriores = reserva.getFechas();
        reserva.setFechas(null);
        boolean salir = false;
        
        while (!salir) {
            ArrayList<ArrayList<Integer>> fechas = Main.ingresarFecha("1");
            reserva.setFechas(fechas);
            
            if (verificacionCondicionesHospedaje(reserva)) {
                salir = true;
            } else {
                String opcion = Main.ingresarOpcion("La reserva no cumple con los parametros del hotel", 0, new ArrayList<>(Arrays.asList("continuar y elimiar Hotel", "Volver a menu de modificar reserva")));
                int opcionInt = Integer.parseInt(opcion);
                if (opcionInt == 1) {
                    for (Cliente cliente : reserva.getClientes()) {
                        cliente.setHotel(null);
                    }
                } else {
                    reserva.setFechas(fechasAnteriores);
                    break;
                }
            }
            
            String opcionAgregar = Main.ingresarOpcion("Desea agregar mas fechas", 0, new ArrayList<>(Arrays.asList("Si", "No")));
            int opcionAgregarInt = Integer.parseInt(opcionAgregar);
            if (opcionAgregarInt == 2) {
                salir = true;
            }
        }
    }

    public static void eliminarFecha(Reserva reserva) {
        ArrayList<ArrayList<Integer>> fechas = reserva.getFechas();
        if (fechas == null || fechas.isEmpty()) {
            System.out.println("No hay fechas para eliminar.");
            return;
        }
    
        boolean salir = false;
        while (!salir) {
            System.out.println("Fechas actuales de la reserva:");
            for (int i = 0; i < fechas.size(); i++) {
                System.out.println((i + 1) + ": " + fechas.get(i));
            }
    
            int indice = Main.ingresarEntero("Ingrese el número de la fecha que desea eliminar (0 para salir): ");
            if (indice == 0) {
                salir = true;
            } else if (indice > 0 && indice <= fechas.size()) {
                fechas.remove(indice - 1);
                reserva.setFechas(fechas);
            } else {
            }
    
            if (!fechas.isEmpty()) {
                String opcion = Main.ingresarOpcion("Desea eliminar otra fecha?", 0, new ArrayList<>(Arrays.asList("Si", "No")));
                int opcionInt = Integer.parseInt(opcion);
                if (opcionInt == 2) {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }
    

    public static void agregarClientes(Reserva reserva) {
    boolean salir = false;
    while (!salir) {
        Cliente cliente = Main.ingresarCliente();
        reserva.getClientes().add(cliente);
        if (verificacionCondicionesHospedaje(reserva)) {
            salir = true;
        } else {
            String entrada = Main.ingresarOpcion("La reserva no cumple con los parametros del hotel", 0, new ArrayList<>(Arrays.asList("continuar y elimiar Hotel", "Volver a menu de modificar reserva")));
            Integer entradaInt = Integer.parseInt(entrada);
            if (entradaInt == 1) {
                
                for (Cliente cliente1 : reserva.getClientes()) {
                    cliente1.setHotel(null);
                }
            } else {
                reserva.getClientes().remove(cliente);
                break;
            }
        }
        
        String entrada = Main.ingresarOpcion("Desea agregar mas clientes", 0, new ArrayList<>(Arrays.asList("Si", "No")));
        int entradaInt = Integer.parseInt(entrada);
        if (entradaInt == 2) {
            salir = true;
        }
    }
}

    public static void eliminarCliente(Reserva reserva){

        ArrayList<Cliente> clientes = reserva.getClientes();
        ArrayList<String> nombres = new ArrayList<>();
        for(Cliente cliente: clientes){
            nombres.add(cliente.getNombre());
        }
        String nombre = Main.ingresarOpcion("Ingrese el nombre del cliente que desea eliminar", 0, nombres);
        for(Cliente cliente: clientes){
            if(cliente.getNombre().equals(nombre)){
                clientes.remove(cliente);
                break;
            }
        }
    }

    public static void modificarIdiomas(Reserva reserva){
    
        ArrayList<Idiomas> idiomas = new ArrayList<>();
        boolean salir = false;
        while(!salir){
            String idioma = Main.ingresarOpcion("Ingrese el idioma", 0, Idiomas.listaNombres());
            idiomas.add(Idiomas.valueOf(idioma));
            String entrada = Main.ingresarOpcion("Desea agregar mas idiomas", 0, new ArrayList<>(Arrays.asList("Si", "No")));
            int entradaInt = Integer.parseInt(entrada);
            if(entradaInt == 2){
                salir = true;
            }
    
        }
        reserva.setIdiomas(idiomas);
    
    }

    

    public static void modificarDestino(Reserva reserva){
        String destinoNombre = Main.ingresarString("Ingrese el nuevo destino, tenga presente que esto obligara a cambiar de hotel: ");
        Destino destino = new Destino(destinoNombre);
        reserva.setDestino(destino);
        for(Cliente cliente: reserva.getClientes()){
            cliente.setHotel(null);
        }   
        
    }

    public static boolean verificacionHospedaje(Reserva reserva){
        if (reserva.getClientes().get(0).getHotel() != null){
            return true;
        }
    
        return false;
    }

    public static boolean verificacionCondicionesHospedaje(Reserva reserva){
        ArrayList<Hotel> hoteles = Hotel.cargarHoteles();
        ArrayList<Hotel> hotelesDisponibles = Hotel.verificarDisponibilidadHotel(reserva, hoteles);

        for (Hotel hotel: hotelesDisponibles){
            if (hotel.getNombre().equals(reserva.getClientes().get(0).getHotel().getNombre())){
                return true;
            }
        }
        return false;
    }



    public static boolean verificaionPlan(Actividad actividad){

        if (actividad.getPlan() != null){
            return true;
        }
        else{
            return false;
        }

    }

    public static boolean verificarTipoPlan(Reserva reserva){
        if (reserva.getTipoPlan() == "Paquete"){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean verificarCapacidadActividad(Reserva reserva){

        


        for (String tipo: reserva.getActividades.getCapacidad){

            if(Actividad.verificarCapacidadActividad(reserva)){
                return true;
            }
        
    }
    return false;
}


}