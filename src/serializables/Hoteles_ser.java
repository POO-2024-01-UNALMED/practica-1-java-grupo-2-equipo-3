package serializables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.Destino;

public class Hoteles_ser {

    public static void main(String[] args) {

        // Hotel 1 - Medellín (Permite suscripción)
        Hotel hotel1 = new Hotel();
        hotel1.setNombre("Hotel Paraíso");
        hotel1.setDestino(new Destino("Medellin"));
        hotel1.setNumeroHabitaciones(50);
        hotel1.setPrecio(150000);
        hotel1.setPermiteSuscripcion(true);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad1 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones1 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 0, 2)),  // Sin disponibilidad
            new ArrayList<>(Arrays.asList("doble", 0, 4)),  // Sin disponibilidad
            new ArrayList<>(Arrays.asList("suite", 20, 8))  // Con disponibilidad
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad1.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones1);
        }
        hotel1.setDisponibilidadHabitaciones(disponibilidad1);
        hotel1.setGrupos(new ArrayList<>());

        // Hotel 2 - Bogotá (No permite suscripción)
        Hotel hotel2 = new Hotel();
        hotel2.setNombre("Hotel Montaña");
        hotel2.setDestino(new Destino("Bogotá"));
        hotel2.setNumeroHabitaciones(30);
        hotel2.setPrecio(100000);
        hotel2.setPermiteSuscripcion(false);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad2 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones2 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 0, 2)),  // Sin disponibilidad
            new ArrayList<>(Arrays.asList("doble", 5, 4)),
            new ArrayList<>(Arrays.asList("suite", 0, 8))  // Sin disponibilidad
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad2.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones2);
        }
        hotel2.setDisponibilidadHabitaciones(disponibilidad2);
        hotel2.setGrupos(new ArrayList<>());

        // Hotel 3 - Cartagena (Permite suscripción)
        Hotel hotel3 = new Hotel();
        hotel3.setNombre("Hotel Urbano");
        hotel3.setDestino(new Destino("Cartagena"));
        hotel3.setNumeroHabitaciones(100);
        hotel3.setPrecio(200000);
        hotel3.setPermiteSuscripcion(true);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad3 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones3 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 50, 2)),
            new ArrayList<>(Arrays.asList("doble", 30, 4)),
            new ArrayList<>(Arrays.asList("suite", 20, 8))
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad3.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones3);
        }
        hotel3.setDisponibilidadHabitaciones(disponibilidad3);
        hotel3.setGrupos(new ArrayList<>());

        // Hotel 4 - Medellín (No permite suscripción)
        Hotel hotel4 = new Hotel();
        hotel4.setNombre("Hotel Nutibara");
        hotel4.setDestino(new Destino("Medellin"));
        hotel4.setNumeroHabitaciones(70);
        hotel4.setPrecio(180000);
        hotel4.setPermiteSuscripcion(false);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad4 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones4 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 35, 2)),
            new ArrayList<>(Arrays.asList("doble", 15, 4)),
            new ArrayList<>(Arrays.asList("suite", 10, 8))
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad4.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones4);
        }
        hotel4.setDisponibilidadHabitaciones(disponibilidad4);
        hotel4.setGrupos(new ArrayList<>());

        // Hotel 5 - Bogotá (Permite suscripción)
        Hotel hotel5 = new Hotel();
        hotel5.setNombre("Hotel Central");
        hotel5.setDestino(new Destino("Bogotá"));
        hotel5.setNumeroHabitaciones(40);
        hotel5.setPrecio(120000);
        hotel5.setPermiteSuscripcion(true);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad5 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones5 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 10, 2)),
            new ArrayList<>(Arrays.asList("doble", 20, 4)),
            new ArrayList<>(Arrays.asList("suite", 5, 8))
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad5.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones5);
        }
        hotel5.setDisponibilidadHabitaciones(disponibilidad5);
        hotel5.setGrupos(new ArrayList<>());

        // Hotel 6 - Cartagena (No permite suscripción)
        Hotel hotel6 = new Hotel();
        hotel6.setNombre("Hotel del Mar");
        hotel6.setDestino(new Destino("Cartagena"));
        hotel6.setNumeroHabitaciones(150);
        hotel6.setPrecio(250000);
        hotel6.setPermiteSuscripcion(false);

        Map<ArrayList<Integer>, ArrayList<ArrayList<Object>>> disponibilidad6 = new HashMap<>();
        ArrayList<ArrayList<Object>> habitaciones6 = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList("sencilla", 80, 2)),
            new ArrayList<>(Arrays.asList("doble", 50, 4)),
            new ArrayList<>(Arrays.asList("suite", 20, 8))
        ));
        for (int day = 18; day <= 25; day++) {
            disponibilidad6.put(new ArrayList<>(Arrays.asList(day, 8, 2024)), habitaciones6);
        }
        hotel6.setDisponibilidadHabitaciones(disponibilidad6);
        hotel6.setGrupos(new ArrayList<>());

        // Lista de hoteles
        ArrayList<Hotel> listaHoteles = new ArrayList<>(Arrays.asList(hotel1, hotel2, hotel3, hotel4, hotel5, hotel6));

        // Serialización de la lista de hoteles
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/serializables/listaHoteles.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listaHoteles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
