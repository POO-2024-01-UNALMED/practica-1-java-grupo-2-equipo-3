package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import gestorAplicacion.gestionHum.Cliente;

public class Suscripcion implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private static final ArrayList<String> listaTipos = new ArrayList<>(Arrays.asList("Básica", "General", "Premium", "VIP"));
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


    public static ArrayList<Integer> ultimaFechaReserva(ArrayList<ArrayList<Integer>> fechas) {
        return fechas.getLast();
    }

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

    public static void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public static void eliminarCliente(Cliente cliente) {
        listaClientes.remove(cliente);
    }

    public static void eliminarCliente(String nombre) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equals(nombre)) {
                listaClientes.remove(cliente);
                break;
            }
        }
    }

    public void asignarPrecio() {
        //Metodo que asigna el precio de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> precio = 100000;
            case "General" -> precio = 200000;
            case "Premium" -> precio = 300000;
            case "VIP" -> precio = 400000;
        }
    }

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

    public void asignarCapacidad() {
        //Metodo que asigna la capacidad de la suscripcion dependiendo del tipo de suscripcion
        switch (tipo) {
            case "Básica" -> capacidad = 1;
            case "General" -> capacidad = 2;
            case "Premium" -> capacidad = 4;
            case "VIP" -> capacidad = 8;
        }
    }

    public void asignarFechaVencimiento(ArrayList<ArrayList<Integer>> fechas) {
        //Metodo que asigna la fecha de vencimiento teniendo en cuenta la fecha actual
        ArrayList<Integer> ultimaFecha = ultimaFechaReserva(fechas);
        fechaVencimiento = new ArrayList<>(Arrays.asList(ultimaFecha.get(0), ultimaFecha.get(1), ultimaFecha.get(2) + 2));
    }

    public ArrayList<String> mostrarPosiblesSuscripciones() {

    }









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

    public void setDescRestauranteGratis(float descRestauranteGratis) {
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
        return listaTipos;
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
