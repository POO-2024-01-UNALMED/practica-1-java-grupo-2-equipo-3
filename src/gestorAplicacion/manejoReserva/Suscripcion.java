package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import gestorAplicacion.gestionHum.Cliente;

public class Suscripcion implements Serializable {
    private static final long serialVersionUID = 10L;
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static final ArrayList<String> LISTA_TIPOS = new ArrayList<>(Arrays.asList("Básica", "General", "Premium", "VIP"));
    private String tipo;
    private ArrayList<Integer> fechaVencimiento;       //Otra instancia de una fecha
    private int capacidad;
    private double precio;
    private int descRestauranteGratis;
    private float descTour;
    private float descHotel;


    // Analizar el temas de listaClientes
    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio, int descRestauranteGratis, float descTour, float descHotel) {
        this.tipo = tipo;
        this.fechaVencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
        this.descRestauranteGratis = descRestauranteGratis;
        this.descTour = descTour;
        this.descHotel = descHotel;
    }

    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio, Cliente titular) {
        this.tipo = tipo;
        this.fechaVencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
        listaClientes.add(titular);
        //Falta asignar la suscripción al cliente
    }

    public Suscripcion(String tipo, ArrayList<ArrayList<Integer>> fechas, Cliente titular) {
        this.tipo = tipo;
        asignarPrecio();
        asignarDescuentos();
        asignarCapacidad();
        asignarFechaVencimiento(fechas);
        listaClientes.add(titular);
        titular.setSuscripcion(this);
    }

    /**
     * Retorna la ultima fecha de reserva
     * @param fechas
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> ultimaFechaReserva(ArrayList<ArrayList<Integer>> fechas) {
        return fechas.getLast();
    }

    /**
     * Verifica si el cliente ya tiene una suscripcion
     * @param nombre
     * @param edad
     * @param listaFechas
     * @return Cliente
     */
    public static Cliente verificarSuscripcion(String nombre, int edad, ArrayList<ArrayList<Integer>> listaFechas) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getNombre().equals(nombre)) {
                if (listaClientes.get(i).getSuscripcion().verificarFechaVencimiento(Suscripcion.ultimaFechaReserva(listaFechas))) { //verifica el vencimiento de la suscripcion
                    listaClientes.get(i).setEdad(edad);
                    return listaClientes.get(i);        //retornar el objeto cliente existente en el array que tiene la suscripcion
                    
                }
                else{
                    listaClientes.remove(i);
                    return null; //retornar el objeto cliente que ingreso el usuario
                }

            }
            else{
                return null; //retornar el objeto cliente que ingreso el usuario
            }
            
        }
        return null;
    }

    /**
     * Asigna el precio de la suscripcion dependiendo del tipo de suscripcion
     */
    public void asignarPrecio() {
        //Metodo que asigna el precio de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> precio = 100000;
            case "General" -> precio = 200000;
            case "Premium" -> precio = 300000;
            case "VIP" -> precio = 400000;
        }
    }

    /**
     * Retorna el precio de la suscripcion dependiendo del tipo de suscripcion
     * @param tipo
     * @return
     */
    public static double precioPorTipo(String tipo) {
        //Metodo que retorna el precio de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> {
                return 100000;
            }
            case "General" -> {
                return 200000;
            }
            case "Premium" -> {
                return 300000;
            }
            case "VIP" -> {
                return 400000;
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Asigna los descuentos de la suscripcion dependiendo del tipo de suscripcion
     */
    public void asignarDescuentos() {
        //Metodo que asigna los descuentos de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> {
                descRestauranteGratis = 0;
                descTour = 0.15f;
                descHotel = 0.15f;
            }
            case "General" -> {
                descRestauranteGratis = 0;
                descTour = 0.2f;
                descHotel = 0.2f;
            }
            case "Premium" -> {
                descRestauranteGratis = 1;
                descTour = 0.35f;
                descHotel = 0.35f;
            }
            case "VIP" -> {
                descRestauranteGratis = 2;
                descTour = 0.5f;
                descHotel = 0.5f;
            }
        }
    }

    /**
     * Retorna los descuentos de la suscripcion dependiendo del tipo de suscripcion
     * @param tipo
     * @return
     */
    public static ArrayList<Float> descuentosPorTipo(String tipo) {
        //Metodo que retorna los descuentos de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> {
                return new ArrayList<>(Arrays.asList(0f, 0.15f, 0.15f));
            }
            case "General" -> {
                return new ArrayList<>(Arrays.asList(0f, 0.2f, 0.2f));
            }
            case "Premium" -> {
                return new ArrayList<>(Arrays.asList(1f, 0.35f, 0.35f));
            }
            case "VIP" -> {
                return new ArrayList<>(Arrays.asList(2f, 0.5f, 0.5f));
            }
            default -> {
                return new ArrayList<>(Arrays.asList(0f, 0f, 0f));
            }
        }
    }

    /**
     * Asigna la capacidad de la suscripcion dependiendo del tipo de suscripcion
     */
    public void asignarCapacidad() {
        //Metodo que asigna la capacidad de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> capacidad = 1;
            case "General" -> capacidad = 2;
            case "Premium" -> capacidad = 4;
            case "VIP" -> capacidad = 8;
        }
    }

    /**
     * Retorna la capacidad de la suscripcion dependiendo del tipo de suscripcion
     * @param tipo
     * @return
     */
    public static int capacidadPorTipo(String tipo) {
        //Metodo que retorna la capacidad de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> {
                return 1;
            }
            case "General" -> {
                return 2;
            }
            case "Premium" -> {
                return 4;
            }
            case "VIP" -> {
                return 8;
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Asigna la fecha de vencimiento de la suscripcion
     * @param fechas
     */
    public void asignarFechaVencimiento(ArrayList<ArrayList<Integer>> fechas) {
        //Metodo que asigna la fecha de vencimiento teniendo en cuenta la fecha actual
        ArrayList<Integer> ultimaFecha = ultimaFechaReserva(fechas);
        fechaVencimiento = new ArrayList<>(Arrays.asList(ultimaFecha.get(0), ultimaFecha.get(1), ultimaFecha.get(2) + 2));
    }

    /**
     * Muestra las posibles suscripciones que se pueden adquirir
     * @return ArrayList<String>
     */
    public static ArrayList<String> mostrarPosiblesSuscripciones() {
        ArrayList<String> posiblesSuscripciones = new ArrayList<>();
        for (String tipo : LISTA_TIPOS) {
            String texto = "Tipo: " + tipo +
                    "\n Precio: " + precioPorTipo(tipo) +
                    "\n Descuentos: " + descuentosPorTipo(tipo) +
                    "\n Capacidad: " + capacidadPorTipo(tipo);
            posiblesSuscripciones.add(texto);
        }
        return posiblesSuscripciones;
    }

    /**
     * Verifica si la fecha de vencimiento de la suscripcion es valida
     * @param ultimaFecha
     * @return
     */
    public boolean verificarFechaVencimiento(ArrayList<Integer> ultimaFecha) {

        //Metodo que toma una fecha de vencimiento y verifica si esta fecha es valida y vigente


        if (fechaVencimiento.get(2) < ultimaFecha.get(2)) {
            return false;
        }
        else if (fechaVencimiento.get(1) < ultimaFecha.get(1)) {
            return false;
        }
        else if (fechaVencimiento.get(0) < ultimaFecha.get(0)) {
            return false;
        }
        else{
            return true;
        }

    }




    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    public static void setListaClientes(ArrayList<Cliente> listaClientes) {
        Suscripcion.listaClientes = listaClientes;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setVencimiento(ArrayList<Integer> vencimiento) {
        this.fechaVencimiento = vencimiento;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescRestauranteGratis(int descRestauranteGratis) {
        this.descRestauranteGratis = descRestauranteGratis;
    }

    public void setDescTour(float descTour) {
        this.descTour = descTour;
    }

    public void setDescHotel(float descHotel) {
        this.descHotel = descHotel;
    }

    public static ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public static ArrayList<String> getListaTipos() {
        return LISTA_TIPOS;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Integer> getVencimiento() {
        return fechaVencimiento;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getPrecio() {
        return precio;
    }

    public float getDescRestauranteGratis() {
        return descRestauranteGratis;
    }

    public float getDescTour() {
        return descTour;
    }

    public float getDescHotel() {
        return descHotel;
    }

}
