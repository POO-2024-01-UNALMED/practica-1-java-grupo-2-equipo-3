package serializables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.Destino;

public class Hoteles_ser {

    public static void main(String[] args) {
        
        Hotel hotel1 = new Hotel();
        hotel1.setNombre("Hotel Paraíso");
        hotel1.setDestino(new Destino("Medellin"));
        hotel1.setNumeroHabitaciones(50);
        hotel1.setPrecio(150000);
        
        // Crear disponibilidad de habitaciones por fecha
        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad1 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones1 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("doble", 10, 4)),
            new ArrayList<>(Arrays.asList("sencilla", 20, 2)),
            new ArrayList<>(Arrays.asList("suite", 20, 8))
        ));
        // Un for que crea disponibilidad de habitaciones del 18 al 25 de agosto de 2024
        for (int day = 18; day <= 25; day++) {
            disponibilidad1.put(new ArrayList<>(Arrays.asList(2024, 8, day)), habitaciones1);
        }
        hotel1.setDisponibilidadHabitaciones(disponibilidad1);
        hotel1.setGrupos(new ArrayList<>());

        Hotel hotel2 = new Hotel();
        hotel2.setNombre("Hotel Montaña");
        hotel2.setDestino(new Destino("Bogotá"));
        hotel2.setNumeroHabitaciones(30);
        hotel2.setPrecio(100000);
        
        
        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad2 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones2 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("doble", 5, 4)),
            new ArrayList<>(Arrays.asList("sencilla", 15, 2)),
            new ArrayList<>(Arrays.asList("suite", 10, 8))
        ));
        // Un for que crea disponibilidad de habitaciones del 18 al 25 de agosto de 2024
        for (int day = 18; day <= 25; day++) {
            disponibilidad2.put(new ArrayList<>(Arrays.asList(2024, 8, day)), habitaciones2);
        }
        hotel2.setDisponibilidadHabitaciones(disponibilidad2);
        hotel2.setGrupos(new ArrayList<>());

        Hotel hotel3 = new Hotel();
        hotel3.setNombre("Hotel Urbano");
        hotel3.setDestino(new Destino("Cartagena"));
        hotel3.setNumeroHabitaciones(100);
        hotel3.setPrecio(200000);
        
        // Crear disponibilidad de habitaciones por fecha
        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad3 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones3 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("doble", 30, 4)),
            new ArrayList<>(Arrays.asList("sencilla", 50, 2)),
            new ArrayList<>(Arrays.asList("suite", 20, 8))
        ));
        // Un for que crea disponibilidad de habitaciones del 18 al 25 de agosto de 2024
        for (int day = 18; day <= 25; day++) {
            disponibilidad3.put(new ArrayList<>(Arrays.asList(2024, 8, day)), habitaciones3);
        }
        hotel3.setDisponibilidadHabitaciones(disponibilidad3);
        hotel3.setGrupos(new ArrayList<>());

        ArrayList<Hotel> listaHoteles = new ArrayList<>(Arrays.asList(hotel1, hotel2, hotel3));

        try (FileOutputStream fileOutputStream = new FileOutputStream("serializables/listaHoteles.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listaHoteles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}